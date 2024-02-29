package entities;

import java.time.LocalDate;

public class Sale {
    private String idOfProduct ;
    private String nameOfProduct ;
    private int count ;
    private float totalPrice ;
    private LocalDate date ;
    private String day;
    public Sale(String idOfProduct , String nameOfProduct , int count , float totalPrice , LocalDate date , String day){
        this.idOfProduct = idOfProduct;
        this.nameOfProduct = nameOfProduct;
        this.count = count ;
        this.totalPrice = totalPrice ;
        this.date = date ;
        this.day = day ;
    }
    public Sale(){

    }
    public String getIdOfProduct() {
        return idOfProduct;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public int getCount() {
        return count;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setIdOfProduct(String idOfProduct) {
        this.idOfProduct = idOfProduct;
    }

    public void setNameOfProduct(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
