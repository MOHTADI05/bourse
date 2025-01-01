package tn.esprit.mfb.exeption;

public class ComplaintServiceException extends RuntimeException {

    public ComplaintServiceException(String message) {
        super(message);
    }

    public ComplaintServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
