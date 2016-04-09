package pl.edu.agh.operationsresearch.utils.view;

public class InvalidTextFieldException extends Exception {
	private ValidatedTextField field;
	
	public InvalidTextFieldException(ValidatedTextField field){
		this.field = field;
        field.setStyle("-fx-background-color: red;");
	}
	
	public ValidatedTextField getField(){
		return field;
	}
}
