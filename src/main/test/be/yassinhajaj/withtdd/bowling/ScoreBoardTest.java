package be.yassinhajaj.withtdd.bowling;

import be.yassinhajaj.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreBoardTest {

    @Test
    @DisplayName("If the game hasn't started yet, the score is 0")
    public void gameHasNotStartedYet() {
        Result<ScoreBoard> scoreBoard = ScoreBoard.newInstance(Collections.emptyList());
        assertThat(scoreBoard.getOrThrow().calculateScore()).isEqualTo(0);
    }

    @Test
    @DisplayName("If the game is not finished, but not a single pin is fallen yet, then the score is equal to 0")
    public void gameNotFinishedYetButNotASinglePinFallen() {
        List<Integer> pinsFallen = Arrays.asList(0, 0, 0, 0);
        Result<ScoreBoard> scoreBoard = ScoreBoard.newInstance(pinsFallen);
        assertThat(scoreBoard.getOrThrow().calculateScore()).isEqualTo(0);
    }

    @Test
    @DisplayName("If the amount of pins fallen is equal to 0, the final score is equal to 0")
    public void allPinsFallenIsEqualToZero() {
        List<Integer> pinsFallen = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Result<ScoreBoard> scoreBoard = ScoreBoard.newInstance(pinsFallen);
        assertThat(scoreBoard.getOrThrow().calculateScore()).isEqualTo(0);
    }

    @Test
    @DisplayName("If the player only managed to make a single pin fall throughout the game, then the score is equal to 1")
    public void aSinglePinFallenDuringWholeGame() {
        List<Integer> pinsFallen = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0);
        Result<ScoreBoard> scoreBoard = ScoreBoard.newInstance(pinsFallen);
        assertThat(scoreBoard.getOrThrow().calculateScore()).isEqualTo(1);
    }

    @Test
    @DisplayName("If the player manages to make pins fall throughout the whole game, but never does a strike or a spare, then its score is the sum of all fallen pins")
    public void pinsFallenThroughoutWholeGameWithoutStrikeOrSpare() {
        List<Integer> pinsFallen = Arrays.asList(3, 4, 4, 1, 2, 2, 4, 5, 3, 4, 7, 1, 1, 3, 4, 4, 5, 1, 9, 0);
        Result<ScoreBoard> scoreBoard = ScoreBoard.newInstance(pinsFallen);
        assertThat(scoreBoard.getOrThrow().calculateScore()).isEqualTo(67);
    }

    @Test
    @DisplayName("If a strike is done during the last frame, then the final score is still the sum of fallen pins, even though the player gets an extra roll")
    public void extraRollThanksToStrikeTest() {
        List<Integer> pinsFallen = Arrays.asList(3, 4, 4, 1, 2, 2, 4, 5, 3, 4, 7, 1, 1, 3, 4, 4, 5, 1, 10, 4, 4);
        Result<ScoreBoard> scoreBoard = ScoreBoard.newInstance(pinsFallen);
        assertThat(scoreBoard.getOrThrow().calculateScore()).isEqualTo(76);
    }

    @Test
    @DisplayName("If a spare is done during the last frame, then the final score is still the sum of fallen pins, even though the player gets an extra roll")
    public void extraRollThanksToSpareTest() {
        List<Integer> pinsFallen = Arrays.asList(3, 4, 4, 1, 2, 2, 4, 5, 3, 4, 7, 1, 1, 3, 4, 4, 5, 1, 5, 5, 5);
        Result<ScoreBoard> scoreBoard = ScoreBoard.newInstance(pinsFallen);
        assertThat(scoreBoard.getOrThrow().calculateScore()).isEqualTo(73);
    }

    @Test
    @DisplayName("When a strike is done outside of the 10th frame, then the points of the next two shots are added to the 10 already won")
    public void strikeTest() {
        List<Integer> pinsFallen = Arrays.asList(10, 4, 1, 2, 2, 4, 5, 3, 4, 7, 1, 1, 3, 4, 4, 5, 1, 5, 3);
        Result<ScoreBoard> scoreBoard = ScoreBoard.newInstance(pinsFallen);
        assertThat(scoreBoard.getOrThrow().calculateScore()).isEqualTo(74);
    }

    @Test
    @DisplayName("When 10 pins fall as second roll, then it counts as a spare")
    public void tenFallButIsSpareTest() {
        List<Integer> pinsFallen = Arrays.asList(0, 10, 1, 2, 2, 4, 5, 3, 4, 1, 1, 1, 3, 4, 4, 5, 1, 5, 3, 3);
        Result<ScoreBoard> scoreBoard = ScoreBoard.newInstance(pinsFallen);
        assertThat(scoreBoard.getOrThrow().calculateScore()).isEqualTo(63);
    }


}
