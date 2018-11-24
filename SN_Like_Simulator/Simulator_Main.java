import java.io.*;
import java.util.*;
/**
 * Write a description of class Simulator_Main here.
 * 
 * @author Joseph E. Cenci
 * @version 1.0 - October 2017
 */
public class Simulator_Main
{
    GUIClass guiClass;
    /**
     * Constructor for objects of class Simulator_Main
     */
    public Simulator_Main() { }

    public static void main(String args[]) {
        Simulator_Main sim = new Simulator_Main();
        sim.callGUI();
    }
    
    public void callGUI() {
        guiClass = new GUIClass();
        guiClass.makeGUI();
    }
}
