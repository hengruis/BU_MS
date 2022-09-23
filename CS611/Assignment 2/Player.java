import java.util.Arrays;
import java.util.Scanner;


public class Player {
    protected String name;
    protected int no;
    protected int[] curMove = new int[2];
    int winSum = 0;

    public void setName(String name) {
        this.name = name;
    }

    public void setNo(int number) {
        this.no = number;
    }

    public void enterMove() {
        // implement a move of a player
        Arrays.fill(this.curMove, 0);
        // in Order-and-Chaos, players' name only represent order and chaos, not their pieces
        System.out.printf("%s please enter your move: ", this.name);
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        // exit if enter end
        if (str.equals("end")) {
            System.out.println("What a pity! See you next time!");
            System.exit(0);
        }
        try {
            int idx = 0;
            for (String s:
                 str.split(",")) {
                curMove[idx] = Integer.parseInt(s);
                idx++;
            }
        } catch (Exception e) {
            System.out.println("A pair of integers is expected! Please try again:");
            enterMove();
        }
    }
}
