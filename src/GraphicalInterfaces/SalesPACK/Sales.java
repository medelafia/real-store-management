package GraphicalInterfaces.SalesPACK;

import DataBase.DataBaseServices;
import entities.Sale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Sales extends JPanel {
    private JPanel header ;
    private JLabel title ;
    private JPanel center;
    private JTable salesTable ;
    private DefaultTableModel salesModel ;
    private static DataBaseServices dataBaseServices = new DataBaseServices();
    private static ArrayList<Sale> sales = dataBaseServices.getAllSales();
    private JPanel footer ;
    private JPanel totalEarningContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JLabel totalEarningLabel = new JLabel("total earning");
    private JTextField totalEarningField = new JTextField(10);
    private JPanel totalSalesContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JLabel totalSalesLabel = new JLabel("total sales");
    private JTextField totalSalesField = new JTextField(10);
    public Sales(){
        this.setHeader();
        this.setCenter();
        this.setFooter();
    }
    private void setHeader(){
        header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        title = new JLabel("SALES");
        this.setLayout(new BorderLayout());
        title.setForeground(new Color(160,160,160));
        title.setFont(new Font(null, Font.BOLD , 30));
        header.add(title);
        this.add(header , BorderLayout.PAGE_START);
    }
    private void setCenter(){
        center = new JPanel(null);
        setTable();
        this.add(center , BorderLayout.CENTER);
    }
    private void setTable(){
        String[] data = {"product id".toUpperCase(),"product name".toUpperCase(),"unités".toUpperCase(),"price of unité".toUpperCase(),"price * unités".toUpperCase(),"date".toUpperCase()};
        salesModel = new DefaultTableModel(data,0);
        salesTable = new JTable(salesModel);
        JScrollPane scrollPane = new JScrollPane(salesTable);
        scrollPane.setBounds(10,10,1000,400);
        setSalesInTable();
        center.add(scrollPane);
    }
    private void setSalesInTable(){
        for(int i=0 ;i<sales.size(); i++){
            String id = sales.get(i).getIdOfProduct();
            String name = sales.get(i).getNameOfProduct();
            int count = sales.get(i).getCount();
            float totalPrice = sales.get(i).getTotalPrice();
            float unitePrice = totalPrice/count ;
            LocalDate date = sales.get(i).getDate();
            String day = sales.get(i).getDay();
            String[] data = {id,name,String.valueOf(count),String.valueOf(unitePrice),String.valueOf(totalPrice),date.toString()+" "+day};
            salesModel.addRow(data);
        }
    }
    private void setFooter(){
        footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalEarningField.setEnabled(false);
        totalEarningField.setText(String.valueOf(this.getMoneyEarning())+" DH");
        totalEarningContainer.add(totalEarningLabel);
        totalEarningContainer.add(totalEarningField);
        footer.add(totalEarningContainer);
        totalSalesField.setEnabled(false);
        totalSalesField.setText(String.valueOf(this.getAllSalesCount())+" product".toUpperCase());
        totalSalesContainer.add(totalSalesLabel);
        totalSalesContainer.add(totalSalesField);
        footer.add(totalSalesContainer);
        this.add(footer,BorderLayout.PAGE_END);
    }
    private float getMoneyEarning(){
        int size = salesTable.getRowCount();
        float total = 0;
        for(int i=0;i<size ;i++){
            total += Float.parseFloat(salesTable.getValueAt(i,4).toString());
        }
        return total;
    }
    private int getAllSalesCount(){
        int size = salesTable.getRowCount();
        int total = 0;
        for(int i=0;i<size ;i++){
            total += Integer.parseInt(salesTable.getValueAt(i,2).toString());
        }
        return total;
    }
    public static void addSale(Sale sale) {
        LocalDate localDate = LocalDate.now();
        sale.setDate(localDate);
        sales.add(sale);
    }
}
