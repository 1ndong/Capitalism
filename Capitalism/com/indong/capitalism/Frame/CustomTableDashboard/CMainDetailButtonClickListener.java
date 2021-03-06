package com.indong.capitalism.Frame.CustomTableDashboard;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Frame.FrameControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CMainDetailButtonClickListener implements ActionListener{

	private CBeing being;
	private CMainCompCellPanel parent;

	public CMainDetailButtonClickListener(CMainCompCellPanel parent)
	{
		this.parent = parent;
		setBeing(parent.getComp().being);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		FrameControl.getInstance().getControlPanel().startCommandProcess(getBeing());
	}

	public CBeing getBeing() {
		return being;
	}

	public void setBeing(CBeing being) {
		this.being = being;
	}
}
