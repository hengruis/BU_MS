import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HeroesList {
    public List<Warrior> warriors = new ArrayList<>();
    public List<Sorcerer> sorcerers = new ArrayList<>();
    public List<Paladin> paladins = new ArrayList<>();

    public HeroesList() {
        // warriors
        File file = new File(LMaH.warriors);
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\s+");
                warriors.add(new Warrior(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[3]), Integer.parseInt(data[5]),
                        Integer.parseInt(data[6])));
            }
        } catch (IOException ignored) {}

        // sorcerers
        file = new File(LMaH.sorcerers);
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\s+");
                sorcerers.add(new Sorcerer(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[3]), Integer.parseInt(data[5]),
                        Integer.parseInt(data[6])));
            }
        } catch (IOException ignored) {}

        // paladins
        file = new File(LMaH.paladins);
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\s+");
                paladins.add(new Paladin(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[3]), Integer.parseInt(data[5]),
                        Integer.parseInt(data[6])));
            }
        } catch (IOException ignored) {}
    }
}
