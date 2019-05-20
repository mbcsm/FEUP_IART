import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main Class Of the project, everything runs from here.
 * All the classes of the algorithms used are called
 * and the constants used by them are also set at the top, you are
 * welcomed to change them.
 *
 */
class C45 {

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

    private Instances testData;
    private Instances trainData;
    ArrayList<Prediction> predictionArrayList;
    void start() throws Exception {

        List<String> result = null;
        try (Stream<Path> walk = Files.walk(Paths.get("src/dataset/discrete/"))) {

            result = walk.filter(Files::isRegularFile)
                    .map(Path::toString).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        int filesAdded = 0, i = -1;
       while( filesAdded < Main.C45_NN_NUMBER_TEST_FILES && i < result.size()){
            i++;
            if(result.get(i).equals("src/dataset/discrete/" + Main.TEST_FILE_NAME)){
                continue;
            }

            filesAdded++;
            BufferedReader trainFile = readDataFile(result.get(i));
            if (trainData == null) {
                trainData = new Instances(trainFile);
                continue;
            }
            trainData.addAll(new Instances(trainFile));
        }

        trainData.setClassIndex(Main.ARGUMENT_GOAL_INDEX);

        //Make tree
        Classifier cls = new J48();
        cls.buildClassifier(trainData);

        //Predictions with test and training set of data
        BufferedReader testFile = readDataFile("src/dataset/discrete/" + Main.TEST_FILE_NAME);
        testData = new Instances(testFile);
        testData.setClassIndex(Main.ARGUMENT_GOAL_INDEX);

        // train classifier
        Evaluation eval = new Evaluation(trainData);
        eval.evaluateModel(cls, testData);
        predictionArrayList = eval.predictions();
        displayData();
    }

    private void displayData() throws Exception {
        ArrayList<PointObject> originalArrayList = new ArrayList();
        ArrayList<PointObject> predictionArrayList = new ArrayList();

        int id = 0;
        int totalCorrectAnswers = 0;

        for(int i=0; i < testData.numInstances(); i++){

            double actual = testData.instance(i).classValue() + 1;
            double prediction = this.predictionArrayList.get(i).predicted() + 1;

            originalArrayList.add(new PointObject(id, actual));
            predictionArrayList.add(new PointObject(id, prediction));

            if(actual == prediction){
                totalCorrectAnswers++;
            }
            id++;
        }

        correctPredictionsPercentage = (double) totalCorrectAnswers / testData.numInstances();

        //display metrics
        System.out.println("correct answers: " + totalCorrectAnswers);
        System.out.println("Instances: " + testData.numInstances());
        System.out.println("Correct Percentage: " + correctPredictionsPercentage);
        Chart.start(originalArrayList, predictionArrayList, "C4.5");
    }

    public double getCorrectPredictionsPercentage() {
        return correctPredictionsPercentage;
    }
}