import java.util.ArrayList;
import java.util.List;

public record Card(Suit suit, String face, int rank) {

    /**
     * Represents the suits in a standard deck of cards: CLUB, DIAMOND, HEART, SPADE.
     * Provides a method to retrieve the ASCII character representation for each suit.
     */
    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE;

        /**
         * Gets the ASCII character representation for each suit.
         * <p>
         * This method returns the ASCII character corresponding to each suit.
         * The suits are represented in the following order: CLUBS, DIAMONDS, HEARTS, SPADES.
         * The ordinal value of the current suit is used to fetch the corresponding ASCII character.
         * ASCII characters for suits:
         * - CLUBS: ♣ (U+2663)
         * - DIAMONDS: ♦ (U+2666)
         * - HEARTS: ♥ (U+2665)
         * - SPADES: ♠ (U+2660)
         *
         * @return The ASCII character representing the current suit.
         */
        public char getImage() {
            // Unicode values for the ASCII characters of each suit
            char[] suitCharacters = {'\u2663', '\u2666', '\u2665', '\u2660'};

            // Fetch the ASCII character based on the ordinal value of the current suit
            return suitCharacters[this.ordinal()];
        }

    }

    /**
     * Returns a string representation of the Card.
     * <p>
     * This method generates and returns a formatted string representing the Card object.
     * The format includes the face of the card, the ASCII character representation of the suit,
     * and the rank of the card.
     *
     * @return A formatted string representing the Card in the format "{face}{suit}{rank}".
     * For example, "10♣(2)" or "K♥(12)".
     */
    @Override
    public String toString() {
        // Determine the index to substring the face for special handling of "10"
        int index = face.equals("10") ? 2 : 1;

        // Extract the substring for the face based on the determined index
        String faceString = face.substring(0, index);

        // Format and return the string representation of the Card
        return "%s%c(%d)".formatted(faceString, suit.getImage(), rank);
    }

    /**
     * Creates and returns a numeric card with the specified suit and card number.
     * <p>
     * This method takes a Suit and an integer card number and generates a Card object
     * representing the numeric card. The card number should be in the range of 2 to 10
     * (inclusive), and the method returns a Card object with the specified suit, card number,
     * and rank ranging from 0 to 8.
     *
     * @param suit       The Suit of the numeric card (CLUB, DIAMOND, HEART, SPADE).
     * @param cardNumber The numeric value of the card (2 to 10).
     * @return A Card object representing the numeric card with the specified suit, card number,
     * and rank. Returns null and prints an error message if an invalid card number is provided.
     */
    public static Card getNumericCard(Suit suit, int cardNumber) {
        // Check if the provided card number is within the valid range (2 to 10)
        if (cardNumber > 1 && cardNumber < 11) {
            // Return a Card object with the specified suit, card number, and rank
            return new Card(suit, String.valueOf(cardNumber), cardNumber - 2);
        }

        // Print an error message for invalid numeric card selection
        System.out.println("Invalid Numeric card selected");
        return null;
    }

    /**
     * Creates and returns a face card (Jack, Queen, King, Ace) with the specified suit.
     * <p>
     * This method takes a Suit and a character abbreviation ('J', 'Q', 'K', 'A') to
     * generate the corresponding face card. The abbreviation determines the face card type,
     * and the method returns a Card object representing the face card with the specified suit
     * and rank ranging from 11 to 14.
     *
     * @param suit   The Suit of the face card (CLUB, DIAMOND, HEART, SPADE).
     * @param abbrev The character abbreviation representing the face card ('J', 'Q', 'K', 'A').
     * @return A Card object representing the face card with the specified suit and rank.
     * Returns null and prints an error message if an invalid face card abbreviation is provided.
     */
    public static Card getFaceCard(Suit suit, char abbrev) {
        // Check if the provided abbreviation corresponds to a valid face card
        int charIndex = "JQKA".indexOf(abbrev);
        if (charIndex > -1) {
            // Return a Card object with the specified suit, abbreviation, and rank
            return new Card(suit, "" + abbrev, charIndex + 9);
        }

        // Print an error message for invalid face card selection
        System.out.println("Invalid Face card selected");
        return null;
    }


    /**
     * Generates and returns a standard deck of 52 playing cards.
     * <p>
     * The deck consists of 4 suits (CLUB, DIAMOND, HEART, SPADE) and includes cards
     * ranging from 2 to 10 with ranks from 0 to 8, and face cards (J, Q, K, A) with
     * ranks from 9 to 12 in each suit.
     * <p/>
     *
     * @return A List of Card objects representing a standard deck of 52 cards.
     */
    public static List<Card> getStandardDeck() {
        // Initialize an ArrayList to hold the cards in the deck
        List<Card> deck = new ArrayList<>(52);

        // Iterate through each suit in the Suit enum
        for (Suit suit : Suit.values()) {
            // Generate numeric cards (2-10) and add them to the deck
            for (int i = 2; i <= 10; i++) {
                deck.add(getNumericCard(suit, i));
            }

            // Generate face cards (J, Q, K, A) and add them to the deck
            for (char c : new char[]{'J', 'Q', 'K', 'A'}) {
                deck.add(getFaceCard(suit, c));
            }
        }
        return deck;
    }

    /**
     * Prints the contents of a deck of cards with a default description and layout.
     * <p>
     * This overloaded method serves as a convenience for printing the contents of the provided deck
     * of cards with a default description set to "Current Deck" and a default layout configured for 4 rows.
     *
     * @param deck The List of Card objects representing the deck to be printed.
     */
    public static void printDeck(List<Card> deck) {
        printDeck(deck, "Current Deck", 4);
    }

    /**
     * Prints the contents of a deck of cards with a custom description and layout.
     * <p>
     * This method prints the contents of the provided deck of cards in rows, with each row
     * containing an equal number of cards. It allows customization of the description and the
     * number of rows in which the cards are displayed. The purpose of this method is to provide
     * flexibility in visualizing the cards in the deck.
     *
     * @param deck        The List of Card objects representing the deck to be printed.
     * @param description The custom description to be printed above the deck.
     * @param rows        The number of rows in which the cards should be displayed.
     */
    public static void printDeck(List<Card> deck, String description, int rows) {
        System.out.println("------------------");
        if (description != null) {
            System.out.println(description);
        }
        // Calculate the number of cards in each row
        int cardsInRow = deck.size() / rows;

        for (int i = 0; i < rows; i++) {

            // Calculate the start and end indices for the current row of cards in the deck
            int startIndex = i * cardsInRow;
            int endIndex = startIndex + cardsInRow;
            deck.subList(startIndex, endIndex).forEach(c -> System.out.print(c + " "));
            System.out.println();
        }
    }


}
