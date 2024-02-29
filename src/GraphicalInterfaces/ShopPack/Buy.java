package GraphicalInterfaces.ShopPack;

import DataBase.DataBaseServices;
import GraphicalInterfaces.HomePack.HomePanel;
import GraphicalInterfaces.SalesPACK.Sales;
import entities.Sale;
import entities.Product;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class Buy extends JPanel{
    private JPanel header ;
    private JLabel title ;
    private JPanel center;
    private JPanel leftOfCenter ;
    private JPanel search ;
    private JLabel searchText ;
    private JTextField searchField ;
    private JButton searchBtn ;
    private JTable tableOfProducts ;
    private DefaultTableModel modelProduct;
    private JPanel btnsPanel ;
    private JPanel card;
    private JTable cardTable ;
    private DefaultTableModel modelCard;
    private JLabel cardLabel ;
    private DataBaseServices dataBaseServices = new DataBaseServices();
    private JPanel cardBtns;
    private JButton clear;
    private JButton buy;
    private JPanel centerBtns ;
    private JButton addToCard ;
    private JButton removeFromCard;
    private JButton removeAllProductFromCard ;
    public Buy(){
        this.setLayout(new BorderLayout());
        this.setHeader();
        this.setCenter();
    }
    private void setHeader(){
        header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        title = new JLabel("SHOP");
        title.setForeground(new Color(160,160,160));
        title.setFont(new Font(null, Font.BOLD , 30));
        header.add(title);
        this.add(header , BorderLayout.PAGE_START);
    }
    private void setCenter(){
        center = new JPanel(new BorderLayout());
        center.setBorder(new EmptyBorder(10,10,10,10));
        setLeftOfCenter();
        setCenterBtns();
        setCard();
        this.add(center ,BorderLayout.CENTER);
    }

    public void setLeftOfCenter() {
        leftOfCenter = new JPanel(new BorderLayout());
        setSeach();
        setTableOfProducts();
        center.add(leftOfCenter,BorderLayout.WEST);
    }

    private void setSeach(){
        search = new JPanel();
        searchText = new JLabel("search");
        searchField = new JTextField(20);
        searchBtn = new JButton("seach");
        searchBtn.setForeground(Color.white);
        searchBtn.setBackground(new Color(50,100,120));
        searchBtn.addActionListener(searchAction());
        search.add(searchText);
        search.add(searchField);
        search.add(searchBtn);
        leftOfCenter.add(search,BorderLayout.PAGE_START);
    }
    private void setTableOfProducts(){
        modelProduct = new DefaultTableModel(new String[] {"id","name","price","count"},0);
        tableOfProducts = new JTable(modelProduct);
        ArrayList<Product> products = dataBaseServices.getAllProducts();
        for(int i=0;i<products.size();i++){
            String[] data = {products.get(i).getID(), products.get(i).getName(),String.valueOf(products.get(i).getPrice()),String.valueOf(products.get(i).getCount())};
            modelProduct.addRow(data);
        }
        leftOfCenter.add(new JScrollPane(tableOfProducts),BorderLayout.CENTER);
    }
    private void setCard(){
        card = new JPanel(new BorderLayout());
        setCardLabel();
        setCardTable();
        setCardBtns();
        center.add(card , BorderLayout.EAST);
    }

    public void setCardLabel() {
        cardLabel = new JLabel("card",SwingConstants.CENTER);
        cardLabel.setFont(new Font(null,Font.BOLD,23));
        cardLabel.setForeground(new Color(160,160,160));
        card.add(cardLabel, BorderLayout.PAGE_START);
    }

    public void setCardTable() {
        modelCard = new DefaultTableModel(new String[]{"id","name","price","count","price * unité" },0);
        cardTable = new JTable(modelCard);
        card.add(new JScrollPane(cardTable),BorderLayout.CENTER);
    }
    private void setCardBtns(){
        cardBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
        clear = btnSet("clear",this.removeAllFromTable());
        buy = btnSet("buy",this.buyAction());
        cardBtns.add(clear);
        cardBtns.add(buy);
        card.add(cardBtns , BorderLayout.PAGE_END);
    }
    private void setCenterBtns(){
        centerBtns = new JPanel(new GridLayout(3,1,0,60));
        centerBtns.setBorder(new EmptyBorder(100,10,100,10));
        addToCard = btnSet("add",this.addToCard());
        removeFromCard = btnSet("remove",this.removeToCard());
        removeAllProductFromCard = btnSet("remove all",this.removeAllFromTable());
        centerBtns.add(addToCard);
        centerBtns.add(removeFromCard);
        centerBtns.add(removeAllProductFromCard);
        center.add(centerBtns);
    }
    private JButton btnSet(String title,ActionListener actionListener){
        JButton btn = new JButton(title);
        btn.setForeground(Color.white);
        btn.setBackground(new Color(160,160,160));
        btn.addActionListener(actionListener);
        return btn ;
    }
    private ActionListener addToCard(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableOfProducts.getSelectedRow();
                if(selectedRow == -1){
                    JOptionPane.showMessageDialog(Buy.this , "you have to select product");
                }else {
                    String count = JOptionPane.showInputDialog(Buy.this , "enter the count of product");
                    if(count != null) {
                        int preCount = Integer.parseInt(tableOfProducts.getValueAt(selectedRow, 3).toString());
                        if( Integer.parseInt(count) <= preCount ) {
                            float priceOfAll = Integer.parseInt(count) * Float.parseFloat(tableOfProducts.getValueAt(selectedRow, 2).toString());
                            String[] data = {tableOfProducts.getValueAt(selectedRow,0).toString(),tableOfProducts.getValueAt(selectedRow, 1).toString(),tableOfProducts.getValueAt(selectedRow,2).toString(),count,String.valueOf(priceOfAll)};
                            modelCard.addRow(data);
                            tableOfProducts.setValueAt(String.valueOf(preCount - Integer.parseInt(count)), selectedRow, 3);
                            if(Integer.parseInt(tableOfProducts.getValueAt(selectedRow, 3).toString()) == 0){
                                modelProduct.removeRow(selectedRow);
                            }
                        }else {
                            JOptionPane.showMessageDialog(Buy.this , "unssufisant count");
                        }
                    }
                }
            }
        };
        return  actionListener;
    }
    private ActionListener removeToCard(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modelCard.getRowCount() != 0) {
                    int selectedRow = cardTable.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(Buy.this, "you have to select row ");
                    } else {
                        boolean findIt = false;
                        int size = modelProduct.getRowCount();
                        String idOfProducts = cardTable.getValueAt(selectedRow, 0).toString();
                        for (int i = 0; i < size; i++) {
                            if (tableOfProducts.getValueAt(i, 0).toString().equals(idOfProducts)) {
                                findIt = true;
                                int count = Integer.parseInt(tableOfProducts.getValueAt(i, 3).toString()) + Integer.parseInt(cardTable.getValueAt(selectedRow, 3).toString());
                                tableOfProducts.setValueAt(String.valueOf(count), i, 3);
                            }
                        }
                        if (!findIt) {
                            String[] data = {cardTable.getValueAt(selectedRow, 0).toString(), cardTable.getValueAt(selectedRow, 1).toString(), cardTable.getValueAt(selectedRow, 2).toString(), cardTable.getValueAt(selectedRow, 3).toString()};
                            modelProduct.addRow(data);
                        }
                        modelCard.removeRow(selectedRow);
                    }
                }else{
                    JOptionPane.showMessageDialog(Buy.this , "the card is empty");
                }
            }
        };
        return actionListener ;
    }
    private ActionListener removeAllFromTable(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modelCard.getRowCount() == 0){
                    JOptionPane.showMessageDialog(Buy.this , "the card is empty");
                }else {
                    if (JOptionPane.showConfirmDialog(Buy.this, "are you sure ? ") == JOptionPane.OK_OPTION) {
                        int size = modelCard.getRowCount();
                        for(int i=0 ;i<size ; i++){
                            boolean findIt = false;
                            int sizeOfProductTable = modelProduct.getRowCount();
                            String idOfProducts = cardTable.getValueAt(i, 0).toString();
                            for (int j = 0; j < size; j++) {
                                if (tableOfProducts.getValueAt(j, 0).toString().equals(idOfProducts)) {
                                    findIt = true;
                                    int count = Integer.parseInt(tableOfProducts.getValueAt(j, 3).toString()) + Integer.parseInt(cardTable.getValueAt(i, 3).toString());
                                    tableOfProducts.setValueAt(String.valueOf(count), j, 3);
                                }
                            }
                            if (!findIt) {
                                String[] data = {cardTable.getValueAt(i, 0).toString(), cardTable.getValueAt(i, 1).toString(), cardTable.getValueAt(i, 2).toString(), cardTable.getValueAt(i, 3).toString()};
                                modelProduct.addRow(data);
                            }
                        }
                        for (int i = 0; i < size; i++) {
                            modelCard.removeRow(0);
                        }
                    }
                }
            }
        };
        return actionListener ;
    }
    private ActionListener buyAction(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modelCard.getRowCount() == 0){
                    JOptionPane.showMessageDialog(Buy.this, "the card is empty");
                }else {
                    Bill bill = new Bill(cardTable);
                    int size = modelCard.getRowCount();
                    for(int i=0 ; i<size ; i++){
                        int count = 0;
                        for(int j=0;j<tableOfProducts.getRowCount(); j++) {
                            if (tableOfProducts.getValueAt(j, 0).toString().equals(cardTable.getValueAt(i, 0))) {
                                count = Integer.parseInt(tableOfProducts.getValueAt(j, 3).toString());
                            }
                        }
                        if(count != 0) {
                            dataBaseServices.decrementCount(modelCard.getValueAt(i, 0).toString(), count);
                        }else {
                            dataBaseServices.deleteProduct(modelCard.getValueAt(i, 0).toString());
                        }
                        Sale sale = new Sale(cardTable.getValueAt(i,0).toString(), cardTable.getValueAt(i,1).toString() , Integer.parseInt(cardTable.getValueAt(i,3).toString()),Float.parseFloat(cardTable.getValueAt(i,4).toString()),null,null);
                        dataBaseServices.insertSale(sale);
                        Sales.addSale(sale);
                        HomePanel.update();
                    }
                    Buy.this.cardTableClear();
                }
            }
        };
        return actionListener ;
    }
    private ActionListener searchAction(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                boolean findIt = false ;
                if(searchField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(Buy.this , "the field is empty");
                }else {
                    for(int i=0 ;i<tableOfProducts.getRowCount() ; i++){
                        if(searchField.getText().equals(tableOfProducts.getValueAt(i,0))){
                            tableOfProducts.setRowSelectionInterval(i,i);
                            findIt = true ;
                        }
                    }
                    if(!findIt){
                        JOptionPane.showMessageDialog(Buy.this , "the product not exist");
                    }
                }
            }
        };
        return actionListener ;
    }
    private void cardTableClear(){
        int size = cardTable.getRowCount();
        for(int i=0;i<size ; i++){
            modelCard.removeRow(0);
        }
    }
}
