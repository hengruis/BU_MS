public abstract class Spell extends Item {
    protected int manaCost;
    protected int dmg;

    // special effect that a spell can cause
    public abstract void spEffect(Monster monster);
}


class IceSpell extends Spell {
    public IceSpell(String name, int price, int reqLV, int manaCost, int dmg) {
        this.name = name;
        this.price = price;
        this.reqLV = reqLV;
        this.manaCost = manaCost;
        this.dmg = dmg;
    }

    @Override
    public void spEffect(Monster monster) {
        int atkReduction = (int)(monster.attack * 0.1);
        monster.attack -= atkReduction;
        System.out.println(LoV.ANSI_YELLOW + monster.name + "'s attack reduced " + atkReduction + "." +
                LoV.ANSI_RESET);
    }

    @Override
    public String toString() {
        return "(Spell - Ice) " + name + "\nprice: " + price + "\nreqLV: " + reqLV + "\nmana cost: " + manaCost +
                "\ndmg: " + dmg + "\n";
    }
}


class FireSpell extends Spell {
    public FireSpell(String name, int price, int reqLV, int manaCost, int dmg) {
        this.name = name;
        this.price = price;
        this.reqLV = reqLV;
        this.manaCost = manaCost;
        this.dmg = dmg;
    }

    @Override
    public void spEffect(Monster monster) {
        int defReduction = (int)(monster.attack * 0.1);
        monster.defense -= defReduction;
        System.out.println(LoV.ANSI_YELLOW + monster.name + "'s defense reduced " + defReduction + "." +
                LoV.ANSI_RESET);
    }

    @Override
    public String toString() {
        return "(Spell - Fire) " + name + "\nprice: " + price + "\nreqLV: " + reqLV + "\nmana cost: " + manaCost +
                "\ndmg: " + dmg + "\n";
    }
}


class LightningSpell extends Spell {
    public LightningSpell(String name, int price, int reqLV, int manaCost, int dmg) {
        this.name = name;
        this.price = price;
        this.reqLV = reqLV;
        this.manaCost = manaCost;
        this.dmg = dmg;
    }

    @Override
    public void spEffect(Monster monster) {
        int dodgeChanceReduction = (int)(monster.dodgeChance * 0.1);
        monster.dodgeChance -= dodgeChanceReduction;
        System.out.println(LoV.ANSI_YELLOW + monster.name + "'s dodge chance reduced " + dodgeChanceReduction + "." +
                LoV.ANSI_RESET);
    }

    @Override
    public String toString() {
        return "(Spell - Light) " + name + "\nprice: " + price + "\nreqLV: " + reqLV + "\nmana cost: " + manaCost +
                "\ndmg: " + dmg + "\n";
    }
}
