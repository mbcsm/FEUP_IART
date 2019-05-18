import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.ShapeUtils;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart extends JFrame {

    ArrayList<PointObject> originalArrayList;
    ArrayList<PointObject> createdArrayList;
    String name;
    public Chart(ArrayList<PointObject> originalArrayList, ArrayList<PointObject> createdArrayList, String name) {
        this.originalArrayList = originalArrayList;
        this.createdArrayList = createdArrayList;
        this.name = name;
        initUI();
    }

    private void initUI() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle(name + "prediction");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset() {

        XYSeries series1 = new XYSeries("real");
        for(PointObject mPoint : originalArrayList){
            series1.add(mPoint.id, mPoint.value);

        }


        XYSeries series2 = new XYSeries(name);
        for(PointObject mPoint : createdArrayList){
            series2.add(mPoint.id, mPoint.value);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                name + "prediction",
                "id",
                "value",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(0.0f));

        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(0.0f));


        double sizeOriginal = 5.0;
        double sizeCreated = 10.0;
        renderer.setSeriesShape(0, new Rectangle2D.Double(-sizeOriginal/2, -sizeOriginal/2, sizeOriginal, sizeOriginal));
        renderer.setSeriesShape(1, new Rectangle2D.Double(-sizeCreated/2, -sizeCreated/2, sizeCreated, sizeCreated));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);


        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(name,
                        new Font("Serif", Font.BOLD, 26)
                )
        );

        return chart;
    }


    static void start(ArrayList<PointObject> originalArrayList, ArrayList<PointObject> createdArrayList, String name) {
        SwingUtilities.invokeLater(() -> {
            Chart ex = new Chart(originalArrayList, createdArrayList, name);
            ex.setVisible(true);
        });
    }
}