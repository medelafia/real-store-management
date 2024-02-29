package entities;

public class Product {
    private String name ;
    private String ID ;
    private float price ;
    private int count ;
    public Product(){

    }

    public float getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.ID + "+ " + this.name ;
    }
    public Product(String id , String name , float price , int count){
        this.ID = id;
        this.count = count ;
        this.price = price ;
        this.name = name;
    }
}
