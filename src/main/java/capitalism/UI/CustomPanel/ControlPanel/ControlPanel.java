package capitalism.UI.CustomPanel.ControlPanel;

import capitalism.Classes.CBeing;
import capitalism.Classes.CPeople;
import capitalism.Classes.CCompany;
import capitalism.Classes.CWorld;
import capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import capitalism.Classes.Stuff.CStuff;
import capitalism.DataCenter.DataCenter;
import capitalism.DataStructure.DPayment;
import capitalism.DataStructure.DServiceItem;
import capitalism.Enum.EGovernmentType;
import capitalism.Enum.EServicePropertyType;
import capitalism.UI.Dialog.DialogPayment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.*;

public class ControlPanel extends VBox {

    private ObservableList<String[]> stuffData;
    private TableView<String[]> stuffTable;
    private ObservableList<String[]> serviceData;
    private TableView<String[]> serviceTable;
    private Label targetLabel;

    private CBeing targetBeing = null;
    private int stage = 0;
    private Vector<DServiceItem> lastItemList = new Vector<>();

    public ControlPanel(double width, double height) {
        setPrefWidth(width);
        setPrefHeight(height);
        setPadding(new Insets(5));
        setStyle("-fx-background-color: #002b36;");

        // Target label
        targetLabel = new Label("대상을 선택하세요 (테이블에서 더블클릭)");
        targetLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        targetLabel.setMaxWidth(Double.MAX_VALUE);

        // Buttons
        Button btnSubmit = new Button("Submit");
        btnSubmit.setStyle("-fx-background-color: #1f8ad1; -fx-text-fill: white; -fx-font-size: 14;");
        btnSubmit.setMaxWidth(Double.MAX_VALUE);
        btnSubmit.setPrefHeight(35);
        btnSubmit.setDisable(true);

        Button btnCancel = new Button("Cancel");
        btnCancel.setStyle("-fx-background-color: #d23355; -fx-text-fill: white; -fx-font-size: 14;");
        btnCancel.setMaxWidth(Double.MAX_VALUE);
        btnCancel.setPrefHeight(35);
        btnCancel.setOnAction(e -> initPanel());

        HBox btnBox = new HBox(5, btnSubmit, btnCancel);
        HBox.setHgrow(btnSubmit, Priority.ALWAYS);
        HBox.setHgrow(btnCancel, Priority.ALWAYS);

        // Service table
        serviceData = FXCollections.observableArrayList();
        serviceTable = new TableView<>(serviceData);
        TableColumn<String[], String> serviceCol = new TableColumn<>("[service list]");
        serviceCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[0]));
        serviceCol.setPrefWidth(width / 2 - 10);
        serviceTable.getColumns().add(serviceCol);

        serviceTable.setOnMouseClicked(e -> {
            int row = serviceTable.getSelectionModel().getSelectedIndex();
            if (row < 0) return;
            String[] selected = serviceData.get(row);
            String selectedItem = selected[0];

            int nextStage = stage + 1;
            stage = nextStage;
            makeStage(stage, selectedItem, row);
        });

        // Stuff table
        stuffData = FXCollections.observableArrayList();
        stuffTable = new TableView<>(stuffData);
        TableColumn<String[], String> stuffNameCol = new TableColumn<>("[stuff list]");
        stuffNameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[0]));
        stuffNameCol.setPrefWidth(width / 4);
        TableColumn<String[], String> stuffValueCol = new TableColumn<>("[value]");
        stuffValueCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[1]));
        stuffValueCol.setPrefWidth(width / 4);
        stuffTable.getColumns().addAll(stuffNameCol, stuffValueCol);

        HBox tableBox = new HBox(5, serviceTable, stuffTable);
        HBox.setHgrow(serviceTable, Priority.ALWAYS);
        HBox.setHgrow(stuffTable, Priority.ALWAYS);
        VBox.setVgrow(tableBox, Priority.ALWAYS);

        getChildren().addAll(targetLabel, btnBox, tableBox);
    }

    public void startCommandProcess(CBeing being) {
        targetBeing = being;
        targetLabel.setText(targetBeing.getBasicData().getName());

        stuffData.clear();
        ArrayList<CStuff> stuffList = targetBeing.getStuffList();
        for (CStuff stuff : stuffList) {
            stuffData.add(new String[]{stuff.getName(), stuff.getPrice()});
        }

        stage = 0;
        makeStage(stage, "", -1);
    }

    public void startCommandProcessByName(String name) {
        // Search through all countries for matching being
        for (int i = 0; i < CWorld.getInstance().getCountryList().size(); i++) {
            var country = CWorld.getInstance().getCountryList().get(i);

            CGMinistryOfHealthAndWelfare mohaw = (CGMinistryOfHealthAndWelfare) country.getGovernmentMap().get(EGovernmentType.HealthAndWelfare);
            for (CPeople p : mohaw.getPeopleMap().values()) {
                if (p.getBasicData().getName().equals(name)) {
                    startCommandProcess(p);
                    return;
                }
            }

            CGMinistryOfTradeIndustryAndEnergy motia = (CGMinistryOfTradeIndustryAndEnergy) country.getGovernmentMap().get(EGovernmentType.TradeIndustryAndEnergy);
            for (CCompany c : motia.getCompanyMap().values()) {
                if (c.getBasicData().getName().equals(name)) {
                    startCommandProcess(c);
                    return;
                }
            }
        }
    }

    public void makeStage(int stage, String selectedItem, int selectedRow) {
        serviceData.clear();
        Vector<DServiceItem> catalog = DataCenter.getInstance().getCatalog();
        Set<String> tableResult = new HashSet<>();

        switch (stage) {
            case 0: {
                for (DServiceItem item : catalog) {
                    String sector = item.getProperty(EServicePropertyType.Sector);
                    if (!tableResult.contains(sector)) {
                        tableResult.add(sector);
                        serviceData.add(new String[]{sector});
                    }
                }
                break;
            }
            case 1: {
                for (DServiceItem item : catalog) {
                    if (!item.getProperty(EServicePropertyType.Sector).equals(selectedItem))
                        continue;
                    String major = item.getProperty(EServicePropertyType.Major);
                    if (!tableResult.contains(major)) {
                        tableResult.add(major);
                        serviceData.add(new String[]{major});
                    }
                }
                break;
            }
            case 2: {
                lastItemList.clear();
                for (DServiceItem item : catalog) {
                    if (!item.getProperty(EServicePropertyType.Major).equals(selectedItem))
                        continue;
                    String modelPrice = item.getProperty(EServicePropertyType.Model) + " / " + item.getProperty(EServicePropertyType.Price);
                    serviceData.add(new String[]{modelPrice});
                    lastItemList.add(item);
                }
                break;
            }
            case 3: {
                DServiceItem item = lastItemList.get(selectedRow);
                DialogPayment dp = new DialogPayment(new DPayment(getTargetBeing(), item));
                if (dp.getResult()) {
                    stuffData.add(new String[]{
                            item.getProperty(EServicePropertyType.Model),
                            item.getProperty(EServicePropertyType.Price)
                    });
                    getTargetBeing().getStuffList().add(new CStuff(
                            item.getProperty(EServicePropertyType.Model),
                            item.getProperty(EServicePropertyType.Price)));
                }
                lastItemList.clear();
                break;
            }
        }
    }

    public void initPanel() {
        targetBeing = null;
        targetLabel.setText("대상을 선택하세요 (테이블에서 더블클릭)");
        serviceData.clear();
        stuffData.clear();
    }

    public CBeing getTargetBeing() {
        return targetBeing;
    }
}
