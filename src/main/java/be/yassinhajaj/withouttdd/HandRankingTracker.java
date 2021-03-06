package be.yassinhajaj.withouttdd;

public class HandRankingTracker {
    boolean isFlush = true;
    boolean isStraight = true;
    boolean highestCardAce = false;
    int[] valueCountTracker = new int[13];
    boolean isPair = false;
    boolean isTwoPair = false;
    boolean isThreeOfAKind = false;
    boolean isFourOfAKind = false;

    public void incrementValueCount(int index) {
        valueCountTracker[index]++;
    }

    public boolean recordedAceAsHighCard() {
        return highestCardAce;
    }

    public void recordAceAsHighCard() {
        this.highestCardAce = true;
    }

    public void updateIsFlush(boolean hasSameSuitThan) {
        this.isFlush &= hasSameSuitThan;
    }

    public void updateIsStraight(boolean isJustBefore) {
        this.isStraight &= isJustBefore;
    }

    public boolean hasStraightFlush() {
        return isFlush && isStraight;
    }

    public boolean hasFlush() {
        return isFlush;
    }

    public boolean hasStraight() {
        return isStraight;
    }

    public void applySameCardsRules() {
        TRACKER_LOOP:
        for (int i : valueCountTracker) {
            switch (i) {
                case 2: {
                    if (isPair) {
                        isPair = false;
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
    }

    public boolean hasFourOfAKind() {
        return isFourOfAKind;
    }

    public boolean hasFullHouse() {
        return isPair && isThreeOfAKind;
    }

    public boolean hasThreeOfAKind() {
        return isThreeOfAKind;
    }

    public boolean hasTwoPair() {
        return isTwoPair;
    }

    public boolean hasPair() {
        return isPair;
    }

    public HandRanking getStraightOrFlushDecision() {
        if (this.hasStraightFlush()) {
            return this.recordedAceAsHighCard() ? HandRanking.ROYAL_FLUSH : HandRanking.STRAIGHT_FLUSH;
        }

        if (this.hasFlush()) {
            return HandRanking.FLUSH;
        }

        if (this.hasStraight()) {
            return HandRanking.STRAIGHT;
        }

        return null;
    }

    public HandRanking getDecision() {
        HandRanking straightOrFlushDecision = getStraightOrFlushDecision();
        if (straightOrFlushDecision != null) {
            return straightOrFlushDecision;
        }
        return getSameCardsDecision();
    }

    private HandRanking getSameCardsDecision() {
        applySameCardsRules();

        if (this.hasFourOfAKind()) {
            return HandRanking.FOUR_OF_A_KIND;
        }
        if (this.hasFullHouse()) {
            return HandRanking.FULL_HOUSE;
        }
        if (this.hasThreeOfAKind()) {
            return HandRanking.THREE_OF_A_KIND;
        }
        if (this.hasTwoPair()) {
            return HandRanking.TWO_PAIR;
        }
        if (this.hasPair()) {
            return HandRanking.PAIR;
        }
        return HandRanking.HIGH_CARD;
    }
}
