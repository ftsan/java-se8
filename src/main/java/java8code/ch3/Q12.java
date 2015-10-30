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
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static java8code.ch3.Q12.ColorTransformer.transformIgnoreXY;

/**
 * Created by futeshi on 2015/10/30.
 */
public class Q12 extends Application {
    public static class LatentImage {
        private Image in;
        private List<ColorTransformer> pendingOperations;

        LatentImage transform(UnaryOperator<Color> f) {
            pendingOperations.add(transformIgnoreXY(f));
            return this;
        }

        LatentImage transform(ColorTransformer f) {
            pendingOperations.add(f);
            return this;
        }

        private LatentImage(Image in) {
            this.in = in;
            pendingOperations = new ArrayList<>();
        }

        public static LatentImage from(Image in) {
            return new LatentImage(in);
        }

        public Image toImage() {
            int width = (int) in.getWidth();
            int height = (int) in.getHeight();

            WritableImage out = new WritableImage(width, height);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color c = in.getPixelReader().getColor(x, y);
                    // ここのジェネリックスの型を明示的にかかなきゃいけないの腹立つ
                    for (ColorTransformer t : pendingOperations) {
                        out.getPixelWriter().setColor(x, y, t.apply(x, y, c));
                    }
                }
            }
            return out;
        }
    }

    @FunctionalInterface
    public interface ColorTransformer {
        Color apply(int x, int y, Color colorAtXY);

        public static ColorTransformer transformIgnoreXY(UnaryOperator<Color> u) {
            return (x, y, color) -> u.apply(color);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        ImageView imageView = new ImageView();

        // プロジェクトルートのファイル名が".jpg"で終わるファイルを取得
        Image image = new Image(new FileInputStream(
                new File(System.getProperty("user.dir"))
                        .listFiles(f -> f.getName().endsWith(".jpg"))[0]
                        .getName()));

        Image transformed = LatentImage.from(image)
                .transform(Color::grayscale)
                .transform(Color::brighter)
                .transform((x, y, color) -> {
                    if (x < 10 || image.getWidth() - 10 < x || y < 10 || image.getHeight() - 10 < y) {
                        return Color.GRAY;
                    }
                    return color;
                })
                .toImage();
        imageView.setImage(transformed);

        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hello world.");
        stage.show();
    }

}
