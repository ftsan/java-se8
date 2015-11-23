package java8code.ch3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.function.BiFunction;

/**
 * Created by ftsan on 2015/10/27.
 */
public class Q6 extends Application{
    public static <T> Image transform(Image in, BiFunction<Color, T, Color> f, T arg) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(
                        x, y, f.apply(in.getPixelReader().getColor(x, y), arg));
            }
        }
        return out;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // プロジェクトルートのファイル名が".jpg"で終わるファイルを取得
        Image image = new Image(new FileInputStream(
                new File(System.getProperty("user.dir"))
                        .listFiles(f -> f.getName().endsWith(".jpg"))[0]
                        .getName()));

        Image brightenedImage = this.transform(image,
                (c, factor) -> c.deriveColor(0, 1, factor, 1),
                // factor分だけ明るくなる
                Math.sqrt(3));

        Scene scene = new Scene(new HBox(new ImageView(image), new ImageView(brightenedImage)));
        stage.setScene(scene);
        stage.setTitle("Hello world.");
        stage.show();

    }
}
