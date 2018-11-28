package fall2018.csc2017.PongTest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fall2018.csc2017.pong.PongScoreBoardActivity;
import fall2018.csc2017.pong.PongScoreBoardManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PongScoreBoardManagerTest {

    private PongScoreBoardActivity testActivity;
    private LinkedHashMap<String, ArrayList<Integer>> testScores;
    private LinkedHashMap<String, Integer> testHighScores;
    PongScoreBoardManager testManager;

    @Before
    public void setUp() throws Exception {
        testScores = mock(LinkedHashMap.class);
        testHighScores = mock(LinkedHashMap.class);
        testActivity = mock(PongScoreBoardActivity.class);
        testManager = new PongScoreBoardManager(testActivity, testScores, testHighScores);
    }

    @Test
    public void displayGlobalRankings() {
    }

    @Test
    public void displayLocalRankings() {
    }

    @Test
    public void displayBlankRankings() {
    }
}