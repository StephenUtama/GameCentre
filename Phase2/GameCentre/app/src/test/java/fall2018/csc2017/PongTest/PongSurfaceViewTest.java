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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        testSurfaceView = mock(PongSurfaceView.class);
        testController.gameInfo = testInfo;
        testSurfaceView.controller = testController;
        testSurfaceView.surfaceHolder = testSurfaceHolder;
        testSurfaceView.context = testContext;
        when(testSurfaceView.getGameInfo()).thenReturn(testInfo);
        doNothing().when(testSurfaceView).run();
        doNothing().when(testController).update();
        doNothing().when(testInfo).setFps(isA(long.class));
        doNothing().when(testSurfaceView).draw();


    }

    @Test
    public void getGameInfo() {
        PongGameInfo temp = testSurfaceView.getGameInfo();
        assertEquals(true,temp == testInfo);
    }

    @Test
    public void run() {
        testSurfaceView.controller.playing = true;
        testSurfaceView.controller.paused = false;
        testSurfaceView.run();
        verify(testSurfaceView.controller).update();
        verify(testSurfaceView).draw();
        verify(testInfo).setFps(isA(long.class));
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