//  Dvir Berlowitz

/**
 * The {@code SumOfInteger} class provides functionality to calculate the sum of the digits of an integer.
 */
public class SumOfInteger {
    private static final String INPUT_INVALID_ERROR = "Invalid input";

    /**
     * The main method takes an integer as input argument
     * and prints the sum of its digits.
     * If the argument is invalid, it prints an error message.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.out.println(INPUT_INVALID_ERROR);
                return;
            }
            int num = Integer.parseInt(args[0]);
            System.out.println(sumDigits(Math.abs(num)));
        } catch (NumberFormatException e) {
            System.out.println(INPUT_INVALID_ERROR);
            return;
        }
    }

    /**
     * Calculates the sum of the digits of the given integer, recursively.
     *
     * @param num The integer to sum its digits.
     * @return The sum of the digits of the given integer.
     */
    private static int sumDigits(int num) {
        if (num == 0) {
            return 0;
        }
        return (num % 10) + sumDigits(num / 10);
    }
}