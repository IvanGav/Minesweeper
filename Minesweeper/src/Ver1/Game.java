package Ver1;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Game {
    Scanner sc;
    Field f;
    Pattern p;

    public Game(int h, int w, int bombs) {
        f = new Field(h,w,bombs);
        sc = new Scanner(System.in);
        p = Pattern.compile(" ");
    }

    public void run() {
        while (true) {
            System.out.println(f);
            parseAndExecute(ask());
            if(f.isLost()) {
                f.revealAll();
                System.out.println(f);
                System.out.println("You lost");
                return;
            }
            if(f.isWon()) {
                System.out.println(f);
                System.out.println("You won");
                return;
            }
        }
    }

    private String ask() {
        System.out.println("Enter next action (type 'help' for rules):");
        return sc.nextLine();
    }

    private void parseAndExecute(String s) {
        String[] split = p.split(s);
        if(split.length == 2) { //_______________________________alternative reveal
            int x;
            int y;
            try {
                x = Integer.parseInt(split[0]);
                y = Integer.parseInt(split[1]);
                f.reveal(y, x);
                return;
            } catch (NumberFormatException ignored) {}
        }
        if(split[0].equals("r")) { //_____________________________REVEAL
            int x;
            int y;
            try {
                x = Integer.parseInt(split[1]);
                y = Integer.parseInt(split[2]);
            } catch (NumberFormatException e) {
                System.out.println("Bad input: parameters are not numbers");
                return;
            }
            f.reveal(y, x);
            return;
        }
        if (split[0].equals("f")) { //_________________________FLAG
            int x;
            int y;
            try {
                x = Integer.parseInt(split[1]);
                y = Integer.parseInt(split[2]);
            } catch (NumberFormatException e) {
                System.out.println("Bad input: parameters are not numbers");
                return;
            }
            f.flag(y, x);
            return;
        }
        if (split[0].equals("help")) { //_______________________HELP
            System.out.println("To flag at (x,y) -> 'f x y' ; To reveal at (x,y) -> 'r x y' or 'x y'");
            return;
        }
        //_____________________________________________________no commands worked
        System.out.println("Bad input: command not found");
    }
}
