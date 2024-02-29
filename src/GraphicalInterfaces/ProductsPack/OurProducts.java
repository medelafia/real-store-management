package GraphicalInterfaces.ProductsPack;

import DataBase.DataBaseServices;
import entities.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class OurProducts extends JPanel {
    private JPanel header;
    private JLabel title;
    private JPanel center;
    private JTable productsTable;
    DefaultTableModel table_model;
    private JPanel footer;
    private JButton addNewProduct;
    private JButton removeProduct;
    private JButton removeAll;
    private JButton search;
    private JButton updateProduct;
    private JButton export;

    private DataBaseServices dataBaseServices = new DataBaseServices();

    public OurProducts() {
        this.requestFocus();
        this.setLayout(new BorderLayout());
        this.setHeader();
        setBackground(Color.cyan);
        this.setCenter();
        this.setFooter();
    }

    private void setHeader() {
        header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        title = new JLabel("OUR PRODUCTS");
        title.setForeground(new Color(160, 160, 160));
        title.setFont(new Font(null, Font.BOLD, 30));
        header.add(title);
        this.add(header, BorderLayout.PAGE_START);
    }

    private void setCenter() {
        center = new JPanel(null);
        //center.setBackground(Color.BLACK);
        String[] column = {"id", "name", "price", "count"};
        //String data[][] = null ;
        table_model = new DefaultTableModel(column, 0);
        productsTable = new JTable(table_model);
        this.setTable();
        JScrollPane tablePane = new JScrollPane(productsTable);
        tablePane.setBounds(30, 20, 1000, 400);
        center.add(tablePane, BorderLayout.CENTER);
        this.add(center, BorderLayout.CENTER);
    }

    private void setFooter() {
        footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBorder(new EmptyBorder(10, 0, 10, 0));
        this.btnSetting(addNewProduct, "add product", addAction());
        this.btnSetting(removeAll, "remove all", removeAllAction());
        this.btnSetting(removeProduct, "remove product", removeProductAction());
        this.btnSetting(updateProduct, "update product", updateAction());
        this.btnSetting(search, "seach", searchAction());
        this.btnSetting(export, "export all product ", exportAction());
        this.add(footer, BorderLayout.PAGE_END);
    }

    private void btnSetting(JButton btn, String text, ActionListener actionListener) {
        btn = new JButton(text);
        btn.setBackground(new Color(64, 64, 64));
        btn.setForeground(Color.white);
        btn.addActionListener(actionListener);
        footer.add(btn);
    }

    private ActionListener addAction() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Form form = new Form();
                form.getSave().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nameText = form.getNameField().getText().trim();
                        String idText = form.getIdField().getText().trim();
                        float price = Float.parseFloat(form.getPriceField().getText().trim());
                        int count = Integer.parseInt(form.getCountField().getText().trim());
                        if (nameText.isEmpty() || idText.isEmpty() || form.getPriceField().getText().isEmpty() || form.getCountField().getText().isEmpty()) {
                            form.emptyField();
                        } else {
                            Product product = new Product(idText, nameText, price, count);
                            dataBaseServices.saveInDb(product);
                            form.dispose();
                            table_model.addRow(new String[]{idText, nameText, String.valueOf(price), String.valueOf(count)});
                        }
                    }
                });
            }
        };
        return actionListener;
    }

    private ActionListener removeProductAction() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (productsTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(OurProducts.this, "you have to select Product");
                } else {
                    String verify = JOptionPane.showInputDialog(OurProducts.this, "please enter the password ");
                    if(verify != null) {
                        if (verify.equals(dataBaseServices.getLogin().get(1))) {
                            dataBaseServices.deleteProduct(table_model.getValueAt(productsTable.getSelectedRow(), 0).toString());
                            table_model.removeRow(productsTable.getSelectedRow());
                        }else {
                            JOptionPane.showMessageDialog(OurProducts.this , "invalid password ");
                        }
                    }
                }
            }
        };
        return actionListener;
    }

    private ActionListener removeAllAction() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String verify = JOptionPane.showInputDialog(OurProducts.this, "please enter the password ");
                if(verify != null){
                    if ( verify.equals(dataBaseServices.getLogin().get(1))) {
                        dataBaseServices.deleteAllProduct();
                        int size = table_model.getRowCount();
                        for (int i = 0; i < size; i++) {
                            table_model.removeRow(0);
                        }
                    }else {
                        JOptionPane.showMessageDialog(OurProducts.this, "invalid password");
                    }
                } else {
                    JOptionPane.showMessageDialog(OurProducts.this, "invalid command");
                }
            }
        };
        return actionListener;
    }

    private ActionListener searchAction() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = table_model.getRowCount();
                String input = JOptionPane.showInputDialog(OurProducts.this, "enter the id or the name of product");
                if(input != null){
                    if (!input.isEmpty()) {
                        boolean found = false;
                        for (int i = 0; i < size; i++) {
                            if (table_model.getValueAt(i, 0).toString().trim().equalsIgnoreCase(input) || table_model.getValueAt(i, 1).toString().trim().equalsIgnoreCase(input)) {
                                productsTable.setRowSelectionInterval(i, i);
                                found = true;
                            }
                        }
                        if (!found) {
                            JOptionPane.showMessageDialog(OurProducts.this, "the product not found");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(OurProducts.this, "invalid command");
                }
            }
        };
        return actionListener;
    }

    private void setTable() {
        for (int i = 0; i < dataBaseServices.getAllProducts().size(); i++) {
            String[] data = {dataBaseServices.getAllProducts().get(i).getID(), dataBaseServices.getAllProducts().get(i).getName(), String.valueOf(dataBaseServices.getAllProducts().get(i).getPrice()), String.valueOf(dataBaseServices.getAllProducts().get(i).getCount())};
            table_model.addRow(data);
        }
    }

    private ActionListener updateAction() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (productsTable.getSelectedRow() != -1) {
                    int position = productsTable.getSelectedRow();
                    String name = table_model.getValueAt(position, 1).toString();
                    String id = table_model.getValueAt(position, 0).toString();
                    Form form = new Form();
                    form.setIdField(id);
                    form.setNameField(name);
                    form.getSave().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            float price = Float.parseFloat(form.getPriceField().getText());
                            int count = Integer.parseInt(form.getCountField().getText());
                            if (form.getPriceField().getText().trim().isEmpty() || form.getCountField().getText().trim().isEmpty()) {
                                form.emptyField();
                            } else {
                                dataBaseServices.updateProduct(id, price, count);
                                table_model.removeRow(position);
                                table_model.addRow(new String[]{id, name, String.valueOf(price), String.valueOf(count)});
                                form.dispose();
                            }
                        }
                    });

                }else{
                    JOptionPane.showMessageDialog(OurProducts.this, "you have to select a product");
                }
            }
        };
        return actionListener;
    }

    private ActionListener exportAction(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try(Writer writer = new FileWriter("products.txt",true);) {
                    Writer bufferedWriter = new BufferedWriter(writer);
                    for(int i=0 ;i<table_model.getRowCount() ;i++){
                        bufferedWriter.write("id : "+table_model.getValueAt(i,0).toString()+",name : "+table_model.getValueAt(i,1).toString()+",price : "+table_model.getValueAt(i,2).toString()+",count : "+table_model.getValueAt(i,3).toString()+"\n");
                    }
                    bufferedWriter.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        return actionListener;
    }
}
