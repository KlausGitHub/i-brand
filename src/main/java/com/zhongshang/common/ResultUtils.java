package com.zhongshang.common;

import java.util.HashMap;
import java.util.Map;

public class ResultUtils {

    public ResultUtils() {
    }


    public static void setErrorInfo(BaseResult<?> br, ErrorCode errCode, Object... args) {
        br.setCode(errCode.getCode());
        br.setMessage(errCode.getErrorMsg(args));
    }

    public static void setErrorInfo(BaseResult<?> br, BaseResult<?> fbr) {
        br.setCode(fbr.getCode());
        br.setMessage(fbr.getMessage());
    }

    public static <T> void setSuccess(BaseResult<T> br, T t) {
        br.setResult(t);
        br.setCode("0");
    }

    public static void setMapSuccess(BaseResult<Map<String, Object>> br, Object t) {
        Map<String, Object> map = new HashMap();
        map.put("value", t);
        setSuccess(br, map);
    }

    public static <T> BaseResult<T> success(T v) {
        BaseResult<T> result = new BaseResult();
        result.setResult(v);
        result.setCodeSuccess();
        return result;
    }

    public static <T> BaseResult<Map<String, Object>> successMap(T v) {
        Map<String, Object> map = new HashMap();
        map.put("value", v);
        return success(map);
    }

    public static <T> BaseResult<T> fail(ErrorCode code, T t, Object... objects) {
        BaseResult<T> result = new BaseResult();
        result.setCode(code.getCode());
        result.setResult(t);
        result.setMessage(code.getErrorMsg(objects));
        return result;
    }
}
