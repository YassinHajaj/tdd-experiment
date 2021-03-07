package be.yassinhajaj.withouttdd.bowling;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreBoardTest {

    @Test
    public void test1() {
        List<Integer> integers = Arrays.asList(10, 9, 0, 3, 4, 7, 3, 10, 10, 10, 10, 10, 10, 10, 10);
        assertThat(ScoreBoard.newInstance(integers).getOrThrow().calculateScore()).isEqualTo(235);
    }

    @Test
    public void test2() {
        List<Integer> integers = Arrays.asList(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertThat(ScoreBoard.newInstance(integers).getOrThrow().calculateScore()).isEqualTo(300);
    }

    @Test
    public void test3() {
        List<Integer> integers = Arrays.asList(2, 8, 3, 4, 5, 1, 1, 1, 2, 2, 3, 3, 5, 4, 4, 3, 3, 4, 2, 1);
        assertThat(ScoreBoard.newInstance(integers).getOrThrow().calculateScore()).isEqualTo(64);
    }

    @Test
    public void test4() {
        List<Integer> integers = Arrays.asList(10, 3, 4);
        assertThat(ScoreBoard.newInstance(integers).getOrThrow().calculateScore()).isEqualTo(24);
    }

    @Test
    public void test5() {
        List<Integer> integers = Arrays.asList(10, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2);
        assertThat(ScoreBoard.newInstance(integers).getOrThrow().calculateScore()).isEqualTo(42);
    }

    @Test
    public void test6() {
        List<Integer> integers = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 1, 1, 1, 1);
        assertThat(ScoreBoard.newInstance(integers).getOrThrow().calculateScore()).isEqualTo(16);
    }

    @Test
    public void test7() {
        List<Integer> integers = Arrays.asList(2, 4, 3, 1, 4, 4, 2, 1, 3, 2, 3, 4, 3, 2, 4, 5, 2, 1, 3, 2);
        assertThat(ScoreBoard.newInstance(integers).getOrThrow().calculateScore()).isEqualTo(55);
    }

}