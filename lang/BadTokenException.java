public class BadTokenException extends Exception {
    // Constructor that takes a custom message
    public BadTokenException(String message) {
        super(message);
    }

    // Constructor that takes a custom message and a cause
    public BadTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        // ANSI escape codes for red color
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        // Get the original exception message
        String originalMessage = super.getMessage();

        // Construct the custom exception message with color
        String errorMessage = String.format("%sError: %s%s", ANSI_RED, originalMessage, ANSI_RESET);

        return errorMessage;
    }

    // Method to create error message with token and line number
    public static String createErrorMessage(String token, int lineNumber) {
        return String.format("Invalid token '%s' encountered at line %d.", token, lineNumber);
    }
}
