package be.yassinhajaj.withtdd.bowling;

import be.yassinhajaj.Result;

import java.util.List;

public class ScoreBoard {
    public static Result<ScoreBoard> newInstance(List<Integer> pinsFallen) {
        return Result.ok(new ScoreBoard());
    }

    public int calculateScore() {
        return 0;
    }
}
