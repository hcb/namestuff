import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NameList {

    ArrayList<Name> names = new ArrayList<Name>();
    int year;
    Random random = new Random();

    public Name getRandomName() {
        return names.get(random.nextInt(names.size()));
    }
    public void printName(Name name) {
        System.out.println(name.value + " " + name.rank + " " + name.gender + " " + year);
    }

    public void printTopNames() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~");
        // By rank
        Collections.sort(names, Name.NameRankComparator);
        for (int i = 0; i < 15; i++) {
            printName(names.get(i));
        }
        // By alphabetical order
        System.out.println("-------------------------");
        Collections.sort(names, Name.NameValueComparator);
        for (int i = 0; i < 3; i++) {
            printName(names.get(i));
        }

        // By gender
        System.out.println(".........................");
        Collections.sort(names, Name.NameGenderComparator);
        for (int i = 0; i < 3; i++) {
            printName(names.get(i));
        }
    }
}
