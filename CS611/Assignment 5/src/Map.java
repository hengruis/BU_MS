import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    private final int size = 8;
    private Cell[][] map;
    private Hero[] heroes = new Hero[3];
    private List<Monster> monsters = new ArrayList<>();

    public Map() {
        map = new Cell[this.size][this.size];
        // generate map
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (i == 0 || i == this.size - 1) {
                    map[i][j] = new Cell("N");
                } else if (j == 2 || j == 5) {
                    map[i][j] = new Cell("I");
                } else {
                    double p = Math.random();
                    if (p <= 0.4) {
                        map[i][j] = new Cell("P");
                    } else if (p <= 0.6) {
                        map[i][j] = new Cell("B");
                    } else if (p <= 0.8) {
                        map[i][j] = new Cell("C");
                    } else {
                        map[i][j] = new Cell("K");
                    }
                }
            }
        }
    }

    public void generateHeroes() {
        System.out.println(LoV.ANSI_CYAN + "You can choose 3 heroes now. There are 3 types of heroes: warrior, " +
                "sorcerer and paladin." + LoV.ANSI_RESET);
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            System.out.println(LoV.ANSI_CYAN + "Which kind of hero do you want?" + LoV.ANSI_RESET);
            System.out.println(LoV.ANSI_YELLOW + "Hero 1: (1) warrior; (2) sorcerer; (3) paladin;" + LoV.ANSI_RESET);
            int type = sc.nextInt();
            System.out.println(LoV.ANSI_CYAN + "Please choose one from the following list." + LoV.ANSI_RESET);
            switch (type) {
                case 1 -> {
                    HeroesList.warriors.forEach(System.out::println);
                    int idx = sc.nextInt();
                    heroes[i] = HeroesList.warriors.get(idx - 1);
                }
                case 2 -> {
                    HeroesList.sorcerers.forEach(System.out::println);
                    int idx = sc.nextInt();
                    heroes[i] = HeroesList.sorcerers.get(idx - 1);
                }
                case 3 -> {
                    HeroesList.paladins.forEach(System.out::println);
                    int idx = sc.nextInt();
                    heroes[i] = HeroesList.paladins.get(idx - 1);
                }
            }
            heroes[i].heroNO = "H" + (i + 1);
            System.out.printf(LoV.ANSI_CYAN + "Which lane do you want Hero %d to start?\n" + LoV.ANSI_RESET, i + 1);
            boolean isSet = false;
            do {
                System.out.println(LoV.ANSI_YELLOW + "Lane 1: (1) top; (2) middle; (3) bottom" + LoV.ANSI_RESET);
                int lane = sc.nextInt();
                switch (lane) {
                    case 1 -> {
                        if (!map[7][0].isFullOfHero) {
                            heroes[i].loc[0] = 7;
                            heroes[i].loc[1] = 0;
                            map[7][0].setHeroLoc(heroes[i]);
                            isSet = true;
                        } else if (!map[7][1].isFullOfHero) {
                            heroes[i].loc[0] = 7;
                            heroes[i].loc[1] = 1;
                            map[7][1].setHeroLoc(heroes[i]);
                            isSet = true;
                        } else {
                            System.out.println(LoV.ANSI_RED + "Top lane is full!" + LoV.ANSI_RESET);
                        }
                    }
                    case 2 -> {
                        if (!map[7][3].isFullOfHero) {
                            heroes[i].loc[0] = 7;
                            heroes[i].loc[1] = 3;
                            map[7][3].setHeroLoc(heroes[i]);
                            isSet = true;
                        } else if (!map[7][4].isFullOfHero) {
                            heroes[i].loc[0] = 7;
                            heroes[i].loc[1] = 4;
                            map[7][4].setHeroLoc(heroes[i]);
                            isSet = true;
                        } else {
                            System.out.println(LoV.ANSI_RED + "Mid lane is full!" + LoV.ANSI_RESET);
                        }
                    }
                    case 3 -> {
                        if (!map[7][6].isFullOfHero) {
                            heroes[i].loc[0] = 7;
                            heroes[i].loc[1] = 6;
                            map[7][6].setHeroLoc(heroes[i]);
                            isSet = true;
                        } else if (!map[7][7].isFullOfHero) {
                            heroes[i].loc[0] = 7;
                            heroes[i].loc[1] = 7;
                            map[7][7].setHeroLoc(heroes[i]);
                            isSet = true;
                        } else {
                            System.out.println(LoV.ANSI_RED + "Bot lane is full!" + LoV.ANSI_RESET);
                        }
                    }
                }
            } while (!isSet);
        }
    }

    public void initMonsters() {
        for (int i = 0; i < 3; i++) {
            // randomly generate monsters
            Monster monster = generateMonster(1);
            monster.monsterNO = "M" + (i + 1);
            monster.loc[0] = 0;
            monster.loc[1] = i * 3;
            monsters.add(monster);
            map[monster.loc[1]][monster.loc[0]].setMonsterLoc(monster);
        }
    }

    public Monster generateMonster(int level) {
        List<Monster> monstersSameLV = new ArrayList<>();
        MonstersList monstersList = new MonstersList();
        for (Monster monster:
                monstersList.monsterList) {
            if (monster.level == level) {
                monstersSameLV.add(monster);
            }
        }
        // random selection
        int idx = (int)(Math.random() * monstersSameLV.size());
        return monstersSameLV.get(idx);
    }

    public Cell getCell(int x, int y) {
        return map[y][x];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                sb.append(map[i][j].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}


class Cell {
    private String type;  // I: inaccessible, P: plain, B: bush, C: cave, K: Koulou, N: Nexus
    private String[][] boundary = new String[3][5];
    public boolean isExplored;  // used for hero TP
    public boolean isFullOfHero;  // used for hero location
    public boolean isFullOfMonster;  // used for monster location

    public Cell(String type) {
        this.type = type;
        this.isExplored = false;
        this.isFullOfHero = false;
        this.isFullOfMonster = false;
        this.boundary[0] = new String[]{this.type, " -", " -", " -", " -"};
        if (type.equals("I")) {
            this.boundary[1] = new String[]{"| ", " X", "  ", " X", " |"};
        } else {
            this.boundary[1] = new String[]{"| ", "  ", "  ", "  ", " |"};
        }
        this.boundary[2] = new String[]{" -", " -", " -", " -", " -"};
    }

    public void goMarket(Hero hero) {
        Market market = new Market();
        System.out.println(LoV.ANSI_CYAN + "You are in the market. Type b/B to buy, s/S to sell items and e/E to " +
                "exit the market." + LoV.ANSI_RESET);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("e") && !input.equals("E")) {
            switch (input) {
                case "b", "B" -> {
                    market.showGoods();
                    System.out.println(LoV.ANSI_GREEN + hero.name + " money left: " + hero.coins + LoV.ANSI_RESET);
                    System.out.println(LoV.ANSI_CYAN + "Type the index of item you want to buy:" + LoV.ANSI_RESET);
                    int itemIdx = sc.nextInt();
                    market.goods.get(itemIdx - 1).purchased(hero);
                }
                case "s", "S" -> {
                    System.out.println(LoV.ANSI_GREEN + hero.name + "'s items:" + LoV.ANSI_RESET);
                    hero.showItems();
                    System.out.println(LoV.ANSI_CYAN + "Type the index of item you want to sell:" + LoV.ANSI_RESET);
                    int itemIdx = sc.nextInt();
                    hero.equips.get(itemIdx - 1).sold(hero);
                }
            }
            System.out.println(LoV.ANSI_CYAN + "What do you want to do next in the market?");
            input = sc.nextLine();
        }
        System.out.println(LoV.ANSI_CYAN + "You left the market." + LoV.ANSI_RESET);
    }

    public void setHeroLoc(Hero hero) {
        this.boundary[1][1] = hero.heroNO;
        this.isFullOfHero = true;
    }

    public void setMonsterLoc(Monster monster) {
        this.boundary[1][3] = monster.monsterNO;
        this.isFullOfMonster = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                sb.append(boundary[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
