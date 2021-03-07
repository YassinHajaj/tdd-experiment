package be.yassinhajaj.withouttdd.holdem.entity;

import be.yassinhajaj.withouttdd.Result;
import be.yassinhajaj.withouttdd.holdem.exception.HandCardNullException;
import be.yassinhajaj.withouttdd.holdem.exception.HandCardsCountIncorrectException;
import be.yassinhajaj.withouttdd.holdem.exception.HandCardsNullException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static be.yassinhajaj.withouttdd.AllPossibleCards.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class HandTest {

    private static Stream<Arguments> highCards() {
        return Stream.of(
                Arguments.of(setOf(ACE_OF_HEART, KING_OF_DIAMOND, DEUCE_OF_DIAMOND, FIVE_OF_DIAMOND, TEN_OF_SPADE), HandRanking.HIGH_CARD),
                Arguments.of(setOf(EIGHT_OF_HEART, JACK_OF_HEART, DEUCE_OF_DIAMOND, TEN_OF_DIAMOND, ACE_OF_HEART), HandRanking.HIGH_CARD)
        );
    }

    private static Stream<Arguments> pairs() {
        return Stream.of(
                Arguments.of(setOf(ACE_OF_HEART, KING_OF_DIAMOND, KING_OF_CLUB, FIVE_OF_DIAMOND, TEN_OF_SPADE), HandRanking.PAIR),
                Arguments.of(setOf(EIGHT_OF_HEART, JACK_OF_HEART, DEUCE_OF_DIAMOND, DEUCE_OF_SPADE, ACE_OF_HEART), HandRanking.PAIR)
        );
    }

    private static Stream<Arguments> twoPair() {
        return Stream.of(
                Arguments.of(setOf(ACE_OF_HEART, ACE_OF_CLUB, KING_OF_CLUB, KING_OF_HEART, TEN_OF_SPADE), HandRanking.TWO_PAIR),
                Arguments.of(setOf(FIVE_OF_CLUB, THREE_OF_CLUB, DEUCE_OF_CLUB, THREE_OF_SPADE, FIVE_OF_DIAMOND), HandRanking.TWO_PAIR)
        );
    }

    private static Stream<Arguments> threeOfAKind() {
        return Stream.of(
                Arguments.of(setOf(ACE_OF_HEART, ACE_OF_CLUB, ACE_OF_DIAMOND, KING_OF_HEART, TEN_OF_SPADE), HandRanking.THREE_OF_A_KIND),
                Arguments.of(setOf(FIVE_OF_CLUB, THREE_OF_CLUB, DEUCE_OF_CLUB, THREE_OF_SPADE, THREE_OF_DIAMOND), HandRanking.THREE_OF_A_KIND)
        );
    }

    private static Stream<Arguments> straight() {
        return Stream.of(
                Arguments.of(setOf(FIVE_OF_CLUB, SIX_OF_CLUB, SEVEN_OF_HEART, EIGHT_OF_CLUB, NINE_OF_SPADE), HandRanking.STRAIGHT),
                Arguments.of(setOf(NINE_OF_CLUB, TEN_OF_DIAMOND, JACK_OF_SPADE, QUEEN_OF_SPADE, KING_OF_DIAMOND), HandRanking.STRAIGHT)
        );
    }

    private static Stream<Arguments> flush() {
        return Stream.of(
                Arguments.of(setOf(FIVE_OF_CLUB, SIX_OF_CLUB, KING_OF_CLUB, EIGHT_OF_CLUB, DEUCE_OF_CLUB), HandRanking.FLUSH),
                Arguments.of(setOf(NINE_OF_SPADE, DEUCE_OF_SPADE, JACK_OF_SPADE, QUEEN_OF_SPADE, ACE_OF_SPADE), HandRanking.FLUSH)
        );
    }

    private static Stream<Arguments> fullHouse() {
        return Stream.of(
                Arguments.of(setOf(ACE_OF_HEART, ACE_OF_CLUB, ACE_OF_DIAMOND, KING_OF_HEART, KING_OF_CLUB), HandRanking.FULL_HOUSE),
                Arguments.of(setOf(FIVE_OF_CLUB, THREE_OF_CLUB, FIVE_OF_DIAMOND, THREE_OF_SPADE, THREE_OF_DIAMOND), HandRanking.FULL_HOUSE)
        );
    }

    private static Stream<Arguments> fourOfAKind() {
        return Stream.of(
                Arguments.of(setOf(ACE_OF_HEART, ACE_OF_CLUB, ACE_OF_DIAMOND, KING_OF_HEART, ACE_OF_SPADE), HandRanking.FOUR_OF_A_KIND),
                Arguments.of(setOf(THREE_OF_HEART, THREE_OF_CLUB, DEUCE_OF_CLUB, THREE_OF_SPADE, THREE_OF_DIAMOND), HandRanking.FOUR_OF_A_KIND)
        );
    }

    private static Stream<Arguments> straightFlush() {
        return Stream.of(
                Arguments.of(setOf(FIVE_OF_CLUB, SIX_OF_CLUB, SEVEN_OF_CLUB, EIGHT_OF_CLUB, NINE_OF_CLUB), HandRanking.STRAIGHT_FLUSH),
                Arguments.of(setOf(NINE_OF_DIAMOND, TEN_OF_DIAMOND, JACK_OF_DIAMOND, QUEEN_OF_DIAMOND, KING_OF_DIAMOND), HandRanking.STRAIGHT_FLUSH)
        );
    }

    private static Stream<Arguments> royalFlush() {
        return Stream.of(
                Arguments.of(setOf(TEN_OF_HEART, JACK_OF_HEART, QUEEN_OF_HEART, KING_OF_HEART, ACE_OF_HEART), HandRanking.ROYAL_FLUSH),
                Arguments.of(setOf(TEN_OF_DIAMOND, JACK_OF_DIAMOND, QUEEN_OF_DIAMOND, KING_OF_DIAMOND, ACE_OF_DIAMOND), HandRanking.ROYAL_FLUSH)
        );
    }


    @MethodSource({"highCards", "pairs", "twoPair", "threeOfAKind", "straight", "flush", "fullHouse", "fourOfAKind", "straightFlush", "royalFlush"})
    @ParameterizedTest(name = "{index} Given {0}, the result of the hand's ranking is {1}")
    public void rankingTest(Set<Card> cards, HandRanking handRanking) {
        Result<Hand> handResult = Hand.newInstance(cards);
        assertThat(handResult.getOrThrow().getRanking()).isEqualTo(handRanking);
    }

    private static Stream<Arguments> cardsAbsence() {
        HashSet<Object> nullContainingSet = new HashSet<>();
        nullContainingSet.add(null);


        return Stream.of(
                Arguments.of(nullContainingSet, HandCardNullException.class),
                Arguments.of(null, HandCardsNullException.class)
        );
    }

    @MethodSource("cardsAbsence")
    @ParameterizedTest(name = "A hand should have at least a card, instead got {0}")
    public void cardsAbsenceTest(Set<Card> cards, Class<? extends RuntimeException> thrown) {
        assertThatExceptionOfType(thrown)
                .isThrownBy(Hand.newInstance(cards)::getOrThrow);
    }

    private static Stream<Arguments> requiredCount() {
        return Stream.of(
                Arguments.of(setOf(JACK_OF_HEART, QUEEN_OF_HEART, KING_OF_HEART, ACE_OF_HEART)),
                Arguments.of(setOf(TEN_OF_DIAMOND, JACK_OF_DIAMOND, QUEEN_OF_DIAMOND, KING_OF_DIAMOND, ACE_OF_DIAMOND, TEN_OF_HEART))
        );
    }

    @MethodSource("requiredCount")
    @ParameterizedTest(name = "A hand should have the required count of cards")
    public void requiredCountTest(Set<Card> cards) {
        assertThatExceptionOfType(HandCardsCountIncorrectException.class)
                .isThrownBy(Hand.newInstance(cards)::getOrThrow);
    }

    private static <T> Set<T> setOf(T... ts) {
        return new HashSet<>(Arrays.asList(ts));
    }
}