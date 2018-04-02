import java.util.Comparator;

public class Name {

    String value;
    Gender gender;
    int count;

    public void Name(String value, Gender gender, int rank, int year) {
        this.value = value;
        this.gender = gender;
        this.count = rank;
    }

    public static Comparator<Name> NameRankComparator = new Comparator<Name> () {
        public int compare(Name name1, Name name2) {
            return name2.count - name1.count;
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
