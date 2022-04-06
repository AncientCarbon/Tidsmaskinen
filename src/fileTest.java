import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class fileTest {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Username: root\nPassword: ");
        String password = in.nextLine();

        IndlaesPersonerOgTilmeldinger indlaes = new IndlaesPersonerOgTilmeldinger();

        List<PersonOgTilmelding> printThis = indlaes.indlaesPersonerOgTilmeldinger("C:\\Users\\Boblo\\OneDrive\\Pictures\\tilmeldinger.csv");

        for (int i = 0; i < printThis.size(); i++) {
            System.out.println(printThis.get(i).getPerson());

        }
    }
}
