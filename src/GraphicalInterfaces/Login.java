package GraphicalInterfaces;

import DataBase.DataBaseServices;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Login extends JFrame implements KeyListener {
    private JPanel header;
    private JLabel imageLabel ;
    private JPanel center ;
    private JPanel usernamePanel ;
    private JLabel username ;
    private JTextField usernameField ;
    private JPanel passwordPanel ;
    private JLabel password ;
    private JTextField passwordField ;
    private JPanel btnPanel ;
    private JButton btnLogin;
    private JButton cancel ;

    private DataBaseServices dataBaseServices = new DataBaseServices() ;

    public Login(){
        mainConfiguration();
    }
    private void mainConfiguration(){
        this.setHeader();
        this.setCenter();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    private void setHeader(){
        header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBorder(new EmptyBorder(10,0,10,0));
        imageLabel = new JLabel("login");
        imageLabel.setFont(new Font(null,Font.BOLD,30));
        imageLabel.setForeground(Color.CYAN);
        header.add(imageLabel);
        this.add(header,BorderLayout.PAGE_START);
    }
    private void setCenter(){
        center = new JPanel(new GridLayout(3,1,20,10));
        //center.setBorder(new EmptyBorder(0,0,10,0));
        usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.setBorder(new EmptyBorder(10,0,10,0));
        username = new JLabel("username");
        usernameField = new JTextField(20);
        usernamePanel.add(username);
        usernamePanel.add(usernameField);
        center.add(usernamePanel);
        passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordPanel.setBorder(new EmptyBorder(10,0,10,0));
        password = new JLabel("password");
        passwordField = new JPasswordField(20);
        passwordPanel.add(password);
        passwordPanel.add(passwordField);
        center.add(passwordPanel);
        btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBorder(new EmptyBorder(10,0,10,0));
        btnLogin = new JButton("login");
        btnLogin.setForeground(Color.white);
        btnLogin.setBackground(Color.CYAN);
        btnLogin.addActionListener(loginAction());
        cancel = new JButton("cancel");
        cancel.setForeground(Color.white);
        cancel.setBackground(Color.red);
        cancel.addActionListener(cancelAction());
        btnPanel.add(cancel);
        btnPanel.add(btnLogin);
        center.add(btnPanel);
        this.add(center,BorderLayout.CENTER);
    }
    private ActionListener loginAction(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameText = usernameField.getText();
                String passwordText = passwordField.getText();
                if(usernameText.isEmpty() || passwordText.isEmpty()){
                    JOptionPane.showMessageDialog(Login.this, "empty field");
                }else {
                    if(!usernameText.equals(dataBaseServices.getLogin().get(0)) || !passwordText.equals(dataBaseServices.getLogin().get(1))){
                        JOptionPane.showMessageDialog(Login.this , "invalid username or password");
                    }else {
                        App home = new App();
                        Login.this.dispose();
                    }
                }
            }
        };
        return actionListener ;
    }
    private ActionListener cancelAction(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login.this.dispose();
            }
        };
        return actionListener;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            this.btnLogin.doClick();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
