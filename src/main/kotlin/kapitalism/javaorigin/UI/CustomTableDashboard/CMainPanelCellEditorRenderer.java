package kapitalism.javaorigin.UI.CustomTableDashboard;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.EventObject;

public class CMainPanelCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

	private static final long serialVersionUID = 1L;
	private CMainCompCellPanel renderer = new CMainCompCellPanel(null ,"",0,0,0,0);
	private CMainCompCellPanel editor = new CMainCompCellPanel(null ,"",0,0,0,0);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		renderer.setComp((CMainTableComp) value);
		return renderer;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		editor.setComp((CMainTableComp) value);
		return editor;
	}

	@Override
	public Object getCellEditorValue() {
		return editor.getComp();
	}

	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return false;
	}
}
