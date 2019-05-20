import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class KNN {
    private double correctPredictionsPercentage = 0.0;
    private static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + filename);
        }

        return inputReader;
    }

    private ArrayList<Instance> instanceArrayList = new ArrayList<>();
    private Classifier ibk = new IBk();

    void start() throws Exception {
        BufferedReader datafile = readDataFile("src/dataset/continuous/" + Main.TEST_FILE_NAME);
        Instances data = new Instances(datafile);
        data.setClassIndex(Main.ARGUMENT_GOAL_INDEX);

        for(int i = 0; i < Main.KNN_NUMBER_INSTANCES; i ++){
            int random_id = (int) (Math.random() * (int) datafile.lines().count()) + 1;
            instanceArrayList.add(data.instance(random_id));
            data.delete(random_id);
        }


        ibk.buildClassifier(data);


        displayData();
    }

    private void displayData() throws Exception {
        ArrayList<PointObject> originalArrayList = new ArrayList<>();
        ArrayList<PointObject> predictionArrayList = new ArrayList<>();

        int id = 0;
        int totalCorrectAnswers = 0;

        for(Instance mInstance : instanceArrayList){
            double predictedValue = ibk.classifyInstance(mInstance);
            double originalValue = Double.valueOf(mInstance.toString(Main.ARGUMENT_GOAL_INDEX));


            PointObject mOriginalPoint = new PointObject(id, originalValue);
            PointObject mPredictedPoint = new PointObject(id, predictedValue);
            originalArrayList.add(mOriginalPoint);
            predictionArrayList.add(mPredictedPoint);

            if(Math.round(originalValue) == Math.round(predictedValue)){
                totalCorrectAnswers++;
            }
            id++;
        }

        correctPredictionsPercentage = (double) totalCorrectAnswers / Main.KNN_NUMBER_INSTANCES;
        //display metrics
        System.out.println("correct answers: " + totalCorrectAnswers);
        System.out.println("Instances: " + Main.KNN_NUMBER_INSTANCES);
        System.out.println("Correct Percentage: " + correctPredictionsPercentage);
        Chart.start(originalArrayList, predictionArrayList, "KNN");
    }
    public double getCorrectPredictionsPercentage() {
        return correctPredictionsPercentage;
    }
}