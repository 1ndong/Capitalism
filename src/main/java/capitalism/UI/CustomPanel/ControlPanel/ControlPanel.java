package capitalism.UI.CustomPanel.ControlPanel;

import capitalism.Classes.CBeing;
import capitalism.Classes.CPeople;
import capitalism.Classes.CCompany;
import capitalism.Classes.CWorld;
import capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import capitalism.Enum.EGovernmentType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {

    private Label targetLabel;
    private CBeing targetBeing = null;

    public ControlPanel(double width, double height) {
        setPrefWidth(width);
        setPrefHeight(height);
        setPadding(new Insets(5));
        setStyle("-fx-background-color: #002b36;");

        targetLabel = new Label("대상을 선택하세요 (테이블에서 더블클릭)");
        targetLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        targetLabel.setMaxWidth(Double.MAX_VALUE);

        getChildren().add(targetLabel);
    }

    public void startCommandProcess(CBeing being) {
        targetBeing = being;
        targetLabel.setText(targetBeing.getBasicData().getName());
    }

    public void startCommandProcessByName(String name) {
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

    public void initPanel() {
        targetBeing = null;
        targetLabel.setText("대상을 선택하세요 (테이블에서 더블클릭)");
    }

    public CBeing getTargetBeing() {
        return targetBeing;
    }
}
