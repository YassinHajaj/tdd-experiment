package be.yassinhajaj.withouttdd.bowling;

import be.yassinhajaj.withouttdd.Result;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.List;

@SuppressWarnings("WrapperTypeMayBePrimitive")
public class ScoreBoard {

    private static final int PIN_PER_FRAME = 10;
    public static final int MAX_NEW_PIN_SET = 12;
    private static final int MAX_PINS_KNOCKED_DOWN = PIN_PER_FRAME * MAX_NEW_PIN_SET;
    private final List<Integer> scorePerRoll;

    private ScoreBoard(List<Integer> scorePerRoll) {
        this.scorePerRoll = scorePerRoll;
    }

    public static Result<ScoreBoard> newInstance(List<Integer> scorePerRoll) {
        if (scorePerRoll == null) {
            return Result.error(new NullPointerException());
        }
        int sum = scorePerRoll.stream().mapToInt(x -> x).sum();
        if (sum > MAX_PINS_KNOCKED_DOWN) {
            return Result.error(new InvalidStateException("Bla"));
        }
        boolean anyScoreHigherThan10 = scorePerRoll.stream().anyMatch(i -> i > 10);
        if (anyScoreHigherThan10) {
            return Result.error(new InvalidStateException("Bla"));
        }
        return Result.ok(new ScoreBoard(scorePerRoll));
    }


    public int calculateScore() {
        int totalScore = 0;
        boolean newFrame = true;
        int frameSequenceNumber = 1;

        int rolls = scorePerRoll.size();
        for (int i = 0; i < rolls; i++) {
            Integer pinsDown = scorePerRoll.get(i);
            Integer nextPinsDown = i + 1 < rolls ? scorePerRoll.get(i + 1) : 0;
            if (frameSequenceNumber < 10 && newFrame && pinsDown == 10) {
                // Strike
                totalScore += 10;
                totalScore += nextPinsDown;
                if (i + 2 < rolls)
                    totalScore += scorePerRoll.get(i + 2);
                frameSequenceNumber++;
            } else if (frameSequenceNumber < 10 && newFrame && pinsDown + nextPinsDown == 10) {
                // Spare
                totalScore += 10;
                if (i + 2 < rolls)
                    totalScore += scorePerRoll.get(i + 2);
                // Skip next one
                i++;
                frameSequenceNumber++;
            } else {
                totalScore += pinsDown;
                newFrame = !newFrame;
                if (!newFrame) {
                    frameSequenceNumber++;
                }
            }
        }

        return totalScore;
    }
}
