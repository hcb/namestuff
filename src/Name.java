import java.util.Comparator;

public class Name {

    String value;
    Gender gender;
    int rank;

    public void Song(String value, Gender gender, int rank, int year) {
        this.value = value;
        this.gender = gender;
        this.rank = rank;
    }

    public enum Gender {
        MALE,
        FEMALE,
        NONE
    }

    public static Comparator<Name> NameRankComparator = new Comparator<Name> () {
        public int compare(Name name1, Name name2) {
            return name2.rank - name1.rank;
        }
    };


    public static Comparator<Name> NameValueComparator = new Comparator<Name> () {
        public int compare(Name name1, Name name2) {
            return name1.value.compareTo(name2.value);
        }
    };

    public static Comparator<Name> NameGenderComparator = new Comparator<Name> () {
        public int compare(Name name1, Name name2) {
            return name2.gender.compareTo(name1.gender);
        }
    };
}
