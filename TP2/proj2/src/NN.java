import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

class NN {

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

    Instances trainData;
    Instances testData;
    MultilayerPerceptron mlp;
    void start(){
        //network variables
        try{

            //prepare historical data
            //Get File
            BufferedReader reader = readDataFile("src/dataset/continous/d1p01M");
            Instances trainData = new Instances(reader);
            reader.close();

            trainData.setClassIndex(trainData.numAttributes() - 1); //final attribute in a line stands for output

            //------------------------------
            //network training
            //deactivate this block to use already trained network
            mlp = new MultilayerPerceptron();

            //Setting Parameters
            mlp.setLearningRate(0.1);
            mlp.setMomentum(0.2);
            mlp.setTrainingTime(10000);
            mlp.setHiddenLayers("5");
            mlp.buildClassifier(trainData);



            BufferedReader reader2 = readDataFile("src/dataset/continous/d1p02M");
            testData = new Instances(reader2);
            reader.close();
            testData.setClassIndex(Main.ARGUMENT_GOAL_INDEX);

            DisplayData();

        }
        catch(Exception ex){

            System.out.println(ex);

        }

    }

    private void DisplayData() throws Exception {
        ArrayList<PointObject> originalArrayList = new ArrayList();
        ArrayList<PointObject> predictionArrayList = new ArrayList();

        int id = 0;

        int totalCorrectAnswers = 0;
        for(int i=0; i < testData.numInstances(); i++){

            double actual = testData.instance(i).classValue();
            double prediction = mlp.distributionForInstance(testData.instance(i))[0];

            originalArrayList.add(new PointObject(id, actual));
            predictionArrayList.add(new PointObject(id, Math.round(prediction)));

            if(actual == Math.round(prediction)){
                totalCorrectAnswers++;
            }
            id++;
        }
        correctPredictionsPercentage = (double) totalCorrectAnswers / testData.numInstances();

        //display metrics
        System.out.println("correct answers: " + totalCorrectAnswers);
        System.out.println("Instances: " + testData.numInstances());
        System.out.println("Correct Percentage: " + (double) totalCorrectAnswers / testData.numInstances());
        Chart.start(originalArrayList, predictionArrayList, "NN");
    }
    public double getCorrectPredictionsPercentage() {
        return correctPredictionsPercentage;
    }
}
