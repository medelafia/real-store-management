package GraphicalInterfaces.ProductsPack;

import entities.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Form extends JFrame implements KeyListener {
    private JPanel container ;
    private JPanel idPanel;
    private JLabel id;
    private JTextField idField ;
    private JPanel namePanel;
    private JLabel name ;
    private JTextField nameField ;
    private JPanel pricePanel;
    private JLabel price;
    private JTextField priceField ;
    private JPanel countPanel;
    private JLabel count;
    private JTextField countField ;
    private JPanel btnsPanel ;
    private JButton save ;
    private JButton cancel ;
    private Product product ;

    Form(){
        mainConfiguration();
    }
    private void mainConfiguration(){
        this.setSize(400,400);
        this.setResizable(false);
        this.setContainer();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    private void setIdPanel(){
        idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        idPanel.setBorder(new EmptyBorder(10,0,10,0));
        id = new JLabel("id of product : ");
        idPanel.add(id);
        idField = new JTextField(20);
        idPanel.add(idField);
    }
    private void setNamePanel(){
        namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.setBorder(new EmptyBorder(10,0,10,0));
        name = new JLabel("name of product : ");
        namePanel.add(name);
        nameField = new JTextField(20);
        namePanel.add(nameField);
    }
    private void setPricePanel(){
        pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pricePanel.setBorder(new EmptyBorder(10,0,10,0));
        price = new JLabel("price of product : ");
        pricePanel.add(price);
        priceField = new JTextField(20);
        pricePanel.add(priceField);
    }
    private void setCountPanel(){
        countPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        countPanel.setBorder(new EmptyBorder(10,0,10,0));
        count = new JLabel("count of product : ");
        countPanel.add(count);
        countField = new JTextField(20);
        countPanel.add(countField);
    }
    private void setBtnsPanel(){
        btnsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnsPanel.setBorder(new EmptyBorder(10,0,10,0));
        save = new JButton("save");
        save.setBackground(Color.CYAN);
        save.setForeground(Color.white);
        btnsPanel.add(save);
        cancel = new JButton("cancel ");
        cancel.setBackground(Color.red);
        cancel.setForeground(Color.white);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Form.this.dispose();
            }
        });
        btnsPanel.add(cancel);
    }

    public void setContainer() {
        container = new JPanel(new GridLayout(5, 1));
        this.setIdPanel();
        this.setNamePanel();
        this.setPricePanel();
        this.setCountPanel();
        this.setBtnsPanel();
        container.add(idPanel);
        container.add(namePanel);
        container.add(pricePanel);
        container.add(countPanel);
        container.add(btnsPanel);
        this.add(container, BorderLayout.CENTER);
    }

    public JButton getSave() {
        return save;
    }

    public JTextField getCountField() {
        return countField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getIdField() {
        return idField;
    }
    public void emptyField(){
        JOptionPane.showMessageDialog(Form.this , "all fields required");
    }

    public void setIdField(String id) {
        idField.setText(id);
        idField.setEnabled(false);
    }
    public void setNameField(String name){
        nameField.setText(name);
        nameField.setEnabled(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!nameField.getText().trim().isEmpty() || !idField.getText().trim().isEmpty() || !priceField.getText().trim().isEmpty() || !countField.getText().trim().isEmpty()){
                save.doClick();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
