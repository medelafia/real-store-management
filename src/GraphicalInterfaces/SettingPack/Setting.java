package GraphicalInterfaces.SettingPack;

import DataBase.DataBaseServices;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class Setting extends JPanel {
    private JPanel header ;
    private JLabel title ;
    private JPanel center ;
    private JPanel ownerBlock ;
    private static DataBaseServices dataBaseServices = new DataBaseServices() ;
    private JPanel elementsContainer ;
    private static Map<String,String> info = dataBaseServices.getLogin() ;
    public Setting(){
        this.setLayout(new BorderLayout());
        this.setHeader();
        this.setCenter();
    }
    private void setHeader(){
        header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        title = new JLabel("SETTING");
        title.setForeground(new Color(160,160,160));
        title.setFont(new Font(null, Font.BOLD , 30));
        header.add(title);
        this.add(header , BorderLayout.PAGE_START);
    }
    private void setCenter(){
        center = new JPanel(new GridLayout(4,1));
        center.setBorder(new EmptyBorder(20,20,20,20));
        setSettingBlock("store owner",info.get("owner"),"change owner name");
        setSettingBlock("email",info.get("email"),"change email");
        setSettingBlock("phone number",info.get("phoneNumber"),"change number phone");
        setSettingBlock("addresse ",info.get("addresse"),"change store addresse");
        this.add(center,BorderLayout.CENTER);
    }

    private JPanel setSettingBlock(String name , String value , String btnText){
        JPanel panel = new JPanel(new BorderLayout()) ;
        JPanel panel1Container = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel panel1 = new JPanel(new GridLayout(2,1));
        JLabel label = new JLabel(name.toUpperCase());
        label.setForeground(Color.GRAY);
        JLabel label1 = new JLabel(value);
        panel1.add(label);
        panel1.add(label1);
        JPanel btnContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton button = new JButton(btnText);
        btnContainer.add(button);
        button.setBackground(Color.GRAY);
        button.setBorder(new EmptyBorder(10,50,10,50));
        button.setForeground(Color.white);
        panel1Container.add(panel1);
        panel.add(panel1Container,BorderLayout.WEST);
        panel.add(btnContainer,BorderLayout.EAST);
        center.add(panel);
        return panel ;
    }
    private ActionListener changeInfo(String label){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newOwner = JOptionPane.showInputDialog("enter the new " + label );
                if(newOwner != null) {

                }
            }
        };
    }
}
