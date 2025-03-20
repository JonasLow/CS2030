import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Roster roster = new Roster("AY2425");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            roster = roster.add(sc.next(), sc.next(), sc.next(), sc.next());
        }

        while (sc.hasNext()) {
            System.out.println(
                roster.getGrade(sc.next(), sc.next(), sc.next()));
        }
    }
}
