package com.zhongshang.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {
    @Value("${mail.account}")
    private String from;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.smtp.host}")
    private String smtpHost;

    @Value("${mail.smtp.port}")
    private String smtpPort;

    @Value("${mail.smtp.auth}")
    private String smtpAuth;

    private Session mailSession;

    @PostConstruct
    private void init() {
        Properties p = new Properties();
        p.put("mail.smtp.host", smtpHost);
        p.put("mail.smtp.port", smtpPort);
        p.put("mail.smtp.auth", smtpAuth);

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        mailSession = Session.getDefaultInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
    }

    private void attachMail(Multipart multipart, String filePath, String fileName) throws Exception {
        // 附加文件到邮件
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(filePath);
        mimeBodyPart.setDataHandler(new DataHandler(fds));
//		mimeBodyPart.setFileName(fds.getName());
        BASE64Encoder enc = new BASE64Encoder();
        //处理邮件附件名称乱码的问题
//		mimeBodyPart.setFileName("=?GBK?B?"+enc.encode(fileName.getBytes())+"?=");
//		mimeBodyPart.setFileName("=?UTF-8?B?"+enc.encode(fileName.getBytes())+"?=");
        mimeBodyPart.setFileName(MimeUtility.encodeText(fileName));
        mimeBodyPart.setHeader("Content-Transfer-Encoding", "base64");
        multipart.addBodyPart(mimeBodyPart);
    }

    public void send(String address, String cc, String subject, String content, List attachments) {
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(mailSession);
            // 设置邮件消息的发送者
            mailMessage.setFrom(new InternetAddress(from));
            // 创建邮件的接收者地址，并设置到邮件消息中
            InternetAddress[] to = InternetAddress.parse(address);

            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipients(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(subject);
            //设置抄送，如果有
            if (StringUtils.isNotBlank(cc)) {
                InternetAddress[] ccArr = InternetAddress.parse(cc);
                mailMessage.setRecipients(Message.RecipientType.CC, ccArr);
            }
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(content, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            if (attachments != null) {
                for (int i = 0; i < attachments.size(); i++) {
                    try {
                        String[] s = (String[]) attachments.get(i);
                        attachMail(mainPart, s[0], s[1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
        } catch (MessagingException ex) {
            throw new RuntimeException("邮件发送失败", ex);
        }
    }
}
