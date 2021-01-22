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
					
					final float RATIO_MAIN = 0.7f;
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
/*
todo list
1. 명령어 어떻게 표시해줄지 동적으로(버튼은? 입력해야되는거 나올때는?)
2. 은행에서 이자 지급하는거 구현해야됨 이자계산식부터 확인
3. 대출이자 원리금균등상환이랑 만기일시상환 구현
4. 국세청 만들어서 각종세금 뜯어가기 세율 정의
* */