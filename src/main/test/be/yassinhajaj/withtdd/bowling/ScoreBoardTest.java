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

}
