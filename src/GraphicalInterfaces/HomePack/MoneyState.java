package GraphicalInterfaces.HomePack;

import DataBase.DataBaseServices;
import entities.Sale;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MoneyState extends JPanel{
    private static DataBaseServices dataBaseServices = new DataBaseServices();
    private static List<Sale> sales = dataBaseServices.getAllSales();
    MoneyState(){
        this.setBackground(new Color(245,255,250));
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.drawLine(30,15,30,160);
        g.drawString("Money(DH)",15,10);
        g.drawString("days",480,150);
        g.drawLine(30,160 , 500,160);
        ArrayList<String> days = new ArrayList<>();
        for(int j=0;j<3;j++) {
            for (int i = 1; i < 10; i++) {
                days.add(String.valueOf(i));
            }
            days.add(String.valueOf(0));
            if(j==2){
                days.add(String.valueOf(1));
            }
        }
        String[] money = {"0","1000","2000","3000","4000","5000","6000"};
        int nbrDay = 35 ;
        int nbrSales = 160;
        for(int i=0 ; i<days.size() ; i++) {
            g.drawString(days.get(i), nbrDay, 170);
            nbrDay += 15;
        }
        for(int i=0 ; i<money.length ; i++){
            g.drawString(money[i], 2, nbrSales);
            nbrSales -= 20;
        }
        nbrDay = 35;
        ArrayList<Float> earnInDay = this.getEarnInDay();
        for(int i=0 ; i<31 ; i++){
            if(i!=30){
                g.drawLine(nbrDay,(int)(160-earnInDay.get(i)*2/100),nbrDay+15,(int)(160-earnInDay.get(i+1)*2/100));
                nbrDay += 15;
            }
        }
    }
    public static float getEarnInThisMonth(){
        float earn = 0 ;
        int size = sales.size();
        LocalDate date  = LocalDate.now();
        int currentMonth = date.getMonthValue();
        for(int i=0 ; i<size ;i++){
            if(sales.get(i).getDate().getMonthValue() == currentMonth){
                earn += sales.get(i).getTotalPrice();
            }
        }
        return earn;
    }
    public static ArrayList<Float> getEarnInDay() {
        ArrayList<Float> salesInDay = new ArrayList<Float>();
        for(int i=0 ;i<31;i++){
            salesInDay.add((float)0);
        }
        int size = sales.size();
        LocalDate localDate = LocalDate.now();
        int month = localDate.getMonthValue();
        for(int i=0 ; i<size ;i++){
            if(sales.get(i).getDate().getMonthValue() == month){
                int j = sales.get(i).getDate().getDayOfMonth();
                salesInDay.set(j-1,salesInDay.get(j-1)+sales.get(i).getTotalPrice());
            }
        }
        return salesInDay ;
    }
}
