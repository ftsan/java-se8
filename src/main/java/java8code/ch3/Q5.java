package java8code.ch3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by futeshi on 2015/10/27.
 */
public class Q5 extends Application {
    public static Image transform(Image in, ColorTransformer transformer) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(
                        x, y, transformer.apply(
                                x, y, in.getPixelReader().getColor(x, y)));
            }
        }
        return out;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ImageView imageView = new ImageView();

        // プロジェクトルートのファイル名が".jpg"で終わるファイルを取得
        Image image = new Image(new FileInputStream(
                new File(System.getProperty("user.dir"))
                        .listFiles(f -> f.getName().endsWith(".jpg"))[0]
                        .getName()));

        Image brighter = this.transform(image, (x, y, color) -> {
            if(x < 10 || image.getWidth() - 10 < x || y < 10 || image.getHeight() - 10 < y) {
                return Color.GRAY;
            }
            // １回だとあまり明るくなった感じがしないので
            return color.brighter().brighter();
        });

        imageView.setImage(brighter);

        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.setTitle("Hello world.");
        stage.show();

    }

    @FunctionalInterface
    public static interface ColorTransformer {
        Color apply(int x, int y, Color colorAtXY);
    }
}
