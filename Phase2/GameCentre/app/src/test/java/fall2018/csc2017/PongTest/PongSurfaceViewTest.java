package fall2018.csc2017.PongTest;

import android.content.Context;
import android.test.mock.MockContext;
import android.view.SurfaceHolder;

import org.junit.Before;
import org.junit.Test;

import fall2018.csc2017.pong.PongGameController;
import fall2018.csc2017.pong.PongGameInfo;
import fall2018.csc2017.pong.PongSurfaceView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class PongSurfaceViewTest {
    Context testContext;
    PongGameInfo testInfo;
    PongSurfaceView testSurfaceView;
    SurfaceHolder testSurfaceHolder;
    PongGameController testController;

    @Before
    public void setUp(){
        testContext = mock(Context.class);
        testInfo = mock(PongGameInfo.class);
        testSurfaceHolder = mock(SurfaceHolder.class);
        testController = mock(PongGameController.class);
        testSurfaceView = new PongSurfaceView(testContext, 1080, 1920, testInfo);
        testSurfaceView.controller = testController;
        testSurfaceView.surfaceHolder = testSurfaceHolder;
        testSurfaceView.gameInfo = testInfo;
        testSurfaceView.context = testContext;

    }

    @Test
    public void getGameInfo() {

    }

    @Test
    public void run() {
    }

    @Test
    public void draw() {
    }

    @Test
    public void onTouchEvent() {
    }

    @Test
    public void pause() {
    }

    @Test
    public void resume() {
    }
}