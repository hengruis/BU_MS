import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MonstersList {
    public List<Dragon> dragonList = new ArrayList<>();
    public List<Exoskeleton> exoskeletonList = new ArrayList<>();
    public List<Spirit> spiritList = new ArrayList<>();

    public MonstersList() {
        // dragons
        File file = new File(LMaH.dragons);
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\s+");
                dragonList.add(new Dragon(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]), Integer.parseInt(data[4])));
            }
        } catch (IOException ignored) {}

        // exoskeletions
        file = new File(LMaH.exoskeletions);
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\s+");
                exoskeletonList.add(new Exoskeleton(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]), Integer.parseInt(data[4])));
            }
        } catch (IOException ignored) {}

        // spirits
        file = new File(LMaH.spirits);
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\s+");
                spiritList.add(new Spirit(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]), Integer.parseInt(data[4])));
            }
        } catch (IOException ignored) {}
    }
}
