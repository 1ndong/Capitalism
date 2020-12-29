package com.indong.capitalism;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import com.indong.capitalism.Frame.FrameControl;
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
					double height = screenSize.getHeight()-50/*작업표시줄*/;
					
					final float RATIO_MAIN = 0.6f;
					final float RATIO_LOG_HEIGHT = 0.4f;
					
					double mainWidth = width * RATIO_MAIN;
					double logWidth = width - mainWidth;
					
					double logheight = height * RATIO_LOG_HEIGHT;
					FrameLog.MakeLogFrame(mainWidth, logWidth, logheight);
					FrameLog.getInstance().setVisible(true);

					double controlheight = height - logheight;
					FrameControl.MakeControlFrame(mainWidth, logheight, logWidth, controlheight);
					FrameControl.getInstance().setVisible(true);
					
					FrameMain.MakeMainFrame(mainWidth, height);
					FrameMain.getInstance().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
