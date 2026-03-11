package capitalism.UI;

import capitalism.DataStructure.DTime;
import capitalism.UI.CustomPanel.ControlPanel.ControlPanel;
import capitalism.Interface.ITime;
import capitalism.Interface.ITimeKeeper;
import capitalism.Processor.ProcessorDay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class FrameControl implements ITime {

    private static FrameControl instance;
    private Label dayLabel;
    private ControlPanel cp;
    private VBox root;

    public static FrameControl getInstance() {
        return instance;
    }

    public static void MakeControlFrame(double width, double height) {
        instance = new FrameControl(width, height);
    }

    public VBox getRoot() {
        return root;
    }

    private FrameControl(double width, double height) {
        // Day button
        Button dayBtn = new Button("1day");
        dayBtn.setStyle("-fx-background-color: #7dc2bd; -fx-text-fill: white; -fx-font-size: 14;");
        dayBtn.setMaxWidth(Double.MAX_VALUE);
        dayBtn.setPrefHeight(40);
        dayBtn.setOnAction(e -> ProcessorDay.GetInstance().Process());

        // Auto-advance button (hold to advance days continuously)
        Button autoBtn = new Button("▶ Hold");
        autoBtn.setStyle("-fx-background-color: #e8833a; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold;");
        autoBtn.setMaxWidth(Double.MAX_VALUE);
        autoBtn.setPrefHeight(40);

        Timeline autoTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            ProcessorDay.GetInstance().Process();
        }));
        autoTimeline.setCycleCount(Timeline.INDEFINITE);

        autoBtn.setOnMousePressed(e -> {
            ProcessorDay.GetInstance().Process();
            autoTimeline.play();
        });
        autoBtn.setOnMouseReleased(e -> autoTimeline.stop());

        // Day display
        dayLabel = new Label();
        dayLabel.setStyle("-fx-background-color: #7719aa; -fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold; -fx-padding: 5 10 5 10;");
        dayLabel.setAlignment(Pos.CENTER);
        dayLabel.setMaxWidth(Double.MAX_VALUE);
        dayLabel.setPrefHeight(40);

        HBox topBar = new HBox(10, dayBtn, autoBtn, dayLabel);
        HBox.setHgrow(dayBtn, Priority.ALWAYS);
        HBox.setHgrow(autoBtn, Priority.ALWAYS);
        HBox.setHgrow(dayLabel, Priority.ALWAYS);
        topBar.setPadding(new Insets(5));

        // Control Panel
        cp = new ControlPanel(width, height - 50);

        root = new VBox(topBar, cp);
        VBox.setVgrow(cp, Priority.ALWAYS);

        ITimeKeeper timekeeper = (ITimeKeeper) ProcessorDay.GetInstance();
        timekeeper.addTimeSlave(this);
    }

    @Override
    public void dayChange(DTime newTime) {
        Platform.runLater(() -> {
            dayLabel.setText(newTime.getYear() + "/" + newTime.getMonth() + "/" + newTime.getDay() + "/" + newTime.getDayoftheweek() + "요일");
        });
    }

    public ControlPanel getControlPanel() {
        return cp;
    }
}
