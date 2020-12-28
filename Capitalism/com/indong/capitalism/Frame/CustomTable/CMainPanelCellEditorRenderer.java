package com.indong.capitalism.Frame.CustomTable;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class CMainPanelCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

	private static final long serialVersionUID = 1L;
	private CMainCompCellPanel renderer = new CMainCompCellPanel("",0);
	private CMainCompCellPanel editor = new CMainCompCellPanel("",0);

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
