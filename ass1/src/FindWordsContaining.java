//  Dvir Berlowitz

/**
 * The {@code FindWordsContaining} class provides functionality to find and print words containing a specific character.
 */
public class FindWordsContaining {
    private static final String INPUT_INVALID_ERROR = "Invalid input";

    /**
     * The main method takes an array of words and a character as input arguments
     * and prints words containing the character, in the order they appear in the array.
     * If the arguments are invalid, it prints an error message.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(INPUT_INVALID_ERROR);
            return;
        }
        String last = args[args.length - 1];
        if (last.length() != 1) {
            System.out.println(INPUT_INVALID_ERROR);
            return;
        }

        char c = last.charAt(0);
        String[] words = new String[args.length - 1];
        for (int i = 0; i < words.length; i++) {
            words[i] = args[i];
        }
        printWordsContaining(words, c);
    }

    /**
     * Prints words from the given array that contain the specified character.
     *
     * @param words The array of words.
     * @param c     The character to search for in the array.
     */
    private static void printWordsContaining(String[] words, char c) {
        for (String word : words) {
            for (int j = 0; j < word.length(); j++) {
                if (word.charAt(j) == c) {
                    System.out.println(word);
                    break;
                }
            }
        }
    }
}