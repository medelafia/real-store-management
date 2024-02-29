package GraphicalInterfaces.HomePack;

import DataBase.DataBaseServices;
import GraphicalInterfaces.App;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    private JPanel header ;
    private JLabel title ;
    private JPanel center ;
    private JPanel centerNorth ;
    private JPanel centerSouth ;
    private JPanel salesState;
    private JPanel moneyState;
    private  DataBaseServices dataBaseServices = new DataBaseServices();
    private static int salesCount = SalesState.getSalesInThisMonth();
    private static float moneyValue = MoneyState.getEarnInThisMonth();

    public static float getMoneyValue() {
        return moneyValue;
    }

    public static int getSalesCount() {
        return salesCount;
    }

    public static void setMoneyValue(float moneyValue) {
        HomePanel.moneyValue = moneyValue;
    }

    public static void setSalesCount(int salesCount) {
        HomePanel.salesCount = salesCount;
    }

    public HomePanel(){
        this.setLayout(new BorderLayout());
        this.setHeader();
        this.setCenter();
    }
    private void setHeader(){
        header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        title = new JLabel("HOME");
        title.setForeground(new Color(160,160,160));
        title.setFont(new Font(null, Font.BOLD , 30));
        header.add(title);
        this.add(header , BorderLayout.PAGE_START);
    }
    private void setCenter(){
        center = new JPanel();
        BoxLayout boxLayout = new BoxLayout(center , BoxLayout.Y_AXIS);
        center.setLayout(boxLayout);
        setCenterSouth();
        setCenterNorth();
        this.add(center,BorderLayout.CENTER);
    }
    private void setCenterSouth(){
        centerSouth = new JPanel(new GridLayout(1,2,10,10));
        centerSouth.setBorder(new EmptyBorder(10,10,10,10));
        JPanel left = new MoneyState();
        JPanel right = new SalesState();
        centerSouth.add(left);
        centerSouth.add(right);
        center.add(centerSouth/* , BorderLayout.PAGE_END*/);
    }
    private void setCenterNorth(){
        centerNorth = new JPanel(new GridLayout(1,3,10,10));
        centerNorth.setBorder(new EmptyBorder(30,30,30,30));
        centerNorth.add(this.setRect("Total Of Product",new Color(120,120,120), String.valueOf(dataBaseServices.getAllProducts().size())));
        centerNorth.add(this.setRect("Total Earning In This Month",new Color(50,100,120),moneyValue+"DH"));
        centerNorth.add(this.setRect("Total Of Sales In This Month",new Color(200,120,120),String.valueOf(salesCount)));
        center.add(centerNorth/*,BorderLayout.PAGE_START*/);
    }
    private JPanel setLine(String title , String value){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel(title);
        JLabel textLabel = new JLabel(value);
        panel.add(titleLabel);
        panel.add(textLabel);
        return panel ;
    }
    private JPanel setRect(String title ,Color color, String text ){
        JPanel jPanel = new JPanel(new GridLayout(3,1, 0,0));
        JLabel titleLabel = new JLabel(title,SwingConstants.CENTER);
        titleLabel.setFont(new Font(null , Font.BOLD , 20));
        titleLabel.setForeground(Color.white);
        JLabel textLabel =new JLabel(text,SwingConstants.CENTER);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font(null,Font.CENTER_BASELINE,17));
        jPanel.add(titleLabel);
        //jPanel.setBorder(new EmptyBorder(10,10,10,10));
        jPanel.add(textLabel);
        JButton showMoreBtn = new JButton("show more >");
        showMoreBtn.setBackground(color);
        showMoreBtn.setBorder(null);
        showMoreBtn.setForeground(Color.white);
        switch (title){
            case "Total Of Product" : showMoreBtn.addActionListener(showMore(App.getProducts()));
                        break;
            default: showMoreBtn.addActionListener(showMore(App.getSales()));
                        break;
        }
        jPanel.add(showMoreBtn);
        jPanel.setBackground(color);
        jPanel.setBorder(new EmptyBorder(0,10,0,10));
        return jPanel;
    }
    private ActionListener showMore(JButton btn){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.doClick();
            }
        };
        return actionListener ;
    }
    public static void  update(){
        salesCount = SalesState.getSalesInThisMonth();
        moneyValue = MoneyState.getEarnInThisMonth();
    }
}