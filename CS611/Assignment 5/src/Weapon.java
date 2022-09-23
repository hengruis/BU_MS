public class Weapon extends Item {
    private int dmg;

    public Weapon(String name, int price, int reqLV, int dmg) {
        this.name = name;
        this.price = price;
        this.reqLV = reqLV;
        this.dmg = dmg;
    }

    public int getDmg() {
        return dmg;
    }

    @Override
    public String toString() {
        return "(Weapon) " + name + "\nprice: " + price + "\nreqLV: " + reqLV + "\nDmg: " + dmg + "\n";
    }
}
