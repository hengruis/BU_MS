import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Market {
    // items for sale, all items will never be sold out
    public List<Item> goods = new ArrayList<>();

    public Market() {
        // weaponry
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(LoV.weaponry));
            String line;
            try {
                // skip the first row
                bufferedReader.readLine();
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split("\\s+");
                    goods.add(new Weapon(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                            Integer.parseInt(data[3])));
                }
                bufferedReader.close();
            } catch (IOException ignored) {}
        } catch (FileNotFoundException ignored) {}

        // armory
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(LoV.armory));
            String line;
            try {
                bufferedReader.readLine();
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split("\\s+");
                    goods.add(new Armor(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                            Integer.parseInt(data[3])));
                }
                bufferedReader.close();
            } catch (IOException ignored) {}
        } catch (FileNotFoundException ignored) {}

        // potions
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(LoV.potions));
            String line;
            try {
                bufferedReader.readLine();
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split("\\s+");
                    goods.add(new Potion(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2])));
                }
                bufferedReader.close();
            } catch (IOException ignored) {}
        } catch (FileNotFoundException ignored) {}

        // fire spells
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(LoV.firespells));
            String line;
            try {
                bufferedReader.readLine();
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split("\\s+");
                    goods.add(new FireSpell(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                            Integer.parseInt(data[4]), Integer.parseInt(data[3])));
                }
                bufferedReader.close();
            } catch (IOException ignored) {}
        } catch (FileNotFoundException ignored) {}

        // ice spells
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(LoV.icespells));
            String line;
            try {
                bufferedReader.readLine();
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split("\\s+");
                    goods.add(new IceSpell(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                            Integer.parseInt(data[4]), Integer.parseInt(data[3])));
                }
                bufferedReader.close();
            } catch (IOException ignored) {}
        } catch (FileNotFoundException ignored) {}

        // lightning spells
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(LoV.lightningspells));
            String line;
            try {
                bufferedReader.readLine();
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split("\\s+");
                    goods.add(new LightningSpell(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                            Integer.parseInt(data[4]), Integer.parseInt(data[3])));
                }
                bufferedReader.close();
            } catch (IOException ignored) {}
        } catch (FileNotFoundException ignored) {}
    }

    public void showGoods() {
        goods.forEach(System.out::print);
    }
}
