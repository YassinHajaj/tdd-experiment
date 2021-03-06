package be.yassinhajaj.withouttdd;

public class SuitNullException extends RuntimeException {

    private static final String SUIT_CANT_BE_NULL_EXCEPTION_MESSAGE = "Suit can't be null for card";

    public SuitNullException() {
        super(SUIT_CANT_BE_NULL_EXCEPTION_MESSAGE);
    }

}
