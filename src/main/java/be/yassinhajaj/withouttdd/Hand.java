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
        HandRankingTracker tracker = new HandRankingTracker();
        enrichTrackerWithPreliminaryData(tracker);
        return tracker.getDecision();
    }

    private void enrichTrackerWithPreliminaryData(HandRankingTracker tracker) {
        Card previous = null;

        for (Card card : cards) {
            tracker.incrementValueCount(card.getValuesRanking() - 1);
            if (!tracker.recordedAceAsHighCard() && card.isAce()) {
                tracker.recordAceAsHighCard();
            }
            if (previous != null) {
                tracker.updateIsFlush(card.hasSameSuitThan(previous));
                tracker.updateIsStraight(previous.isJustBefore(card));
            }
            previous = card;
        }
    }

}
