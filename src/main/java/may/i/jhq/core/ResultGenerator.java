package may.i.jhq.core;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午4:16
 * @desc The response result generator
 **/
public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                   .setCode(ResultCodeEnum.SUCCESS)
                   .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                   .setCode(ResultCodeEnum.SUCCESS)
                   .setMessage(DEFAULT_SUCCESS_MESSAGE)
                   .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                   .setCode(ResultCodeEnum.FAIL)
                   .setMessage(message);
    }

    public static Result genFailResult(ResultCodeEnum resultCode, String message) {
        return new Result()
                   .setCode(resultCode.code)
                   .setMessage(message);
    }

}
