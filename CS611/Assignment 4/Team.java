import java.util.Scanner;

public class Team {
    private Hero[] heroes;
    private int[] loc = new int[2];
    private int maxLV;

    public Team() {
        HeroesList heroesList = new HeroesList();
        // create hero team according to player's choice
        System.out.println(LMaH.ANSI_CYAN + "Your team will contain 1~3 heroes. How many do you want?" +
                LMaH.ANSI_RESET);
        Scanner sc = new Scanner(System.in);
        int heroNum = sc.nextInt();
        heroes = new Hero[heroNum];
        System.out.println(LMaH.ANSI_CYAN + "There are 3 types of heroes, [Warrior], [Sorcerer] and [Paladin]. " +
                "[Warrior]s specialize in strength and agility. [Sorcerer]s specialize in dexterity and agility. " +
                "[Paladin]s specialize in strength and dexterity." + LMaH.ANSI_RESET);
        System.out.println(LMaH.ANSI_CYAN + "Strength is connected to hero's damage. Dexterity is connected to hero's" +
                " spell damage. Agility is connected to hero's dodge chance." + LMaH.ANSI_RESET);
        System.out.println(LMaH.ANSI_CYAN + "Now please choose the type for each hero. Type the corresponding number " +
                "and your hero will be randomly generated." + LMaH.ANSI_RESET);
        for (int i = 0; i < heroes.length; i++) {
            System.out.printf(LMaH.ANSI_YELLOW + "Hero %d: (1) warrior; (2) sorcerer; (3) paladin;\n" + LMaH.ANSI_RESET,
                    i + 1);
            int type = sc.nextInt();
            switch (type) {
                case 1 -> {
                    // random hero
                    int idx = (int)(Math.random() * (heroesList.warriors.size() + 1));
                    heroes[i] = heroesList.warriors.get(idx);
                }
                case 2 -> {
                    int idx = (int)(Math.random() * (heroesList.sorcerers.size() + 1));
                    heroes[i] = heroesList.sorcerers.get(idx);
                }
                case 3 -> {
                    int idx = (int)(Math.random() * (heroesList.paladins.size() + 1));
                    heroes[i] = heroesList.paladins.get(idx);
                }
            }
            System.out.printf(LMaH.ANSI_GREEN + "Hero %d:\n" + heroes[i] + LMaH.ANSI_RESET, i + 1);
        }
        setMaxLV();

        // player choose starting place
        System.out.println(LMaH.ANSI_CYAN + "Please choose the starting place for your team by giving the coordinate " +
                "like \"2,3\". You cannot start at the inaccessible." + LMaH.ANSI_RESET);
        sc.nextLine();
        String locs = sc.nextLine();
        String[] locStr = locs.split(",");
        setLoc(Integer.parseInt(locStr[0]) - 1, Integer.parseInt(locStr[1]) - 1);
    }

    public void setLoc(int x, int y) {
        loc[0] = x;
        loc[1] = y;
    }

    public int[] getLoc() {
        return loc;
    }

    public void setMaxLV() {
        maxLV = heroes[0].level;
        for (Hero hero : heroes) {
            if (hero.level > maxLV) {
                maxLV = hero.level;
            }
        }
    }

    public int getMaxLV() {
        return maxLV;
    }

    public int getHeroesNum() {
        return heroes.length;
    }

    public Hero getHero(int idx) {
        return heroes[idx];
    }

    public Hero[] getHeroes() {
        return heroes;
    }
}
