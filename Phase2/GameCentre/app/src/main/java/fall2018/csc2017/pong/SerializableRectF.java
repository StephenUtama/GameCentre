package fall2018.csc2017.pong;

import android.graphics.Rect;
import android.graphics.RectF;

import java.io.Serializable;

public class SerializableRectF extends RectF implements Serializable {

    public SerializableRectF() {

    }

    public SerializableRectF(float left, float top, float right, float bottom) {

        super(left, top, right, bottom);
    }
}
