package chef.database;

import chef.solutions.Actions;
import chef.solutions.Salad;
import chef.solutions.Vegetable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class DbActions {
    private final DatabaseConnection connection;
    public DbActions(){
        connection = new DatabaseConnection();
    }
    public void saveSalad(Salad salad) throws Exception{
        String insert = "INSERT INTO " + TablesConst.SALADS_INFO_TABLE + "(" + TablesConst.SALAD_NAME + ")" +
                "VALUES(?)";
        String get = "SELECT " + TablesConst.SALAD_ID +  " FROM " + TablesConst.SALADS_INFO_TABLE  +
                " ORDER BY " + TablesConst.SALAD_ID + " DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,salad.getName());
        preparedStatement.executeUpdate();
        if(salad.getIngredients() == null) {
            connection.closeDbConnection();
            return;
        }
        Statement statement = connection.getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(get);
        int saladId;
        if(resultSet.next()) {
            saladId = resultSet.getInt(TablesConst.SALAD_ID);
            for (Vegetable vegetable : salad.getIngredients()) saveIngredient(vegetable, saladId);
        }
        connection.closeDbConnection();
    }
    public void saveIngredient(Vegetable vegetable,int saladId) {
        String query = "INSERT INTO " + TablesConst.INGREDIENTS_TABLE + "(" + TablesConst.INGREDIENT_NAME + "," +
                   TablesConst.WEIGHT +"," + TablesConst.SALAD_ID + ")"
                 +  "VALUES(?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.getDbConnection().prepareStatement(query);
            preparedStatement.setString(1,vegetable.getName());
            preparedStatement.setDouble(2,vegetable.getWeight());
            preparedStatement.setInt(3,saladId);
            preparedStatement.executeUpdate();
            connection.closeDbConnection();
        }
        catch(Exception e) {e.printStackTrace();}
    }
    public List<Vegetable> downloadIngredients(int saladId) {
        List<Vegetable> list = new ArrayList<>();
        String ingredientName;
        double weight;
        String getIngredients = "SELECT * FROM " + TablesConst.INGREDIENTS_TABLE + " WHERE " +
                TablesConst.SALAD_ID + " = " + "'" + saladId + "'";
        try {
            Statement statement = connection.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(getIngredients);
            while (resultSet.next()) {
               ingredientName = resultSet.getString(2);
               weight = resultSet.getDouble(3);
               list.add(Actions.getIngredient(ingredientName,weight));
            }
        }
        catch(Exception e){e.printStackTrace();}
        return list;
    }
    public Salad downloadSalad(String saladName){
        String getSalad = "SELECT * FROM " + TablesConst.SALADS_INFO_TABLE + " WHERE " +
                TablesConst.SALAD_NAME + " = " + "'" + saladName + "'";
        try {
            Statement statement = connection.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(getSalad);
            if(resultSet.next()) {
                int saladId = resultSet.getInt(TablesConst.SALAD_ID);
                List<Vegetable> ingredientList = new ArrayList<>(downloadIngredients(saladId));
                return new Salad(saladName,ingredientList);
            }
        }
        catch(Exception e){e.printStackTrace();}
        return null;
    }
    public List<String> getSaladsName(){
        String query = "SELECT " + TablesConst.SALAD_NAME  + " FROM " + TablesConst.SALADS_INFO_TABLE;
        List<String> names = new ArrayList<>();
        try {
            Statement statement = connection.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                names.add(resultSet.getString(1));
            }
            connection.closeDbConnection();
        }catch(Exception e){e.printStackTrace();}
        return names;
    }

    public void setSaladName(String oldName,String newName){
        String query = "UPDATE " + TablesConst.SALADS_INFO_TABLE + " SET " +
                TablesConst.SALAD_NAME + " = '" + newName + "' WHERE "
                + TablesConst.SALAD_NAME + " = '" + oldName + "'";
        try{
            Statement statement = connection.getDbConnection().createStatement();
            statement.executeUpdate(query);
            connection.closeDbConnection();
        }catch(Exception e) {e.printStackTrace();}
    }
    public int getSaladId(String saladName){
        String query = "SELECT " + TablesConst.SALAD_ID  + " FROM " + TablesConst.SALADS_INFO_TABLE +
                " WHERE " + TablesConst.SALAD_NAME + " = " + "'" + saladName + "'";
        int id = -100;
        try {
            Statement statement = connection.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
            connection.closeDbConnection();
        }catch(Exception e){e.printStackTrace();}
        return id;
    }
    public void deleteIngredient(String saladName, String ingredientName) throws Exception{
            int saladId = getSaladId(saladName);
            String query = " DELETE FROM " + TablesConst.INGREDIENTS_TABLE +
                    " WHERE " + TablesConst.SALAD_ID + " = " + saladId + " and " +
                    TablesConst.INGREDIENT_NAME + " = " + "'" + ingredientName + "'";
            Statement statement = connection.getDbConnection().createStatement();
            statement.executeUpdate(query);
        connection.closeDbConnection();
    }
    public void deleteSalad(String saladName) throws Exception{
        int saladId = getSaladId(saladName);
        String query1 = " DELETE FROM " + TablesConst.INGREDIENTS_TABLE +
                " WHERE " + TablesConst.SALAD_ID + " = " + saladId;
        String query2 =" DELETE FROM " + TablesConst.SALADS_INFO_TABLE + " WHERE " +
                TablesConst.SALAD_ID + " = " + saladId;
        Statement statement = connection.getDbConnection().createStatement();
        statement.executeUpdate(query1);
        statement.executeUpdate(query2);
        connection.closeDbConnection();
    }
}
