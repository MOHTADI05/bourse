package tn.esprit.mfb.exeption;

public class ImmobilierNotFoundException extends RuntimeException {
    public ImmobilierNotFoundException(String message) {
        super(message);
    }
}