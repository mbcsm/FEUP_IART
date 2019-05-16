import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class KNN {
    private static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + filename);
        }

        return inputReader;
    }

    public void start() throws Exception {
        ArrayList<Instance> instanceArrayList = new ArrayList();
        BufferedReader datafile = readDataFile("src/dataset/d1p01M");

        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);

        for(int i = 0; i < 10; i ++){
            int random_id = (int)Math.random() * (int)datafile.lines().count() + 1;
            instanceArrayList.add(data.instance(random_id));
            data.delete(random_id);
        }


        Classifier ibk = new IBk();
        ibk.buildClassifier(data);



        ArrayList<PointObject> originalArrayList = new ArrayList();
        ArrayList<PointObject> knnArrayList = new ArrayList();

        int id = 0;

        for(Instance mInstance : instanceArrayList){
            double class1 = ibk.classifyInstance(mInstance);
            System.out.println("ID: " + mInstance.toString(0) + " | " + class1);
            originalArrayList.add(new PointObject(id, Double.valueOf(mInstance.toString(data.numAttributes() - 5))));
            knnArrayList.add(new PointObject(id, class1));
            id++;
        }

        Chart.start(originalArrayList, knnArrayList);

    }
}