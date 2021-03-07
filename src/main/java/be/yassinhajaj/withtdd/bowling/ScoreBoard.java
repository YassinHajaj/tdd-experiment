package be.yassinhajaj.withtdd.bowling;

import be.yassinhajaj.Result;

import java.util.List;

public class ScoreBoard {
    private final List<Integer> pinsFallen;

    private ScoreBoard(List<Integer> pinsFallen) {
        this.pinsFallen = pinsFallen;
    }

    public static Result<ScoreBoard> newInstance(List<Integer> pinsFallen) {
        return Result.ok(new ScoreBoard(pinsFallen));
    }

    public int calculateScore() {
        return pinsFallen.stream().mapToInt(x -> x).sum();
    }
}
