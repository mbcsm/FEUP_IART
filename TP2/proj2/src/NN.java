import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.Utils;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;

public class NN {

    public static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + filename);
        }

        return inputReader;
    }
    public void start(){
        //network variables
        try{

            //prepare historical data
            //Get File
            BufferedReader reader = readDataFile("src/dataset/d1p01M");
            Instances data = new Instances(reader);
            reader.close();

            data.setClassIndex(data.numAttributes() - 1); //final attribute in a line stands for output

            //------------------------------
            //network training
            //deactivate this block to use already trained network
            MultilayerPerceptron mlp = new MultilayerPerceptron();

            //Setting Parameters
            mlp.setLearningRate(0.1);
            mlp.setMomentum(0.1);
            mlp.setTrainingTime(20000);
            mlp.setHiddenLayers("10");
            mlp.buildClassifier(data);



            BufferedReader reader2 = readDataFile("src/dataset/d1p02M");
            Instances data2 = new Instances(reader2);
            reader.close();
            data2.setClassIndex(data.numAttributes() - 1);

            int totalCorrectAnswers = 0;
            for(int i=0;i<data2.numInstances();i++){

                double actual = data2.instance(i).classValue();
                double prediction = mlp.distributionForInstance(data2.instance(i))[0];

                if(actual == Math.round(prediction)){
                    totalCorrectAnswers++;
                }
                System.out.println(actual+"\t"+prediction);

            }


            //success metrics
            Evaluation eval = new Evaluation(data2);
            eval.evaluateModel(mlp, data2);

            //display metrics
            System.out.println("correct answers: " + totalCorrectAnswers);
            System.out.println("Correlation: "+eval.correlationCoefficient());
            System.out.println("Instances: "+eval.numInstances());
        }
        catch(Exception ex){

            System.out.println(ex);

        }

    }
}
