package be.yassinhajaj.withouttdd;

public class HandCardsNullException extends RuntimeException {

    private static final String HAND_CARDS_NULL_EXCEPTION_MESSAGE = "Hand cards can't be <null>";

    public HandCardsNullException() {
        super(HAND_CARDS_NULL_EXCEPTION_MESSAGE);
    }

}
