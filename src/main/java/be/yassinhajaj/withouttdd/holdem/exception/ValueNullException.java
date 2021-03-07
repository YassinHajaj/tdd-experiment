package be.yassinhajaj.withouttdd.holdem.exception;

public class ValueNullException extends RuntimeException {

    private static final String VALUE_CANT_BE_NULL_EXCEPTION_MESSAGE = "Value can't be null for card";

    public ValueNullException() {
        super(VALUE_CANT_BE_NULL_EXCEPTION_MESSAGE);
    }
}
