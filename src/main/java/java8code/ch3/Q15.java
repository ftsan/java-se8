package java8code.ch3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;

import static java8code.ch3.Q15.ColorTransformer.transformIgnoreXY;

/**
 * Created by ftsan on 2015/10/30.
 */
public class Q15 extends Application {
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
            int n = Runtime.getRuntime().availableProcessors();
            int width = (int) in.getWidth();
            int height = (int) in.getHeight();

            WritableImage out = copy(in);
            ExecutorService pool = Executors.newCachedThreadPool();

            for (int i = 0; i < n; i++) {
                int fromY = i * height / n;
                int toY = (i + 1) * height / n;
                pool.submit(() -> {
                    for (int x = 0; x < width; x++) {
                        for (int y = fromY; y < toY; y++) {
                            Color c = out.getPixelReader().getColor(x, y);
                            for (ColorTransformer t : pendingOperations) {
                                out.getPixelWriter().setColor(x, y, t.apply(x, y, c));
                            }
                        }
                    }
                });
            }
            pool.shutdown();
            try {
                pool.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return out;
        }

        private WritableImage copy(Image org) {
            int width = (int)org.getWidth();
            int height = (int)org.getHeight();
            WritableImage out = new WritableImage(width, height);
            PixelReader pr = org.getPixelReader();
            PixelWriter pw = out.getPixelWriter();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    out.getPixelWriter().setColor(x, y, pr.getColor(x, y));
                }
            }

            return out;
        }

        public static Color[][] parallesTransform(Color[][] in, UnaryOperator<Color> f) {
            int n = Runtime.getRuntime().availableProcessors();
            int height = in.length;
            int width = in[0].length;
            Color[][] out = new Color[height][width];
            try {
                ExecutorService pool = Executors.newCachedThreadPool();
                for (int i = 0; i < n; i++) {
                    int fromY = i * height / n;
                    int toY = (i + 1) * height / n;
                    pool.submit(() -> {
                        for (int x = 0; x < width; x++) {
                            for (int y = fromY; y < toY; y++) {
                                out[y][x] = f.apply(in[y][x]);
                            }
                        }
                    });
                    pool.shutdown();
                    pool.awaitTermination(1, TimeUnit.HOURS);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
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
                .transform((x, y, color) -> {
                    if (x < 10 || image.getWidth() - 10 < x || y < 10 || image.getHeight() - 10 < y) {
                        return Color.GRAY;
                    }
                    return color;
                })
                .toImage();
        Scene scene = new Scene(new HBox(new ImageView(image), new ImageView(transformed)));
        stage.setScene(scene);
        stage.setTitle("Hello world.");
        stage.show();
    }

}
