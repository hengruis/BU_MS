import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Round {
    private Hero[] heroes;
    private Monster[] monsters;
    private int liveHeroes;
    private int liveMonsters;
    private List<Hero> heroesAlive = new ArrayList<>();
    private List<Monster> monstersAlive = new ArrayList<>();

    public Round(Hero[] heroes, Monster[] monsters) {
        this.heroes = heroes;
        this.monsters = monsters;
        this.heroesAlive.addAll(List.of(this.heroes));
        this.monstersAlive.addAll(List.of(this.monsters));
        this.liveHeroes = this.heroesAlive.size();
        this.liveMonsters = this.monstersAlive.size();
    }

    public void setLiveHeroes() {
        liveHeroes = heroesAlive.size();
    }

    public void setLiveMonsters() {
        liveMonsters = monstersAlive.size();
    }

    public int roundStart() {
        System.out.println(LMaH.ANSI_CYAN + "Heroes will take action in order." + LMaH.ANSI_RESET);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(LMaH.ANSI_RED + "------------------------------------------------" + LMaH.ANSI_RESET);
            System.out.println(LMaH.ANSI_CYAN + " New round starts!" + LMaH.ANSI_RESET);
            System.out.println(LMaH.ANSI_CYAN + "Heroes alive: " + LMaH.ANSI_RESET);
            heroesAlive.forEach(System.out::print);
            System.out.println(LMaH.ANSI_CYAN + "Monsters alive: " + LMaH.ANSI_RESET);
            monstersAlive.forEach(System.out::print);
            for (int i = 0; i < heroesAlive.size(); i++) {
                Hero hero = heroesAlive.get(i);
                System.out.printf(LMaH.ANSI_CYAN + "Hero %d what to do next?\n" + LMaH.ANSI_RESET, i + 1);
                System.out.println(LMaH.ANSI_CYAN + "i/I: show info" + LMaH.ANSI_RESET);
                System.out.println(LMaH.ANSI_CYAN + "a/A: attack" + LMaH.ANSI_RESET);
                System.out.println(LMaH.ANSI_CYAN + "c/C: cast spells" + LMaH.ANSI_RESET);
                System.out.println(LMaH.ANSI_CYAN + "p/P: consume potions" + LMaH.ANSI_RESET);
                System.out.println(LMaH.ANSI_CYAN + "e/E: change equipment" + LMaH.ANSI_RESET);
                String act = sc.nextLine();
                while (act.equals("i") || act.equals("I")) {
                    heroesAlive.forEach(System.out::print);
                    monstersAlive.forEach(System.out::print);
                    act = sc.nextLine();
                }
                switch (act) {
                    case "a", "A" -> heroAttack(hero);
                    case "c", "C" -> heroCastSpells(hero);
                    case "p", "P" -> hero.consumePotion();
                    case "e", "E" -> hero.changeEquipment();
                }
            }
            if (liveMonsters == 0) {
                break;
            } else {
                for (Monster monster:
                        monstersAlive) {
                    monsterAttack(monster);
                }
                if (liveHeroes == 0) {
                    break;
                }
            }
            roundEnd();
            System.out.println(LMaH.ANSI_RED + "------------------------------------------------" + LMaH.ANSI_RESET);
        }
        if (liveMonsters == 0) {
            // heroes won
            return 1;
        } else {
            // heroes lost
            return 0;
        }
    }

    public void roundEnd() {
        for (Hero hero:
             heroesAlive) {
            hero.hpLeft *= 1.1;
            hero.manaLeft *= 1.1;
            if (hero.hpLeft > hero.hp) {
                hero.hpLeft = hero.hp;
            }
            if (hero.manaLeft > hero.mana) {
                hero.manaLeft = hero.mana;
            }
        }
    }

    public void heroAttack(Hero hero) {
        System.out.println(LMaH.ANSI_CYAN + "Please choose a target:" + LMaH.ANSI_RESET);
        Scanner sc = new Scanner(System.in);
        int target = sc.nextInt();
        Monster monster = monstersAlive.get(target - 1);
        boolean isMiss = Math.random() <= monster.dodgeChance;
        if (isMiss) {
            System.out.println(LMaH.ANSI_YELLOW + hero.name + " attack missed!" + LMaH.ANSI_RESET);
        } else {
            int damage = Math.max((int)((hero.strength + hero.weaponEquipped.getDmg()) * 0.05 - monster.defense), 0);
            monster.hpLeft -= damage;
            System.out.println(LMaH.ANSI_YELLOW + hero.name + " attacked " + monster.name + " and dealt " + damage + " "
                    + "damage!" + LMaH.ANSI_RESET);
            if (monster.hpLeft <= 0) {
                System.out.println(LMaH.ANSI_YELLOW + monster.name + " died!" + LMaH.ANSI_RESET);
                monstersAlive.remove(monster);
                setLiveMonsters();
            }
        }
    }

    public void heroCastSpells(Hero hero) {
        System.out.println(LMaH.ANSI_CYAN + "Please choose a target:" + LMaH.ANSI_RESET);
        Scanner sc = new Scanner(System.in);
        int target = sc.nextInt();
        Monster monster = monstersAlive.get(target - 1);
        // assume that spells cannot miss and ignore monsters' defense
        Spell spell = hero.chooseSpell();
        if (spell == null) {
            return;
        }
        int damage = spell.dmg * (1 + hero.dexterity / 10000);
        monster.hpLeft -= damage;
        System.out.println(LMaH.ANSI_YELLOW + hero.name + " casted " + spell.name + " and dealt " + damage + " damage" +
                "!" + LMaH.ANSI_RESET);
        if (monster.hpLeft <= 0) {
            System.out.println(LMaH.ANSI_YELLOW + monster.name + " died!" + LMaH.ANSI_RESET);
            monstersAlive.remove(monster);
            setLiveMonsters();
        } else {
            spell.spEffect(monster);
        }
    }

    public void monsterAttack(Monster monster) {
        // randomly choose a hero as target
        int idx = (int)(Math.random() * heroesAlive.size());
        Hero hero = heroesAlive.get(idx);
        boolean isMiss = Math.random() <= hero.dodgeChance;
        if (isMiss) {
            System.out.println(LMaH.ANSI_YELLOW + monster.name + " attack missed!" + LMaH.ANSI_RESET);
        } else {
            // assume that damage dealt by monster is 0.06 * its attack
            int damage = Math.max((int)(monster.attack * 0.06), 0);
            hero.hpLeft -= damage;
            System.out.println(LMaH.ANSI_YELLOW + monster.name + " attacked " + hero.name + " and dealt " + damage +
                    "!" + LMaH.ANSI_RESET);
            if (hero.hpLeft <= 0) {
                System.out.println(LMaH.ANSI_YELLOW + hero.name + " died!" + LMaH.ANSI_RESET);
                heroesAlive.remove(hero);
                setLiveHeroes();
            }
        }
    }

    public Hero[] getHeroes() {
        return heroes;
    }

    public Monster[] getMonsters() {
        return monsters;
    }

    public List<Hero> getHeroesAlive() {
        return heroesAlive;
    }
}
