//  Dvir Berlowitz

/**
 * The {@code CountPairs} class provides functionality to count pairs in an array whose sum is less than a target value.
 */
public class CountPairs {
    private static final String INPUT_INVALID_ERROR = "Invalid input";

    /**
     * The main method takes an array of integers and a target value as input arguments
     * and prints the count of pairs whose sum is less than the target.
     * If the arguments are invalid, it prints an error message.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(INPUT_INVALID_ERROR);
            return;
        }

        int target = Integer.parseInt(args[args.length - 1]);
        int[] arr = new int[args.length - 1];
        try {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(args[i]);
            }
        } catch (NumberFormatException e) {
            System.out.println(INPUT_INVALID_ERROR);
            return;
        }
        System.out.println(countPairs(arr, target));
    }

    /**
     * Counts the number of pairs in the given array whose sum is less than the target value.
     *
     * @param arr    The array of integers.
     * @param target The target value to compare the sum against.
     * @return The count of pairs whose sum is less than the target value.
     */
    private static int countPairs(int[] arr, int target) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] < target) {
                    count++;
                }
            }
        }
        return count;
    }
}
