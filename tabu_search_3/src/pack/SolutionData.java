package pack;

import java.util.ArrayList;
import java.util.List;

public class SolutionData {
    List<Job>[] arrayListArray;
    int costs[][];
    int temp_tot[][];
    int total_cost[][];
    SolutionData(int numIterations, int numTables) {
        arrayListArray = new ArrayList[numIterations+1];
        costs=new int[numIterations+1][numTables];
        temp_tot=new int[numIterations+1][numTables];
        total_cost=new int[numIterations+1][numTables];

        for (int i = 0; i < numIterations+1; i++) {
            arrayListArray[i] = new ArrayList<Job>();
        }
    }
    public void save_data(int i, List<Job> jobs) {
        arrayListArray[i]=new ArrayList<>(jobs);
        for (int j=0; j<jobs.size(); j++) {
            temp_tot[i][j] = (j!=0) ?  temp_tot[i][j-1] + jobs.get(j).processingTime : jobs.get(j).processingTime;
            costs[i][j] =  jobs.get(j).weight * Math.max(temp_tot[i][j] - jobs.get(j).dueDate, 0);
            total_cost[i][j] =  (j!=0) ? total_cost[i][j-1]+costs[i][j]: costs[i][j];
        }

    }
}