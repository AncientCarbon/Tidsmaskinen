import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;


public class fileTest {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.print("Username: ");
        String username = in.nextLine();

        System.out.print("Password: ");
        String password = in.nextLine();
        System.out.println();

        System.out.println("Type '0' to exit program, '1' to type SQL command, or '2' to load file: ");
        int choice = in.nextInt();
        in.nextLine();




        String host = "localhost";
        String port = "3306";
        String database = "tidsmaskinen_db";
        String cp = "utf8";

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=" + cp;


        while(true){
            if (choice == 0) System.exit(0);
            else if (choice == 1) {
                try {
                    System.out.println("Type SQL manipulation: ");
                    String sqlManip = in.nextLine();
                    Connection connection = DriverManager.getConnection(url, username, password);

                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sqlManip);
                    System.out.println("Success");
                } catch (SQLInvalidAuthorizationSpecException e){
                    System.out.print("Username or Password is wrong. Please retry.\nUsername: ");
                    username = in.nextLine();

                    System.out.print("Password: ");
                    password = in.nextLine();
                    System.out.println();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (choice == 2) {
                IndlaesPersonerOgTilmeldinger indlaes = new IndlaesPersonerOgTilmeldinger();
                System.out.print("File path: ");
                String filePath = in.nextLine(); //"C:\Users\Boblo\OneDrive\Pictures\tilmeldinger.csv";
                List<PersonOgTilmelding> personOgTilmeldingList;

                while (true){
                    try {
                        personOgTilmeldingList = indlaes.indlaesPersonerOgTilmeldinger(filePath);
                        break;
                    } catch (FileNotFoundException e){
                        System.out.print("File path not working. Please try again.\nFile path: ");
                        filePath = in.nextLine();
                    }
                }

                boolean finished = true;
                int duplicate = 0;
                for (int i = 0; i < personOgTilmeldingList.size(); i++) {
                    String person = personOgTilmeldingList.get(i).getPerson().toString();
                    String[] personArray = person.split(";");


                    if (personOgTilmeldingList.get(i).getTilmelding() != null){
                        String tilmelding = personOgTilmeldingList.get(i).getTilmelding().toString();
                        String[] tilmeldingArray = tilmelding.split(";");

                        try {
                            // 0 = KLUBID, 1 = EVENTTYPE, 2 = DATO
                            String sqlManipulation = "INSERT IGNORE eventsTable VALUES('" + tilmeldingArray[2] + "', '" +
                                    tilmeldingArray[1] + "', '" + tilmeldingArray[0] + "')";

                            Connection connection = DriverManager.getConnection(url, username, password);

                            Statement statement = connection.createStatement();
                            statement.executeUpdate(sqlManipulation);

                            connection.close();

                        } catch (SQLException e){
                            e.printStackTrace();
                            duplicate++;
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        try {

                            AgeCalculator ageCalculator = new AgeCalculator();
                            Connection connection = DriverManager.getConnection(url, username, password);

                            String sqlManipulation = "INSERT resultat VALUES(NULL, '" + personArray[0] + "', '" + tilmeldingArray[2] + "', '" +
                                    tilmeldingArray[1] + "', '" + tilmeldingArray[0] + "', '" + personArray[1] +
                                    "', NULL, " + ageCalculator.getAge(Integer.parseInt(personArray[4]), Integer.parseInt(tilmeldingArray[2])) +
                                    ", '" + personArray[3] + "')";
                            Statement statement = connection.createStatement();
                            statement.executeUpdate(sqlManipulation);
                            connection.close();

                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    try {

                        String sqlManipulation = "INSERT person VALUES('" + personArray[0] + "', '" + personArray[1] + "', '" +
                                personArray[2] + "', '" + personArray[3] + "', '" + Integer.parseInt(personArray[4]) + "')";

                        Connection connection = DriverManager.getConnection(url, username, password);

                        Statement statement = connection.createStatement();
                        statement.executeUpdate(sqlManipulation);

                        connection.close();
                    } catch (SQLInvalidAuthorizationSpecException e) {
                        System.out.print("Username or Password is wrong. Please retry.\nUsername: ");
                        username = in.nextLine();

                        System.out.print("Password: ");
                        password = in.nextLine();
                        System.out.println();
                        i--;

                    } catch (Exception e) {
                        finished = false;
                        e.printStackTrace();
                    }
                }
                if (finished) System.out.println("Success");
                if (duplicate != 0) System.out.println(duplicate + " duplicates.");
                else System.out.println("Failure");
            }
            else {
                System.out.println("Typed number not an option. Try again.");
            }
            System.out.println("Type '0' to exit program, '1' to type SQL command, or '2' to load file: ");
            choice = in.nextInt();
            in.nextLine();
        }
    }
}
