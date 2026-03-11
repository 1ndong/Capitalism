package kapitalism.javaorigin;

import kapitalism.javaorigin.UI.FrameControl;
import kapitalism.javaorigin.UI.FrameLog;
import kapitalism.javaorigin.UI.FrameMain;

import java.awt.*;

public class JavaMain {
	public JavaMain()
	{
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
	}
}
/*
todo list
2. 은행에서 이자 지급하는거 구현해야됨 이자계산식부터 확인
3. 대출이자 원리금균등상환이랑 만기일시상환 구현
4. 국세청 만들어서 각종세금 뜯어가기 세율 정의
* */