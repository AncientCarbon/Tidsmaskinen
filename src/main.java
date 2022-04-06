import java.sql.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        String host = "localhost";
        String port = "3306";
        String database = "tidsmaskinen_db";
        String cp = "utf8";

        //set username
        String username = "root";
        String password = "passwordLmao";

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=" + cp;

        try{
            Scanner in = new Scanner(System.in, "CP850");
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
