package GraphicalInterfaces.ShopPack;

import DataBase.DataBaseServices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.Map;

public class Bill extends JFrame {
    private JTextArea bill ;
    private JPanel btns ;
    private JButton cancel ;
    private JButton print ;
    private DataBaseServices dataBaseServices = new DataBaseServices();
    private Map<String,String> infos = dataBaseServices.getLogin();

    public Bill(JTable card){
        this.setBill(card);
        this.setBtns();
        this.setLayout(null);
        this.setSize(350,500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void setBill(JTable card) {
        bill = new JTextArea(20,20);
        bill.append("\tWELCOME IN "+infos.get("store name")+"\n\n");
        bill.append("---------------------------------------------------------------------------\n");
        bill.append("addresse : \t"+infos.get("addresse")+"\n");
        bill.append("phone number :\t"+infos.get("phoneNumber")+"\n");
        bill.append("email addresse : \t"+infos.get("email")+"\n");
        bill.append("---------------------------------------------------------------------------\n");
        bill.append("count\tname\tprice\tprice * count \n");
        bill.append("---------------------------------------------------------------------------\n");
        int total = 0;
        for(int i=0 ;i<card.getModel().getRowCount(); i++){
            bill.append(card.getValueAt(i,3).toString()+"\t"+card.getValueAt(i,1)+"\t"+card.getValueAt(i, 2)+"\t"+card.getValueAt(i,4)+"\n");
            total += Float.parseFloat(card.getValueAt(i,4).toString());
        }
        bill.append("---------------------------------------------------------------------------\n");
        bill.append("\n\n\n\ntotal:\t\t\t"+total+"\n");
        bill.append("---------------------------------------------------------------------------\n");
        bill.append("\t thank you for visit us \n");
        bill.setEditable(false);
        bill.setBounds(10,10,310,400);
        this.add(bill);
    }
    private void setBtns(){
        btns = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cancel = new JButton("cancel");
        cancel.setBackground(new Color(50,100,120));
        cancel.setForeground(Color.white);
        cancel.addActionListener(cancelAction());
        print = new JButton("print ");
        print.setBackground(new Color(50,100,120));
        print.setForeground(Color.white);
        print.addActionListener(printAction());
        btns.add(cancel);
        btns.add(print);
        btns.setBounds(10,420,310,40);
        this.add(btns);
    }
    private ActionListener cancelAction(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bill.this.dispose();
            }
        };
        return actionListener ;
    }
    private ActionListener printAction(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bill.print();
                } catch (PrinterException ex) {
                    throw new RuntimeException(ex);
                }
                Bill.this.dispose();
            }
        };
        return actionListener;
    }
}
