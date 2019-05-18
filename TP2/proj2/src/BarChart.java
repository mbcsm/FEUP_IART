import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChart extends JFrame {

    private static final long serialVersionUID = 1L;
    private double percentageNN;
    private double percentageC45;
    private double percentageKNN;


    public BarChart(double percentageNN, double percentageC45, double percentageKNN) {
        super("Percentage");

        this.percentageNN = percentageNN;
        this.percentageC45 = percentageC45;
        this.percentageKNN = percentageKNN;

        // Create Dataset
        CategoryDataset dataset = createDataset();

        //Create chart
        JFreeChart chart=ChartFactory.createBarChart(
                "Percentage of correct values in the different Algorithms implemented", //Chart Title
                "Algorithm", // Category axis
                "Percentage", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false
        );

        ChartPanel panel=new ChartPanel(chart);
        setContentPane(panel);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Population in 2005
        dataset.addValue(percentageKNN, "percentage", "KNN");
        dataset.addValue(percentageC45, "percentage", "C45");
        dataset.addValue(percentageNN, "percentage", "NN");

        return dataset;
    }

    public static void main(double percentageNN, double percentageC45, double percentageKNN) throws Exception {
        SwingUtilities.invokeAndWait(()->{
            BarChart example=new BarChart(percentageNN, percentageC45, percentageKNN);
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}  