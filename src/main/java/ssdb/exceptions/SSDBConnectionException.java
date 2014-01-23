package ssdb.exceptions;

/**
 * Desc:
 * User: weiguili(li5220008@gmail.com)
 * Date: 14-1-21
 * Time: 下午2:17
 */
public class SSDBConnectionException extends SSDBException {
    private static final long serialVersionUID = 3878126572474819403L;

    public SSDBConnectionException(String message) {
        super(message);
    }

    public SSDBConnectionException(Throwable cause) {
        super(cause);
    }

    public SSDBConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
