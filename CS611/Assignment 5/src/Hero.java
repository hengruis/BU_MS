import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Hero {
    protected String name;
    protected int hp;
    protected int mana;
    protected int hpLeft;
    protected int manaLeft;
    protected int level;
    protected int exp;
    protected int coins;
    protected int strength;
    protected int dexterity;
    protected int defense;
    protected int agility;
    protected double dodgeChance;
    protected boolean isAlive;
    protected String heroNO;
    protected int[] loc = new int[2];

    protected List<Item> equips = new ArrayList<>();  // weapons, armors, potions and spells

    protected Weapon weaponEquipped = new Weapon("Hunter's Knife", 0, 1, 150); // default weapon
    protected Armor armorEquipped = new Armor("Leather Suit", 0, 1, 180); // default armor

    public void setHeroNO(String heroNO) {
        this.heroNO = heroNO;
    }

    public void showItems() {
        System.out.println("##### ----- Equipments ----- #####:");
        if (equips.isEmpty()) {
            System.out.println(LoV.ANSI_RED + "You have no equipment!" + LoV.ANSI_RESET);
        } else {
            System.out.println(equips);
        }
    }

    public void consumePotion() {
        List<Potion> potions = new ArrayList<>();
        for (Item item:
             equips) {
            if (item instanceof Potion) potions.add((Potion) item);
        }
        if (potions.isEmpty()) {
            System.out.println(LoV.ANSI_RED + "You have no potion!" + LoV.ANSI_RESET);
        } else {
            System.out.println(LoV.ANSI_CYAN + "Choose one potion to use:" + potions + LoV.ANSI_RESET);
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            potions.get(option - 1).used(this);
        }
    }

    public Spell chooseSpell() {
        List<Spell> spells = new ArrayList<>();
        for (Item item:
                equips) {
            if (item instanceof Spell) spells.add((Spell) item);
        }
        if (spells.isEmpty()) {
            System.out.println(LoV.ANSI_RED + "You have no spell!" + LoV.ANSI_RESET);
            return null;
        } else {
            System.out.println(LoV.ANSI_CYAN + "Choose one spell to use:" + spells + LoV.ANSI_RESET);
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            return spells.get(option - 1);
        }
    }

    public void changeEquipment() {
        List<Item> weaponarmors = new ArrayList<>();
        for (Item item:
                equips) {
            if (item instanceof Armor) weaponarmors.add(item);
            if (item instanceof Weapon) weaponarmors.add(item);
        }
        if (weaponarmors.isEmpty()) {
            System.out.println(LoV.ANSI_RED + "You have no equipment left!" + LoV.ANSI_RESET);
        } else {
            System.out.println(LoV.ANSI_CYAN + "Choose one to use:" + weaponarmors + LoV.ANSI_RESET);
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            if (weaponarmors.get(option - 1) instanceof Weapon) {
                Weapon curWeapon = this.weaponEquipped;
                this.weaponEquipped = (Weapon) weaponarmors.get(option - 1);
                equips.add(curWeapon);
            } else {
                Armor curArmor = this.armorEquipped;
                this.armorEquipped = (Armor) weaponarmors.get(option - 1);
                equips.add(curArmor);
                this.defense = this.armorEquipped.getDmgReduction();
            }
        }
    }

    public abstract void levelUp();
}


class Warrior extends Hero {
    public Warrior(String name, int mana, int strength, int dexterity, int agility, int coins, int exp) {
        this.name = name;
        this.level = 1;
        this.hp = 100 * this.level;
        this.hpLeft = this.hp;
        this.mana = mana;
        this.manaLeft = this.mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.defense = 1;
        this.agility = agility;
        this.dodgeChance = this.agility * 0.002;
        this.exp = exp;
        this.coins = coins;
        this.isAlive = true;
    }

    public void levelUp() {
        exp -= level * 10;
        level += 1;
        hp += 100;
        mana *= 1.1;
        hpLeft = hp;
        manaLeft = mana;
        strength *= 1.1;
        dexterity *= 1.05;
        agility *= 1.1;
        dodgeChance = agility * 0.002;
        System.out.printf("%s level up!\n", name);
    }

    @Override
    public String toString() {
        return "(Warrior) " + name + "\nLv: " + level + "\nHP: " + hpLeft + "/" + hp + "\nMana: " + manaLeft + "/" +
                mana + "\nExp: " + exp + "\nCoins: " + coins + "\nStrength: " + strength + "\nDexterity: " + dexterity +
                "\nAgility: " + agility + "\nweapon: " + weaponEquipped + "\narmor: " + armorEquipped + "\n";
    }
}


class Sorcerer extends Hero {
    public Sorcerer(String name, int mana, int strength, int dexterity, int agility, int coins, int exp) {
        this.name = name;
        this.level = 1;
        this.hp = 100 * this.level;
        this.hpLeft = this.hp;
        this.mana = mana;
        this.manaLeft = this.mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.defense = 1;
        this.agility = agility;
        this.dodgeChance = this.agility * 0.002;
        this.exp = exp;
        this.coins = coins;
        this.isAlive = true;
    }

    public void levelUp() {
        exp -= level * 10;
        level += 1;
        hp += 100;
        mana *= 1.1;
        hpLeft = hp;
        manaLeft = mana;
        strength *= 1.05;
        dexterity *= 1.1;
        agility *= 1.1;
        dodgeChance = agility * 0.002;
        System.out.printf("%s level up!\n", name);
    }

    @Override
    public String toString() {
        return "(Sorcerer) " + name + "\nLv: " + level + "\nHP: " + hpLeft + "/" + hp + "\nMana: " + manaLeft + "/" +
                mana + "\nExp: " + exp + "\nCoins: " + coins + "\nStrength: " + strength + "\nDexterity: " + dexterity +
                "\nAgility: " + agility + "\nweapon: " + weaponEquipped + "\narmor: " + armorEquipped + "\n";
    }
}


class Paladin extends Hero {
    public Paladin(String name, int mana, int strength, int dexterity, int agility, int coins, int exp) {
        this.name = name;
        this.level = 1;
        this.hp = 100 * this.level;
        this.hpLeft = this.hp;
        this.mana = mana;
        this.manaLeft = this.mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.defense = 1;
        this.agility = agility;
        this.dodgeChance = this.agility * 0.002;
        this.exp = exp;
        this.coins = coins;
        this.isAlive = true;
    }

    public void levelUp() {
        exp -= level * 10;
        level += 1;
        hp += 100;
        mana *= 1.1;
        hpLeft = hp;
        manaLeft = mana;
        strength *= 1.1;
        dexterity *= 1.1;
        agility *= 1.05;
        dodgeChance = agility * 0.002;
        System.out.printf("%s level up!\n", name);
    }

    @Override
    public String toString() {
        return "(Paladin) " + name + "\nLv: " + level + "\nHP: " + hpLeft + "/" + hp + "\nMana: " + manaLeft + "/" +
                mana + "\nExp: " + exp + "\nCoins: " + coins + "\nStrength: " + strength + "\nDexterity: " + dexterity +
                "\nAgility: " + agility + "\nweapon: " + weaponEquipped + "\narmor: " + armorEquipped + "\n";
    }
}
