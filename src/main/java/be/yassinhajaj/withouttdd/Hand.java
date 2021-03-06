package be.yassinhajaj.withouttdd;

import java.util.Set;
import java.util.TreeSet;

public class Hand {

    public static final int EXPECTED_CARDS_COUNT = 5;
    private final Set<Card> cards;

    private Hand(Set<Card> cards) {
        this.cards = new TreeSet<>(cards);
    }

    public static Result<Hand> newInstance(Set<Card> cards) {
        if (cards == null) {
            return Result.error(new HandCardsNullException());
        }
        for (Card card : cards) {
            if (card == null) {
                return Result.error(new HandCardNullException());
            }
        }
        int cardsCount = cards.size();
        if (cardsCount != EXPECTED_CARDS_COUNT) {
            return Result.error(new HandCardsCountIncorrectException(cardsCount, EXPECTED_CARDS_COUNT));
        }
        return Result.ok(new Hand(cards));
    }

    public HandRanking getRanking() {
        boolean isFlush = true;
        boolean isStraight = true;
        boolean highestCardAce = false;
        int[] valueCountTracker = new int[12];

        Card previous = null;

        for (Card card : cards) {
            valueCountTracker[card.getValuesRanking()]++;
            if (!highestCardAce && card.isAce()) {
                highestCardAce = true;
            }
            if (previous == null) {
                previous = card;
            } else {
                isFlush &= card.hasSameSuitThan(previous);
                isStraight &= previous.isJustBefore(card);
                previous = card;
            }
        }

        if (isFlush && isStraight && highestCardAce) {
            return HandRanking.ROYAL_FLUSH;
        }
        if (isFlush && isStraight) {
            return HandRanking.STRAIGHT_FLUSH;
        }
        if (isFlush) {
            return HandRanking.FLUSH;
        }
        if (isStraight) {
            return HandRanking.STRAIGHT;
        }

        boolean isPair = false;
        boolean isTwoPair = false;
        boolean isThreeOfAKind = false;
        boolean isFourOfAKind = false;

        TRACKER_LOOP:
        for (int i : valueCountTracker) {
            switch (i) {
                case 2: {
                    if (isPair) {
                        isTwoPair = true;
                        break TRACKER_LOOP;
                    } else {
                        isPair = true;
                    }
                }
                break;
                case 3: {
                    isThreeOfAKind = true;
                }
                break;
                case 4: {
                    isFourOfAKind = true;
                    break TRACKER_LOOP;
                }
                default: {
                    // empty default - does not do anything
                }
            }
        }

        if (isFourOfAKind) {
            return HandRanking.FOUR_OF_A_KIND;
        }
        if (isPair && isThreeOfAKind) {
            return HandRanking.FULL_HOUSE;
        }
        if (isThreeOfAKind) {
            return HandRanking.THREE_OF_A_KIND;
        }
        if (isTwoPair) {
            return HandRanking.TWO_PAIR;
        }
        if (isPair) {
            return HandRanking.PAIR;
        }
        return HandRanking.HIGH_CARD;
    }

    public enum HandRanking {
        HIGH_CARD,
        PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH
    }
}
