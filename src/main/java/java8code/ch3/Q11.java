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
import java.util.function.UnaryOperator;

import static java8code.ch3.Q11.ColorTransformer.*;

/**
 * Created by ftsan on 2015/10/30.
 */
public class Q11 extends Application {
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

        Image transformed = this.transform(image, compose(
                transformIgnoreXY(color -> color.brighter().brighter()),
                (x, y, color) -> {
                    if (x < 10 || image.getWidth() - 10 < x || y < 10 || image.getHeight() - 10 < y) {
                        return Color.GRAY;
                    }
                    return color;
                }));
        imageView.setImage(transformed);

        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hello world.");
        stage.show();
    }

    @FunctionalInterface
    public static interface ColorTransformer {
        Color apply(int x, int y, Color colorAtXY);

        public static ColorTransformer compose(ColorTransformer c1, ColorTransformer c2) {
            return (x, y, color) -> c2.apply(x, y, c1.apply(x, y, color));
        }

        public static ColorTransformer transformIgnoreXY(UnaryOperator<Color> u) {
            return (x, y, color) -> u.apply(color);
        }
    }
}
