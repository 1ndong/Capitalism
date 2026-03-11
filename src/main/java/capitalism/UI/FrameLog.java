package capitalism.UI;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;

public class FrameLog {
    private static FrameLog instance;
    private TableView<String[]> table;
    private ObservableList<String[]> data;

    public static void MakeLogFrame(double x, double width, double height) {
        instance = new FrameLog(x, width, height);
    }

    public static FrameLog getInstance() {
        return instance;
    }

    public synchronized void addLog(String tag, String log) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatTime = format.format(System.currentTimeMillis());
        String[] row = new String[]{formatTime, tag, log, ""};

        Platform.runLater(() -> {
            data.add(row);
            table.scrollTo(data.size() - 1);
        });
    }

    private FrameLog(double x, double width, double height) {
        Stage stage = new Stage();
        stage.setTitle("Log");
        stage.setX(x);
        stage.setY(0);

        data = FXCollections.observableArrayList();
        table = new TableView<>(data);

        TableColumn<String[], String> timeCol = new TableColumn<>("time");
        timeCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[0]));
        timeCol.setPrefWidth(150);

        TableColumn<String[], String> tagCol = new TableColumn<>("tag");
        tagCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[1]));
        tagCol.setPrefWidth(50);

        TableColumn<String[], String> logCol = new TableColumn<>("log");
        logCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[2]));
        logCol.setPrefWidth(490);

        TableColumn<String[], String> reservedCol = new TableColumn<>("reserved");
        reservedCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[3]));
        reservedCol.setPrefWidth(10);

        table.getColumns().addAll(timeCol, tagCol, logCol, reservedCol);
        table.setFixedCellSize(50);

        BorderPane root = new BorderPane(table);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }
}
