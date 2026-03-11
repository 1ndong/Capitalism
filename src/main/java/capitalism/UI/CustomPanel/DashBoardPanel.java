package capitalism.UI.CustomPanel;

import capitalism.Classes.Bank.CBAccount;
import capitalism.Classes.Bank.CBCentral;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Iterator;
import java.util.LinkedList;

public class DashBoardPanel extends VBox implements ITime {

    private ComboBox<String> combobox;
    private Label dayLabel;
    private boolean bInit = false;

    // Bank tab
    private FlowPane bankCardPane;
    private StackPane bankContentPane;
    private VBox bankCardListView;
    private VBox bankDetailView;

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

        // TabPane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-font-size: 13;");
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        // Bank tab
        Tab bankTab = new Tab("은행");
        bankCardPane = new FlowPane(10, 10);
        bankCardPane.setPadding(new Insets(10));
        bankCardPane.setStyle("-fx-background-color: #1a1a2e;");

        ScrollPane bankScrollPane = new ScrollPane(bankCardPane);
        bankScrollPane.setFitToWidth(true);
        bankScrollPane.setStyle("-fx-background: #1a1a2e; -fx-background-color: #1a1a2e;");

        bankCardListView = new VBox(bankScrollPane);
        VBox.setVgrow(bankScrollPane, Priority.ALWAYS);

        bankDetailView = new VBox();
        bankDetailView.setPadding(new Insets(10));
        bankDetailView.setStyle("-fx-background-color: #1a1a2e;");

        bankContentPane = new StackPane(bankCardListView, bankDetailView);
        bankDetailView.setVisible(false);
        bankTab.setContent(bankContentPane);

        tabPane.getTabs().add(bankTab);

        getChildren().addAll(upperPanel, tabPane);

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

        bankCardPane.getChildren().clear();

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
            // Central bank card
            CBCentral centralBank = country.getCentralBank();
            DCareTaker careTaker = centralBank.getCareTaker();
            long allMadeMoney = 0;
            for (int i = 0; i < careTaker.getList().size(); i++) {
                DHCentralBank history = (DHCentralBank) careTaker.getList().get(i);
                allMadeMoney += history.getMakeMoneyAmount();
            }

            String cbCash = UCurrency.getInstance().toString(centralBank.getBalance().getCash(), ECurrency.Won);
            String cbTotal = UCurrency.getInstance().toString(allMadeMoney, ECurrency.Won);
            float baseRate = centralBank.getBaseInterestRate();

            VBox centralCard = createBankCard(
                    "한국은행 (중앙은행)",
                    "#e74c3c",
                    new String[]{
                            "기준금리 : " + baseRate + "%",
                            "총 통화 발행량 : " + cbTotal,
                            "현금보유량 : " + cbCash
                    }
            );
            final long finalAllMadeMoney = allMadeMoney;
            final CCountry finalCountry = country;
            centralCard.setOnMouseClicked(e -> showCentralBankDetail(centralBank, finalAllMadeMoney));
            bankCardPane.getChildren().add(centralCard);

            // Commercial bank cards
            for (int i = 0; i < country.getBankList().size(); i++) {
                CBCommercial bank = (CBCommercial) country.getBankList().get(i);

                long creditCurrency = 0;
                for (int j = 0; j < bank.getBankMemberList().size(); j++) {
                    DBankMember bankmember = bank.getBankMemberList().get(j);
                    for (int k = 0; k < bankmember.getAccountList().size(); k++) {
                        CBAccount account = bankmember.getAccountList().get(k);
                        creditCurrency += account.getRightsOfCash();
                    }
                }

                float totalRate = baseRate + bank.getSpreadInterestRate();
                String bankCash = UCurrency.getInstance().toString(bank.getBalance().getCash(), ECurrency.Won);
                String bankCredit = UCurrency.getInstance().toString(creditCurrency, ECurrency.Won);

                VBox card = createBankCard(
                        bank.getName() + "은행",
                        "#2980b9",
                        new String[]{
                                "적용금리 : " + totalRate + "% (가산 " + bank.getSpreadInterestRate() + "%)",
                                "신용통화 : " + bankCredit,
                                "현금보유량 : " + bankCash
                        }
                );
                final CBCommercial finalBank = bank;
                final long finalCredit = creditCurrency;
                card.setOnMouseClicked(e -> showCommercialBankDetail(finalBank, finalCredit, baseRate));
                bankCardPane.getChildren().add(card);
            }
        }

        // Update people/company tables
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

    private VBox createBankCard(String title, String accentColor, String[] infoLines) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(12));
        card.setPrefWidth(220);
        card.setStyle(
                "-fx-background-color: #16213e;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: " + accentColor + ";" +
                "-fx-border-radius: 8;" +
                "-fx-border-width: 2;" +
                "-fx-cursor: hand;"
        );

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-text-fill: white; -fx-font-size: 15; -fx-font-weight: bold;");

        card.getChildren().add(titleLbl);

        for (String line : infoLines) {
            Label lbl = new Label(line);
            lbl.setStyle("-fx-text-fill: #b0b0b0; -fx-font-size: 12;");
            lbl.setWrapText(true);
            card.getChildren().add(lbl);
        }

        // Hover effect
        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #1a2744;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: " + accentColor + ";" +
                "-fx-border-radius: 8;" +
                "-fx-border-width: 2;" +
                "-fx-cursor: hand;"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #16213e;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: " + accentColor + ";" +
                "-fx-border-radius: 8;" +
                "-fx-border-width: 2;" +
                "-fx-cursor: hand;"
        ));

        return card;
    }

    private void showCentralBankDetail(CBCentral bank, long allMadeMoney) {
        bankDetailView.getChildren().clear();

        Button backBtn = new Button("< 돌아가기");
        backBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 13;");
        backBtn.setOnAction(e -> {
            bankDetailView.setVisible(false);
            bankCardListView.setVisible(true);
        });

        Label title = new Label("한국은행 (중앙은행)");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold;");
        title.setPadding(new Insets(10, 0, 10, 0));

        VBox infoBox = new VBox(8);
        infoBox.setPadding(new Insets(10));
        infoBox.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8;");

        addDetailRow(infoBox, "기준금리", bank.getBaseInterestRate() + "%");
        addDetailRow(infoBox, "총 통화 발행량", UCurrency.getInstance().toString(allMadeMoney, ECurrency.Won));
        addDetailRow(infoBox, "현금보유량", UCurrency.getInstance().toString(bank.getBalance().getCash(), ECurrency.Won));
        addDetailRow(infoBox, "등록 시중은행 수", bank.getBankList().size() + "개");

        bankDetailView.getChildren().addAll(backBtn, title, infoBox);
        bankCardListView.setVisible(false);
        bankDetailView.setVisible(true);
    }

    private void showCommercialBankDetail(CBCommercial bank, long creditCurrency, float baseRate) {
        bankDetailView.getChildren().clear();

        Button backBtn = new Button("< 돌아가기");
        backBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 13;");
        backBtn.setOnAction(e -> {
            bankDetailView.setVisible(false);
            bankCardListView.setVisible(true);
        });

        Label title = new Label(bank.getName() + "은행");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold;");
        title.setPadding(new Insets(10, 0, 10, 0));

        VBox infoBox = new VBox(8);
        infoBox.setPadding(new Insets(10));
        infoBox.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8;");

        float totalRate = baseRate + bank.getSpreadInterestRate();
        addDetailRow(infoBox, "기준금리", baseRate + "%");
        addDetailRow(infoBox, "가산금리", bank.getSpreadInterestRate() + "%");
        addDetailRow(infoBox, "적용금리", totalRate + "%");
        addDetailRow(infoBox, "신용통화", UCurrency.getInstance().toString(creditCurrency, ECurrency.Won));
        addDetailRow(infoBox, "현금보유량", UCurrency.getInstance().toString(bank.getBalance().getCash(), ECurrency.Won));
        addDetailRow(infoBox, "고객 수", bank.getBankMemberList().size() + "명");

        bankDetailView.getChildren().addAll(backBtn, title, infoBox);
        bankCardListView.setVisible(false);
        bankDetailView.setVisible(true);
    }

    private void addDetailRow(VBox container, String label, String value) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label lblKey = new Label(label);
        lblKey.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13;");
        lblKey.setMinWidth(120);

        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-text-fill: white; -fx-font-size: 13; -fx-font-weight: bold;");

        row.getChildren().addAll(lblKey, lblValue);
        container.getChildren().add(row);
    }

    @Override
    public void dayChange(DTime newTime) {
        Platform.runLater(() -> {
            dayLabel.setText(newTime.getYear() + "/" + newTime.getMonth() + "/" + newTime.getDay() + "/" + newTime.getDayoftheweek() + "요일");
        });
    }
}
