public abstract class Monster {
    protected String name;
    protected int hp;
    protected int hpLeft;
    protected int level;
    protected int attack;
    protected int defense;
    protected double dodgeChance;
    protected boolean isAlive;
    protected String monsterNO;
    protected int[] loc = new int[2];

    public Monster(String name, int level, int attack, int defense, int dodge) {
        this.name = name;
        this.level = level;
        this.hp = 120 * this.level;
        this.hpLeft = this.hp;
        this.attack = attack;
        this.defense = defense;
        this.dodgeChance = dodge * 0.01;
        this.isAlive = true;
    }

    public void setMonsterNO(String monsterNO) {
        this.monsterNO = monsterNO;
    }
}


class Dragon extends Monster {
    public Dragon(String name, int level, int attack, int defense, int dodgeChance) {
        super(name, level, attack, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return "(Dragon) " + name + "\nLV: " + level + "\nHP: " + hpLeft + "/" + hp + "\nATK: " + attack + "\nDEF: " +
                defense + "\n";
    }
}


class Exoskeleton extends Monster {
    public Exoskeleton(String name, int level, int attack, int defense, int dodgeChance) {
        super(name, level, attack, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return "(Exoskeleton) " + name + "\nLV: " + level + "\nHP: " + hpLeft + "/" + hp + "\nATK: " + attack + "\n" +
                "DEF: " + defense + "\n";
    }
}


class Spirit extends Monster {
    public Spirit(String name, int level, int attack, int defense, int dodgeChance) {
        super(name, level, attack, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return "(Spirit) " + name + "\nLV: " + level + "\nHP: " + hpLeft + "/" + hp + "\nATK: " + attack + "\nDEF: " +
                defense + "\n";
    }
}
