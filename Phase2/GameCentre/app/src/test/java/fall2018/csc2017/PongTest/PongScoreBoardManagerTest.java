package fall2018.csc2017.PongTest;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

import fall2018.csc2017.pong.PongScoreBoardActivity;
import fall2018.csc2017.pong.PongScoreBoardManager;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PongScoreBoardManagerTest {

    private PongScoreBoardActivity testActivity;
    private LinkedHashMap<String, ArrayList<Integer>> testScores;
    private LinkedHashMap<String, Integer> testHighScores;
    private Collections testCollections;
    private Comparator testComparator;
    PongScoreBoardManager testManager;

    @Before
    public void setUp() throws Exception {
        testScores = mock(LinkedHashMap.class);
        testHighScores = mock(LinkedHashMap.class);
        testActivity = mock(PongScoreBoardActivity.class);
//        testCollections = mock(Collections.class);
//        testComparator = new Comparator() {
//            @Override
//            public int compare(Object o1, Object o2) {
//                return 0;
//            }
//        };
        testManager = spy(new PongScoreBoardManager(testActivity, testScores, testHighScores));
    }

    @Test
    public void displayGlobalRankings() {
    }

    @Test
    public void displayLocalRankings() {
        ArrayList<Integer> localScores = new ArrayList<>();
        localScores.add(1);
        localScores.add(2);
        localScores.add(3);
        localScores.add(4);
        localScores.add(5);
        localScores.add(6);
        localScores.add(7);
        localScores.add(8);
        localScores.add(9);
        localScores.add(10);
        when(testScores.get(isA(String.class))).thenReturn(localScores);
        doNothing().when(testManager).sortHelper(localScores);
//        when(testCollections.reverseOrder()).thenReturn(testComparator);
//        doNothing().when(testCollections).sort(localScores, testCollections.reverseOrder());
        doNothing().when(testManager).setTextValues(isA(int.class), isA(String.class), isA(String.class));

        testManager.displayLocalRankings("0601");
        verify(testManager,times(9)).setTextValues(isA(int.class), isA(String.class), isA(String.class));

    }

    @Test
    public void displayBlankRankings() {
    }
}