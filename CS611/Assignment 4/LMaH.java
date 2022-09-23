import java.util.Scanner;

// class for "Legends: Monsters and Heroes"
public class LMaH extends RPGame {
    public static final String ANSI_RESET = "\u001b[0m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_YELLOW = "\u001b[33m";
    public static final String ANSI_CYAN = "\u001b[36m";

    public static final String armory = "./Armory.txt";
    public static final String weaponry = "./Weaponry.txt";
    public static final String potions = "./Potions.txt";
    public static final String firespells = "./FireSpells.txt";
    public static final String lightningspells = "./LightningSpells.txt";
    public static final String icespells = "./IceSpells.txt";
    public static final String warriors = "./Warriors.txt";
    public static final String sorcerers = "./Sorcerers.txt";
    public static final String paladins = "./Paladins.txt";
    public static final String dragons = "./Dragons.txt";
    public static final String exoskeletions = "./exoskeletons.txt";
    public static final String spirits = "./Spirits.txt";

    public static Map map;
    public static Team team;

    public static void main(String[] args) {
        System.out.println("""
                 .                                      _               __   __                       .                                                _      __  __
                 /       ___    ___.   ___  , __     ___/   ____        |    |    __.  , __     ____ _/_     ___  .___    ____        ___  , __     ___/      |   |    ___  .___    __.    ___    ____
                 |     .'   ` .'   ` .'   ` |'  `.  /   |  (     /      |\\  /|  .'   \\ |'  `.  (      |    .'   ` /   \\  (           /   ` |'  `.  /   |      |___|  .'   ` /   \\ .'   \\ .'   `  (
                 |     |----' |    | |----' |    | ,'   |  `--.         | \\/ |  |    | |    |  `--.   |    |----' |   '  `--.       |    | |    | ,'   |      |   |  |----' |   ' |    | |----'  `--.
                 /---/ `.___,  `---| `.___, /    | `___,' \\___.' /      /    /   `._.' /    | \\___.'  \\__/ `.___, /     \\___.'      `.__/| /    | `___,'      /   /  `.___, /      `._.' `.___, \\___.'
                               \\___/                    `                                                                                              `
                           
                """);
        System.out.println(ANSI_CYAN + "Welcome to Legends: Monsters and Heroes!\n" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "In this game, you will control several heroes. Your team live in a square, " +
                "dangerous world. You are not able to enter some places in this world, and you have a chance to be " +
                "caught in a battle with ferocious monsters. But don't be scared. There are some markets in the " +
                "world where you could buy some weapons and armors or something else for your heroes to make them " +
                "stronger. Hope you have fun in this game!" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Example of world map:" + ANSI_RESET);
        System.out.println("""
                -----------------------> x [1 ~ N]
                |  XXXXXXXXOOOOO$OOOO$O
                |  XXXOOOOOO$OOOOOO$XXX
                |  XOXOOOO$OOOOOOOOXXXX
                |  OOXOOOOOOOOO$OOOOOXX
                |  OXXXXOO$OOOOOOOOOOOO
                |  OOOOO$OOOOXXXXOOOXXO
                |  O$OOOOOOOOOXXXOOOXXX
                v  OOOOOOOOOOOOOOOOOOXX
                y
                [1 ~ N]
                
                X: inaccessible
                O: common place
                $: market
                """);
        System.out.println(ANSI_CYAN + "Your starting point and current place will be in red." + ANSI_RESET);
        System.out.println(ANSI_CYAN + "*** Press Enter to start the game ***" + ANSI_RESET);
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.out.println(ANSI_CYAN + "Please enter the size of the world:" + ANSI_RESET);
        int size = sc.nextInt();
        System.out.println(ANSI_CYAN + "Please wait for the world creating..." + ANSI_RESET);
        map = new Map(size);
        System.out.print(map);
        team = new Team();
        while (true) {
            prompt();
        }
    }

    public static void prompt() {
        System.out.println(ANSI_CYAN + "What to do next?" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + """
                w/W: move up
                a/A: move left
                s/S: move down
                d/D: move right
                m/M: show map
                i/I: show heroes info
                q/Q: quit the game
                """ + ANSI_RESET);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        switch (input) {
            case "w", "W" -> moveUp();
            case "a", "A" -> moveLeft();
            case "s", "S" -> moveDown();
            case "d", "D" -> moveRight();
            case "m", "M" -> showMap();
            case "i", "I" -> {
                System.out.println(ANSI_CYAN + "Heroes info:" + ANSI_RESET);
                for (Hero hero:
                     team.getHeroes()) {
                    System.out.println(hero);
                }
            }
            case "q", "Q" -> {
                System.out.println(ANSI_CYAN + "Thanks for playing!" + ANSI_RESET);
                System.exit(0);
            }
        }
    }

    public static void moveUp() {
        int x = team.getLoc()[0];
        int y = team.getLoc()[1];
        if (y == 0) {
            System.out.println(ANSI_RED + "You can't move up! You are on the edge of the map!" + ANSI_RESET);
        } else if (map.getBlock(x, y - 1).getType() == 0) {
            System.out.println(ANSI_RED + "You can't enter that place!" + ANSI_RESET);
        } else {
            team.setLoc(x, y - 1);
            map.getBlock(x, y).setHeroesHere();
            map.getBlock(x, y - 1).setHeroesHere();
            map.getBlock(x, y - 1).nextAct(team);
        }
    }

    public static void moveLeft() {
        int x = team.getLoc()[0];
        int y = team.getLoc()[1];
        if (x == 0) {
            System.out.println(ANSI_RED + "You can't move left! You are on the edge of the map!" + ANSI_RESET);
        } else if (map.getBlock(x - 1, y).getType() == 0) {
            System.out.println(ANSI_RED + "You can't enter that place!" + ANSI_RESET);
        } else {
            team.setLoc(x - 1, y);
            map.getBlock(x, y).setHeroesHere();
            map.getBlock(x - 1, y).setHeroesHere();
            map.getBlock(x - 1, y).nextAct(team);
        }
    }

    public static void moveDown() {
        int x = team.getLoc()[0];
        int y = team.getLoc()[1];
        if (y == map.getSize() - 1) {
            System.out.println(ANSI_RED + "You can't move down! You are on the edge of the map!" + ANSI_RESET);
        } else if (map.getBlock(x, y + 1).getType() == 0) {
            System.out.println(ANSI_RED + "You can't enter that place!" + ANSI_RESET);
        } else {
            team.setLoc(x, y + 1);
            map.getBlock(x, y).setHeroesHere();
            map.getBlock(x, y + 1).setHeroesHere();
            map.getBlock(x, y + 1).nextAct(team);
        }
    }

    public static void moveRight() {
        int x = team.getLoc()[0];
        int y = team.getLoc()[1];
        if (x == map.getSize() - 1) {
            System.out.println(ANSI_RED + "You can't move down! You are on the edge of the map!" + ANSI_RESET);
        } else if (map.getBlock(x + 1, y).getType() == 0) {
            System.out.println(ANSI_RED + "You can't enter that place!" + ANSI_RESET);
        } else {
            team.setLoc(x + 1, y);
            map.getBlock(x, y).setHeroesHere();
            map.getBlock(x + 1, y).setHeroesHere();
            map.getBlock(x + 1, y).nextAct(team);
        }
    }

    public static void showMap() {
        System.out.println(map);
    }
}
