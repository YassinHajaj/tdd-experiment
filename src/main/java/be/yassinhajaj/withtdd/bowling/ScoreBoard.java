package be.yassinhajaj.withtdd.bowling;

import be.yassinhajaj.Result;

import java.util.List;

public class ScoreBoard {
    private final static int FRAME_COUNT = 10;
    private final List<Integer> pinsFallen;

    private ScoreBoard(List<Integer> pinsFallen) {
        this.pinsFallen = pinsFallen;
    }

    public static Result<ScoreBoard> newInstance(List<Integer> pinsFallen) {
        return Result.ok(new ScoreBoard(pinsFallen));
    }

    public int calculateScore() {
        int score = 0;
        int framePlayed = 0;

        for (int roll = 0; roll < pinsFallen.size() && framePlayed < 10; roll++, framePlayed++) {
            if (isStrike(roll)) {
                score += calculateBonusPoints(roll);
            } else if (isSpare(roll)) {
                score += calculateBonusPoints(roll);
                roll++;
            } else {
                score += calculateRegularPoints(roll);
                roll++;
            }
        }

        return score;
    }

    private int calculateBonusPoints(int roll) {
        return pinsFallen.get(roll) + pinsFallen.get(roll + 1) + pinsFallen.get(roll + 2);
    }

    private int calculateRegularPoints(int roll) {
        return pinsFallen.get(roll) + pinsFallen.get(roll + 1);
    }

    private boolean isStrike(int roll) {
        return pinsFallen.get(roll) == 10;
    }

    private boolean isSpare(int roll) {
        return pinsFallen.get(roll) + pinsFallen.get(roll + 1) == 10;
    }
}
