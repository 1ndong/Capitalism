package kapitalism.javaorigin.UI.CustomTableCommandList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ControlPanelTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = null;
        if(row == 0)
            comp = new JTextField();
        else if(row == 1)
            comp = new JButton("btn");
        else if(row == 2)
            comp = new JRadioButton("radiobtn");
        else if(row == 3)
            comp = new JCheckBox("test");

        return comp;
    }
}
