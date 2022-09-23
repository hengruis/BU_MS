public class Armor extends Item {
    private int dmgReduction;

    public Armor(String name, int price, int reqLV, int dmgReduction) {
        this.name = name;
        this.price = price;
        this.reqLV = reqLV;
        this.dmgReduction = dmgReduction;
    }

    public int getDmgReduction() {
        return dmgReduction;
    }

    @Override
    public String toString() {
        return "(Armor) " + name + "\nprice: " + price + "\nreqLV: " + reqLV + "\nDmg Reduction: " + dmgReduction +
                "\n";
    }
}
