import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("What year would you like your name to be generated from?");
        int year = in.nextInt();
        System.out.println("How common do you want the name to be? (Popular, Common, Uncommon, Rare)");
        String input = in.next();
        Popularity popularity = parsePopularity(input);

        Map<Integer, NameList> firstNameLists = getFirstNameLists();
        NameList firstNameList = getFirstNameList(firstNameLists, year);
        NameList surnameList = getSurnameList();
        for (int i = 0; i < 15; i++) {
            generateName(firstNameList, surnameList, popularity);
        }
    }

    public static Popularity parsePopularity(String input) {
        input.toLowerCase();
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

    public static void generateName(NameList firstNameList, NameList surnameList, Popularity popularity) {
        Name first = firstNameList.getRandomName(popularity);
        Name last = surnameList.getRandomName(popularity);
        System.out.println(toSentenceCase(first.value) + " ("+ first.count + ") " +
                toSentenceCase(last.value) + " (" + last.count + ")");
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

    private static NameList getSurnameList() {
        // Get surnames into a list
        File surnameListDir = new File("src/surnames");
        NameList surNameList = new NameList();
        try {
            surNameList = readNameList(surnameListDir + "/surnames2010.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return surNameList;
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
                    name.gender = Name.Gender.FEMALE;
                } else if (splitLine[1].equalsIgnoreCase("m")) {
                    name.gender = Name.Gender.MALE;
                } else {
                    name.gender = Name.Gender.NONE;
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


    /**
     * @param value
     * @return
     */
    private static String toSentenceCase(String value) {
        String modified = value.toLowerCase().substring(1, value.length());
        String capital = value.substring(0, 1).toUpperCase();
        return capital + modified;
    }
}