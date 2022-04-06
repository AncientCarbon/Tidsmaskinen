import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class fileTest {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.print("Username: ");
        String username = in.nextLine();
        System.out.println();

        System.out.print("Password: ");
        String password = in.nextLine();
        System.out.println();

        IndlaesPersonerOgTilmeldinger indlaes = new IndlaesPersonerOgTilmeldinger();
        System.out.print("File path: ");
        String filePath = in.nextLine();

        List<PersonOgTilmelding> personOgTilmeldingList = indlaes.indlaesPersonerOgTilmeldinger(filePath);

        SQLManipulation sqlManipulation = new SQLManipulation();
        sqlManipulation.createConnection(username, password);



    }
}
