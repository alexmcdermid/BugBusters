package memorizingtool; // Chapter 4

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Ah, the class NumberMemorize! Well, if we're following the same line of thinking, it is all about helping us remember numbers.
 * Because, let's be honest, numbers can be quite slippery and elusive sometimes.
 *
 * But that's not all! It has some additional features tailored specifically for numbers like:
 * "increment" to increase the stored value by a certain amount,
 * "decrement" to decrease it, and maybe even "multiply" and "divide" to perform basic arithmetic operations.
 *
 * With NumberMemorize at our disposal, we won't have to worry about forgetting or losing track of important numerical values.
 * It's like having a virtual assistant dedicated solely to keeping our numbers safe and accessible.
 */
public class NumberMemorize {

  static ArrayList<Integer> list = new ArrayList<>();
  boolean finished = false;
  static List<Object> args = new ArrayList<>();
  static Map<String, Class<?>[]> commands;

  public NumberMemorize() {
    list.clear();
    commands = new HashMap<>();
    commands.put("/help", new Class<?>[]{});
    commands.put("/menu", new Class<?>[]{});
    commands.put("/add", new Class<?>[]{int.class});
    commands.put("/remove", new Class<?>[]{int.class});
    commands.put("/replace", new Class<?>[]{int.class, int.class});
    commands.put("/replaceAll", new Class<?>[]{int.class, int.class});
    commands.put("/index", new Class<?>[]{int.class});
    commands.put("/sort", new Class<?>[]{String.class});
    commands.put("/frequency", new Class<?>[]{});
    commands.put("/print", new Class<?>[]{int.class});
    commands.put("/printAll", new Class<?>[]{String.class});
    commands.put("/getRandom", new Class<?>[]{});
    commands.put("/count", new Class<?>[]{int.class});
    commands.put("/size", new Class<?>[]{});
    commands.put("/equals", new Class<?>[]{int.class, int.class});
    commands.put("/readFile", new Class<?>[]{String.class});
    commands.put("/writeFile", new Class<?>[]{String.class});
    commands.put("/clear", new Class<?>[]{});
    commands.put("/compare", new Class<?>[]{int.class, int.class});
    commands.put("/mirror", new Class<?>[]{});
    commands.put("/unique", new Class<?>[]{});
    commands.put("/sum", new Class<?>[]{int.class, int.class});
    commands.put("/subtract", new Class<?>[]{int.class, int.class});
    commands.put("/multiply", new Class<?>[]{int.class, int.class});
    commands.put("/divide", new Class<?>[]{int.class, int.class});
    commands.put("/pow", new Class<?>[]{int.class, int.class});
    commands.put("/factorial", new Class<?>[]{int.class});
    commands.put("/sumAll", new Class<?>[]{});
    commands.put("/average", new Class<?>[]{});
  }

  void Run() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Scanner scanner = new Scanner(System.in);
    while (!finished) {
      args.clear();
      System.out.println("Perform action:");
      String[] data = scanner.nextLine().split(" ");
      for (int i = 1; i < data.length; i++) {
        if (commands.get(data[0])[i - 1].equals(int.class)) {
          args.add(Integer.parseInt(data[i]));
        } else {
          args.add(data[i]);
        }
      }
      this.getClass()
              .getDeclaredMethod(data[0].substring(1), commands.get(data[0]))
              .invoke(this, args.toArray());
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
                    "Number-specific commands:\n" +
                    "===================================================================================================================\n" +
                    "/sum [<int> INDEX1] [<int> INDEX2] - Calculate the sum of the two specified elements\n" +
                    "/subtract [<int> INDEX1] [<int> INDEX2] - Calculate the difference between the two specified elements\n" +
                    "/multiply [<int> INDEX1] [<int> INDEX2] - Calculate the product of the two specified elements\n" +
                    "/divide [<int> INDEX1] [<int> INDEX2] - Calculate the division of the two specified elements\n" +
                    "/pow [<int> INDEX1] [<int> INDEX2] - Calculate the power of the specified element to the specified exponent element\n" +
                    "/factorial [<int> INDEX] - Calculate the factorial of the specified element\n" +
                    "/sumAll - Calculate the sum of all elements\n" +
                    "/average - Calculate the average of all elements\n" +
                    "==================================================================================================================="
    );
  }

  void menu() {
    this.finished = true;
  }

  void add(int element) {
    list.add(element);
    System.out.println("Element " + element + " added");
  }

  void remove(int index) {
    list.remove(index);
    System.out.println("Element on " + index + " position removed");
  }

  void replace(int index, int element) {
    list.set(index, element);
    System.out.println("Element on " + index + " position replaced with " + element);
  }

  void replaceAll(int from, int to) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).equals(from)) {
        list.set(i, to);
      }
    }
    System.out.println("Each " + from + " element replaced with " + to);
  }

  void index(int value) {
    int i = list.indexOf(value);
    System.out.println("First occurrence of " + value + " is on " + i + " position");
  }

  void sort(String way) {
    // Use a simple bubble sort for clarity.
    for (int i = 0; i < list.size(); i++) {
      for (int j = i + 1; j < list.size(); j++) {
        if ("ascending".equalsIgnoreCase(way) && list.get(i) > list.get(j)) {
          int temp = list.get(i);
          list.set(i, list.get(j));
          list.set(j, temp);
        } else if ("descending".equalsIgnoreCase(way) && list.get(i) < list.get(j)) {
          int temp = list.get(i);
          list.set(i, list.get(j));
          list.set(j, temp);
        }
      }
    }
    System.out.printf("Memory sorted %s\n", way);
  }

  void frequency() {
    Map<Integer, Long> counts = new HashMap<>();
    for (int i : list) {
      counts.put(i, counts.getOrDefault(i, 0L) + 1);
    }
    System.out.println("Frequency:");
    for (Map.Entry<Integer, Long> entry : counts.entrySet()) {
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
      System.out.println("List is empty.");
    }
  }

  void printAll(String type) {
    switch (type) {
      case "asList":
        System.out.println("List of elements:\n" + Arrays.toString(list.toArray()));
        break;
      case "lineByLine":
        System.out.println("List of elements:");
        for (int i : list) {
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
    }
  }

  void count(int value) {
    int amount = 0;
    for (int i : list) {
      if (i == value) {
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

  void readFile(String path) throws IOException {
    FileReaderInteger readerThread = new FileReaderInteger();
    ArrayList<Integer> list2 = readerThread.read(path);
    list.addAll(list2);
    System.out.println("Data imported: " + list2.size());
  }

  void writeFile(String path) throws IOException {
    FileWriterInteger writer = new FileWriterInteger();
    writer.write(path, list);
    System.out.println("Data exported: " + list.size());
  }

  void clear() {
    list.clear();
    System.out.println("Data cleared");
  }

  void compare(int i, int j) {
    if (list.get(i) > list.get(j)) {
      System.out.println("Result: " + list.get(i) + " > " + list.get(j));
    } else if (list.get(i) < list.get(j)) {
      System.out.println("Result: " + list.get(i) + " < " + list.get(j));
    } else {
      System.out.println("Result: " + list.get(i) + " = " + list.get(j));
    }
  }

  void mirror() {
    ArrayList<Integer> list2 = new ArrayList<>();
    for (int i = list.size() - 1; i >= 0; i--) {
      list2.add(list.get(i));
    }
    list = list2;
    System.out.println("Data reversed");
  }

  void unique() {
    Map<Integer, Long> counts = new HashMap<>();
    for (int i : list) {
      counts.put(i, counts.getOrDefault(i, 0L) + 1);
    }
    ArrayList<Integer> list2 = new ArrayList<>();
    for (Map.Entry<Integer, Long> entry : counts.entrySet()) {
      list2.add(entry.getKey());
    }
    System.out.println("Unique values: " + Arrays.toString(list2.toArray()));
  }

  void sum(int i, int j) {
    int a = list.get(i), b = list.get(j);
    long res = (long) a + b;
    System.out.printf("Calculation performed: %d + %d = %d\n", a, b, res);
  }

  void subtract(int i, int j) {
    int a = list.get(i), b = list.get(j);
    long res = (long) a - b;
    System.out.printf("Calculation performed: %d - %d = %d\n", a, b, res);
  }

  void multiply(int i, int j) {
    int a = list.get(i), b = list.get(j);
    long res = (long) a * b;
    System.out.printf("Calculation performed: %d * %d = %d\n", a, b, res);
  }

  void divide(int i, int j) {
    int a = list.get(i), b = list.get(j);
    double res = (double) a / b;
    System.out.printf("Calculation performed: %d / %d = %f\n", a, b, res);
  }

  void pow(int i, int j) {
    int a = list.get(i), b = list.get(j);
    if (b < 0) {
      double res = 1.0;
      for (int k = 0; k < -b; k++) {
        res *= a;
      }
      res = 1.0 / res;
      System.out.printf("Calculation performed: %d ^ %d = %f\n", a, b, res);
    } else {
      long res = 1;
      for (int k = 0; k < b; k++) {
        res *= a;
      }
      System.out.printf("Calculation performed: %d ^ %d = %d\n", a, b, res);
    }
  }


  void factorial(int index) {
    int n = list.get(index);
    if (n <= 1) {
      System.out.printf("Calculation performed: %d! = 1\n", n);
      return;
    }

    long res = 1;
    int i = 2;
    do {
      res *= i++;
    } while (i <= n);
    System.out.printf("Calculation performed: %d! = %d\n", n, res);
  }

  void sumAll() {
    long sum = 0;
    for (int i : list) {
      sum += i;
    }
    System.out.println("Sum of all elements: " + sum);
  }

  void average() {
    if (list.isEmpty()) {
      System.out.println("Cannot calculate average: list is empty.");
      return;
    }
    double sum = 0;
    for (int i : list) {
      sum += i;
    }
    double avg = (double) sum / list.size();
    if (avg == (long) avg) {
      System.out.printf("Average of all elements: %d\n", (long) avg);
    } else {
      System.out.println("Average of all elements: " + avg);
    }
  }
}
