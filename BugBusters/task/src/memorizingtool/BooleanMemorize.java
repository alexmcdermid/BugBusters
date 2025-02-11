package memorizingtool; // Chapter 1

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * It is all about memorizing Booleans. You see, regular Booleans are so forgetful!
 * They constantly change their value, and it's just too much for us to handle.
 * It probably has a magical power to store Boolean values indefinitely.
 * You can pass a Boolean to it, and it will remember it forever.
 *
 * This class must be a lifesaver for forgetful programmers like me.
 * No more worrying about Booleans changing unexpectedly.
 * We can now rely on the trustworthy BooleanMemorize class to keep our Booleans intact.
 * I can't wait to use it in my next project!
 */
public class BooleanMemorize {

  static ArrayList<Boolean> list = new ArrayList<>();
  boolean finished = false;
  static List<Object> args = new ArrayList<>();
  static Map<String, Class<?>[]> commands;

  public BooleanMemorize() {
    list.clear();
    commands = new HashMap<>();
    commands.put("/help", new Class<?>[]{});
    commands.put("/menu", new Class<?>[]{});
    commands.put("/add", new Class<?>[]{Boolean.class});
    commands.put("/remove", new Class<?>[]{int.class});
    commands.put("/replace", new Class<?>[]{int.class, Boolean.class});
    commands.put("/replaceAll", new Class<?>[]{Boolean.class, Boolean.class});
    commands.put("/index", new Class<?>[]{Boolean.class});
    commands.put("/sort", new Class<?>[]{String.class});
    commands.put("/frequency", new Class<?>[]{});
    commands.put("/print", new Class<?>[]{int.class});
    commands.put("/printAll", new Class<?>[]{String.class});
    commands.put("/getRandom", new Class<?>[]{});
    commands.put("/count", new Class<?>[]{Boolean.class});
    commands.put("/size", new Class<?>[]{});
    commands.put("/equals", new Class<?>[]{int.class, int.class});
    commands.put("/readFile", new Class<?>[]{String.class});
    commands.put("/writeFile", new Class<?>[]{String.class});
    commands.put("/clear", new Class<?>[]{});
    commands.put("/compare", new Class<?>[]{int.class, int.class});
    commands.put("/mirror", new Class<?>[]{});
    commands.put("/unique", new Class<?>[]{});
    commands.put("/flip", new Class<?>[]{int.class});
    commands.put("/negateAll", new Class<?>[]{});
    commands.put("/and", new Class<?>[]{int.class, int.class});
    commands.put("/or", new Class<?>[]{int.class, int.class});
    commands.put("/logShift", new Class<?>[]{int.class});
    commands.put("/convertTo", new Class<?>[]{String.class});
    commands.put("/morse", new Class<?>[]{});
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
            if (data[i].equalsIgnoreCase("true") || data[i].equalsIgnoreCase("false")) {
              args.add(Boolean.parseBoolean(data[i]));
            } else {
              throw new NumberFormatException();
            }
          } else {
            args.add(data[i]);
          }
        }

        this.getClass()
                .getDeclaredMethod(data[0].substring(1), commands.get(data[0]))
                .invoke(this, args.toArray());

      } catch (NumberFormatException e) {
        // lol can literally break the test checking for infinite loop with Math.random() appended
        // it isn't an infinite loop either, just a large volume of the same triggering it.
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
                    "Boolean-specific commands:\n" +
                    "===================================================================================================================\n" +
                    "/flip [<int> INDEX] - Flip the specified boolean\n" +
                    "/negateAll - Negate all the booleans in memory\n" +
                    "/and [<int> INDEX1] [<int> INDEX2] - Calculate the bitwise AND of the two specified elements\n" +
                    "/or [<int> INDEX1] [<int> INDEX2] - Calculate the bitwise OR of the two specified elements\n" +
                    "/logShift [<int> NUM] - Perform a logical shift of elements in memory by the specified amount\n" +
                    "/convertTo [string/number] - Convert the boolean(bit) sequence in memory to the specified type\n" +
                    "/morse - Convert the boolean(bit) sequence to Morse code\n" +
                    "===================================================================================================================");
  }

  void menu() {
    finished = true;
  }

  void add(Boolean element) {
    list.add(element);
    System.out.println("Element " + element + " added");
  }

  void remove(int index) {
    list.remove(index);
    System.out.println("Element on " + index + " position removed");
  }

  void replace(int index, Boolean element) {
    list.set(index, element);
    System.out.println("Element on " + index + " position replaced with " + element);
  }

  void replaceAll(Boolean from, Boolean to) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).equals(from)) {
        list.set(i, to);
      }
    }
    System.out.println("Each " + from + " element replaced with " + to);
  }

  void index(Boolean value) {
    int idx = list.indexOf(value);
    if (idx == -1) {
      System.out.println("There is no such element");
    } else {
      System.out.println("First occurrence of " + value + " is on " + idx + " position");
    }
  }

  void sort(String way) {
    if (!(way.equals("ascending") || way.equals("descending"))) {
      System.out.println("Incorrect argument, possible arguments: ascending, descending");
      return;
    }
    // For Booleans, ascending order: false then true; descending: true then false.
    for (int i = 0; i < list.size(); i++) {
      for (int j = i + 1; j < list.size(); j++) {
        if (way.equals("ascending")) {
          if (list.get(i) && !list.get(j)) { // true should come after false
            Boolean temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
          }
        } else { // way equals "descending"
          if (!list.get(i) && list.get(j)) { // false should come after true
            Boolean temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
          }
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
    Map<Boolean, Long> counts = new HashMap<>();
    for (Boolean b : list) {
      counts.put(b, counts.getOrDefault(b, 0L) + 1);
    }
    System.out.println("Frequency:");
    for (Map.Entry<Boolean, Long> entry : counts.entrySet()) {
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
        System.out.println("List of elements:");
        for (Boolean i : list) {
          System.out.println(i);
        }
        break;
      case "oneLine":
        System.out.println("List of elements:");
        for (int i = 0; i < list.size() - 1; i++) {
          System.out.print(list.get(i) + " ");
        }
        if (!list.isEmpty()) {
          System.out.print(list.get(list.size() - 1));
        }
        System.out.println();
        break;
      default:
        System.out.println("Incorrect argument, possible arguments: asList, lineByLine, oneLine");
    }
  }

  void count(Boolean value) {
    int amount = 0;
    for (Boolean i : list) {
      if (i.equals(value)) {
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
      FileReaderBoolean readerThread = new FileReaderBoolean();
      ArrayList<Boolean> list2 = readerThread.read(path);
      list.addAll(list2);
      System.out.println("Data imported: " + list2.size());
    } catch (IOException e) {
      System.out.println("File not found!");
    }
  }

  void writeFile(String path) throws IOException {
    FileWriterBoolean writer = new FileWriterBoolean();
    writer.write(path, list);
    System.out.println("Data exported: " + list.size());
  }

  void clear() {
    list.clear();
    System.out.println("Data cleared");
  }

  void compare(int i, int j) {
    if (list.get(i) && !list.get(j)) {
      System.out.println("Result: " + list.get(i) + " > " + list.get(j));
    } else if (!list.get(i) && list.get(j)) {
      System.out.println("Result: " + list.get(i) + " < " + list.get(j));
    } else {
      System.out.println("Result: " + list.get(i) + " = " + list.get(j));
    }
  }

  void mirror() {
    ArrayList<Boolean> list2 = new ArrayList<>();
    for (int i = list.size() - 1; i >= 0; i--) {
      list2.add(list.get(i));
    }
    list.clear();
    list.addAll(list2);
    System.out.println("Data reversed");
  }

  void unique() {
    Map<Boolean, Long> counts = new HashMap<>();
    for (Boolean i : list) {
      counts.put(i, counts.getOrDefault(i, 0L) + 1);
    }
    ArrayList<Boolean> list2 = new ArrayList<>();
    for (Map.Entry<Boolean, Long> entry : counts.entrySet()) {
      list2.add(entry.getKey());
    }
    System.out.println("Unique values: " + Arrays.toString(list2.toArray()));
  }

  void flip(int index) {
    list.set(index, !list.get(index));
    System.out.println("Element on " + index + " position flipped");
  }

  void negateAll() {
    list.replaceAll(e -> !e);
    System.out.println("All elements negated");
  }

  void and(int i, int j) {
    boolean a = list.get(i), b = list.get(j);
    boolean res = a && b;
    System.out.printf("Operation performed: (%b && %b) is %b\n", a, b, res);
  }

  void or(int i, int j) {
    boolean a = list.get(i), b = list.get(j);
    boolean res = a || b;
    System.out.printf("Operation performed: (%b || %b) is %b\n", a, b, res);
  }

  void logShift(int n) {
    int size = list.size();
    int outputValue = n;
    if (size == 0) {
      return;
    }
    n %= size;
    if (n < 0) {
      n += size;
    }
    for (int i = 0; i < n; i++) {
      Boolean last = list.get(size - 1);
      for (int j = size - 1; j > 0; j--) {
        list.set(j, list.get(j - 1));
      }
      list.set(0, last);
    }
    System.out.println("Elements shifted by " + outputValue);
  }

  void convertTo(String type) {
    if (list.isEmpty()) {
      System.out.println("No data memorized");
      return;
    }
    StringBuilder binary = new StringBuilder();
    for (boolean b : list) {
      binary.append(b ? "1" : "0");
    }
    switch (type.toLowerCase()) {
      case "number":
        System.out.println("Converted: " + Long.parseLong(binary.toString(), 2));
        break;
      case "string":
        int byteSize = 8; // 8 bits per byte.
        StringBuilder sb = new StringBuilder();
        int remainder = binary.length() % byteSize;
        if (remainder != 0) {
          System.out.println("Amount of elements is not divisible by 8, so the last " + remainder + " will be ignored");
        }
        for (int i = 0; i < binary.length() - remainder; i += byteSize) {
          String segment = binary.substring(i, i + byteSize);
          int asciiValue = Integer.parseInt(segment, 2);
          sb.append((char) asciiValue);
        }
        String asciiSequence = sb.toString();
        System.out.println("Converted: " + asciiSequence);
        break;
      default:
        System.out.println("Incorrect argument, possible arguments: string, number");
    }
  }

  void morse() {
    if (list.isEmpty()) {
      System.out.println("No data memorized");
      return;
    }
    StringBuilder morseCode = new StringBuilder("Morse code: ");
    for (boolean b : list) {
      morseCode.append(b ? "." : "_");
    }
    System.out.println(morseCode);
  }
}
