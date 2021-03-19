package com.indong.capitalism.UI.CustomPanel.ControlPanel;

import com.indong.capitalism.Classes.Stuff.CStuff;
import com.indong.capitalism.DataCenter.DataCenter;
import com.indong.capitalism.DataStructure.DPayment;
import com.indong.capitalism.DataStructure.DService;
import com.indong.capitalism.Enum.ECurrency;
import com.indong.capitalism.Enum.ESectorType;
import com.indong.capitalism.Interface.ISearchable;
import com.indong.capitalism.Interface.ISector;
import com.indong.capitalism.UI.Dialog.DialogPayment;
import com.indong.capitalism.Util.UCurrency;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class SubmitClickListener implements ActionListener {

    private ControlPanel cp;
    private int stage = -1;

    public SubmitClickListener(ControlPanel cp)
    {
        this.cp = cp;
    }

    public int getStage()
    {
        return stage;
    }

    public void setStage(int n)
    {
        stage = n;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(cp.getCombobox().getSelectedIndex() == -1)
            return;

        String selStr = (String)cp.getCombobox().getSelectedItem();

        String asis = cp.getTextField().getText();
        StringBuilder sb= new StringBuilder(asis);

        sb.append(' ');
        sb.append(selStr);
        cp.getTextField().setText(sb.toString());

        if(getStage() == 0)
        {
            stage0();
        }
        else if(getStage() == 1)
        {
            stage1();
        }
        else
        {
            stageN();
        }

        setStage(getStage()+1);
    }

    private void stage0()
    {
        JComboBox<String> cb = cp.getCombobox();
        String selItem = cb.getSelectedItem().toString();

        ESectorType[] types = ESectorType.values();
        ESectorType result = null;
        for(int i = 0 ; i < types.length ; i++)
        {
            if(types[i].getLocaleValue(types[i]).equals(selItem) == true)
            {
                result = types[i];
                break;
            }
        }
        if(result != null)
        {
            LinkedList<ISearchable> list = DataCenter.getInstance().getList(-1);

            ArrayList<ISearchable> resultList = new ArrayList<ISearchable>();

            for(int i = 0 ; i < list.size() ; i++)
            {
                ISearchable temp = list.get(i);
                if(temp instanceof ISector)
                {
                    if(((ISector)temp).getSector() == result.getValue())
                    {
                        resultList.add(temp);
                    }
                }
            }

            cp.setZeroStage(resultList);
        }
    }

    private void stage1()
    {
        JComboBox<String> cb = cp.getCombobox();
        int selIndex = cb.getSelectedIndex();

        ArrayList<ISearchable> list = cp.getZeroStageList();
        ISearchable selItem = list.get(selIndex);
        if(selItem instanceof ISector)
        {
            ISector item = (ISector)selItem;
            ArrayList<DService> serviceList = item.getServiceList();
            if(serviceList != null)
            {//다시 리스트를 만들어서 콤보 세팅해야됨
                cp.setNextStage(serviceList);
            }
        }
    }

    private void stageN()
    {
        JComboBox<String> cb = cp.getCombobox();
        int selIndex = cb.getSelectedIndex();

        ArrayList<DService> list = cp.getCurrentStageList();
        DService selItem = list.get(selIndex);
        ArrayList<DService> nextList = selItem.getNextList();
        if(nextList == null)
        {//todo last 아이템이므로 결제를하고 stuff list에 들어가야됨
            StringBuilder sb = new StringBuilder("구매하시겠습니까?");
            sb.append("\n");
            sb.append("가격 : ");
            sb.append(UCurrency.getInstance().toString(selItem.getValue(),ECurrency.Won));

            int result = JOptionPane.showConfirmDialog(null,sb.toString(),"구매여부",JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION)
            {
                DialogPayment dp = new DialogPayment(new DPayment(cp.getTargetBeing() , selItem));

                if(dp.getResult() == true)
                {
                    String[] resultItem = new String[1];
                    resultItem[0] = cp.getTextField().getText();
                    cp.getModel().addRow(resultItem);

                    cp.getTargetBeing().getStuffList().add(new CStuff(resultItem[0]));
                }
            }

            cp.getCombobox().removeAllItems();
            cp.getCombobox().setSelectedIndex(-1);
            cp.getTextField().setText("");
        }
        else
        {
            cp.setNextStage(nextList);
        }
    }
}
