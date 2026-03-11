package capitalism.UI;

import capitalism.DataStructure.DTime;
import capitalism.Processor.ProcessorDay;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.text.SimpleDateFormat;

public class FrameLog {
    private static FrameLog instance;
    private TableView<String[]> table;
    private ObservableList<String[]> data;
    private BorderPane root;

    public static void MakeLogFrame() {
        instance = new FrameLog();
    }

    public static FrameLog getInstance() {
        return instance;
    }

    public BorderPane getRoot() {
        return root;
    }

    public synchronized void addLog(String tag, String log) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatTime = format.format(System.currentTimeMillis());

        DTime today = ProcessorDay.GetInstance().getToday();
        String simDate = String.format("%04d/%02d/%02d", today.getYear(), today.getMonth(), today.getDay());

        String[] row = new String[]{simDate, formatTime, tag, log};

        Platform.runLater(() -> {
            data.add(row);
            table.scrollTo(data.size() - 1);
        });
    }

    private FrameLog() {
        data = FXCollections.observableArrayList();
        table = new TableView<>(data);

        TableColumn<String[], String> simDateCol = new TableColumn<>("sim date");
        simDateCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[0]));
        simDateCol.setPrefWidth(100);

        TableColumn<String[], String> timeCol = new TableColumn<>("time");
        timeCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[1]));
        timeCol.setPrefWidth(150);

        TableColumn<String[], String> tagCol = new TableColumn<>("tag");
        tagCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[2]));
        tagCol.setPrefWidth(50);

        TableColumn<String[], String> logCol = new TableColumn<>("log");
        logCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[3]));
        logCol.setPrefWidth(400);

        table.getColumns().addAll(simDateCol, timeCol, tagCol, logCol);
        table.setFixedCellSize(50);

        root = new BorderPane(table);
    }
}
