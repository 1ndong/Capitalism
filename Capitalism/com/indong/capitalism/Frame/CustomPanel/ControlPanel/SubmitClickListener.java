package com.indong.capitalism.Frame.CustomPanel.ControlPanel;

import com.indong.capitalism.DataCenter.DataCenter;
import com.indong.capitalism.DataStructure.DService;
import com.indong.capitalism.Enum.ESectorType;
import com.indong.capitalism.Interface.ISearchable;
import com.indong.capitalism.Interface.ISector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class SubmitClickListener implements ActionListener {

    private ControlPanel cp;
    private int stage = -1;
    private ArrayList<DService> currentService = null;

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

    /*
    stage 0 소비 금
    * */

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(cp.getCombobox().getSelectedIndex() == -1)
            return;

        String selStr = (String)cp.getCombobox().getSelectedItem();

        String asis = cp.getTextField().getText();
        StringBuilder sb= new StringBuilder(asis);

        if(asis.isEmpty() == true)
        {
            sb.append(cp.getTargetBeing().getBasicData().getName());
        }

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
            JOptionPane.showMessageDialog(null,"last");
        }
        else
        {
            cp.setNextStage(nextList);
        }
    }
}
