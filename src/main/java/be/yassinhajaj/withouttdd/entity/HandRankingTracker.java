package be.yassinhajaj.withouttdd.entity;

import java.util.Set;

public class HandRankingTracker {
    boolean foundFlush = true;
    boolean foundStraight = true;
    boolean recordedAceAsHighCard = false;
    int[] valueCountTracker = new int[13];
    boolean foundPair = false;
    boolean foundTwoPair = false;
    boolean foundThreeOfAKind = false;
    boolean foundFourOfAKind = false;

    public void scan(Set<Card> cards) {
        Card previous = null;

        for (Card card : cards) {
            valueCountTracker[card.getValuesRanking() - 1]++;
            if (!recordedAceAsHighCard && card.isAce()) {
                this.recordedAceAsHighCard = true;
            }
            if (previous != null) {
                this.foundFlush &= card.hasSameSuitThan(previous);
                this.foundStraight &= previous.isJustBefore(card);
            }
            previous = card;
        }
    }

    public HandRanking getTrackingResult() {
        HandRanking straightOrFlushDecision = getStraightOrFlushTrackingResult();
        if (straightOrFlushDecision != null) {
            return straightOrFlushDecision;
        }
        return getSameCardsTrackingResult();
    }

    private boolean foundStraightFlush() {
        return foundFlush && foundStraight;
    }

    private boolean foundFullHouse() {
        return foundPair && foundThreeOfAKind;
    }

    private HandRanking getStraightOrFlushTrackingResult() {
        if (foundStraightFlush()) {
            return recordedAceAsHighCard ? HandRanking.ROYAL_FLUSH : HandRanking.STRAIGHT_FLUSH;
        }

        if (foundFlush) {
            return HandRanking.FLUSH;
        }

        if (foundStraight) {
            return HandRanking.STRAIGHT;
        }

        return null;
    }

    private HandRanking getSameCardsTrackingResult() {
        applySameCardsRules();

        if (foundFourOfAKind) {
            return HandRanking.FOUR_OF_A_KIND;
        }
        if (foundFullHouse()) {
            return HandRanking.FULL_HOUSE;
        }
        if (foundThreeOfAKind) {
            return HandRanking.THREE_OF_A_KIND;
        }
        if (foundTwoPair) {
            return HandRanking.TWO_PAIR;
        }
        if (foundPair) {
            return HandRanking.PAIR;
        }
        return HandRanking.HIGH_CARD;
    }

    private void applySameCardsRules() {
        for (int i : valueCountTracker) {
            switch (i) {
                case 2: {
                    if (foundPair) {
                        foundPair = false;
                        foundTwoPair = true;
                        return;
                    } else {
                        foundPair = true;
                    }
                }
                break;
                case 3: {
                    foundThreeOfAKind = true;
                }
                break;
                case 4: {
                    foundFourOfAKind = true;
                    return;
                }
                default: {
                    // empty default - does not do anything
                }
            }
        }
    }
}
