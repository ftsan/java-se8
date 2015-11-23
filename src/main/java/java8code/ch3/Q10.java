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

/**
 * Created by ftsan on 2015/10/29.
 */
public class Q10 extends Application {
    public static Image transform(Image in, UnaryOperator<Color> f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y, f.apply(in.getPixelReader().getColor(x, y)));
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
        // 以下はコンパイルエラーとなる。UnaryOperator#composeメソッドの戻り値がFunction<V, R>であるため。
        // UnaryOperator op = Color::brighter;
//        Image brighter = this.transform(image, op.compose(Color::grayscale));
        Image brighter = this.transform(image, color -> color.grayscale().brighter());
        imageView.setImage(brighter);

        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hello world.");
        stage.show();

    }


}
