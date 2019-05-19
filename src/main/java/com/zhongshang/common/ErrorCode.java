package com.zhongshang.common;

import java.text.MessageFormat;


public enum ErrorCode {

    /**
     * 1：系统错误
     */

    SYS_RUN_EXCEPTION("10000", "{0}运行异常."),
    SYS_RUNTIME_ERROR("10001", "系统运行出错，{0}."),
    SYS_INITIAL_ERROR("10002", "系统初始化出错，{0}."),
    SYS_RETRY_ERROR("10003", "系统重试."),

    /**
     * 2：通用错误
     */
    COMMON_MY_ERR("20000", "{0}"),
    COMMON_ILLEGAL_ARG_ERR("20001", "参数不合法，{0}"),
    COMMON_ILLEGAL_OPERATION_ERR("20002", "非法操作，{0}."),
    COMMON_REPOSITORY_ERR("20003", "持久层操作异常，{0}"),
    COMMON_NOT_LOGIN("20004", "用户未登录"),
    COMMON_HSF_CONNECTION_ERR("20005", "HSF调用异常，{0}"),
    COMMON_BATCH_SUBMISSION_ERR("20006", "Batch提交异常，{0}"),
    COMMON_BATCH_EXEC_ERR("20007", "Batch执行异常，{0}"),
    COMMON_ONS_SEND_ERR("20008", "ONS消息发送异常，{0}"),
    COMMON_INITIAL_ERR("20009", "初始化出错，{0}"),
    COMMON_RESOURCE_NO_AUTH("20010", "无权限访问"),
    COMMON_ILLEGAL_STATUS("20011", "非法状态:{0}."),
    COMMON_CREATE_ERR("20012", "创建失败,{0}"),
    COMMON_UPDATE_ERR("20013", "修改失败,{0}"),
    COMMON_DELETE_ERR("20014", "删除失败,{0}"),
    COMMON_QUERY_ERR("20015", "查询失败,{0}"),
    COMMON_NOT_EMPTY_ERR("20016", "{0}信息不能为空"),
    COMMON_REQUEST_INVALID("20018", "请求无效：{0}"),
    COMMON_LOCK_GET_FAIL("20019", "无法获取锁"),
    COMMON_VERIFY_SING_FAIL("20020", "验签失败"),
    COMMON_UPLOAD_ERR("20021", "文件上传失败"),

    //注册
    REGISTER_ERROR("30001", "注册失败"),
    REGISTER_EMAIL_EXIST_ERROR("30002", "邮箱已经存在"),
    REGISTER_CODE_NOT_EXIST_ERROR("30003", "激活码不存在"),
    REGISTER_ACTIVATE_ERROR("30004", "激活失败,{0}"),
    LOGIN_NAME_OR_PWD_ERROR("30005", "用户名或密码错误"),
    LOGIN_SYS_ERROR("30006", "登录失败"),
    UPDATE_CUSTOMER_ERROR("30007", "更新失败"),
    ;

    private String code;
    private String pattern;

    private ErrorCode(String code, String pattern) {
        this.code = code;
        this.pattern = pattern;
    }

    public String getCode() {
        return code;
    }

    public String getErrorMsg(Object... params) {
        String errorMsg = null;
        if ((params == null) || (params.length == 0)) {
            errorMsg = pattern;
        } else {
            MessageFormat msgFmt = new MessageFormat(pattern);
            errorMsg = msgFmt.format(params);
        }
        return errorMsg;
    }

}
