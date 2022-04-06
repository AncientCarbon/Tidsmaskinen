import java.sql.*;
import java.util.Scanner;

public class SQLManipulation {
    public void createConnection(String username, String password){
        Scanner in = new Scanner(System.in, "CP850");

        String host = "localhost";
        String port = "3306";
        String database = "tidsmaskinen_db";
        String cp = "utf8";

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=" + cp;

        tryManipulation(url, username, password);
    }

    public void tryManipulation(String url, String username, String password){
        Scanner in = new Scanner(System.in);
        try{
            System.out.println("Type sql manipulation: ");
            String sqlManipulation = in.nextLine();
            in.close();

            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlManipulation);

            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
