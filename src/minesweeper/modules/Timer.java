package minesweeper.modules;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timer {
    private Timeline timeline;
    private Duration time = Duration.ZERO;
    private final StringProperty timeSeconds = new SimpleStringProperty();

    public Timer(Label t) {
        t.textProperty().bind(timeSeconds);
    }

    public void start() {
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

    private String makeText(final Duration duration) {
        return String.format("%02d:%02d",
                (long) (duration.toMinutes() % 60.0),
                (long) (duration.toSeconds() % 60.0)
        );
    }

    public void reset() {
        time = Duration.ZERO;
        timeSeconds.set(makeText(time));
    }

}
