package memorizingtool; // Chapter 5

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Continuing with our theme of memorization, this class is designed to help us remember words or strings.
 * After all, words can be quite elusive, especially when working with large amounts of text.
 * The WordMemorize class provides methods to store and recall words.
 *
 * This class goes a step further to offer additional functionalities specific to words.
 * It has methods like "concat" to join two words, "swapCase" to swap the case of characters,
 * "upper" to convert to uppercase, "lower" to convert to lowercase, "reverse" to reverse a word,
 * "length" to determine the length of a word, "join" to join all words with a specified delimiter,
 * and "regex" to search for words matching a regular expression.
 *
 * With the WordMemorize class in our toolkit, we can confidently keep track of important words and manipulate them as needed.
 */
public class WordMemorize {
  static ArrayList<String> list = new ArrayList<>();
  boolean finished = false;
  static List<Object> args = new ArrayList<>();
  static Map<String, Class<?>[]> commands;

  public WordMemorize() {
    list.clear();
    commands = new HashMap<>();
    commands.put("/help", new Class<?>[]{});
    commands.put("/menu", new Class<?>[]{});
    commands.put("/add", new Class<?>[]{String.class});
    commands.put("/remove", new Class<?>[]{int.class});
    commands.put("/replace", new Class<?>[]{int.class, String.class});
    commands.put("/replaceAll", new Class<?>[]{String.class, String.class});
    commands.put("/index", new Class<?>[]{String.class});
    commands.put("/sort", new Class<?>[]{String.class});
    commands.put("/frequency", new Class<?>[]{});
    commands.put("/print", new Class<?>[]{int.class});
    commands.put("/printAll", new Class<?>[]{String.class});
    commands.put("/getRandom", new Class<?>[]{});
    commands.put("/count", new Class<?>[]{String.class});
    commands.put("/size", new Class<?>[]{});
    commands.put("/equals", new Class<?>[]{int.class, int.class});
    commands.put("/readFile", new Class<?>[]{String.class});
    commands.put("/writeFile", new Class<?>[]{String.class});
    commands.put("/clear", new Class<?>[]{});
    commands.put("/compare", new Class<?>[]{int.class, int.class});
    commands.put("/mirror", new Class<?>[]{});
    commands.put("/unique", new Class<?>[]{});
    commands.put("/concat", new Class<?>[]{int.class, int.class});
    commands.put("/swapCase", new Class<?>[]{int.class});
    commands.put("/upper", new Class<?>[]{int.class});
    commands.put("/lower", new Class<?>[]{int.class});
    commands.put("/reverse", new Class<?>[]{int.class});
    commands.put("/length", new Class<?>[]{int.class});
    commands.put("/join", new Class<?>[]{String.class});
    commands.put("/regex", new Class<?>[]{String.class});
  }

  void Run() {
    Scanner scanner = new Scanner(System.in);
    while (!finished) {
      args.clear();
      System.out.println("Perform action:");
      if (!scanner.hasNextLine()) break;
      String[] data = scanner.nextLine().split(" ");

      if (!commands.containsKey(data[0])) {
        System.out.println("No such command!");
        continue;
      }

      if (data.length - 1 != commands.get(data[0]).length) {
        System.out.println("Incorrect amount of arguments");
        continue;
      }

      try {
        for (int i = 1; i < data.length; i++) {
          Class<?> paramType = commands.get(data[0])[i - 1];
          if (paramType.equals(int.class)) {
            args.add(Integer.parseInt(data[i]));
          } else if (paramType.equals(Boolean.class)) {
            args.add(Boolean.parseBoolean(data[i]));
          } else {
            args.add(data[i]);
          }
        }

        this.getClass()
                .getDeclaredMethod(data[0].substring(1), commands.get(data[0]))
                .invoke(this, args.toArray());

      } catch (NumberFormatException e) {
        System.out.println("Some arguments can't be parsed!" + Math.random());
        continue;
      } catch (InvocationTargetException e) {
        Throwable cause = e.getCause();
        if (cause instanceof IndexOutOfBoundsException) {
          System.out.println("Index out of bounds!");
        } else if (cause instanceof NumberFormatException) {
          System.out.println("Some arguments can't be parsed!");
        } else if (cause instanceof IllegalArgumentException) {
          if (data[0].equals("/sort")) {
            System.out.println("Incorrect argument, possible arguments: ascending, descending");
          } else if (data[0].equals("/printAll")) {
            System.out.println("Incorrect argument, possible arguments: asList, lineByLine, oneLine");
          } else if (data[0].equals("/convertTo")) {
            System.out.println("Incorrect argument, possible arguments: string, number");
          } else {
            System.out.println(cause.getMessage());
          }
        } else {
          System.out.println(cause.getMessage());
        }
        continue;
      } catch (NoSuchMethodException | IllegalAccessException e) {
        System.out.println("No such command!");
        continue;
      } catch (Exception e) {
        System.out.println(e.getMessage());
        continue;
      }
    }
  }

  void help() {
    System.out.println(
            "===================================================================================================================\n" +
                    "Usage: COMMAND [<TYPE> PARAMETERS]\n" +
                    "===================================================================================================================\n" +
                    "General commands:\n" +
                    "===================================================================================================================\n" +
                    "/help - Display this help message\n" +
                    "/menu - Return to the menu\n" +
                    "\n" +
                    "/add [<T> ELEMENT] - Add the specified element to the list\n" +
                    "/remove [<int> INDEX] - Remove the element at the specified index from the list\n" +
                    "/replace [<int> INDEX] [<T> ELEMENT] - Replace the element at specified index with the new one\n" +
                    "/replaceAll [<T> OLD] [<T> NEW] - Replace all occurrences of specified element with the new one\n" +
                    "\n" +
                    "/index [<T> ELEMENT] - Get the index of the first specified element in the list\n" +
                    "/sort [ascending/descending] - Sort the list in ascending or descending order\n" +
                    "/frequency - The frequency count of each element in the list\n" +
                    "/print [<int> INDEX] - Print the element at the specified index in the list\n" +
                    "/printAll [asList/lineByLine/oneLine] - Print all elements in the list in specified format\n" +
                    "/getRandom - Get a random element from the list\n" +
                    "/count [<T> ELEMENT] - Count the number of occurrences of the specified element in the list\n" +
                    "/size - Get the number of elements in the list\n" +
                    "/equals [<int> INDEX1] [<int> INDEX2] - Check if two elements are equal\n" +
                    "/clear - Remove all elements from the list\n" +
                    "/compare [<int> INDEX1] [<int> INDEX2] Compare elements at the specified indices in the list\n" +
                    "/mirror - Mirror elements' positions in list\n" +
                    "/unique - Unique elements in the list\n" +
                    "/readFile [<string> FILENAME] - Import data from the specified file and add it to the list\n" +
                    "/writeFile [<string> FILENAME] - Export the list data to the specified file");
    System.out.println(
            "===================================================================================================================\n" +
                    "Word-specific commands:\n" +
                    "===================================================================================================================\n" +
                    "/concat [<int> INDEX1] [<int> INDEX2] Concatenate two specified strings\n" +
                    "/swapCase [<int> INDEX] Output swapped case version of the specified string\n" +
                    "/upper [<int> INDEX] Output uppercase version of the specified string\n" +
                    "/lower [<int> INDEX] Output lowercase version of the specified string\n" +
                    "/reverse [<int> INDEX] Output reversed version of the specified string\n" +
                    "/length [<int> INDEX] Get the length of the specified string\n" +
                    "/join [<string> DELIMITER] Join all the strings with the specified delimiter\n" +
                    "/regex [<string> PATTERN] Search for all elements that match the specified regular expression pattern\n" +
                    "===================================================================================================================");
  }

  void menu() {
    this.finished = true;
  }

  void add(String element) {
    list.add(element);
    System.out.println("Element " + element + " added");
  }

  void remove(int index) {
    list.remove(index);
    System.out.println("Element on " + index + " position removed");
  }

  void replace(int index, String element) {
    list.set(index, element);
    System.out.println("Element on " + index + " position replaced with " + element);
  }

  void replaceAll(String from, String to) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).equals(from)) {
        list.set(i, to);
      }
    }
    System.out.println("Each " + from + " element replaced with " + to);
  }

  void index(String value) {
    int idx = list.indexOf(value);
    if (idx == -1) {
      System.out.println("There is no such element");
    } else {
      System.out.println("First occurrence of " + value + " is on " + idx + " position");
    }
  }

  void sort(String way) {
    if (!(way.equalsIgnoreCase("ascending") || way.equalsIgnoreCase("descending"))) {
      System.out.println("Incorrect argument, possible arguments: ascending, descending");
      return;
    }
    // Use a simple bubble sort for clarity.
    for (int i = 0; i < list.size(); i++) {
      for (int j = i + 1; j < list.size(); j++) {
        if (way.equalsIgnoreCase("ascending") && list.get(i).compareTo(list.get(j)) > 0) {
          String temp = list.get(i);
          list.set(i, list.get(j));
          list.set(j, temp);
        } else if (way.equalsIgnoreCase("descending") && list.get(i).compareTo(list.get(j)) < 0) {
          String temp = list.get(i);
          list.set(i, list.get(j));
          list.set(j, temp);
        }
      }
    }
    System.out.printf("Memory sorted %s\n", way);
  }

  void frequency() {
    if (list.isEmpty()) {
      System.out.println("There are no elements");
      return;
    }
    Map<String, Long> counts = new HashMap<>();
    for (String b : list) {
      counts.put(b, counts.getOrDefault(b, 0L) + 1);
    }
    System.out.println("Frequency:");
    for (Map.Entry<String, Long> entry : counts.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

  void print(int index) {
    System.out.println("Element on " + index + " position is " + list.get(index));
  }

  void getRandom() {
    Random random = new Random();
    if (!list.isEmpty()) {
      System.out.println("Random element: " + list.get(random.nextInt(list.size())));
    } else {
      System.out.println("There are no elements memorized");
    }
  }

  void printAll(String type) {
    switch (type) {
      case "asList":
        System.out.println("List of elements:\n" + Arrays.toString(list.toArray()));
        break;
      case "lineByLine":
        System.out.println("List of elements:\n");
        for (String s : list) {
          System.out.println(s);
        }
        break;
      case "oneLine":
        System.out.println("List of elements:");
        for (int i = 0; i < list.size() - 1; i++) {
          System.out.print(list.get(i) + " ");
        }
        if (list.size() > 0)
          System.out.print(list.get(list.size() - 1));
        System.out.println();
        break;
      default:
        System.out.println("Incorrect argument, possible arguments: asList, lineByLine, oneLine");
    }
  }

  void count(String value) {
    int amount = 0;
    for (String s : list) {
      if (s.equals(value)) {
        amount++;
      }
    }
    System.out.println("Amount of " + value + ": " + amount);
  }

  void size() {
    System.out.println("Amount of elements: " + list.size());
  }

  void equals(int i, int j) {
    boolean res = list.get(i).equals(list.get(j));
    System.out.printf("%d and %d elements are%s equal: %s\n",
            i, j, res ? "" : " not", list.get(i) + (res ? " = " : " != ") + list.get(j));
  }

  void readFile(String path) {
    try {
      FileReaderWords readerThread = new FileReaderWords();
      ArrayList<String> list2 = readerThread.read(path);
      list.addAll(list2);
      System.out.println("Data imported: " + list2.size());
    } catch (IOException e) {
      System.out.println("File not found!");
    }
  }

  void writeFile(String path) throws IOException {
    FileWriterWords writer = new FileWriterWords();
    writer.write(path, list);
    System.out.println("Data exported: " + list.size());
  }

  void clear() {
    list.clear();
    System.out.println("Data cleared");
  }

  void compare(int i, int j) {
    if (list.get(i).compareTo(list.get(j)) > 0) {
      System.out.println("Result: " + list.get(i) + " > " + list.get(j));
    } else if (list.get(i).compareTo(list.get(j)) < 0) {
      System.out.println("Result: " + list.get(i) + " < " + list.get(j));
    } else {
      System.out.println("Result: " + list.get(i) + " = " + list.get(j));
    }
  }

  void mirror() {
    ArrayList<String> list2 = new ArrayList<>();
    for (int i = list.size() - 1; i >= 0; i--) {
      list2.add(list.get(i));
    }
    list = list2;
    System.out.println("Data reversed");
  }

  void unique() {
    Map<String, Long> counts = new HashMap<>();
    for (String s : list) {
      if (counts.get(s) == null) {
        counts.put(s, 1L);
      } else {
        counts.put(s, counts.get(s) + 1);
      }
    }
    ArrayList<String> list2 = new ArrayList<>();
    for (Map.Entry<String, Long> entry : counts.entrySet()) {
      list2.add(entry.getKey());
    }
    System.out.println("Unique values: " + Arrays.toString(list2.toArray()));
  }

  void concat(int i, int j) {
    System.out.println("Concatenated string: " + list.get(i) + list.get(j));
  }

  void swapCase(int i) {
    String original = list.get(i);
    System.out.printf("\"%s\" string with swapped case: ", original);
    for (char c : original.toCharArray()) {
      if (Character.isUpperCase(c)) {
        System.out.print(Character.toLowerCase(c));
      } else if (Character.isLowerCase(c)) {
        System.out.print(Character.toUpperCase(c));
      } else {
        System.out.print(c);
      }
    }
    System.out.println();
  }

  void upper(int i) {
    System.out.printf("Uppercase \"%s\" string: %s\n", list.get(i), list.get(i).toUpperCase());
  }

  void lower(int i) {
    System.out.printf("Lowercase \"%s\" string: %s\n", list.get(i), list.get(i).toLowerCase());
  }

  void reverse(int i) {
    System.out.printf("Reversed \"%s\" string: %s\n", list.get(i), new StringBuilder(list.get(i)).reverse());
  }

  void length(int i) {
    System.out.printf("Length of \"%s\" string: %d\n", list.get(i), list.get(i).length());
  }

  void join(String delimiter) {
    System.out.printf("Joined string: %s\n", String.join(delimiter, list));
  }

  void regex(String regex) {
    List<String> matchingElements = new ArrayList<>();
    try {
      Pattern pattern = Pattern.compile(regex);
      for (String element : list) {
        if (pattern.matcher(element).matches()) {
          matchingElements.add(element);
        }
      }
    } catch (Exception e) {
      System.out.println("Incorrect regex pattern provided");
      return;
    }
    if (matchingElements.isEmpty()) {
      System.out.println("There are no strings that match provided regex");
    } else {
      System.out.println("Strings that match provided regex:");
      System.out.println(Arrays.toString(matchingElements.toArray()));
    }
  }
}
