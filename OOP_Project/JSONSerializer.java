package OOP_Project;

import OOP_Project.MetricsJSONModels.MetricModel;

import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONSerializer {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private PrintWriter pw;

    public JSONSerializer() {
    }

    public void serializeOutputFile(String outputPath, Dataset dataset, ArrayList<LabelAssignment> lAssignments,
            ArrayList<User> users) throws Exception {
        try {
            Output myOutput = new Output(dataset.getDatasetId(), dataset.getDatasetName(), dataset.getMaxLabel(),
                    dataset.getLabels(), dataset.getInstances(), lAssignments, users);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String json = gson.toJson(myOutput);

            // writing JSON to file:"JSONExample.json" in cwd
            pw = new PrintWriter(outputPath);
            logger.info("Output file was created successfully");

            pw.write(json);

            pw.flush();
            pw.close();

        } catch (Exception e) {
            logger.warn("Error while serializing Output file!");
        }

    }

    public void serializeMetricFile(MetricModel metrics, String filePath) {
        try {
            // TimeUnit.SECONDS.sleep(1);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            // TimeUnit.SECONDS.sleep(1);
            String json = gson.toJson(metrics);
            // TimeUnit.SECONDS.sleep(1);

            // writing JSON to file:"JSONExample.json" in cwd
            pw = new PrintWriter(filePath);
            logger.info("Metrics file was created successfully");

            pw.write(json);

            pw.flush();
            pw.close();
        } catch (Exception e) {
            logger.warn("Error while serializing metrics file!");
        }

    }
}