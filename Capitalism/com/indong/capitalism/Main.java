package com.indong.capitalism;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Frame.FrameMain;

public class Main {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					double width = screenSize.getWidth();
					double height = screenSize.getHeight();
					
					final float RATIO_MAIN = 0.6f;
					
					double mainWidth = width * RATIO_MAIN;
					double logWidth = width - mainWidth;
					
					FrameLog.MakeLogFrame(mainWidth, logWidth, height);
					FrameLog.GetInstance().setVisible(true);
					FrameMain frame = new FrameMain(mainWidth,height);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
