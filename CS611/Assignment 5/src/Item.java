public class Item implements Tradable {
    protected String name;
    protected int price;
    protected int reqLV;

    public void purchased(Hero hero) {
        // the item is purchased by a hero
        if (hero.level < reqLV) {
            System.out.println(LoV.ANSI_RED + "Your level is too low!" + LoV.ANSI_RESET);
        } else if (hero.coins < price) {
            System.out.println(LoV.ANSI_RED + "You don't have enough money!" + LoV.ANSI_RESET);
        } else {
            hero.coins -= price;
            hero.equips.add(this);
            System.out.println(LoV.ANSI_CYAN + "Purchased successfully." + LoV.ANSI_RESET);
        }
    }

    public void sold(Hero hero) {
        // the item is sold by the hero
        hero.equips.remove(this);
        hero.coins += price / 2;
        System.out.println(LoV.ANSI_CYAN + "Sold successfully." + LoV.ANSI_RESET);
    }
}
