import java.util.*;

public class NameList {

    List<Name> names = new ArrayList<Name>();
    List<Name> namesSubList = new ArrayList<Name>();
    int year;

    Random random = new Random();

    public Name getRandomName(Popularity popularity) {
        Map<Popularity, Integer> popRange = new HashMap<Popularity, Integer>();
        popRange.put(Popularity.POPULAR, (names.size() * 1) / 100);
        popRange.put(Popularity.COMMON, (names.size() * 10) / 100);
        popRange.put(Popularity.UNCOMMON, (names.size() * 25) / 100);

        switch (popularity) {
            case POPULAR: {
                setTopNames(0, popRange.get(Popularity.POPULAR));
                break;
            }
            case COMMON: {
                setTopNames(popRange.get(Popularity.POPULAR), popRange.get(Popularity.COMMON));
                break;
            }
            case UNCOMMON: {
                setTopNames(popRange.get(Popularity.COMMON), popRange.get(Popularity.UNCOMMON));
                break;
            }
            case RARE: {
                setTopNames(popRange.get(Popularity.UNCOMMON), names.size());
                break;
            }
        }
        return names.get(random.nextInt(namesSubList.size()));
    }

    public void setTopNames(int lowerLimit, int upperLimit) {
        Collections.sort(names, Name.NameRankComparator);
        namesSubList = names.subList(lowerLimit, upperLimit);
    }
}
