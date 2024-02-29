package GraphicalInterfaces;

import DataBase.DataBaseServices;
import GraphicalInterfaces.HomePack.HomePanel;
import GraphicalInterfaces.ProductsPack.OurProducts;
import GraphicalInterfaces.SalesPACK.Sales;
import GraphicalInterfaces.SettingPack.Setting;
import GraphicalInterfaces.ShopPack.Buy;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class App extends JFrame{
    private JPanel header ;
    private JLabel homeTitle ;
    private JPanel leftSide ;
    private JButton goHome ;
    private static JButton products ;
    private JPanel center ;
    private JButton Buy;
    private static JButton sales ;
    private JButton setting ;
    private JButton logout ;
    private String active = "Home";
    private DataBaseServices dataBaseServices = new DataBaseServices();

    public App(){
        this.mainConfiguration();
    }

    private void mainConfiguration(){
        this.setHeader();
        this.setLeftSide();
        this.setCenter(new HomePanel());
        this.setTitle("crud system");
        this.setSize(1200,650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    private void setHeader(){
        header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBorder(new EmptyBorder(10,0,10,0));
        header.setBackground(new Color(64,64,64));
        homeTitle = new JLabel("CRUD SYSTEME");
        homeTitle.setFont(new Font(null , Font.BOLD , 30 ));
        homeTitle.setForeground(Color.white);
        header.add(homeTitle);
        this.add(header , BorderLayout.PAGE_START);
    }
    private void setLeftSide(){
        leftSide = new JPanel(new GridLayout(6,1,20,40));
        leftSide.setBorder(new EmptyBorder(30,10,50,10));
        leftSide.setBackground(new Color(160,160,160));
        goHome = new JButton("HOME");
        this.btnSetting(goHome,toHomePage());
        leftSide.add(goHome);
        products = new JButton("PRODUCTS");
        this.btnSetting(products,toOurProductsPage());
        leftSide.add(products);
        Buy =new JButton("Buy");
        this.btnSetting(Buy,toBuyPage());
        leftSide.add(Buy);
        sales = new JButton("SALES");
        this.btnSetting(sales,toSalesPage());
        leftSide.add(sales);
        setting = new JButton("SETTING");
        this.btnSetting(setting,toSettingPage());
        leftSide.add(setting);
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("switch.png"));
            logout = new JButton(imageIcon);
            //logout = new JButton(UIManager.getIcon("OptionPane.informationIcon"));
            logout.setBorder(null);
            logout.addActionListener(Logout());
        }catch (Exception e){
            e.printStackTrace();
        }
        logout.setBackground(new Color(160,160,160));
        leftSide.add(logout);
        setPanelActive();
        this.add(leftSide , BorderLayout.WEST);
    }
    private void setCenter(JPanel homePanel){
        center = homePanel;
        this.add(center ,BorderLayout.CENTER);
    }
    private ActionListener toOurProductsPage(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active = "Product";
                App.this.setPanelActive();
                App.this.requestFocus();
                App.this.center.removeAll();
                App.this.setCenter(new OurProducts());
                App.this.center.revalidate();
                App.this.center.repaint();
            }
        };
        return actionListener;
    }
    private ActionListener toHomePage(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active = "Home";
                App.this.setPanelActive();
                App.this.requestFocus();
                App.this.center.removeAll();
                App.this.setCenter(new HomePanel());
                App.this.center.revalidate();
                App.this.center.repaint();
            }
        };
        return actionListener;
    }
    private ActionListener toSalesPage(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active = "Sales";
                App.this.setPanelActive();
                App.this.requestFocus();
                App.this.center.removeAll();
                App.this.setCenter(new Sales());
                App.this.center.revalidate();
                App.this.center.repaint();
            }
        };
        return actionListener;
    }
    private ActionListener toBuyPage(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active = "Buy";
                App.this.setPanelActive();
                App.this.requestFocus();
                App.this.center.removeAll();
                App.this.setCenter(new Buy());
                App.this.center.revalidate();
                App.this.center.repaint();
            }
        };
        return actionListener;
    }
    private ActionListener toSettingPage(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active = "Setting";
                App.this.setPanelActive();
                App.this.requestFocus();
                App.this.center.removeAll();
                App.this.setCenter(new Setting());
                App.this.center.revalidate();
                App.this.center.repaint();
            }
        };
        return actionListener;
    }
    private void btnSetting(JButton btn , ActionListener actionListener){
        btn.addActionListener(actionListener);
        btn.setBackground(new Color(64,64,64));
        btn.setForeground(Color.white);
    }
    private ActionListener Logout(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(App.this , "do ou want to logout ? ")==JOptionPane.OK_OPTION){
                    new Login();
                    App.this.dispose();
                }else {
                    return;
                }
            }
        };
        return actionListener ;
    }
    public static JButton getHomeBtn(){
        return products;
    }
    private void setPanelActive(){
        JButton[] btns = {goHome,products,Buy,sales,setting};
        Color color = new Color(50,100,120) ;
        for(int i=0;i<btns.length ;i++){
            btns[i].setBackground(new Color(64,64,64));
        }
        switch (active){
            case "Home" :   goHome.setBackground(color);
                            break;
            case "Product" :    products.setBackground(color);
                                break;
            case "Buy" :    Buy.setBackground(color);
                            break;
            case "Sales" : sales.setBackground(color);
                            break;
            case "Setting" :    setting.setBackground(color);
                                break ;
            default: break;
        }
    }
    public static JButton getSales() {
        return sales;
    }

    public static JButton getProducts() {
        return products;
    }
}
