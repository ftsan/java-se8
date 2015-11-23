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
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static java8code.ch3.Q13.ColorTransformer.transformIgnoreXY;

/**
 * Created by ftsan on 2015/10/30.
 */
public class Q13 extends Application {
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
        // プロジェクトルートのファイル名が".jpg"で終わるファイルを取得
        Image image = new Image(new FileInputStream(
                new File(System.getProperty("user.dir"))
                        .listFiles(f -> f.getName().endsWith(".jpg"))[0]
                        .getName()));

        Image transformed = LatentImage.from(image)
                .transform(Color::grayscale)
                .transform(Color::brighter)
//                        よくわからないのでエッジ検出はパス
//                .transform((x, y, color) -> {
//                    List<Color> colors = new ArrayList<>();
//
//                    if (x > 0) {
//                        colors.add(image.getPixelReader().getColor(x - 1, y));
//                    }
//                    if (y > 0) {
//                        colors.add(image.getPixelReader().getColor(x, y - 1));
//                    }
//
//                    if (x < (int) image.getWidth() - 1) {
//                        colors.add(image.getPixelReader().getColor(x + 1, y));
//                    }
//
//                    if (y < (int) image.getHeight() - 1) {
//                        colors.add(image.getPixelReader().getColor(x, y + 1));
//                    }
//
//                    double r = color.getRed() * 4 - colors.stream().mapToDouble(c -> c.getRed()).sum();
//                    double g = color.getGreen() * 4 - colors.stream().mapToDouble(c -> c.getGreen()).sum();
//                    double b = color.getBlue() * 4 - colors.stream().mapToDouble(c -> c.getBlue()).sum();
//
//                    return Color.color(r, g, b);
//                })
                // ぼかし
                .transform((x, y, color) -> {
                    List<Color> colors = new ArrayList<Color>() {
                        {
                            add(color);
                        }
                    };

                    if (x > 0) {
                        colors.add(image.getPixelReader().getColor(x - 1, y));
                        if (y > 0) {
                            colors.add(image.getPixelReader().getColor(x - 1, y - 1));
                        }
                        if (y < image.getHeight() - 1) {
                            colors.add(image.getPixelReader().getColor(x - 1, y + 1));
                        }
                    }

                    if (x < (int) image.getWidth() - 1) {
                        colors.add(image.getPixelReader().getColor(x + 1, y));
                        if (y > 0) {
                            colors.add(image.getPixelReader().getColor(x + 1, y - 1));
                        }
                        if (y < image.getHeight() - 1) {
                            colors.add(image.getPixelReader().getColor(x + 1, y + 1));
                        }
                    }

                    if (y > 0) {
                        colors.add(image.getPixelReader().getColor(x, y - 1));
                    }

                    if (y < (int) image.getHeight() - 1) {
                        colors.add(image.getPixelReader().getColor(x, y + 1));
                    }

                    double r = colors.stream().mapToDouble(c -> c.getRed()).average().orElse(0.0);
                    double g = colors.stream().mapToDouble(c -> c.getGreen()).average().orElse(0.0);
                    double b = colors.stream().mapToDouble(c -> c.getBlue()).average().orElse(0.0);

                    return Color.color(r, g, b);
                })
                .toImage();
        Scene scene = new Scene(new HBox(new ImageView(image), new ImageView(transformed)));
        stage.setScene(scene);
        stage.setTitle("Hello world.");
        stage.show();
    }
}
