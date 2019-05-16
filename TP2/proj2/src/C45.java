import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Random;

/**
 * Main Class Of the project, everything runs from here.
 * All the classes of the algorithms used are called
 * and the constants used by them are also set at the top, you are
 * welcomed to change them.
 *
 */
public class C45 {

    public static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + filename);
        }

        return inputReader;
    }

    public void start() throws Exception {

        //Get File
        BufferedReader reader = readDataFile("src/dataset/d1p01M");

        //Get the data
        Instances data = new Instances(reader);
        reader.close();

        //Setting class attribute
        data.setClassIndex(data.numAttributes() - 1);

        //Make tree
        J48 tree = new J48();
        String[] options = new String[1];
        options[0] = "-U";
        tree.setOptions(options);
        tree.buildClassifier(data);

        //Print tree
        System.out.println(tree);

        //Predictions with test and training set of data

        BufferedReader datafile = readDataFile("src/dataset/d1p01M");
        BufferedReader datafile2 = readDataFile("src/dataset/d1p03M");
        Instances train = new Instances(datafile);
        Instances train2 = new Instances(datafile2);
        train.setClassIndex(data.numAttributes() - 1);
        train2.setClassIndex(data.numAttributes() - 1);


        BufferedReader testfile = readDataFile("src/dataset/d1p02M");
        Instances test = new Instances(testfile);
        test.setClassIndex(data.numAttributes() - 1);


        // train classifier
        Classifier cls = new J48();
        cls.buildClassifier(train);
        cls.buildClassifier(train2);
        // evaluate classifier and print some statistics
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(cls, test);

        System.out.println(eval.toSummaryString("\nResults\n======\n", false) + "________-" +  eval.predictions().get(0).predicted());
    }
}
