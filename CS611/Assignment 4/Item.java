public class Item implements Tradable {
    protected String name;
    protected int price;
    protected int reqLV;

    public void purchased(Hero hero) {
        // the item is purchased by a hero
        if (hero.level < reqLV) {
            System.out.println(LMaH.ANSI_RED + "Your level is too low!" + LMaH.ANSI_RESET);
        } else if (hero.coins < price) {
            System.out.println(LMaH.ANSI_RED + "You don't have enough money!" + LMaH.ANSI_RESET);
        } else {
            hero.coins -= price;
            hero.equips.add(this);
            System.out.println(LMaH.ANSI_CYAN + "Purchased successfully." + LMaH.ANSI_RESET);
        }
    }

    public void sold(Hero hero) {
        // the item is sold by the hero
        hero.equips.remove(this);
        hero.coins += price / 2;
        System.out.println(LMaH.ANSI_CYAN + "Sold successfully." + LMaH.ANSI_RESET);
    }
}
