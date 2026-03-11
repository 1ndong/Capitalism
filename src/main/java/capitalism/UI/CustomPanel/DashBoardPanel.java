package capitalism.UI.CustomPanel;

import capitalism.Classes.Bank.CBAccount;
import capitalism.Classes.Bank.CBCentral;
import capitalism.Classes.Bank.CBCommercial;
import capitalism.Classes.Bank.CBank;
import capitalism.Classes.CCompany;
import capitalism.Classes.CCountry;
import capitalism.Classes.CPeople;
import capitalism.Classes.CWorld;
import capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import capitalism.Classes.Government.CGNationalTaxService;
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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

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

    // Government tab
    private VBox govContent;
    private Label govBalanceLabel;
    private Label govEarnedTaxLabel;
    private Label govCorpTaxLabel;
    private Label govTotalTaxLabel;
    private Label govMonthlyEarnedLabel;
    private Label govMonthlyCorpLabel;
    private Slider earnedTaxSlider;
    private Slider corpTaxSlider;
    private Label earnedTaxValueLabel;
    private Label corpTaxValueLabel;

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
        upperPanel.setPrefHeight(40);

        // 1day / Hold buttons
        Button dayBtn = new Button("1day");
        dayBtn.setStyle("-fx-background-color: #7dc2bd; -fx-text-fill: white; -fx-font-size: 14;");
        dayBtn.setMaxWidth(Double.MAX_VALUE);
        dayBtn.setPrefHeight(35);
        dayBtn.setOnAction(e -> ProcessorDay.GetInstance().Process());

        Button autoBtn = new Button("▶ Hold");
        autoBtn.setStyle("-fx-background-color: #e8833a; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold;");
        autoBtn.setMaxWidth(Double.MAX_VALUE);
        autoBtn.setPrefHeight(35);

        Timeline autoTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            ProcessorDay.GetInstance().Process();
        }));
        autoTimeline.setCycleCount(Timeline.INDEFINITE);

        autoBtn.setOnMousePressed(e -> {
            ProcessorDay.GetInstance().Process();
            autoTimeline.play();
        });
        autoBtn.setOnMouseReleased(e -> autoTimeline.stop());

        HBox btnBar = new HBox(5, dayBtn, autoBtn);
        HBox.setHgrow(dayBtn, Priority.ALWAYS);
        HBox.setHgrow(autoBtn, Priority.ALWAYS);
        btnBar.setPadding(new Insets(3, 0, 3, 0));

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

        // Government tab
        Tab govTab = new Tab("정부");
        govContent = buildGovernmentTab();
        govTab.setContent(govContent);

        tabPane.getTabs().addAll(bankTab, govTab);

        getChildren().addAll(upperPanel, btnBar, tabPane);

        ITimeKeeper timekeeper = (ITimeKeeper) ProcessorDay.GetInstance();
        timekeeper.addTimeSlave(this);
    }

    // ==================== Government Tab ====================

    private VBox buildGovernmentTab() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #1a1a2e;");

        // 국고 현황
        Label sectionTitle = new Label("국고 현황");
        sectionTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");

        VBox balanceBox = new VBox(6);
        balanceBox.setPadding(new Insets(10));
        balanceBox.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8; -fx-border-color: #27ae60; -fx-border-radius: 8; -fx-border-width: 2;");

        govBalanceLabel = createValueLabel("0");
        govTotalTaxLabel = createValueLabel("0");
        govMonthlyEarnedLabel = createValueLabel("0");
        govMonthlyCorpLabel = createValueLabel("0");
        govEarnedTaxLabel = createValueLabel("0");
        govCorpTaxLabel = createValueLabel("0");

        balanceBox.getChildren().addAll(
                createInfoRow("국고 잔액", govBalanceLabel),
                createInfoRow("누적 세수", govTotalTaxLabel),
                new Separator(),
                createInfoRow("이번달 소득세", govMonthlyEarnedLabel),
                createInfoRow("이번달 법인세", govMonthlyCorpLabel),
                new Separator(),
                createInfoRow("누적 소득세", govEarnedTaxLabel),
                createInfoRow("누적 법인세", govCorpTaxLabel)
        );

        // 세율 조절
        Label taxTitle = new Label("세율 조절");
        taxTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        taxTitle.setPadding(new Insets(10, 0, 0, 0));

        VBox taxBox = new VBox(8);
        taxBox.setPadding(new Insets(10));
        taxBox.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8; -fx-border-color: #e67e22; -fx-border-radius: 8; -fx-border-width: 2;");

        // 근로소득세
        earnedTaxValueLabel = new Label("6.0%");
        earnedTaxValueLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14; -fx-font-weight: bold;");

        earnedTaxSlider = new Slider(0, 50, 6.0);
        earnedTaxSlider.setShowTickLabels(true);
        earnedTaxSlider.setShowTickMarks(true);
        earnedTaxSlider.setMajorTickUnit(10);
        earnedTaxSlider.setBlockIncrement(0.5);
        earnedTaxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            float rate = Math.round(newVal.floatValue() * 2) / 2.0f;
            earnedTaxValueLabel.setText(rate + "%");
            applyTaxRate("earned", rate);
        });

        Label earnedLabel = new Label("근로소득세");
        earnedLabel.setStyle("-fx-text-fill: #b0b0b0; -fx-font-size: 13;");

        // 법인세
        corpTaxValueLabel = new Label("10.0%");
        corpTaxValueLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14; -fx-font-weight: bold;");

        corpTaxSlider = new Slider(0, 50, 10.0);
        corpTaxSlider.setShowTickLabels(true);
        corpTaxSlider.setShowTickMarks(true);
        corpTaxSlider.setMajorTickUnit(10);
        corpTaxSlider.setBlockIncrement(0.5);
        corpTaxSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            float rate = Math.round(newVal.floatValue() * 2) / 2.0f;
            corpTaxValueLabel.setText(rate + "%");
            applyTaxRate("corp", rate);
        });

        Label corpLabel = new Label("법인세");
        corpLabel.setStyle("-fx-text-fill: #b0b0b0; -fx-font-size: 13;");

        taxBox.getChildren().addAll(
                new HBox(10, earnedLabel, earnedTaxValueLabel),
                earnedTaxSlider,
                new HBox(10, corpLabel, corpTaxValueLabel),
                corpTaxSlider
        );

        root.getChildren().addAll(sectionTitle, balanceBox, taxTitle, taxBox);
        return root;
    }

    private HBox createInfoRow(String label, Label valueLabel) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        Label key = new Label(label);
        key.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13;");
        key.setMinWidth(120);
        row.getChildren().addAll(key, valueLabel);
        return row;
    }

    private Label createValueLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-text-fill: white; -fx-font-size: 13; -fx-font-weight: bold;");
        return lbl;
    }

    private void applyTaxRate(String type, float rate) {
        if (CWorld.getInstance() == null || CWorld.getInstance().getCountryList().isEmpty()) return;
        CCountry country = CWorld.getInstance().getCountryList().get(0);
        CGNationalTaxService tax = (CGNationalTaxService) country.getGovernmentMap().get(EGovernmentType.NationalTaxService);
        if (tax == null) return;

        if ("earned".equals(type)) {
            tax.setEarnedIncomeTaxRate(rate);
        } else {
            tax.setCorporateTaxRate(rate);
        }
    }

    private void updateGovernmentTab() {
        if (CWorld.getInstance() == null || CWorld.getInstance().getCountryList().isEmpty()) return;
        CCountry country = CWorld.getInstance().getCountryList().get(0);
        CGNationalTaxService tax = (CGNationalTaxService) country.getGovernmentMap().get(EGovernmentType.NationalTaxService);
        if (tax == null) return;

        govBalanceLabel.setText(UCurrency.getInstance().toString(tax.getNationalBalance(), ECurrency.Won));
        govTotalTaxLabel.setText(UCurrency.getInstance().toString(tax.getTotalTaxRevenue(), ECurrency.Won));
        govEarnedTaxLabel.setText(UCurrency.getInstance().toString(tax.getTotalEarnedIncomeTax(), ECurrency.Won));
        govCorpTaxLabel.setText(UCurrency.getInstance().toString(tax.getTotalCorporateTax(), ECurrency.Won));
        govMonthlyEarnedLabel.setText(UCurrency.getInstance().toString(tax.getMonthlyEarnedIncomeTax(), ECurrency.Won));
        govMonthlyCorpLabel.setText(UCurrency.getInstance().toString(tax.getMonthlyCorporateTax(), ECurrency.Won));
    }

    // ==================== Bank Tab ====================

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
        updateGovernmentTab();

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
                    "한국은행 (중앙은행)", "#e74c3c",
                    new String[]{
                            "기준금리 : " + baseRate + "%",
                            "총 통화 발행량 : " + cbTotal,
                            "현금보유량 : " + cbCash,
                            "국고 잔액 : " + UCurrency.getInstance().toString(centralBank.getNationalAccount().getRightsOfCash(), ECurrency.Won)
                    }
            );
            final long finalAllMadeMoney = allMadeMoney;
            centralCard.setOnMouseClicked(e -> showCentralBankDetail(centralBank, finalAllMadeMoney));
            bankCardPane.getChildren().add(centralCard);

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
                        bank.getName() + "은행", "#2980b9",
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
            peopleList.add(mohaw.getPeopleMap().get(mapIter.next()));
        }
        FrameMain.getInstance().addPeopleList(peopleList);

        CGMinistryOfTradeIndustryAndEnergy motia = (CGMinistryOfTradeIndustryAndEnergy) country.getGovernmentMap().get(EGovernmentType.TradeIndustryAndEnergy);
        Iterator<Integer> mapIter2 = motia.getCompanyMap().keySet().iterator();
        LinkedList<CCompany> companyList = new LinkedList<>();
        while (mapIter2.hasNext()) {
            companyList.add(motia.getCompanyMap().get(mapIter2.next()));
        }
        FrameMain.getInstance().addCompanyList(companyList);
    }

    // ==================== Bank Cards & Details ====================

    private VBox createBankCard(String title, String accentColor, String[] infoLines) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(12));
        card.setPrefWidth(220);
        String baseStyle = "-fx-background-color: #16213e; -fx-background-radius: 8; -fx-border-color: " + accentColor + "; -fx-border-radius: 8; -fx-border-width: 2; -fx-cursor: hand;";
        String hoverStyle = "-fx-background-color: #1a2744; -fx-background-radius: 8; -fx-border-color: " + accentColor + "; -fx-border-radius: 8; -fx-border-width: 2; -fx-cursor: hand;";
        card.setStyle(baseStyle);

        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-text-fill: white; -fx-font-size: 15; -fx-font-weight: bold;");
        card.getChildren().add(titleLbl);

        for (String line : infoLines) {
            Label lbl = new Label(line);
            lbl.setStyle("-fx-text-fill: #b0b0b0; -fx-font-size: 12;");
            lbl.setWrapText(true);
            card.getChildren().add(lbl);
        }

        card.setOnMouseEntered(e -> card.setStyle(hoverStyle));
        card.setOnMouseExited(e -> card.setStyle(baseStyle));
        return card;
    }

    private void showCentralBankDetail(CBCentral bank, long allMadeMoney) {
        bankDetailView.getChildren().clear();

        Button backBtn = new Button("< 돌아가기");
        backBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 13;");
        backBtn.setOnAction(e -> { bankDetailView.setVisible(false); bankCardListView.setVisible(true); });

        Label title = new Label("한국은행 (중앙은행)");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-weight: bold;");
        title.setPadding(new Insets(10, 0, 10, 0));

        VBox infoBox = new VBox(8);
        infoBox.setPadding(new Insets(10));
        infoBox.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8;");

        addDetailRow(infoBox, "기준금리", bank.getBaseInterestRate() + "%");
        addDetailRow(infoBox, "총 통화 발행량", UCurrency.getInstance().toString(allMadeMoney, ECurrency.Won));
        addDetailRow(infoBox, "현금보유량", UCurrency.getInstance().toString(bank.getBalance().getCash(), ECurrency.Won));
        addDetailRow(infoBox, "국고 잔액", UCurrency.getInstance().toString(bank.getNationalAccount().getRightsOfCash(), ECurrency.Won));
        addDetailRow(infoBox, "등록 시중은행 수", bank.getBankList().size() + "개");

        // Controls
        Label ctrlTitle = new Label("조작");
        ctrlTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        ctrlTitle.setPadding(new Insets(15, 0, 5, 0));

        VBox ctrlBox = new VBox(10);
        ctrlBox.setPadding(new Insets(10));
        ctrlBox.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8; -fx-border-color: #f39c12; -fx-border-radius: 8; -fx-border-width: 2;");

        // Base interest rate slider
        Label rateLabel = new Label("기준금리: " + bank.getBaseInterestRate() + "%");
        rateLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13;");

        Slider rateSlider = new Slider(0, 20, bank.getBaseInterestRate());
        rateSlider.setShowTickLabels(true);
        rateSlider.setShowTickMarks(true);
        rateSlider.setMajorTickUnit(5);
        rateSlider.setBlockIncrement(0.25);
        rateSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            float rate = Math.round(newVal.floatValue() * 4) / 4.0f;
            rateLabel.setText("기준금리: " + rate + "%");
        });

        Button rateApplyBtn = new Button("금리 적용");
        rateApplyBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 13; -fx-font-weight: bold;");
        rateApplyBtn.setOnAction(e -> {
            float rate = Math.round(rateSlider.getValue() * 4) / 4.0f;
            bank.setBaseInterestRate(rate);
        });

        // Money printing
        Label printLabel = new Label("통화 발행 (조 단위)");
        printLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13;");

        TextField printField = new TextField("1");
        printField.setPrefWidth(100);
        printField.setStyle("-fx-font-size: 13;");

        Button printBtn = new Button("발행");
        printBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 13; -fx-font-weight: bold;");
        printBtn.setOnAction(e -> {
            try {
                long trillion = Long.parseLong(printField.getText().trim());
                bank.makeMoney(trillion * 1_000_000_000_000L);
            } catch (NumberFormatException ex) { /* ignore */ }
        });

        // Release money to commercial bank
        Label releaseLabel = new Label("시중은행 배분 (조 단위)");
        releaseLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13;");

        ComboBox<String> bankCombo = new ComboBox<>();
        for (CBank b : bank.getBankList()) {
            bankCombo.getItems().add(b.getName() + "은행");
        }
        if (!bankCombo.getItems().isEmpty()) bankCombo.getSelectionModel().selectFirst();

        TextField releaseField = new TextField("1");
        releaseField.setPrefWidth(80);
        releaseField.setStyle("-fx-font-size: 13;");

        Button releaseBtn = new Button("배분");
        releaseBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 13; -fx-font-weight: bold;");
        releaseBtn.setOnAction(e -> {
            int idx = bankCombo.getSelectionModel().getSelectedIndex();
            if (idx >= 0 && idx < bank.getBankList().size()) {
                try {
                    long trillion = Long.parseLong(releaseField.getText().trim());
                    bank.releaseMoney(bank.getBankList().get(idx), trillion * 1_000_000_000_000L);
                } catch (NumberFormatException ex) { /* ignore */ }
            }
        });

        ctrlBox.getChildren().addAll(
                rateLabel, rateSlider, rateApplyBtn,
                new Separator(),
                printLabel, new HBox(5, printField, printBtn),
                new Separator(),
                releaseLabel, new HBox(5, bankCombo, releaseField, releaseBtn)
        );

        ScrollPane scrollPane = new ScrollPane(new VBox(10, backBtn, title, infoBox, ctrlTitle, ctrlBox));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #1a1a2e; -fx-background-color: #1a1a2e;");

        bankDetailView.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        bankCardListView.setVisible(false);
        bankDetailView.setVisible(true);
    }

    private void showCommercialBankDetail(CBCommercial bank, long creditCurrency, float baseRate) {
        bankDetailView.getChildren().clear();

        Button backBtn = new Button("< 돌아가기");
        backBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-font-size: 13;");
        backBtn.setOnAction(e -> { bankDetailView.setVisible(false); bankCardListView.setVisible(true); });

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

        // Spread rate control
        Label ctrlTitle = new Label("조작");
        ctrlTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        ctrlTitle.setPadding(new Insets(15, 0, 5, 0));

        VBox ctrlBox = new VBox(10);
        ctrlBox.setPadding(new Insets(10));
        ctrlBox.setStyle("-fx-background-color: #16213e; -fx-background-radius: 8; -fx-border-color: #f39c12; -fx-border-radius: 8; -fx-border-width: 2;");

        Label spreadLabel = new Label("가산금리: " + bank.getSpreadInterestRate() + "%");
        spreadLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13;");

        Slider spreadSlider = new Slider(0, 20, bank.getSpreadInterestRate());
        spreadSlider.setShowTickLabels(true);
        spreadSlider.setShowTickMarks(true);
        spreadSlider.setMajorTickUnit(5);
        spreadSlider.setBlockIncrement(0.1);
        spreadSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            float rate = Math.round(newVal.floatValue() * 10) / 10.0f;
            spreadLabel.setText("가산금리: " + rate + "%");
        });

        Button spreadApplyBtn = new Button("금리 적용");
        spreadApplyBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 13; -fx-font-weight: bold;");
        spreadApplyBtn.setOnAction(e -> {
            float rate = Math.round(spreadSlider.getValue() * 10) / 10.0f;
            bank.setSpreadInterestRate(rate);
        });

        ctrlBox.getChildren().addAll(spreadLabel, spreadSlider, spreadApplyBtn);

        ScrollPane scrollPane = new ScrollPane(new VBox(10, backBtn, title, infoBox, ctrlTitle, ctrlBox));
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #1a1a2e; -fx-background-color: #1a1a2e;");

        bankDetailView.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
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
