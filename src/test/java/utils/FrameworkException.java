package utils;

public class FrameworkException extends RuntimeException{
	
	
	public String errorName = "Error";
	
	public FrameworkException(String errorDescription)  {
		super(errorDescription);
		//throw new RuntimeException();
	}

	
	public FrameworkException(String errorName, String errorDescription) {
		super(errorDescription);
		this.errorName=errorName;
		//throw new RuntimeException();
	}
	
	public String getErrorName() {
		return errorName;
	}
}
