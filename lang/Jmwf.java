import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Jmwf {

    public enum TokenType {
        NUMBER,
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        LEFT_PAREN,
        RIGHT_PAREN,
        BAD_TOKEN,
        VARIABLE
    }

    public static void main(String[] args) {
        ArrayList<String> numberTokens = new ArrayList<>();
        ArrayList<String> operatorTokens = new ArrayList<>();
        ArrayList<String> variableTokens = new ArrayList<>();

        String expression = "C:\\Users\\John Marc\\Documents\\lang_jmwf\\lang\\test.jmwf";
        System.out.println("Expression: " + expression);

        try (BufferedReader reader = new BufferedReader(new FileReader(expression))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                processLine(line, numberTokens, operatorTokens, variableTokens, lineNumber);
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print the tokens stored in the ArrayLists
        System.out.println("Number Tokens: " + numberTokens);
        System.out.println("Operator Tokens: " + operatorTokens);
        System.out.println("Variable Tokens: " + variableTokens);
    }

    public static void processLine(String line, ArrayList<String> numberTokens, ArrayList<String> operatorTokens,
            ArrayList<String> variableTokens, int lineNumber) {
        // Split the line based on spaces
        String[] tokens = line.split("\\s+");

        for (String token : tokens) {
            token = token.trim();
            TokenType tokenType;

            // Check if the token is a digit
            if (isDigit(token)) {
                tokenType = TokenType.NUMBER;
                numberTokens.add(token); // Add the number token to the ArrayList
            } else {
                // If not a digit, check for other token types
                switch (token) {
                    case "+":
                        tokenType = TokenType.PLUS;
                        operatorTokens.add(token); // Add the operator token to the ArrayList
                        break;
                    case "-":
                        tokenType = TokenType.MINUS;
                        operatorTokens.add(token); // Add the operator token to the ArrayList
                        break;
                    case "*":
                        tokenType = TokenType.MULTIPLY;
                        operatorTokens.add(token); // Add the operator token to the ArrayList
                        break;
                    case "/":
                        tokenType = TokenType.DIVIDE;
                        operatorTokens.add(token); // Add the operator token to the ArrayList
                        break;
                    case "(":
                        tokenType = TokenType.LEFT_PAREN;
                        operatorTokens.add(token); // Add the operator token to the ArrayList
                        break;
                    case ")":
                        tokenType = TokenType.RIGHT_PAREN;
                        operatorTokens.add(token); // Add the operator token to the ArrayList
                        break;
                    default:
                        // If not a number or operator, check if it's a valid variable
                        if (isValidVariable(token)) {
                            tokenType = TokenType.VARIABLE;
                            variableTokens.add(token); // Add the variable token to the ArrayList
                        } else {
                            // Otherwise, consider it as a bad token
                            tokenType = TokenType.BAD_TOKEN;
                            // Raise an error for bad token
                            try {
                                String errorMessage = BadTokenException.createErrorMessage(token, lineNumber);
                                throw new BadTokenException(errorMessage);
                            } catch (Exception e) {
                                System.out.println(e);
                            }

                        }
                }
            }

            // // Print the type of token
            // System.out.println("Token: " + token + ", Type: " + tokenType);
        }
    }

    public static boolean isDigit(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidVariable(String token) {
        // A valid variable name must start with a letter or underscore
        // and can contain letters, digits, or underscores thereafter.
        // It cannot start with a digit.
        if (token.isEmpty() || !Character.isLetter(token.charAt(0)) && token.charAt(0) != '_') {
            return false;
        }
        for (int i = 1; i < token.length(); i++) {
            char c = token.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }
        return true;
    }
}
