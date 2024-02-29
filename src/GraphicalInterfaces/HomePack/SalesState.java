package GraphicalInterfaces.HomePack;

import DataBase.DataBaseServices;
import entities.Sale;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class SalesState extends JPanel{

    private static DataBaseServices dataBaseServices = new DataBaseServices();
    private static ArrayList<Sale> sales = dataBaseServices.getAllSales();
    SalesState(){
        this.setBackground(new Color(245,255,250));
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.drawLine(20,15,20,160);
        g.drawString("Sales(U)",15,10);
        g.drawString("day",480,150);
        g.drawLine(20,160 , 500,160);
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
        String[] sales = {"0","10","20","30","40"};
        int nbrDay = 25 ;
        int nbrSales = 160;
        for(int i=0 ; i<days.size() ; i++) {
            g.drawString(days.get(i), nbrDay, 170);
            nbrDay += 14;
        }
        for(int i=0 ; i<sales.length ; i++){
            g.drawString(sales[i], 2, nbrSales);
            nbrSales -= 30;
        }
        nbrDay = 25;
        ArrayList<Integer> salesInDay = this.getSalesInDay() ;
        for(int i=0 ; i<31 ; i++){
            if(i != 30) {
                g.drawLine(nbrDay,160-salesInDay.get(i)*3,nbrDay+14,160-salesInDay.get(i+1) *3);
                nbrDay += 14;
            }
        }
    }

    public static int getSalesInThisMonth(){
        int salesCount = 0 ;
        int size = sales.size();
        LocalDate date  = LocalDate.now();
        int currentMonth = date.getMonthValue();
        for(int i=0 ; i<size ;i++){
            if(sales.get(i).getDate().getMonth().getValue() == currentMonth){
                salesCount += 1 ;
            }
        }
        return salesCount;
    }
    public static ArrayList<Integer> getSalesInDay() {
        ArrayList<Integer> salesInDay = new ArrayList<Integer>();
        for(int i=0 ;i<31;i++){
            salesInDay.add(0);
        }
        int size = sales.size();
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        for(int i=0 ; i<size ;i++) {
            if (sales.get(i).getDate().getMonthValue() == month) {
                int j = sales.get(i).getDate().getDayOfMonth();
                salesInDay.set(j-1, salesInDay.get(j-1) + sales.get(i).getCount());
            }
        }
        return salesInDay ;
    }
}
