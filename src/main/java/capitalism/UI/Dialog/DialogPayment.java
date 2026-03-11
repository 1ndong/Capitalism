package capitalism.UI.Dialog;

import capitalism.Classes.Bank.CBAccount;
import capitalism.Classes.CBeing;
import capitalism.Classes.CCompany;
import capitalism.DataCenter.DataCenter;
import capitalism.DataStructure.DPayment;
import capitalism.DataStructure.DServiceItem;
import capitalism.Enum.EAccountType;
import capitalism.Enum.ECurrency;
import capitalism.Enum.EServicePropertyType;
import capitalism.Interface.IBankService;
import capitalism.Property.PAAccount;
import capitalism.Property.PCompanyData;
import capitalism.Util.UCurrency;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.LinkedList;

public class DialogPayment {

    private boolean result = false;
    private LinkedList<PAAccount> beingAccountList = new LinkedList<>();

    public DialogPayment(DPayment payment) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("결제");
        dialog.setWidth(800);
        dialog.setHeight(500);

        CBeing being = payment.getTargetBeing();
        DServiceItem service = payment.getTargetService();

        // Item info
        Label nameLabel = new Label(service.getProperty(EServicePropertyType.Model));
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setMaxWidth(Double.MAX_VALUE);

        Label priceLabel = new Label(service.getProperty(EServicePropertyType.Price));
        priceLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");
        priceLabel.setAlignment(Pos.CENTER);
        priceLabel.setMaxWidth(Double.MAX_VALUE);

        HBox itemBox = new HBox(10, nameLabel, priceLabel);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        HBox.setHgrow(priceLabel, Priority.ALWAYS);

        // Radio buttons
        ToggleGroup paymentGroup = new ToggleGroup();
        RadioButton cardBtn = new RadioButton("계좌 이체");
        cardBtn.setToggleGroup(paymentGroup);
        cardBtn.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");

        RadioButton cashBtn = new RadioButton("현금 결제");
        cashBtn.setToggleGroup(paymentGroup);
        cashBtn.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");

        HBox radioBox = new HBox(20, cardBtn, cashBtn);
        radioBox.setPadding(new Insets(5));

        // Account table
        ObservableList<String[]> accountData = FXCollections.observableArrayList();
        TableView<String[]> accountTable = new TableView<>(accountData);

        TableColumn<String[], String> bankCol = new TableColumn<>("예금 계좌");
        bankCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[0]));
        bankCol.setPrefWidth(200);

        TableColumn<String[], String> numCol = new TableColumn<>("계좌 번호");
        numCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[1]));
        numCol.setPrefWidth(150);

        TableColumn<String[], String> balCol = new TableColumn<>("잔액");
        balCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[2]));
        balCol.setPrefWidth(200);

        accountTable.getColumns().addAll(bankCol, numCol, balCol);

        // Init account table
        LinkedList<PAAccount> list = being.getBasicData().getPropertyAsset().getAccountList();
        for (PAAccount account : list) {
            if (account.getAccountType() == EAccountType.Deposit) {
                CBAccount realAccount = ((IBankService) account.getBank()).findAccount(account.getOwnerName(), account.getAccountNumber());
                accountData.add(new String[]{
                        account.getBank().getName(),
                        "" + account.getAccountNumber(),
                        UCurrency.getInstance().toString(realAccount.getRightsOfCash(), ECurrency.Won)
                });
                beingAccountList.add(account);
            }
        }

        // Being info
        Label beingNameLabel = new Label(being.getBasicData().getName());
        beingNameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");

        String cashStr = UCurrency.getInstance().toString(being.getWallet().getCash(), ECurrency.Won);
        if (cashStr.isEmpty()) cashStr = "현금 없음";
        Label cashLabel = new Label(cashStr);
        cashLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");

        VBox infoBox = new VBox(10, beingNameLabel, cashLabel);
        infoBox.setPadding(new Insets(10));

        HBox contentBox = new HBox(10, accountTable, infoBox);
        HBox.setHgrow(accountTable, Priority.ALWAYS);
        VBox.setVgrow(contentBox, Priority.ALWAYS);

        // Buttons
        Button confirmBtn = new Button("확인");
        confirmBtn.setStyle("-fx-background-color: #3e86a0; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        confirmBtn.setMaxWidth(Double.MAX_VALUE);
        confirmBtn.setPrefHeight(40);

        Button cancelBtn = new Button("취소");
        cancelBtn.setStyle("-fx-background-color: #3e86a0; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");
        cancelBtn.setMaxWidth(Double.MAX_VALUE);
        cancelBtn.setPrefHeight(40);

        confirmBtn.setOnAction(e -> {
            if (paymentGroup.getSelectedToggle() == null) {
                showAlert("결제방식을 선택하세요");
                return;
            }
            if (accountTable.getSelectionModel().getSelectedIndex() == -1 && cardBtn.isSelected()) {
                showAlert("계좌를 선택하세요");
                return;
            }
            if (cashBtn.isSelected()) {
                if (being.getWallet().getCash() <
                        UCurrency.getInstance().toOriginValue(service.getProperty(EServicePropertyType.Price), ECurrency.Won)) {
                    showAlert("잔여 현금이 부족합니다");
                    return;
                }
            }
            if (cardBtn.isSelected()) {
                int row = accountTable.getSelectionModel().getSelectedIndex();
                String balance = accountData.get(row)[2];
                if (UCurrency.getInstance().toOriginValue(balance, ECurrency.Won) <
                        UCurrency.getInstance().toOriginValue(service.getProperty(EServicePropertyType.Price), ECurrency.Won)) {
                    showAlert("계좌 잔액이 부족합니다");
                    return;
                }
            }

            CCompany serviceCompany = DataCenter.getInstance().findCompanyByName(service.getProperty(EServicePropertyType.Brand));

            if (cashBtn.isSelected()) {
                if (serviceCompany.getBasicData() instanceof PCompanyData) {
                    PAAccount mainDepositAccountInfo = ((PCompanyData) serviceCompany.getBasicData()).getMainDepositAccount();
                    boolean transferResult = ((IBankService) mainDepositAccountInfo.getBank()).transferCash(mainDepositAccountInfo, being.getWallet(),
                            UCurrency.getInstance().toOriginValue(service.getProperty(EServicePropertyType.Price), ECurrency.Won));
                    if (!transferResult) return;
                }
            } else if (cardBtn.isSelected()) {
                if (serviceCompany.getBasicData() instanceof PCompanyData) {
                    PAAccount mainDepositAccountInfo = ((PCompanyData) serviceCompany.getBasicData()).getMainDepositAccount();
                    boolean sendResult = ((IBankService) mainDepositAccountInfo.getBank()).sendMoney(
                            beingAccountList.get(accountTable.getSelectionModel().getSelectedIndex()),
                            mainDepositAccountInfo,
                            UCurrency.getInstance().toOriginValue(service.getProperty(EServicePropertyType.Price), ECurrency.Won));
                    if (!sendResult) return;
                }
            }

            result = true;
            dialog.close();
        });

        cancelBtn.setOnAction(e -> dialog.close());

        HBox btnBox = new HBox(10, confirmBtn, cancelBtn);
        HBox.setHgrow(confirmBtn, Priority.ALWAYS);
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);

        VBox root = new VBox(10, itemBox, radioBox, contentBox, btnBox);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #0056a8;");

        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("선택");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public boolean getResult() {
        return result;
    }
}
