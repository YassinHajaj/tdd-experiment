package be.yassinhajaj.withouttdd.exception;

public class HandCardsCountIncorrectException extends RuntimeException {

    private static final String INCORRECT_CARDS_COUNT_EXCEPTION_MESSAGE = "Incorrect cards count: %s. Should be %s.";

    public HandCardsCountIncorrectException(int cardCount, int expectedCount) {
        super(String.format(INCORRECT_CARDS_COUNT_EXCEPTION_MESSAGE, cardCount, expectedCount));
    }

}
