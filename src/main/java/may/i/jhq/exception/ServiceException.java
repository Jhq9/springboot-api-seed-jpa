package may.i.jhq.exception;

/**
 * @author jinhuaquan
 * @create 2018-01-18 下午3:45
 * @desc The customer runtime exception of service
 **/
public class ServiceException extends RuntimeException{

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
