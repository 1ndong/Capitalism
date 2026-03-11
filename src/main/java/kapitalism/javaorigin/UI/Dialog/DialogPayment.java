package kapitalism.javaorigin.UI.Dialog;

import kapitalism.javaorigin.Classes.Bank.CBAccount;
import kapitalism.javaorigin.Classes.CBeing;
import kapitalism.javaorigin.Classes.CCompany;
import kapitalism.javaorigin.DataCenter.DataCenter;
import kapitalism.javaorigin.DataStructure.DPayment;
import kapitalism.javaorigin.DataStructure.DServiceItem;
import kapitalism.javaorigin.Enum.EAccountType;
import kapitalism.javaorigin.Enum.ECurrency;
import kapitalism.javaorigin.Enum.EServicePropertyType;
import kapitalism.javaorigin.Interface.IBankService;
import kapitalism.javaorigin.Property.PAAccount;
import kapitalism.javaorigin.Property.PCompanyData;
import kapitalism.javaorigin.Util.UCurrency;

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
    private JTable table;
    //private JLabel labelAccountBalance;
    private JRadioButton cashBtn;
    private JRadioButton cardBtn;
    private DPayment payment;

    private LinkedList<PAAccount> beingAccountList = new LinkedList<PAAccount>();

    private boolean result = false;

    public DialogPayment(DPayment payment)
    {
        this.payment = payment;
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
        labelStuffName = new JLabel(payment.getTargetService().getProperty(EServicePropertyType.Model));
        setComponentProperty(labelStuffName);
        labelStuffName.setBounds(cx,cy,cw,ch);
        panel.add(labelStuffName);
        //
        labelStuffPrice = new JLabel(payment.getTargetService().getProperty(EServicePropertyType.Price));
        setComponentProperty(labelStuffPrice);
        labelStuffPrice.setBounds(cw,cy,cw,ch);
        panel.add(labelStuffPrice);
        //
        cardBtn = new JRadioButton("계좌 이체");
        setComponentProperty(cardBtn);
        cardBtn.setBounds(cx,cy + ch ,cw,ch);
        panel.add(cardBtn);
        //
        cashBtn = new JRadioButton("현금 결제");
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

        table = new JTable(model);

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
        //labelAccountBalance = new JLabel("결제후예상잔액");
        //setComponentProperty(labelAccountBalance);
        //labelAccountBalance.setBounds(cw,cashBtn.getY() + (cashBtn.getHeight() * 3) , cw , (int)(rect.height * 0.1f));
        //panel.add(labelAccountBalance);
        //
        JButton confirmBtn = new JButton("확인");
        setComponentProperty(confirmBtn);
        confirmBtn.setBounds(cw,(int)(rect.height - ((rect.height * 0.1f) * 3)),cw, (int)(rect.height * 0.1f));
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cashBtn.isSelected() == false && cardBtn.isSelected() == false)
                {
                    JOptionPane.showMessageDialog(null,"결제방식을 선택하세요","선택",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else if(table.getSelectedRow() == -1 && cardBtn.isSelected() == true)
                {
                    JOptionPane.showMessageDialog(null,"계좌를 선택하세요","선택",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else if(cashBtn.isSelected())
                {
                    if(payment.getTargetBeing().getWallet().getCash() <
                            UCurrency.getInstance().toOriginValue(payment.getTargetService().getProperty(EServicePropertyType.Price),ECurrency.Won))
                    {
                        JOptionPane.showMessageDialog(null,"잔여 현금이 부족합니다","선택",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                else if(cardBtn.isSelected())
                {
                    int row = table.getSelectedRow();
                    String balance = (String)table.getValueAt(row,2);

                    if(UCurrency.getInstance().toOriginValue(balance,ECurrency.Won) <
                            UCurrency.getInstance().toOriginValue(payment.getTargetService().getProperty(EServicePropertyType.Price),ECurrency.Won))
                    {
                        JOptionPane.showMessageDialog(null,"계좌 잔액이 부족합니다","선택",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                CBeing being = payment.getTargetBeing();
                DServiceItem service = payment.getTargetService();
                CCompany serviceCompany = DataCenter.getInstance().findCompanyByName(service.getProperty(EServicePropertyType.Brand));

                if(cashBtn.isSelected())
                {
                    /*
                    1. 사람 cash 에서 
                    2. servicecompany의 대표 deposit계좌로 이동
                     */
                    if(serviceCompany.getBasicData() instanceof PCompanyData)
                    {
                        PAAccount mainDepositAccountInfo = ((PCompanyData)serviceCompany.getBasicData()).getMainDepositAccount();
                        boolean result = ((IBankService)mainDepositAccountInfo.getBank()).transferCash(mainDepositAccountInfo,being.getWallet()
                                ,UCurrency.getInstance().toOriginValue(payment.getTargetService().getProperty(EServicePropertyType.Price),ECurrency.Won));
                        if(result == false)
                            return;
                    }
                }
                else if(cardBtn.isSelected())
                {
                    /*
                    1. 사람 선택된 계좌로
                    2. servicecompany의 대표 deposit계좌로 이동
                     */
                    if(serviceCompany.getBasicData() instanceof PCompanyData)
                    {
                        PAAccount mainDepositAccountInfo = ((PCompanyData)serviceCompany.getBasicData()).getMainDepositAccount();
                        boolean result = ((IBankService)mainDepositAccountInfo.getBank()).sendMoney(beingAccountList.get(table.getSelectedRow()),mainDepositAccountInfo
                                ,UCurrency.getInstance().toOriginValue(payment.getTargetService().getProperty(EServicePropertyType.Price),ECurrency.Won));
                        if(result == false)
                            return;
                    }
                }

                result = true;
                dispose();
            }
        });
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
        LinkedList<PAAccount> list = being.getBasicData().getPropertyAsset().getAccountList();
        for(int i = 0 ; i < list.size() ; i++)
        {
            PAAccount account = list.get(i);
            if(account.getAccountType() == EAccountType.Deposit)
            {
                CBAccount realAccount = ((IBankService)account.getBank()).findAccount(account.getOwnerName(),account.getAccountNumber());
                //"[예금 계좌]","[계좌 번호]","[잔액]"
                String[] item = new String[]{account.getBank().getName()
                        ,""+account.getAccountNumber()
                        ,UCurrency.getInstance().toString(realAccount.getRightsOfCash(),ECurrency.Won)};
                model.addRow(item);
                beingAccountList.add(account);
            }
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
