package capitalism.UI;

import capitalism.Classes.Bank.CBAccount;
import capitalism.Classes.CCompany;
import capitalism.Classes.CPeople;
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
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedList;

public class FrameMain implements ITime {

    private static FrameMain instance;
    private DashBoardPanel dashboard;
    private ObservableList<String[]> peopleData;
    private ObservableList<String[]> companyData;
    private TableView<String[]> peopleTable;
    private TableView<String[]> companyTable;

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

        double dashboardWidth = width * 0.4;
        double tableWidth = width - dashboardWidth;

        dashboard = new DashBoardPanel(dashboardWidth, height);

        // People table
        peopleData = FXCollections.observableArrayList();
        peopleTable = createEntityTable(peopleData);

        // Company table
        companyData = FXCollections.observableArrayList();
        companyTable = createEntityTable(companyData);

        VBox tableBox = new VBox(peopleTable, companyTable);
        VBox.setVgrow(peopleTable, Priority.ALWAYS);
        VBox.setVgrow(companyTable, Priority.ALWAYS);
        tableBox.setPrefWidth(tableWidth);

        HBox root = new HBox(dashboard, tableBox);
        HBox.setHgrow(tableBox, Priority.ALWAYS);

        Scene scene = new Scene(root, width, height);

        scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            ProcessorDay.GetInstance().Process();
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        new ProcessorMain();

        ITimeKeeper timekeeper = (ITimeKeeper) ProcessorDay.GetInstance();
        timekeeper.addTimeSlave(this);
    }

    private TableView<String[]> createEntityTable(ObservableList<String[]> data) {
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

        // Detail button click -> start control panel
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                int idx = table.getSelectionModel().getSelectedIndex();
                if (idx >= 0 && idx < data.size()) {
                    String[] row = data.get(idx);
                    if (row.length > 5 && row[5] != null) {
                        // row[5] stores the being name for lookup
                        FrameControl.getInstance().getControlPanel().startCommandProcessByName(row[5]);
                    }
                }
            }
        });

        return table;
    }

    @Override
    public void dayChange(DTime newTime) {
        Platform.runLater(this::updateComponent);
    }

    public void updateComponent() {
        dashboard.updateComponent();
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
                        UCurrency.getInstance().toString(loan, ECurrency.Won),
                        name
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
                        UCurrency.getInstance().toString(loan, ECurrency.Won),
                        name
                });
            }
        });
    }
}
