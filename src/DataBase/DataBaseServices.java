package DataBase;

import entities.Sale;
import entities.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBaseServices {
    private Connection connection ;
    public void saveInDb(Product product ) {
        connection = ConnectionDB.getConnection();
        String query = "INSERT INTO pro (id , name , price , count ) VALUES (?,?,?,?);";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, product.getID());
            preparedStatement.setString(2,product.getName());
            preparedStatement.setFloat(3,product.getPrice());
            preparedStatement.setInt(4,product.getCount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Product getProduct(String id){
        connection = ConnectionDB.getConnection();
        Product product = null ;
        String query = "SELECT * FROM pro WHERE id=?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            product.setName(resultSet.getString("name"));
            product.setCount(resultSet.getInt("count"));
            product.setPrice(resultSet.getFloat("price"));
            product.setID(resultSet.getString("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return product;
    }
    public ArrayList<Product> getAllProducts(){
        connection = ConnectionDB.getConnection();
        ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT * FROM pro ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Product product = new Product();
                product.setName(resultSet.getString("name"));
                product.setCount(resultSet.getInt("count"));
                product.setPrice(resultSet.getFloat("price"));
                product.setID(resultSet.getString("id"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return products;
    }
    public void decrementCount(String id,int count){
        connection = ConnectionDB.getConnection();
        String query = "UPDATE pro SET count=? WHERE id=? ;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,count);
            preparedStatement.setString(2 , id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void incrementProducts(String id , int count){
        connection = ConnectionDB.getConnection();
        String query = "UPDATE pro SET count=? WHERE id=? ;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,count );
            preparedStatement.setString(2 , id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void deleteProduct(String id){
        connection = ConnectionDB.getConnection();
        String query = "DELETE FROM pro WHERE id=?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1 , id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void deleteAllProduct(){
        connection = ConnectionDB.getConnection();
        String query = "DELETE FROM pro;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void updateProduct(String id ,float price , int count){
        connection = ConnectionDB.getConnection();
        String query = "UPDATE pro SET price=? , count=? WHERE id=? ;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setFloat(1,price);
            preparedStatement.setInt(2,count);
            preparedStatement.setString(3 , id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Map<String,String> getLogin(){
        Map<String,String> map = new HashMap<>();
        connection = ConnectionDB.getConnection();
        if(connection != null){
            String query = "SELECT * FROM info;";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    map.put(resultSet.getString("label"),resultSet.getString("value"));
                }
                return map;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
    public void setLoginInfo(String username , String password) {
        int size = this.getLogin().size();
        connection = ConnectionDB.getConnection();
        try {
            if (connection != null) {
                if (size == 0) {
                    String query = "INSERT INTO info (label,value) VALUES (?,?),(?,?);";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, "username");
                        preparedStatement.setString(2, username);
                        preparedStatement.setString(3, "password");
                        preparedStatement.setString(4, password);
                        preparedStatement.executeUpdate();
                    }
                } else {
                    String updateUsername = "UPDATE info SET value=? WHERE label='username';";
                    String updatePassword = "UPDATE info SET value=? WHERE label='password';";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateUsername);
                         PreparedStatement preparedStatement1 = connection.prepareStatement(updatePassword)
                    ) {
                        preparedStatement.setString(1, username);
                        preparedStatement1.setString(1, password);
                        preparedStatement.executeUpdate();
                        preparedStatement1.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void changeElementFromLoginInfo(String label , String newValue){
        connection = ConnectionDB.getConnection();
        try {
            if (connection != null) {
                    String updateUsername = "UPDATE info SET value=? WHERE label='"+label+"';";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateUsername);
                    ) {
                        preparedStatement.setString(1, newValue);
                        preparedStatement.executeUpdate();
                    }
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void insertSale(Sale sale){
        connection = ConnectionDB.getConnection();
        String query = "INSERT INTO sales (productId , productName, productCount , productTotalPrice , date ,day) VALUES (?,?,?,?,?,?);";
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, sale.getIdOfProduct());
            preparedStatement.setString(2,sale.getNameOfProduct());
            preparedStatement.setInt(3,sale.getCount());
            preparedStatement.setFloat(4,sale.getTotalPrice());
            preparedStatement.setDate(5,date);
            Format f = new SimpleDateFormat("EEEE");
            String str = f.format(new java.util.Date());
            preparedStatement.setString(6,str);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public ArrayList<Sale> getAllSales(){
        connection = ConnectionDB.getConnection();
            ArrayList<Sale> sales = new ArrayList<>();
        String query = "SELECT * FROM sales;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Sale sale = new Sale();
                sale.setIdOfProduct(resultSet.getString("productId"));
                sale.setNameOfProduct(resultSet.getString("productName"));
                sale.setCount(resultSet.getInt("productCount"));
                sale.setTotalPrice(resultSet.getFloat("productTotalPrice"));
                sale.setDate(resultSet.getDate("date").toLocalDate());
                sale.setDay(resultSet.getString("day"));
                sales.add(sale);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return sales;
    }

}