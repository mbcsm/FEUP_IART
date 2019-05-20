import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            List<String> result = null;
            try (Stream<Path> walk = Files.walk(Paths.get("src/dataset/continuous/"))) {

                result = walk.filter(Files::isRegularFile)
                        .map(Path::toString).collect(Collectors.toList());

            } catch (IOException e) {
                e.printStackTrace();
            }

            int filesAdded = 0, i = -1;
            while( filesAdded < Main.C45_NN_NUMBER_TEST_FILES && i < result.size()){
                i++;
                if(result.get(i).equals("src/dataset/continuous/" + Main.TEST_FILE_NAME)){
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


            trainData.setClassIndex(trainData.numAttributes() - 1); //final attribute in a line stands for output

            //------------------------------
            //network training
            //deactivate this block to use already trained network
            mlp = new MultilayerPerceptron();

            //Setting Parameters
            mlp.setLearningRate(Main.NN_LEARNING_RATE);
            mlp.setMomentum(Main.NN_MOMENTUM);
            mlp.setTrainingTime(Main.NN_TRAINING_TIME);
            mlp.setHiddenLayers(Main.NN_HIDDEN_LAYERS);
            mlp.buildClassifier(trainData);



            BufferedReader reader2 = readDataFile("src/dataset/continuous/" + Main.TEST_FILE_NAME);
            testData = new Instances(reader2);
            reader2.close();
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
