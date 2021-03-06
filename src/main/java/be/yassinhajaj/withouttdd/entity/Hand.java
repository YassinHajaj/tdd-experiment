package be.yassinhajaj.withouttdd.entity;

import be.yassinhajaj.withouttdd.exception.HandCardNullException;
import be.yassinhajaj.withouttdd.exception.HandCardsCountIncorrectException;
import be.yassinhajaj.withouttdd.exception.HandCardsNullException;

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
        scanCards(tracker);
        return tracker.getTrackingResult();
    }

    private void scanCards(HandRankingTracker tracker) {
        Card previous = null;

        for (Card card : cards) {
            tracker.incrementValueCount(card.getValuesRanking() - 1);
            if (!tracker.alreadyRecordedAceAsHighCard() && card.isAce()) {
                tracker.recordAceAsHighCard();
            }
            if (previous != null) {
                tracker.decideForFlush(card.hasSameSuitThan(previous));
                tracker.decideForStraight(previous.isJustBefore(card));
            }
            previous = card;
        }
    }

}