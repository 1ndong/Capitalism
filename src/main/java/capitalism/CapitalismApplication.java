package capitalism;

import javafx.application.Application;
import javafx.stage.Stage;

public class CapitalismApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        new JavaMain(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
