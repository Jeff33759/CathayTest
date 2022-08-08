package cathaybk.exception;

/**
 * 當從coindeskApi取資料時，將錯誤重新包裝成此例外，方便後續處理。
 * 
 * {@link MyUtil #getDataFromCoindeskApiAndConvertToObj}
 * */
public class CyApiException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CyApiException(String message) {
		super(message);
	}
	
	public CyApiException(String message,Throwable cause) {
		super(message,cause);
	}

}
