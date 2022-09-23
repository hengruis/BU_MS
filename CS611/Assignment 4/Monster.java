public abstract class Monster {
    protected String name;
    protected int hp;
    protected int hpLeft;
    protected int level;
    protected int attack;
    protected int defense;
    protected double dodgeChance;
    protected boolean isAlive;

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
}


class Dragon extends Monster {
    public Dragon(String name, int level, int attack, int defense, int dodgeChance) {
        super(name, level, attack, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return "(Dragon) " + name + ", LV: " + level + ", HP: " + hpLeft + "/" + hp + ", ATK: " + attack + ", DEF: " +
                defense + "\n";
    }
}


class Exoskeleton extends Monster {
    public Exoskeleton(String name, int level, int attack, int defense, int dodgeChance) {
        super(name, level, attack, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return "(Exoskeleton) " + name + ", LV: " + level + ", HP: " + hpLeft + "/" + hp + ", ATK: " + attack + ", " +
                "DEF: " + defense + "\n";
    }
}


class Spirit extends Monster {
    public Spirit(String name, int level, int attack, int defense, int dodgeChance) {
        super(name, level, attack, defense, dodgeChance);
    }

    @Override
    public String toString() {
        return "(Spirit) " + name + ", LV: " + level + ", HP: " + hpLeft + "/" + hp + ", ATK: " + attack + ", DEF: " +
                defense + "\n";
    }
}
