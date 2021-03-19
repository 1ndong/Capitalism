package com.indong.capitalism.UI.Dialog;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.DataCenter.DataCenter;
import com.indong.capitalism.DataStructure.DPayment;
import com.indong.capitalism.Enum.ECurrency;
import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.Interface.IBankService;
import com.indong.capitalism.Interface.ISearchable;
import com.indong.capitalism.Util.UCurrency;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class DialogPayment extends JDialog {

    /*
    살 물건이름 label    가격 label
    라디오버튼(카드,현금)    현금 잔액 표시될 label
    계좌리스트 테이블   선택시 잔액 표시될 label
    확인버튼    취소버튼
    * */

    private JLabel labelStuffName;
    private JLabel labelStuffPrice;
    private ButtonGroup buttonGroup;
    private JLabel labelCash;
    private DefaultTableModel model;
    private JLabel labelAccountBalance;

    private boolean result = false;

    //todo 결제하면 돈을 보내야됨
    //돈을 보내려면 어느계좌에서 보낼지 어떻게 보낼지
    //결제시스템 만들어야됨!!
    public DialogPayment(DPayment payment)
    {
        setModal(true);
        setTitle("결제");
        setLayout(null);

        int width = 1000;
        int height = 600;
        setSize(width,height);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width / 2) - (width/2);
        int y = (size.height / 2) - (height/2);
        setLocation(x,y);

        //
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0,86,168));
        panel.setSize(width,height);
        add(panel);
        //
        Rectangle rect = getBounds();
        int cx = 0;
        int cy = 0;
        int cw = rect.width / 2;
        int ch = (int)(rect.height * 0.1f);
        //
        labelStuffName = new JLabel(payment.getTargetService().getName());
        setComponentProperty(labelStuffName);
        labelStuffName.setBounds(cx,cy,cw,ch);
        panel.add(labelStuffName);
        //
        labelStuffPrice = new JLabel(UCurrency.getInstance().toString(payment.getTargetService().getValue(), ECurrency.Won));
        setComponentProperty(labelStuffPrice);
        labelStuffPrice.setBounds(cw,cy,cw,ch);
        panel.add(labelStuffPrice);
        //
        JRadioButton cardBtn = new JRadioButton("계좌 이체");
        setComponentProperty(cardBtn);
        cardBtn.setBounds(cx,cy + ch ,cw,ch);
        panel.add(cardBtn);
        //
        JRadioButton cashBtn = new JRadioButton("현금 결제");
        setComponentProperty(cashBtn);
        cashBtn.setBounds(cw,cy+ch,cw,ch);
        panel.add(cashBtn);
        //
        buttonGroup = new ButtonGroup();
        buttonGroup.add(cardBtn);
        buttonGroup.add(cashBtn);
        //
        CBeing being = payment.getTargetBeing();
        //account list
        String[] colName = new String[] {"[예금 계좌]","[계좌 번호]","[잔액]"};
        model = new DefaultTableModel(colName,0);

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        int spx = 0;
        int spy = cardBtn.getY() + cardBtn.getHeight();
        int spw = cw;
        int sph = (int)(rect.height * 0.7);
        scrollPane.setBounds(spx, spy, spw, sph);
        table.setBounds(scrollPane.getBounds());
        table.setFillsViewportHeight(true);

        initTable(payment.getTargetBeing());

        panel.add(scrollPane);
        //
        JLabel labelName = new JLabel(being.getBasicData().getName());
        setComponentProperty(labelName);
        labelName.setBounds(cw,cashBtn.getY() + cashBtn.getHeight() , cw , (int)(rect.height * 0.1f));
        panel.add(labelName);
        //being의 현금 표시하기
        String str = UCurrency.getInstance().toString(being.getWallet().getCash(),ECurrency.Won);
        if(str.isEmpty())
            str = "현금 없음";
        labelCash = new JLabel(str);
        setComponentProperty(labelCash);
        labelCash.setBounds(cw,cashBtn.getY() + (cashBtn.getHeight() * 2) , cw , (int)(rect.height * 0.1f));
        panel.add(labelCash);
        //
        labelAccountBalance = new JLabel("계좌잔액");
        setComponentProperty(labelAccountBalance);
        labelAccountBalance.setBounds(cw,cashBtn.getY() + (cashBtn.getHeight() * 3) , cw , (int)(rect.height * 0.1f));
        panel.add(labelAccountBalance);
        //
        JButton confirmBtn = new JButton("확인");
        setComponentProperty(confirmBtn);
        confirmBtn.setBounds(cw,(int)(rect.height - ((rect.height * 0.1f) * 3)),cw, (int)(rect.height * 0.1f));
        panel.add(confirmBtn);
        //
        JButton cancelBtn = new JButton("취소");
        setComponentProperty(cancelBtn);
        cancelBtn.setBounds(cw,(int)(rect.height - ((rect.height * 0.1f) * 2)),cw, (int)(rect.height * 0.1f));
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelBtn);
        //

        setVisible(true);
    }

    private void initTable(CBeing being)
    {
        //뱅크 돌면서 이사람 계좌 다 가져오기
        //그 계좌중에 deposit만 리스트에 보여주기
        LinkedList<ISearchable> banklist = DataCenter.getInstance().getList(ESearchType.Bank.getValue());
        for(int i = 0 ; i < banklist.size() ; i++)
        {
            IBankService bs = (IBankService) banklist.get(i);
            //todo
            //implement list = getaccountlist(name) at ibankservice
            //list 에서 deposit 찾아서
            //리스트에 넣어줌
        }
    }

    private void setComponentProperty(JComponent comp)
    {
        if(comp instanceof JLabel)
        {
            JLabel label = (JLabel)comp;
            label.setHorizontalAlignment(JLabel.CENTER);
            Font font = new Font("맑은 고딕",Font.BOLD,20);
            label.setFont(font);
            label.setForeground(Color.white);
        }
        else if(comp instanceof JRadioButton)
        {
            JRadioButton radio = (JRadioButton) comp;
            radio.setHorizontalAlignment(SwingConstants.CENTER);
            Font font = new Font("맑은 고딕",Font.BOLD,20);
            radio.setFont(font);
            radio.setBackground(new Color(7,54,66));
            radio.setForeground(Color.white);
        }
        else if(comp instanceof JButton)
        {
            JButton btn = (JButton) comp;
            btn.setHorizontalAlignment(SwingConstants.CENTER);
            Font font = new Font("맑은 고딕",Font.BOLD,20);
            btn.setFont(font);
            btn.setBackground(new Color(62,134,160));
            btn.setForeground(Color.white);
        }
    }

    public boolean getResult()
    {
        return result;
    }
}
