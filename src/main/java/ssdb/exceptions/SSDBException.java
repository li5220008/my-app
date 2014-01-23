package ssdb.exceptions;

/**
 * Desc:
 * User: weiguili(li5220008@gmail.com)
 * Date: 14-1-21
 * Time: 下午2:13
 */
public class SSDBException extends RuntimeException{
    private static final long serialVersionUID = -2946266495682282677L;

    public SSDBException(String message) {
        super(message);
    }

    public SSDBException(Throwable e) {
        super(e);
    }

    public SSDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
