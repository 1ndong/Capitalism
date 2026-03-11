package capitalism;

import capitalism.UI.FrameControl;
import capitalism.UI.FrameLog;
import capitalism.UI.FrameMain;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.SplitPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class JavaMain {
    public JavaMain(Stage primaryStage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();

        final float RATIO_MAIN = 0.7f;
        final float RATIO_LOG_HEIGHT = 0.4f;

        double mainWidth = width * RATIO_MAIN;
        double logWidth = width - mainWidth;

        double logHeight = height * RATIO_LOG_HEIGHT;
        double controlHeight = height - logHeight;

        // Create embedded panels (no separate windows)
        FrameLog.MakeLogFrame();
        FrameControl.MakeControlFrame(logWidth, controlHeight);

        // Right side: Log (top) + Control (bottom)
        SplitPane rightPane = new SplitPane(
                FrameLog.getInstance().getRoot(),
                FrameControl.getInstance().getRoot()
        );
        rightPane.setOrientation(Orientation.VERTICAL);
        rightPane.setDividerPositions(RATIO_LOG_HEIGHT);

        // Main split: Main (left) + Right pane
        FrameMain.MakeMainFrame(primaryStage, mainWidth, height, rightPane);
    }
}
/*
todo list
2. 은행에서 이자 지급하는거 구현해야됨 이자계산식부터 확인
3. 대출이자 원리금균등상환이랑 만기일시상환 구현
4. 국세청 만들어서 각종세금 뜯어가기 세율 정의
* */
