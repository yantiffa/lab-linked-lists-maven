package edu.grinnell.csc207.experiments;

import edu.grinnell.csc207.util.SimpleDLL;
import edu.grinnell.csc207.util.SimpleList;

import java.io.PrintWriter;

/**
 * Some simple experiments with SimpleDLLs.
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 * @author Your Name Here
 */
public class SDLLExperiment {
  /**
   * Run the experiments.
   *
   * @param args
   *   Command-line arguments (ignored).
   *
   * @throws Exception
   *   If one of the experiments goes awry.
   */
  public static void main(String[] args) throws Exception {
    PrintWriter pen = new PrintWriter(System.out, true);
    SimpleListExperiments.experiment1(pen, new SimpleDLL<String>());
    SimpleListExperiments.experiment2(pen, new SimpleDLL<String>());
    SimpleListExperiments.experiment3(pen, new SimpleDLL<String>());
    // SimpleListExperiments.experiment1(pen, new SimpleDLL<String>());
    // SimpleListExperiments.experiment4(pen, new SimpleDLL<String>(), 3);
    pen.close();
  } // main(String[]
} // SDLLExperiment
