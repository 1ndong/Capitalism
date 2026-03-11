package capitalism.UI;

import capitalism.Classes.Bank.CBAccount;
import capitalism.Classes.Bank.CBCommercial;
import capitalism.Classes.CCompany;
import capitalism.Classes.CCountry;
import capitalism.Classes.CPeople;
import capitalism.Classes.CWorld;
import capitalism.DataStructure.DBankMember;
import capitalism.DataStructure.DCareTaker;
import capitalism.DataStructure.DHCentralBank;
import capitalism.DataStructure.DTime;
import capitalism.Enum.EAccountType;
import capitalism.Enum.ECurrency;
import capitalism.Interface.IBankService;
import capitalism.Interface.ITime;
import capitalism.Interface.ITimeKeeper;
import capitalism.Processor.ProcessorDay;
import capitalism.Processor.ProcessorMain;
import capitalism.Property.PAAccount;
import capitalism.UI.CustomPanel.DashBoardPanel;
import capitalism.Util.UCurrency;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedList;

public class FrameMain implements ITime {

    private static FrameMain instance;
    private DashBoardPanel dashboard;
    private ObservableList<String[]> peopleData;
    private ObservableList<String[]> companyData;
    private TableView<String[]> peopleTable;
    private TableView<String[]> companyTable;

    // Chart
    private PieChart moneyChart;
    private VBox chartStatsBox;

    // UI throttle
    private long lastUpdateTime = 0;
    private boolean updatePending = false;
    private static final long UPDATE_INTERVAL_MS = 200;

    public static void MakeMainFrame(Stage primaryStage, double width, double height) {
        instance = new FrameMain(primaryStage, width, height);
        instance.updateComponent();
    }

    public static FrameMain getInstance() {
        return instance;
    }

    private FrameMain(Stage primaryStage, double width, double height) {
        primaryStage.setTitle("Capitalism");
        primaryStage.setX(0);
        primaryStage.setY(0);

        double dashboardWidth = width * 0.3;

        dashboard = new DashBoardPanel(dashboardWidth, height);

        // People table
        peopleData = FXCollections.observableArrayList();
        peopleTable = createEntityTable(peopleData, "시민");

        // Company table
        companyData = FXCollections.observableArrayList();
        companyTable = createEntityTable(companyData, "기업");

        VBox tableBox = new VBox(peopleTable, companyTable);
        VBox.setVgrow(peopleTable, Priority.ALWAYS);
        VBox.setVgrow(companyTable, Priority.ALWAYS);

        // Chart tab content
        moneyChart = new PieChart();
        moneyChart.setTitle("통화 분포");
        moneyChart.setLegendSide(Side.BOTTOM);
        moneyChart.setLabelsVisible(true);
        moneyChart.setStyle("-fx-font-size: 12;");

        chartStatsBox = new VBox(8);
        chartStatsBox.setPadding(new Insets(10));
        chartStatsBox.setStyle("-fx-background-color: #1a1a2e;");

        VBox chartContent = new VBox(10, moneyChart, chartStatsBox);
        chartContent.setPadding(new Insets(5));
        VBox.setVgrow(moneyChart, Priority.ALWAYS);

        // TabPane for tables/chart
        TabPane centerTabPane = new TabPane();
        centerTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tableTab = new Tab("시민/기업", tableBox);
        Tab chartTab = new Tab("통화 분포", chartContent);

        centerTabPane.getTabs().addAll(tableTab, chartTab);

        HBox leftPane = new HBox(dashboard, centerTabPane);
        HBox.setHgrow(centerTabPane, Priority.ALWAYS);

        // Right side: Log (full height)
        SplitPane mainSplit = new SplitPane(leftPane, FrameLog.getInstance().getRoot());
        mainSplit.setDividerPositions(0.7);

        Scene scene = new Scene(mainSplit, Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight());

        scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            ProcessorDay.GetInstance().Process();
        });

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        new ProcessorMain();

        ITimeKeeper timekeeper = (ITimeKeeper) ProcessorDay.GetInstance();
        timekeeper.addTimeSlave(this);
    }

    private TableView<String[]> createEntityTable(ObservableList<String[]> data, String title) {
        TableView<String[]> table = new TableView<>(data);

        TableColumn<String[], String> nameCol = new TableColumn<>("이름");
        nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[0]));
        nameCol.setPrefWidth(80);

        TableColumn<String[], String> assetCol = new TableColumn<>("총자산");
        assetCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[1]));
        assetCol.setPrefWidth(120);

        TableColumn<String[], String> depositCol = new TableColumn<>("예금");
        depositCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[2]));
        depositCol.setPrefWidth(120);

        TableColumn<String[], String> cashCol = new TableColumn<>("현금");
        cashCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[3]));
        cashCol.setPrefWidth(120);

        TableColumn<String[], String> loanCol = new TableColumn<>("대출");
        loanCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[4]));
        loanCol.setPrefWidth(120);

        table.getColumns().addAll(nameCol, assetCol, depositCol, cashCol, loanCol);
        table.setFixedCellSize(40);

        return table;
    }

    @Override
    public void dayChange(DTime newTime) {
        long now = System.currentTimeMillis();
        if (now - lastUpdateTime < UPDATE_INTERVAL_MS) {
            if (!updatePending) {
                updatePending = true;
                Platform.runLater(() -> {
                    updatePending = false;
                    lastUpdateTime = System.currentTimeMillis();
                    doUpdateComponent();
                });
            }
            return;
        }
        lastUpdateTime = now;
        Platform.runLater(this::doUpdateComponent);
    }

    public void updateComponent() {
        doUpdateComponent();
    }

    private void doUpdateComponent() {
        dashboard.updateComponent();
        updateChart();
    }

    private void updateChart() {
        if (CWorld.getInstance() == null || CWorld.getInstance().getCountryList().isEmpty())
            return;

        CCountry country = CWorld.getInstance().getCountryList().get(0);
        if (country == null) return;

        // Central bank cash (unreleased money)
        long centralBankCash = country.getCentralBank().getBalance().getCash();

        // Total issued money
        DCareTaker careTaker = country.getCentralBank().getCareTaker();
        long totalIssued = 0;
        for (int i = 0; i < careTaker.getList().size(); i++) {
            DHCentralBank history = (DHCentralBank) careTaker.getList().get(i);
            totalIssued += history.getMakeMoneyAmount();
        }

        // Bank reserves + credit currency per bank
        long totalBankReserves = 0;
        long totalDeposits = 0;
        long totalLoans = 0;
        for (int i = 0; i < country.getBankList().size(); i++) {
            CBCommercial bank = (CBCommercial) country.getBankList().get(i);
            totalBankReserves += bank.getBalance().getCash();

            for (int j = 0; j < bank.getBankMemberList().size(); j++) {
                DBankMember member = bank.getBankMemberList().get(j);
                for (int k = 0; k < member.getAccountList().size(); k++) {
                    CBAccount account = member.getAccountList().get(k);
                    if (account.getAccountType() == EAccountType.Deposit) {
                        totalDeposits += account.getRightsOfCash();
                    } else if (account.getAccountType() == EAccountType.Loan) {
                        totalLoans += account.getRightsOfCash();
                    }
                }
            }
        }

        // People/Company cash (held outside banks)
        long peopleCash = 0;
        long companyCash = 0;
        long peopleCount = 0;
        long companyCount = 0;

        if (peopleData != null) {
            peopleCount = peopleData.size();
        }
        if (companyData != null) {
            companyCount = companyData.size();
        }

        // Calculate from raw data
        for (int ci = 0; ci < CWorld.getInstance().getCountryList().size(); ci++) {
            CCountry c = CWorld.getInstance().getCountryList().get(ci);
            var mohaw = (capitalism.Classes.Government.CGMinistryOfHealthAndWelfare)
                    c.getGovernmentMap().get(capitalism.Enum.EGovernmentType.HealthAndWelfare);
            if (mohaw != null) {
                for (CPeople p : mohaw.getPeopleMap().values()) {
                    peopleCash += p.getWallet().getCash();
                }
            }
            var motia = (capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy)
                    c.getGovernmentMap().get(capitalism.Enum.EGovernmentType.TradeIndustryAndEnergy);
            if (motia != null) {
                for (CCompany comp : motia.getCompanyMap().values()) {
                    companyCash += comp.getWallet().getCash();
                }
            }
        }

        long publicCash = peopleCash + companyCash;

        // Update donut chart
        moneyChart.getData().clear();
        if (centralBankCash > 0)
            moneyChart.getData().add(new PieChart.Data("중앙은행 보유", centralBankCash));
        if (totalBankReserves > 0)
            moneyChart.getData().add(new PieChart.Data("은행 지급준비금", totalBankReserves));
        if (publicCash > 0)
            moneyChart.getData().add(new PieChart.Data("민간 보유 현금", publicCash));
        if (totalDeposits > 0)
            moneyChart.getData().add(new PieChart.Data("예금 (신용통화)", totalDeposits));
        if (totalLoans > 0)
            moneyChart.getData().add(new PieChart.Data("대출 잔액", totalLoans));

        // Update stats
        chartStatsBox.getChildren().clear();

        long m0 = totalIssued;
        long m1 = publicCash + totalDeposits;

        addStatRow("본원통화 (M0)", UCurrency.getInstance().toString(m0, ECurrency.Won));
        addStatRow("통화량 (M1)", UCurrency.getInstance().toString(m1, ECurrency.Won));
        addStatRow("신용승수", String.format("%.2fx", m0 > 0 ? (double) m1 / m0 : 0));
        addStatRow("", "");
        addStatRow("시민 수", peopleCount + "명");
        addStatRow("기업 수", companyCount + "개");
        addStatRow("시민 보유 현금", UCurrency.getInstance().toString(peopleCash, ECurrency.Won));
        addStatRow("기업 보유 현금", UCurrency.getInstance().toString(companyCash, ECurrency.Won));
        addStatRow("총 예금", UCurrency.getInstance().toString(totalDeposits, ECurrency.Won));
        addStatRow("총 대출", UCurrency.getInstance().toString(totalLoans, ECurrency.Won));
    }

    private void addStatRow(String label, String value) {
        if (label.isEmpty()) {
            chartStatsBox.getChildren().add(new Label(" "));
            return;
        }
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label lblKey = new Label(label);
        lblKey.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 13;");
        lblKey.setMinWidth(140);

        Label lblValue = new Label(value);
        lblValue.setStyle("-fx-text-fill: white; -fx-font-size: 13; -fx-font-weight: bold;");

        row.getChildren().addAll(lblKey, lblValue);
        chartStatsBox.getChildren().add(row);
    }

    public void addPeopleList(LinkedList<CPeople> peopleList) {
        Platform.runLater(() -> {
            peopleData.clear();
            for (CPeople people : peopleList) {
                String name = people.getBasicData().getName();
                long cash = people.getWallet().getCash();
                long deposit = 0;
                long loan = 0;
                for (int j = 0; j < people.getBasicData().getPropertyAsset().getAccountList().size(); j++) {
                    PAAccount infoaccount = people.getBasicData().getPropertyAsset().getAccountList().get(j);
                    IBankService bankservice = (IBankService) infoaccount.getBank();
                    CBAccount account = bankservice.findAccount(people.getBasicData().getName(), infoaccount.getAccountNumber());
                    if (account.getAccountType() == EAccountType.Deposit) {
                        deposit += account.getRightsOfCash();
                    } else if (account.getAccountType() == EAccountType.Loan) {
                        loan += account.getRightsOfCash();
                    }
                }
                long allAsset = deposit + cash + loan;
                peopleData.add(new String[]{
                        name,
                        UCurrency.getInstance().toString(allAsset, ECurrency.Won),
                        UCurrency.getInstance().toString(deposit, ECurrency.Won),
                        UCurrency.getInstance().toString(cash, ECurrency.Won),
                        UCurrency.getInstance().toString(loan, ECurrency.Won)
                });
            }
        });
    }

    public void addCompanyList(LinkedList<CCompany> companyList) {
        Platform.runLater(() -> {
            companyData.clear();
            for (CCompany company : companyList) {
                String name = company.getBasicData().getName();
                long cash = company.getWallet().getCash();
                long deposit = 0;
                long loan = 0;
                for (int j = 0; j < company.getBasicData().getPropertyAsset().getAccountList().size(); j++) {
                    PAAccount infoaccount = company.getBasicData().getPropertyAsset().getAccountList().get(j);
                    IBankService bankservice = (IBankService) infoaccount.getBank();
                    CBAccount account = bankservice.findAccount(company.getBasicData().getName(), infoaccount.getAccountNumber());
                    if (account.getAccountType() == EAccountType.Deposit) {
                        deposit += account.getRightsOfCash();
                    } else if (account.getAccountType() == EAccountType.Loan) {
                        loan += account.getRightsOfCash();
                    }
                }
                long allAsset = deposit + cash + loan;
                companyData.add(new String[]{
                        name,
                        UCurrency.getInstance().toString(allAsset, ECurrency.Won),
                        UCurrency.getInstance().toString(deposit, ECurrency.Won),
                        UCurrency.getInstance().toString(cash, ECurrency.Won),
                        UCurrency.getInstance().toString(loan, ECurrency.Won)
                });
            }
        });
    }
}
