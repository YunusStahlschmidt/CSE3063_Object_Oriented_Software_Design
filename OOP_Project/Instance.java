package OOP_Project;

<<<<<<< HEAD
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

=======
>>>>>>> 2c1b3507124ccef3cf4fac2e273ddc3682cf4771
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
Class for the instance object to store them in our array list in the dataset
*/

public class Instance {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("instance")
    @Expose
    private String instance;
<<<<<<< HEAD
    private double entropy;
    private Label mostFrequentClassLabel;
    private double percentageOfMostFrequentLabel;
    private int totalNumberOfAssignedLabels;
    private int totalNumberOfUniqueAssignedLabels;
    private ArrayList<String[]> labelAssignments = new ArrayList<String[]>();
    private HashMap<Label, Double> classLabelsAndPercentagesNotUsed = new HashMap<Label,Double>();
    HashMap<String, Double> classLabelsAndPercentages = new HashMap<String,Double>();
    HashMap<String, Double> labelPercMap = new HashMap<String,Double>();
    Set<String> setOfUsers = new HashSet<>();
    int count = 0;
    int totalNumberOfUniqueUsers = 0;
    double total=0;
    NavigableMap<Double, String> treeMapofPercentages =  new TreeMap<Double, String>();
=======
    private InstanceMetric instanceMetric = new InstanceMetric();
>>>>>>> 2c1b3507124ccef3cf4fac2e273ddc3682cf4771

    public Instance() {}
    public Instance(int id, String instance) {
        this.id = id;
        this.instance = instance;
    }


    // Getters
    public int getId() {
        return id;
    }

    public String getInstance() {
        return instance;
    }

<<<<<<< HEAD
    public double getEntropy(){
        return entropy;
    }

    public int getTotalNumberOfAssignedLabels(){
    	populateLabelPercentageMap(labelAssignments);
    	for (Map.Entry<String,Double> entry : classLabelsAndPercentages.entrySet()) {

            if(entry.getValue()==1d) {
         	   totalNumberOfUniqueAssignedLabels = totalNumberOfUniqueAssignedLabels+1;
            }
        }
        return totalNumberOfAssignedLabels;
    }

    public int getNumberOfUniqueAssignedLabels(){
        return totalNumberOfUniqueAssignedLabels;
    }

    public int getNumberOfUniqueUsers(){
    	populateLabelPercentageMap(labelAssignments);
    	if(setOfUsers!=null) {
     	   totalNumberOfUniqueUsers = setOfUsers.size();
        }
        return totalNumberOfUniqueUsers;
    }

    public Label getMostFrequentLabel(){
        return mostFrequentClassLabel;
    }

    public double getMostFrequentLabelPercentage(){
    	populateLabelPercentageMap(labelAssignments);
    	Entry<Double, String> ent = treeMapofPercentages.lastEntry();
        String mostFrequentLabel = ent.getValue();
        Double mostFrequentLabelPerc = ent.getKey();

        return mostFrequentLabelPerc;
    }

    public HashMap getClassLabelsAndPercentages(){
        return classLabelsAndPercentages;
=======
    public InstanceMetric getInstanceMetric(){
        return instanceMetric;
>>>>>>> 2c1b3507124ccef3cf4fac2e273ddc3682cf4771
    }

    // Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

<<<<<<< HEAD
    public void setClassLabelsAndPercentages(HashMap<Label, Double> classLabelsAndPercentagesNotUsed){
        this.classLabelsAndPercentagesNotUsed=classLabelsAndPercentagesNotUsed;
    }

    public void setPercentageOfMostFrequentLabel(double percentageOfMostFrequentLabel){
        this.percentageOfMostFrequentLabel=percentageOfMostFrequentLabel;
    }

    public void setEntropy(){

    	populateLabelPercentageMap(labelAssignments);

        double dEntropy = 0d;
        double resultEnt = 0d;

        for (Map.Entry<String,Double> entry : labelPercMap.entrySet()) {

            treeMapofPercentages.put((entry.getValue()*100)/total,entry.getKey());

            dEntropy = entry.getValue()/total;
            dEntropy =  -(dEntropy* log2(dEntropy));
            resultEnt = resultEnt+dEntropy;
        }
        DecimalFormat df = new DecimalFormat("#.000");
        String strResultEntropy = df.format(resultEnt);
    }
    public void populateLabelPercentageMap(ArrayList<String[]> labelAssignments) {
    	double labelCount;

    	for(String[] list: labelAssignments) {
 	       for (String element: list) {

 	    	   labelCount = 0d;
 	    	   if(element!=null) {
 	    		   count++;

 	    		   if(classLabelsAndPercentages.get(element)!=null) {

 	    			   Double count1 = classLabelsAndPercentages.get(element);
 	    			   classLabelsAndPercentages.put(element, ++count1);
 	    		   }else {
 	    			   classLabelsAndPercentages.put(element, 1d);
 	    		   }

 	    		   String strToken1 = ((String)element.split(",")[0]).substring(1);
 		    	   setOfUsers.add(strToken1);

 		    	   String strToken2 = ((String)element.split(",")[1]);
 		    	   strToken2= strToken2.substring(0, strToken2.length()-1);

 		    	   if(labelPercMap.get(strToken2)!=null) {

 	    			   Double count2 = labelPercMap.get(strToken2);
 	    			   labelPercMap.put(strToken2, ++count2);
 	    		   }else {
 	    			   labelPercMap.put(strToken2, 1d);
 	    		   }
 	    	   }


 	       }
    	}
    	 for (Map.Entry<String,Double> entry : labelPercMap.entrySet()) {

             total = total+entry.getValue();
         }
    }
      public static double log2(double N)
	  {
	      // calculate log2 N indirectly
	      // using log() method
		  double result = (double)(Math.log(N) / Math.log(2));
	      return result;
	  }
=======
    public void setInstanceMetric(){
        //not sure do
    }
    
>>>>>>> 2c1b3507124ccef3cf4fac2e273ddc3682cf4771
}