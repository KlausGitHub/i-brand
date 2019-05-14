package com.zhongshang.utils;

/**
 * @author yangsheng
 * @date 2019-05-14
 */
public class RegexUtils {


    public static boolean phoneRegex(String phone) {
        String regex = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
        return phone.matches(regex);
    }

    public static boolean emailRegex(String email) {
        String regex = "^[a-z0-9A-Z]+[-|a-z0-9A-Z._]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
        return email.matches(regex);
    }

    public static void main(String[] args) {
        String email = "9899908@qq.com";
        System.out.println(emailRegex(email));
        String phone = "14799998888";
        System.out.println(phoneRegex(phone));
    }
}
