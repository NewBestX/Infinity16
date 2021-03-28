package pizzashop.validator;

public class ValidationException extends Exception {
    private String message;

    public ValidationException(String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
