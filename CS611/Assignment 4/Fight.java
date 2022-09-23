import java.util.ArrayList;
import java.util.List;

public class Fight {
    private Round round;
    private Team team;

    public Fight(Team team) {
        this.team = team;
        // generate monster(s)
        Monster[] monsters = new Monster[team.getHeroesNum()];
        int teamLV = Math.min(team.getMaxLV(), 10);
        for (int i = 0; i < monsters.length; i++) {
            // randomly generate monster type
            int monsterType = (int)(Math.random() * 3);
            switch (monsterType) {
                // dragon
                case 0 -> monsters[i] = generateDragon(teamLV);
                // exoskeleton
                case 1 -> monsters[i] = generateExoskeleton(teamLV);
                // spirit
                case 2 -> monsters[i] = generateSpirit(teamLV);
            }
        }
        round = new Round(team.getHeroes(), monsters);
    }

    public Dragon generateDragon(int level) {
        List<Dragon> dragonSameLV = new ArrayList<>();
        MonstersList monstersList = new MonstersList();
        for (Dragon dragon:
                monstersList.dragonList) {
            if (dragon.level == level) {
                dragonSameLV.add(dragon);
            }
        }
        // random selection
        int idx = (int)(Math.random() * dragonSameLV.size());
        return dragonSameLV.get(idx);
    }

    public Exoskeleton generateExoskeleton(int level) {
        List<Exoskeleton> exoskeletonSameLV = new ArrayList<>();
        MonstersList monstersList = new MonstersList();
        for (Exoskeleton exoskeleton:
                monstersList.exoskeletonList) {
            if (exoskeleton.level == level) {
                exoskeletonSameLV.add(exoskeleton);
            }
        }
        int idx = (int)(Math.random() * exoskeletonSameLV.size());
        return exoskeletonSameLV.get(idx);
    }

    public Spirit generateSpirit(int level) {
        List<Spirit> spiritSameLV = new ArrayList<>();
        MonstersList monstersList = new MonstersList();
        for (Spirit spirit:
                monstersList.spiritList) {
            if (spirit.level == level) {
                spiritSameLV.add(spirit);
            }
        }
        int idx = (int)(Math.random() * spiritSameLV.size());
        return spiritSameLV.get(idx);
    }

    public void start() {
        System.out.println(LMaH.ANSI_CYAN + "You met with monster(s):" + LMaH.ANSI_RESET);
        for (Monster monster:
             round.getMonsters()) {
            System.out.println(monster);
        }
        int fightResult = round.roundStart();  // 0: lost, 1: won
        if (fightResult == 0) {
            heroesLost(round.getHeroes());
        } else {
            heroesWon(round.getHeroes());
            team.setMaxLV();
        }
    }

    public void heroesLost(Hero[] heroes) {
        for (Hero hero:
             heroes) {
            // all heroes' HP will recover up to 80% of max HP, mana up to 100%, money halved
            hero.hpLeft = (int)(hero.hp * 0.8);
            hero.manaLeft = hero.mana;
            hero.coins /= 2;
        }
    }

    public void heroesWon(Hero[] heroes) {
        for (Hero hero:
             heroes) {
            // all heroes' HP and Mana will recover up to 100%
            hero.hpLeft = hero.hp;
            hero.manaLeft = hero.mana;
            if (round.getHeroesAlive().contains(hero)) {
                // if hero is alive, gain money and exp
                hero.coins += round.getMonsters()[0].level * 100;
                hero.exp += round.getMonsters().length * 2;
                if (hero.exp >= hero.level * 10) {
                    hero.levelUp();
                }
            }
        }
    }
}
