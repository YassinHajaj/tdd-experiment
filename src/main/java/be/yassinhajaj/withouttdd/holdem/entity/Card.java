package be.yassinhajaj.withouttdd.holdem.entity;

import be.yassinhajaj.withouttdd.Result;
import be.yassinhajaj.withouttdd.holdem.exception.SuitNullException;
import be.yassinhajaj.withouttdd.holdem.exception.ValueNullException;

import java.util.Objects;

public class Card implements Comparable<Card> {

    private final Value value;
    private final Suit suit;

    private Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public static Result<Card> newInstance(Value value, Suit suit) {
        if (value == null) {
            return Result.error(new ValueNullException());
        }
        if (suit == null) {
            return Result.error(new SuitNullException());
        }
        return Result.ok(new Card(value, suit));
    }

    public boolean isJustBefore(Card other) {
        validateNonNull(other);
        return this.value.ranking + 1 == other.value.ranking;
    }

    public boolean hasSameSuitThan(Card other) {
        validateNonNull(other);
        return this.suit == other.suit;
    }

    private void validateNonNull(Card card) {
        if (card == null) {
            throw new NullPointerException("Card can't be compared to <null>");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }

    @Override
    public String toString() {
        return "Card[" + value + " of " + suit + "]";
    }

    @Override
    public int compareTo(Card other) {
        validateNonNull(other);
        int comparisonResult = Objects.compare(this.value.ranking, other.value.ranking, Integer::compare);
        if (comparisonResult == 0) {
            return Objects.compare(this.suit.ordinal(), other.suit.ordinal(), Integer::compare);
        }
        return comparisonResult;
    }

    public int getValuesRanking() {
        return this.value.ranking;
    }

    public boolean isAce() {
        return this.value == Value.ACE;
    }

    public enum Suit {
        HEART,
        DIAMOND,
        CLUB,
        SPADE;
    }

    public enum Value {
        DEUCE(1),
        THREE(2),
        FOUR(3),
        FIVE(4),
        SIX(5),
        SEVEN(6),
        EIGHT(7),
        NINE(8),
        TEN(9),
        JACK(10),
        QUEEN(11),
        KING(12),
        ACE(13);

        private final int ranking;

        Value(int ranking) {
            this.ranking = ranking;
        }
    }
}
