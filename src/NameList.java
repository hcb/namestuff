import java.util.*;

public class NameList {

    List<Name> names = new ArrayList<Name>();
    List<Name> namesSubList = new ArrayList<Name>();
    int year;



    Random random = new Random();

    public void setPopularityRange(Map<Popularity, Integer> popRange, Popularity popularity) {
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
    }

    public void setGender(Gender gender) {
        if (gender.equals(Gender.NONE)) {
            return;
        } else {
            for (Name name : names) {
                if (name.gender.equals(gender)) {
                    namesSubList.add(name);
                }
            }
            names.clear();
            names.addAll(namesSubList);
        }
    }

    public Name getRandomName() {
        int next = random.nextInt(names.size());
        return names.get(next);
    }

    public void setTopNames(int lowerLimit, int upperLimit) {
        Collections.sort(names, Name.NameRankComparator);
        names = names.subList(lowerLimit, upperLimit);
    }
}
