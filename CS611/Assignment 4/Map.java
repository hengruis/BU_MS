import java.util.Scanner;

public class Map {
    private int size;
    private Block[][] map;

    public Map(int size) {
        this.size = size;
        map = new Block[this.size][this.size];
        // generate map
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                map[i][j] = new Block();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Block getBlock(int x, int y) {
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


class Block {
    private int type;  // 0: inaccessible, 1: common place, 2: market
    private boolean heroesHere;

    public Block() {
        // 20% inaccessible, 50% common place, 30% market
        int rand = (int)(1 + Math.random() * 10);
        if (rand <= 2) {
            type = 0;
        } else if (rand <= 7) {
            type = 1;
        } else {
            type = 2;
        }
        heroesHere = false;
    }

    public void nextAct(Team team) {
        // what happens next when get to this block
        if (type == 1) {
            goFight(team);
        } else if (type == 2) {
            goMarket(team);
        }
    }

    public void goMarket(Team team) {
        Market market = new Market();
        System.out.println(LMaH.ANSI_CYAN + "You are in the market. Type b/B to buy, s/S to sell items and e/E to " +
                "exit the market." + LMaH.ANSI_RESET);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while (!input.equals("e") && !input.equals("E")) {
            switch (input) {
                case "b", "B" -> {
                    market.showGoods();
                    for (int i = 0; i < team.getHeroesNum(); i++) {
                        Hero hero = team.getHero(i);
                        System.out.printf(LMaH.ANSI_GREEN + "Hero %d money left: %d\n" + LMaH.ANSI_RESET, i + 1,
                                hero.coins);
                    }
                    System.out.println(LMaH.ANSI_CYAN + "Which hero do you want to buy for?" + LMaH.ANSI_RESET);
                    int heroIdx = sc.nextInt();
                    System.out.println(LMaH.ANSI_CYAN + "Type the index of item you want to buy:" + LMaH.ANSI_RESET);
                    int itemIdx = sc.nextInt();
                    market.goods.get(itemIdx - 1).purchased(team.getHero(heroIdx - 1));
                }
                case "s", "S" -> {
                    for (int i = 0; i < team.getHeroesNum(); i++) {
                        Hero hero = team.getHero(i);
                        System.out.printf(LMaH.ANSI_GREEN + "Hero %d's items:\n" + LMaH.ANSI_RESET, i + 1);
                        hero.showItems();
                    }
                    System.out.println(LMaH.ANSI_CYAN + "Whose items do you want to sell?" + LMaH.ANSI_RESET);
                    int heroIdx = sc.nextInt();
                    Hero hero = team.getHero(heroIdx - 1);
                    System.out.println(LMaH.ANSI_CYAN + "Type the index of item you want to sell:" + LMaH.ANSI_RESET);
                    int itemIdx = sc.nextInt();
                    hero.equips.get(itemIdx - 1).sold(hero);
                }
            }
            System.out.println(LMaH.ANSI_CYAN + "What do you want to do next in the market?");
            input = sc.nextLine();
        }
        System.out.println(LMaH.ANSI_CYAN + "You left the market." + LMaH.ANSI_RESET);
    }

    public void goFight(Team team) {
        int fightChance = (int)(1 + Math.random() * 10);
        if (fightChance <= 3) {
            // 30% for fight
            System.out.println(LMaH.ANSI_RED + "Encounter monsters!" + LMaH.ANSI_RESET);
            Fight fight = new Fight(team);
            fight.start();
        } else {
            System.out.println(LMaH.ANSI_CYAN + "Nothing happens here." + LMaH.ANSI_RESET);
        }
    }

    public int getType() {
        return type;
    }

    public void setHeroesHere() {
        heroesHere = !heroesHere;
    }

    @Override
    public String toString() {
        if (type == 0) {
            if (heroesHere) {
                return LMaH.ANSI_RED + "X" + LMaH.ANSI_RESET;
            }
            return "X";
        } else if (type == 1) {
            if (heroesHere) {
                return LMaH.ANSI_RED + "O" + LMaH.ANSI_RESET;
            }
            return "O";
        } else {
            if (heroesHere) {
                return LMaH.ANSI_RED + "$" + LMaH.ANSI_RESET;
            }
            return "$";
        }
    }
}
