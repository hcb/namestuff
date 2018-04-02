import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("What year would you like your name to be generated from?");
        int year = in.nextInt();
        System.out.println("How common do you want the first name to be? (Popular, Common, Uncommon, Rare)");
        String firstNamePopularityInput = in.next();
        Popularity firstNamePopularity = parsePopularity(firstNamePopularityInput);
        System.out.println("How common do you want the surname to be? (Popular, Common, Uncommon, Rare)");
        String surnamePopularityInput = in.next();
        Popularity surnamePopularity = parsePopularity(surnamePopularityInput);

        // TODO: Implement `either` in NameList class
        System.out.println("Gender? (Male, Female, Either)");
        String genderInput = in.next();
        Gender gender = parseGender(genderInput);

        //Map<Integer, NameList> firstNameLists = getFirstNameLists();
        NameList firstNameList = getFirstNameList(year, firstNamePopularity, gender);
        NameList surnameList = getSurnameList(surnamePopularity);
        for (int i = 0; i < 15; i++) {
            generateName(firstNameList, surnameList);
        }
    }

    public static Popularity parsePopularity(String input) {
        input = input.toLowerCase();
        switch (input) {
            case "popular": {
                return Popularity.POPULAR;
            }
            case "common": {
                return Popularity.COMMON;
            }
            case "uncommon": {
                return Popularity.UNCOMMON;
            }
            case "rare": {
                return Popularity.RARE;
            }
            default: {
                return Popularity.COMMON;
            }
        }
    }

    public static Gender parseGender(String input) {
        input = input.toLowerCase();
        if (input.equals("female")) {
            return Gender.FEMALE;
        } else if (input.equals("male")) {
            return Gender.MALE;
        }
        return Gender.NONE;
    }

    public static void generateName(NameList firstNameList, NameList surnameList) {
        Name first = firstNameList.getRandomName();
        Name last = surnameList.getRandomName();
        System.out.println(Utils.toSentenceCase(first.value) + " ("+ first.count + ") " +
                Utils.toSentenceCase(last.value) + " (" + last.count + ")");
    }

    public static Map<Integer, NameList> getFirstNameLists() {
        Map<Integer, NameList> firstNameLists = new HashMap();
        File listDir = new File("src/names");
        for (int i = 0; i < listDir.listFiles().length; i++) {
            if (listDir.listFiles()[i].isFile()) {
                try {
                    NameList nameList = readNameList("src/names/" + listDir.listFiles()[i].getName());
                    firstNameLists.put(nameList.year, nameList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return firstNameLists;
    }

    private static NameList getFirstNameList(Map<Integer, NameList> nameLists, int year) {
        return nameLists.get(year);
    }

    private static NameList getFirstNameList(int year, Popularity popularity, Gender gender) {
        String fileName = "/yob" + year + ".txt";
        File nameListDir = new File("src/names");
        NameList firstNameList = new NameList();
        try {
            firstNameList = readNameList(nameListDir + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        firstNameList.setGender(gender);

        Map<Popularity, Integer> popRange = new HashMap<Popularity, Integer>();
        popRange.put(Popularity.POPULAR, (firstNameList.names.size() * 1) / 100);
        popRange.put(Popularity.COMMON, (firstNameList.names.size() * 5) / 100);
        popRange.put(Popularity.UNCOMMON, (firstNameList.names.size() * 25) / 100);

        firstNameList.setPopularityRange(popRange, popularity);

        return firstNameList;
    }

    private static NameList getSurnameList(Popularity popularity) {
        // Get surnames into a list
        File surnameListDir = new File("src/surnames");
        NameList surnameList = new NameList();
        try {
            surnameList = readNameList(surnameListDir + "/surnames2010.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Popularity, Integer> popRange = new HashMap<Popularity, Integer>();
        popRange.put(Popularity.POPULAR, (surnameList.names.size() * 1) / 1000);
        popRange.put(Popularity.COMMON, (surnameList.names.size() * 1) / 100);
        popRange.put(Popularity.UNCOMMON, (surnameList.names.size() * 5) / 100);

        surnameList.setPopularityRange(popRange, popularity);
        return surnameList;
    }

    private static NameList readNameList(String fileName) throws IOException {
        String line = null;
        NameList nameList =  new NameList();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Extract census year from fileName
            Pattern pattern = Pattern.compile("([\\d{4}])+");
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                nameList.year = Integer.parseInt(matcher.group(0));
            }
            // Skip first line if headers
            String headerLine = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                Name name = new Name();
                name.value = splitLine[0];
                if (splitLine[1].equalsIgnoreCase("f")) {
                    name.gender = Gender.FEMALE;
                } else if (splitLine[1].equalsIgnoreCase("m")) {
                    name.gender = Gender.MALE;
                } else {
                    name.gender = Gender.NONE;
                }
                name.count = Integer.parseInt(splitLine[2]);
                nameList.names.add(name);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found!");
            e.printStackTrace();
        }
        return nameList;
    }
}