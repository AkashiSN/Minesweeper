package minesweeper.modules;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timer {
    private static Timeline timeline; // タイマー
    private static Duration time = Duration.ZERO; // 時間
    private static final StringProperty timeSeconds = new SimpleStringProperty(); // プロパティ

    /**
     * Timer()
     * タイマーを初期化する
     * @param t Label
     */
    public Timer(Label t) {
        t.textProperty().bind(timeSeconds);
    }

    /**
     * start()
     * タイマーをスタートする
     */
    static void start() {
        if (timeline == null) {
            timeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                    e2 -> {
                        final Duration duration = ((KeyFrame) e2.getSource()).getTime();
                        time = time.add(duration);
                        timeSeconds.set(makeText(time));
                    }
                )
            );
        }
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * makeText()
     * タイマーの時間を文字列にする
     * @param duration 時間
     * @return String 文字列
     */
    private static String makeText(final Duration duration) {
        return String.format("%02d:%02d",
                (long) (duration.toMinutes() % 60.0),
                (long) (duration.toSeconds() % 60.0)
        );
    }


    /**
     * stop()
     * タイマーを止める
     */
    static void stop() {
        if (timeline == null) {
            return;
        }
        timeline.stop();
        timeSeconds.set(makeText(time));
    }

}
