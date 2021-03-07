package be.yassinhajaj.withouttdd.entity;

public class HandRankingTracker {
    boolean foundFlush = true;
    boolean foundStraight = true;
    boolean recordedAceAsHighCard = false;
    int[] valueCountTracker = new int[13];
    boolean foundPair = false;
    boolean foundTwoPair = false;
    boolean foundThreeOfAKind = false;
    boolean foundFourOfAKind = false;

    public void incrementValueCount(int index) {
        valueCountTracker[index]++;
    }

    public boolean alreadyRecordedAceAsHighCard() {
        return recordedAceAsHighCard;
    }

    public void recordAceAsHighCard() {
        this.recordedAceAsHighCard = true;
    }

    public void decideForFlush(boolean stillFlush) {
        this.foundFlush &= stillFlush;
    }

    public void decideForStraight(boolean stillStraight) {
        this.foundStraight &= stillStraight;
    }

    public boolean foundStraightFlush() {
        return foundFlush && foundStraight;
    }

    public boolean foundFlush() {
        return foundFlush;
    }

    public boolean foundStraight() {
        return foundStraight;
    }

    public boolean foundFourOfAKind() {
        return foundFourOfAKind;
    }

    public boolean foundFullHouse() {
        return foundPair && foundThreeOfAKind;
    }

    public boolean foundThreeOfAKind() {
        return foundThreeOfAKind;
    }

    public boolean foundTwoPair() {
        return foundTwoPair;
    }

    public boolean foundPair() {
        return foundPair;
    }

    public HandRanking getTrackingResult() {
        HandRanking straightOrFlushDecision = getStraightOrFlushTrackingResult();
        if (straightOrFlushDecision != null) {
            return straightOrFlushDecision;
        }
        return getSameCardsTrackingResult();
    }

    private HandRanking getStraightOrFlushTrackingResult() {
        if (this.foundStraightFlush()) {
            return this.alreadyRecordedAceAsHighCard() ? HandRanking.ROYAL_FLUSH : HandRanking.STRAIGHT_FLUSH;
        }

        if (this.foundFlush()) {
            return HandRanking.FLUSH;
        }

        if (this.foundStraight()) {
            return HandRanking.STRAIGHT;
        }

        return null;
    }

    private HandRanking getSameCardsTrackingResult() {
        applySameCardsRules();

        if (this.foundFourOfAKind()) {
            return HandRanking.FOUR_OF_A_KIND;
        }
        if (this.foundFullHouse()) {
            return HandRanking.FULL_HOUSE;
        }
        if (this.foundThreeOfAKind()) {
            return HandRanking.THREE_OF_A_KIND;
        }
        if (this.foundTwoPair()) {
            return HandRanking.TWO_PAIR;
        }
        if (this.foundPair()) {
            return HandRanking.PAIR;
        }
        return HandRanking.HIGH_CARD;
    }

    public void applySameCardsRules() {
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
