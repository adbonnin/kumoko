package fr.adbonnin.kumoko.io;

public class NonExistentFileLimitExceededException extends RuntimeException {

    public NonExistentFileLimitExceededException(String message) {
        super(message);
    }
}
