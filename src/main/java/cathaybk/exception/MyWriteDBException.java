package cathaybk.exception;


public class MyWriteDBException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public MyWriteDBException(String message) {
		super(message);
	}

	public MyWriteDBException(String message, Throwable cause) {
		super(message, cause);
	}

}
