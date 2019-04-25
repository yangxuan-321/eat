package com.eat.vo;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 1:47 2018/6/1
 * @Modifyed By :
 */
public class ResultUtil {

    private static final Integer OK = 0;

    private static final Integer ERR = 500;

    public static final <T> ResultVO success(T data){
       return buildResult(OK, "成功", data);
    }

    public static final <T> ResultVO success(String message, T data){
        return buildResult(OK, message, data);
    }

    public static final <T> ResultVO commonError(T data){
        return buildResult(ERR, "普通错误", data);
    }


    public static final <T> ResultVO buildResult(Integer code, String message, T data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        resultVO.setData(data);
        return resultVO;
    }
}
