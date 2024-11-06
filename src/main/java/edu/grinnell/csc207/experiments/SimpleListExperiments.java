package edu.grinnell.csc207.experiments;

import edu.grinnell.csc207.util.SimpleList;

import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Some simple experiments with SimpleLists.
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 * @author Your Name Here
 */
public class SimpleListExperiments {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /** We'll be generating some random numbers. */
  static Random rand = new Random();

  // +-----------+---------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Add an element using an iterator.
   *
   * @param pen
   *   Used to log the addition
   * @param it
   *   The list iterator used to add the element.
   * @param val
   *   The value we're adding.
   */
  static void add(PrintWriter pen, ListIterator<String> it, String val)
      throws Exception {
    pen.println("Add \"" + val + "\" at position " + it.nextIndex());
    it.add(val);
  } // add(PrintWriter)

  /**
   * Print a list.
   *
   * @param pen
   *   How to print.
   * @param lst
   *   The list to print.
   */
  static void printList(PrintWriter pen, SimpleList<String> lst) {
    int i = 0;
    for (String val : lst) {
      pen.print(i++ + ":" + val + "\t");
    } // for
    pen.println();
  } // printList(PrintWriter, SimpleList<String>)

  /**
   * Add a variety of elements, describing what happens.
   *
   * @param pen
   *   How to print.
   * @param lst
   *   The list to mutate.
   * @param strings
   *   The strings to add.
   */
  static void addExpt(PrintWriter pen, SimpleList<String> lst,
      String[] strings) throws Exception {
    ListIterator<String> lit = lst.listIterator();

    for (String str : strings) {
      add(pen, lit, str);
      printList(pen, lst);
      pen.println();
    } // for
  } // addExpt(PrintWriter, SimpleList<String>, String[])

  /**
   * Add a variety of elements, without describing what happens.
   *
   * @param pen
   *   How to print.
   * @param lst
   *   The list to mutate.
   * @param strings
   *   The strings to add.
   */
  static void addStrings(PrintWriter pen, SimpleList<String> lst,
      String[] strings) throws Exception {
    ListIterator<String> lit = lst.listIterator();

    for (String str : strings) {
      lit.add(str);
    } // for
    printList(pen, lst);
    pen.println();
  } // addStrings

  /**
   * Remove a variety of elements, moving forward.
   *
   * @param pen
   *   Used to print updates.
   * @param lst
   *   The list to modify.
   * @param pred
   *   The predicate used to determine whether to remove an element.
   */
  static void removeForwardExpt(PrintWriter pen, SimpleList<String> lst,
      Predicate<String> pred) throws Exception {
    ListIterator<String> lit = lst.listIterator();

    while (lit.hasNext()) {
      String str = lit.next();
      if (pred.test(str)) {
        pen.println("Remove " + str);
        lit.remove();
        printList(pen, lst);
        pen.println();
      } // if
    } // while
  } // removeForwardExpt(PrintWriter, SimpleList<String>, Predicate<String>)

  /**
   * Remove a variety of elements, moving backward.
   *
   * @param pen
   *   Used to print updates.
   * @param lst
   *   The list to modify.
   * @param pred
   *   The predicate used to determine whether to remove an element.
   */
  static void removeBackwardExpt(PrintWriter pen, SimpleList<String> lst,
      Predicate<String> pred) throws Exception {
    ListIterator<String> lit = lst.listIterator();

    // Advance to the end of the list
    while (lit.hasNext()) {
      lit.next();
    } // while

    // And then back up
    while (lit.hasPrevious()) {
      String str = lit.previous();
      if (pred.test(str)) {
        pen.println("Remove " + str);
        lit.remove();
        printList(pen, lst);
        pen.println();
      } // if
    } // while
  } // removeBackwardExpt(PrintWriter, SimpleList<String>, Predicate<String>)

  /**
   * Randomly remove n elements, moving forward and backward.
   *
   * @param pen
   *   Where to print updates.
   * @param lst
   *   The list to update.
   * @param n
   *   How many elements to remove.
   *
   * @pre 0 <= n < length of lst
   */
  static void randomWalkRemove(PrintWriter pen, SimpleList<String> lst,
      int n) {
    ListIterator<String> lit = lst.listIterator();

    for (int i = 0; i < n; i++) {
      String val = "";

      // Random walk
      for (int j = 0; j < 5; j++) {
        if (!lit.hasNext() || (lit.hasPrevious() && rand.nextInt(2) == 0)) {
          pen.println("Backward to " + lit.previousIndex());
          val = lit.previous();
        } else {
          pen.println("Forward to " + lit.nextIndex());
          val = lit.next();
        } // if/else
      } // for j
      pen.println("Removing " + val);
      lit.remove();
      printList(pen, lst);
    } // for i
  } // randomWalkRemove(n)

  // +-------------+-------------------------------------------------
  // | Experiments |
  // +-------------+

  /**
   * A simple experiment of adding a few elements.
   *
   * @param pen
   *   Where to print a log of what happens.
   * @param lst
   *   The list we're modifying.
   */
  static void experiment1(PrintWriter pen, SimpleList<String> lst)
      throws Exception {
    pen.println("Experiment 1: Add a variety of elements.");
    addExpt(pen, lst, new String[] {"A", "B", "C"});
    addExpt(pen, lst, new String[] {"X", "Y", "Z"});
    pen.println();
  } // experiment1(PrintWriter, SimpleList<String>)

  /**
   * A simple experiment of removing alternating elements.
   *
   * @param pen
   *   Where to print a log of what happens.
   * @param lst
   *   The list we're modifying.
   */
  static void experiment2(PrintWriter pen, SimpleList<String> lst)
       throws Exception {
    pen.println("Experiment 2: Remove alternating elements, moving forward.");
    final Counter counter = new Counter();
    addStrings(pen, lst, new String[] {"A", "B", "C", "D", "E", "F", "G"});
    removeForwardExpt(pen, lst, (str) -> (counter.get() % 2) == 0);
    pen.println();
  } // experiment2(PrintWriter, SimpleList<String>)

  /**
   * A simple experiment of removing randomly-selected elements.
   *
   * @param pen
   *   Where to print a log of what happens.
   * @param lst
   *   The list we're modifying.
   */
  static void experiment3(PrintWriter pen, SimpleList<String> lst)
      throws Exception {
    pen.println("Experiment 3: Remove random elements, moving forward.");
    addStrings(pen, lst, new String[] {"A", "B", "C", "D", "E", "F", "G"});
    removeForwardExpt(pen, lst, (str) -> rand.nextInt(2) == 0);
    pen.println();
  } // experiment3(PrintWriter, SimpleList<String>

  /**
   * A more complex experiment of removing randomly-selected elements.
   *
   * @param pen
   *   Where to print a log of what happens.
   * @param lst
   *   The list we're modifying.
   * @param n
   *   How many elements to remove.
   */
  static void experiment4(PrintWriter pen, SimpleList<String> lst, int n)
      throws Exception {
    pen.println("Experiment 4: Removing elements with a random walk.");
    addStrings(pen, lst, new String[] {"A", "B", "C", "D", "E", "F", "G"});
    try {
      randomWalkRemove(pen, lst, n);
    } catch (Exception e) {
      pen.println("Experiment ended early because " + e.toString());
    } // try/catch
    pen.println();
  } // experiment4(PrintWriter, SimpleList<String>, int)

} // class SimpleListExperiments

/**
 * A simple counter.
 */
class Counter {
  /** The current value of the counter. */
  int val = 0;
  /**
   * Get the value of the counter, thereby incrementing the counter.
   *
   * @return the current value of the counter.
   */
  int get() {
    return val++;
  } // get()
} // class Counter
