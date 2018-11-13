package fall2018.csc2017.slidingtiles;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.Display;

import java.util.ArrayList;

public class SplitingImage {
    //image = DownloadingImage.download();
    private static Bitmap image;

    public static Bitmap[] split(Bitmap bmp) {
        Bitmap[] image_list;
        // adjust image size into screen size
        image = Bitmap.createScaledBitmap(bmp, 1080, 1920, false);
        System.out.println("Passed line 28");
        int tile_width = 1080 / 3;
        int tile_height = 1920 / 3;
        int x = 0;
        int y = 0;
        int numTiles = Board.NUM_COLS * Board.NUM_ROWS;
        image_list = new Bitmap[numTiles];
        // split image
        for (int i = 0; i < numTiles; i++) {
            if (i > 0 && i % 3 == 0) {
                x = 0;
                y += tile_height;
            } else if (i > 0) {
                x += tile_width;
            }
            image_list[i] = Bitmap.createBitmap(image, x, y, tile_width, tile_height);
        }

        return image_list;
    }
    /*

     */
}
