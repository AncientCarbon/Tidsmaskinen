import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AgeCalculator {
    public int getAge(int birthdate) {

        String now = java.time.LocalDate.now().toString();
        String[] nowArr = now.split("-");
        now = nowArr[0] + nowArr[1] + nowArr[2];
        int nowInt = Integer.parseInt(now);

        return (nowInt-birthdate)/10000;
    }
}
