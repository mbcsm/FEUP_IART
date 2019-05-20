import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Main Class Of the project, everything runs from here.
 * All the classes of the algorithms used are called
 * and the constants used by them are also set at the top, you are
 * welcomed to change them.
 *
 */
public class Main {

    static int ARGUMENT_GOAL_INDEX = 8;
    static String TEST_FILE_NAME = "d1p01M";
    static int C45_NN_NUMBER_TEST_FILES = 10;

    static int KNN_NUMBER_INSTANCES = 259;
    static double NN_LEARNING_RATE = 0.1;
    static double NN_MOMENTUM = 0.4;
    static int NN_TRAINING_TIME = 100;
    static String NN_HIDDEN_LAYERS = "15";

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        double percentageKNN = runKNN();
        double percentageC45 = runC45();
        double percentageNN = runNN();

        try {
            BarChart.main(percentageNN, percentageC45, percentageKNN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the A* Algorithm and displays the data from it
     * @throws CloneNotSupportedException
     */
    private static double runNN() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("                NN                   ");
        System.out.println("=====================================");

        long startTime = System.currentTimeMillis();


        NN mNN = new NN();
        try {
            mNN.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("TIME SPENT = " + elapsedTime + "ms");
        return mNN.getCorrectPredictionsPercentage();
    }

    /**
     * Runs the A* Algorithm and displays the data from it
     * @throws CloneNotSupportedException
     */
    private static double runC45() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("               C4.5                  ");
        System.out.println("=====================================");

        long startTime = System.currentTimeMillis();


        C45 mC45 = new C45();
        try {
            mC45.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("TIME SPENT = " + elapsedTime + "ms");

        return mC45.getCorrectPredictionsPercentage();
    }


    /**
     * Runs the A* Algorithm and displays the data from it
     * @throws CloneNotSupportedException
     */
    private static double runKNN() throws CloneNotSupportedException {

        System.out.println("=====================================");
        System.out.println("               KNN                  ");
        System.out.println("=====================================");

        long startTime = System.currentTimeMillis();

        KNN mKNN = new KNN();
        try {
            mKNN.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        System.out.println("TIME SPENT = " + elapsedTime + "ms");
        return mKNN.getCorrectPredictionsPercentage();
    }
}
