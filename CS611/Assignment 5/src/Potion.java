import java.util.Arrays;

public class Potion extends Item implements Consumable {
    // save effects of each potion, 0 by default
    private int[] effects = new int[6];  // [HP, Mana, Strength, Dexterity, Defense, Agility]

    public Potion(String name, int price, int reqLV) {
        this.name = name;
        this.price = price;
        this.reqLV = reqLV;
        switch (this.name) {
            case "Healing Potion" -> effects[0] = 100;
            case "Magic Potion" -> effects[1] = 100;
            case "Strength Potion" -> effects[2] = 75;
            case "Luck Elixir" -> effects[5] = 65;
            case "Mermaid Tears" -> {
                effects[0] = 100;
                effects[1] = 100;
                effects[2] = 100;
                effects[5] = 100;
            }
            case "Ambrosia" -> Arrays.fill(effects, 150);
        }
    }

    public void used(Hero hero) {
        int hpRecover;
        int manaRecover;
        if (hero.hpLeft + effects[0] > hero.hp) {
            hpRecover = hero.hp - hero.hpLeft;
            hero.hpLeft = hero.hp;
        } else {
            hpRecover = effects[0];
            hero.hpLeft += hpRecover;
        }
        if (hero.manaLeft + effects[1] > hero.mana) {
            manaRecover = hero.mana - hero.manaLeft;
            hero.manaLeft = hero.mana;
        } else {
            manaRecover = effects[1];
            hero.manaLeft += manaRecover;
        }
        // for simplicity and lower difficulty, the following attributes will change permanently
        hero.strength += effects[2];
        hero.dexterity += effects[3];
        hero.defense += effects[4];
        hero.agility += effects[5];
        hero.dodgeChance = hero.agility * 0.002;
        System.out.println(LoV.ANSI_YELLOW + hero.name + " used " + name + "." + LoV.ANSI_RESET);
        hero.equips.remove(this);
        System.out.printf(LoV.ANSI_GREEN + """
                HP\t+%d
                Mana\t+%d
                Strength\t+%d
                Dexterity\t+%d
                Defense\t+%d
                Agility\t+%d
                """ + LoV.ANSI_RESET, hpRecover, manaRecover, effects[2], effects[3], effects[4], effects[5]);
    }

    @Override
    public String toString() {
        return "(Potion) " + name + "\nprice: " + price + "\nreqLV: " + reqLV + "\neffects: " + Arrays.toString(effects) +
                "\n";
    }
}
