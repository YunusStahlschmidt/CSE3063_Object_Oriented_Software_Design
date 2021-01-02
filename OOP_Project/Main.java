package OOP_Project;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import OOP_Project.MetricsJSONModels.DatasetModel;
import OOP_Project.MetricsJSONModels.InstanceModel;
import OOP_Project.MetricsJSONModels.MetricModel;
import OOP_Project.MetricsJSONModels.UserModel;

/* 
This class hold all "computations" will take place
*/

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        // Required Objects
        HashMap<Integer, Dataset> datasetHashMap;
        Dataset dataset;
        MetricModel metrics;
        ArrayList<User> users = new ArrayList<>();
        Parser parser = new Parser();
        int currentDatasetId = 0;
        String currentDirectory = System.getProperty("user.dir");
        currentDirectory += "\\OOP_Project";
        HashMap<Integer, ArrayList<LabelAssignment>> allLabelAssignments = new HashMap<>();
        UI ui = new UI();
        Login login = new Login();

        // parsing the files
        while (true) {
            try {
                parser.parseConfigFile(currentDirectory);
                datasetHashMap = parser.getDatasetHashMap();
                allLabelAssignments = parser.getPreviousLabelAssignments();
                parser.parseMetrics(currentDirectory);
                metrics = parser.getMetrics();// if null we have to initialize for all models first

                currentDatasetId = parser.getCurrentDatasetId();
                users = parser.getUsers();
                break;

            } catch (Exception e) {
                logger.error("FileNotFound error has occured!");
                System.exit(0);
            }
        }

        dataset = datasetHashMap.get(currentDatasetId);

        ArrayList<DatasetModel> datasetModelList = new ArrayList<DatasetModel>();
        ArrayList<InstanceModel> instanceModelList = new ArrayList<InstanceModel>();
        ArrayList<UserModel> userModelList = new ArrayList<UserModel>();

        // if we ran the program before we assign the previous data to the new modelds
        // and add it to the algorithm
        if (metrics != null) {
            datasetModelList = (ArrayList<DatasetModel>) metrics.getDatasetList();
            instanceModelList = (ArrayList<InstanceModel>) metrics.getInstanceList();
            userModelList = (ArrayList<UserModel>) metrics.getUsersList();

            ArrayList<LabelAssignment> updatedLabelAssignments = new ArrayList<>();
            LabelAssignment newestLabelAssignment;
            ArrayList<Label> newAddedLabels;
            Boolean checkpoint = false;

            // add previous label assignments for current dataset to our main list of
            // assignments
            for (LabelAssignment prevLabelAssignment : allLabelAssignments.get(currentDatasetId)) {
                for (Instance currInstance : dataset.getInstances()) {
                    if (!prevLabelAssignment.getInstanceId().equals(currInstance.getId())) {
                        continue;
                    }
                    for (User currUser : dataset.getAssignedUsers()) {
                        if (!prevLabelAssignment.getUserId().equals(currUser.getId())) {
                            continue;
                        } else {
                            newAddedLabels = new ArrayList<>();
                            for (Integer labelId : prevLabelAssignment.getAssignedLabelId()) {
                                for (Label currentLabel : dataset.getLabels()) {
                                    if (currentLabel.getId().equals(labelId)) {
                                        currInstance.getInstanceMetric().addUniqueLabel(currentLabel);
                                        dataset.getDatasetMetric().addInstanceForLabel(currentLabel, currInstance);
                                        newAddedLabels.add(currentLabel);

                                    }
                                }
                            }
                            newestLabelAssignment = new LabelAssignment(currInstance.getId(), newAddedLabels,
                                    currUser.getId(), prevLabelAssignment.getDate());
                            updatedLabelAssignments.add(newestLabelAssignment);

                            currInstance.getInstanceMetric().callAllNecessaryMethods(currUser, newestLabelAssignment);
                            currUser.getUserMetric().callAllNecessaryMethods(currInstance, dataset,
                                    newestLabelAssignment, null, null);
                            checkpoint = true;
                        }
                    }
                    if (checkpoint) {
                        dataset.getDatasetMetric().callAllNecessaryMethods(currInstance, dataset);
                        checkpoint = false;
                    }
                }
            }
            for (User user : users) {
                for (UserModel userModel : userModelList) {
                    if (userModel.getUserId().equals(user.getId())) {
                        user.getUserMetric().setUserModel(userModel);
                        break;
                    }
                }
            }

            for (Dataset myDataset : datasetHashMap.values()) {
                for (DatasetModel datasetModel : datasetModelList) {
                    if (datasetModel.getDatasetId().equals(myDataset.getDatasetId())) {
                        myDataset.getDatasetMetric().setDatasetModel(datasetModel);
                        break;
                    }
                }

                for (Instance instanceForId : myDataset.getInstances()) {
                    for (InstanceModel instanceModel : instanceModelList) {
                        if (instanceModel.getInstanceId().equals(instanceForId.getId())
                                && (instanceModel.getDatasetId().equals(myDataset.getDatasetId()))) {
                            instanceForId.getInstanceMetric().setInstanceModel(instanceModel);
                            break;
                        }
                    }
                }
            }

        } else {
            metrics = new MetricModel();
            for (Dataset myDataset : datasetHashMap.values()) {
                myDataset.setDatasetIdToModel();
                myDataset.getDatasetMetric().setInitialDatasetModel();
                datasetModelList.add(myDataset.getDatasetMetric().getDatasetModel());

                for (User user : myDataset.getAssignedUsers()) {
                    user.getUserMetric().incrementDatasetCompleteness(myDataset);
                    user.setUserIdToModel();
                    user.getUserMetric().setInitialUserModel();
                }
                for (Instance instanceForId : myDataset.getInstances()) {
                    instanceForId.setDatasetIdToInstanceModel(myDataset.getDatasetId());
                    instanceForId.setInstanceIdToModel();
                    instanceForId.getInstanceMetric().setInitialInstanceModel();
                    instanceModelList.add(instanceForId.getInstanceMetric().getInstanceModel());
                }
            }
            for (User user : users) {
                userModelList.add(user.getUserMetric().getUserModel());
            }
        }

        LabelingMechanism labelingMechanism = new LabelingMechanism(dataset, metrics, currentDirectory,
                datasetModelList, instanceModelList, userModelList, allLabelAssignments);

        User user = login.logIn(dataset.getAssignedUsers(), ui);

        if (user == null) {
            labelingMechanism.botLabeling();
        } else {
            if (user.getType().equals("Human")) 
                labelingMechanism.userLabeling(user);   
        }

        logger.info("Program has excuted Succesfully.");
    }
}