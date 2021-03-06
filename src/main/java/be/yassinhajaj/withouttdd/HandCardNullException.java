package be.yassinhajaj.withouttdd;

public class HandCardNullException extends RuntimeException {

    private static final String HAND_CARD_NULL_EXCEPTION_MESSAGE = "Hand card can't be <null>";

    public HandCardNullException() {
        super(HAND_CARD_NULL_EXCEPTION_MESSAGE);
    }

}
