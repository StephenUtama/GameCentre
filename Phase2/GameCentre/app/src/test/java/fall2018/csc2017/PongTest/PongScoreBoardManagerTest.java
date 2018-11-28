package fall2018.csc2017.PongTest;

import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import fall2018.csc2017.pong.PongScoreBoardActivity;
import fall2018.csc2017.pong.PongScoreBoardManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    public void testUpdateHighScores(){

        //testManager.updateHighScores();

    }

    @Test
    public void testGetHighScoreArray(){

    }

    @Test
    public void testSortHighScoreArray(){

    }

    @Test
    public void testDisplayBlankRankings(){

    }

    @Test
    public void displayGlobalRankings() {
    }

    @Test
    public void displayLocalRankings() {
    }

    @Test
    public void displayBlankRankings() {

        //testManager.displayBlankRankings();

    }

    @Test
    public void setTextValues() {

    }
}