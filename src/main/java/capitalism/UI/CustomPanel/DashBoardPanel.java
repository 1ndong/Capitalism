package capitalism.UI.CustomPanel;

import capitalism.Classes.Bank.CBAccount;
import capitalism.Classes.Bank.CBCommercial;
import capitalism.Classes.CCompany;
import capitalism.Classes.CCountry;
import capitalism.Classes.CPeople;
import capitalism.Classes.CWorld;
import capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import capitalism.DataStructure.DBankMember;
import capitalism.DataStructure.DCareTaker;
import capitalism.DataStructure.DHCentralBank;
import capitalism.DataStructure.DTime;
import capitalism.Enum.ECurrency;
import capitalism.Enum.EGovernmentType;
import capitalism.Interface.ITime;
import capitalism.Interface.ITimeKeeper;
import capitalism.Processor.ProcessorDay;
import capitalism.UI.FrameMain;
import capitalism.Util.UCurrency;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Iterator;
import java.util.LinkedList;

public class DashBoardPanel extends VBox implements ITime {

    private ComboBox<String> combobox;
    private Label dayLabel;
    private boolean bInit = false;
    private ObservableList<String[]> infoData;
    private TableView<String[]> infoTable;

    public DashBoardPanel(double width, double height) {
        setPrefWidth(width);
        setPrefHeight(height);

        // Upper panel
        combobox = new ComboBox<>();
        combobox.setPrefWidth(width * 0.3);
        combobox.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        dayLabel = new Label();
        dayLabel.setStyle("-fx-background-color: #7719aa; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-padding: 5 10 5 10;");
        dayLabel.setAlignment(Pos.CENTER);
        dayLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(dayLabel, Priority.ALWAYS);

        Label titleLabel = new Label("Capitalism simulator");
        titleLabel.setStyle("-fx-background-color: #2dc2d8; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-padding: 5 10 5 10;");
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);

        HBox upperPanel = new HBox(combobox, dayLabel, titleLabel);
        upperPanel.setStyle("-fx-background-color: blue;");
        upperPanel.setPrefHeight(height * 0.1);

        // Info table
        infoData = FXCollections.observableArrayList();
        infoTable = new TableView<>(infoData);
        TableColumn<String[], String> infoCol = new TableColumn<>("[information]");
        infoCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[0]));
        infoCol.setPrefWidth(width);
        infoTable.getColumns().add(infoCol);
        infoTable.setFixedCellSize(40);
        infoTable.setStyle("-fx-base: #2c2255; -fx-text-fill: white;");

        VBox.setVgrow(infoTable, Priority.ALWAYS);

        getChildren().addAll(upperPanel, infoTable);

        ITimeKeeper timekeeper = (ITimeKeeper) ProcessorDay.GetInstance();
        timekeeper.addTimeSlave(this);
    }

    public void initComponent() {
        combobox.getItems().clear();
        for (int i = 0; i < CWorld.getInstance().getCountryList().size(); i++) {
            combobox.getItems().add(CWorld.getInstance().getCountryList().get(i).getCountryName());
        }
        combobox.getSelectionModel().clearSelection();
    }

    public void updateComponent() {
        if (!bInit) {
            initComponent();
            bInit = true;
        }

        infoData.clear();

        if (combobox.getSelectionModel().getSelectedIndex() == -1)
            return;

        String countryName = combobox.getSelectionModel().getSelectedItem();

        CCountry country = null;
        for (int i = 0; i < CWorld.getInstance().getCountryList().size(); i++) {
            if (CWorld.getInstance().getCountryList().get(i).getCountryName().equalsIgnoreCase(countryName)) {
                country = CWorld.getInstance().getCountryList().get(i);
                break;
            }
        }

        if (country != null) {
            DCareTaker careTaker = country.getCentralBank().getCareTaker();
            long allMadeMoney = 0;
            for (int i = 0; i < careTaker.getList().size(); i++) {
                DHCentralBank history = (DHCentralBank) careTaker.getList().get(i);
                allMadeMoney += history.getMakeMoneyAmount();
            }
            addInfo("중앙은행 총 통화 발행량 : " + UCurrency.getInstance().toString(allMadeMoney, ECurrency.Won));
            addInfo("기준금리 : " + country.getCentralBank().getBaseInterestRate() + "%");
            addInfo("중앙은행 현금보유량 : " + UCurrency.getInstance().toString(country.getCentralBank().getBalance().getCash(), ECurrency.Won));

            for (int i = 0; i < country.getBankList().size(); i++) {
                CBCommercial bank = (CBCommercial) country.getBankList().get(i);
                addInfo(bank.getName() + " , 가산금리 : " + bank.getSpreadInterestRate() + "%");

                long creditCurrency = 0;
                for (int j = 0; j < bank.getBankMemberList().size(); j++) {
                    DBankMember bankmember = bank.getBankMemberList().get(j);
                    for (int k = 0; k < bankmember.getAccountList().size(); k++) {
                        CBAccount account = bankmember.getAccountList().get(k);
                        creditCurrency += account.getRightsOfCash();
                    }
                }
                addInfo(bank.getName() + " , 신용통화 : " + UCurrency.getInstance().toString(creditCurrency, ECurrency.Won));
                addInfo(bank.getName() + " , 현금보유량 : " + UCurrency.getInstance().toString(bank.getBalance().getCash(), ECurrency.Won));
            }
        }

        CGMinistryOfHealthAndWelfare mohaw = (CGMinistryOfHealthAndWelfare) country.getGovernmentMap().get(EGovernmentType.HealthAndWelfare);

        Iterator<Integer> mapIter = mohaw.getPeopleMap().keySet().iterator();
        LinkedList<CPeople> peopleList = new LinkedList<>();
        while (mapIter.hasNext()) {
            int key = mapIter.next();
            CPeople value = mohaw.getPeopleMap().get(key);
            peopleList.add(value);
        }
        FrameMain.getInstance().addPeopleList(peopleList);

        CGMinistryOfTradeIndustryAndEnergy motia = (CGMinistryOfTradeIndustryAndEnergy) country.getGovernmentMap().get(EGovernmentType.TradeIndustryAndEnergy);

        Iterator<Integer> mapIter2 = motia.getCompanyMap().keySet().iterator();
        LinkedList<CCompany> companyList = new LinkedList<>();
        while (mapIter2.hasNext()) {
            int key = mapIter2.next();
            CCompany value = motia.getCompanyMap().get(key);
            companyList.add(value);
        }
        FrameMain.getInstance().addCompanyList(companyList);
    }

    private void addInfo(String content) {
        infoData.add(new String[]{content});
    }

    @Override
    public void dayChange(DTime newTime) {
        Platform.runLater(() -> {
            dayLabel.setText(newTime.getYear() + "/" + newTime.getMonth() + "/" + newTime.getDay() + "/" + newTime.getDayoftheweek() + "요일");
        });
    }
}
