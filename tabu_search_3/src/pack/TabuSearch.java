package pack;

import java.util.*;


public class TabuSearch {
    ArrayList<Job> original_jobs;
    ArrayList<Job> current_jobs;
    List<List<Job>> neighborhood;
    List<Integer> tabuList_to_remove;
    List<int[]> tabuList_to_add;
    int tabuTenure_to_remove=2;
    int tabuTenure_to_add=3;

    public TabuSearch() {
        tabuList_to_remove=new ArrayList<>();
        tabuList_to_add=new ArrayList<>();
    }

    void create_istance() {
        original_jobs = new ArrayList<Job>();
        original_jobs.add(new Job(1, 1, 6, 9));
        original_jobs.add(new Job(2, 1, 4, 12));
        original_jobs.add(new Job(3, 1, 8, 15));
        original_jobs.add(new Job(4, 1, 2, 8));
        original_jobs.add(new Job(5, 1, 10, 20));
        original_jobs.add(new Job(6, 1, 3, 22));
        neighborhood = new ArrayList<List<Job>>();
        for (int i = 0; i < original_jobs.size() - 1; i++) {
            neighborhood.add(new ArrayList<Job>());
        }
    }

    public List<Job> findInitialSolution() {
        List<Job> new_jobs = new ArrayList<Job>(original_jobs);
        sortJobsByDate(new_jobs);
        return new_jobs;
    }

    /**
     * This method calculates the value of the solution
     * @param jobs
     * @return
     */
    public int calculateValue(List<Job> jobs) {
        int time_sum = 0;
        int cost_sum = 0;
        for (Job job : jobs) {
            time_sum+= job.processingTime;
            cost_sum+= job.weight * Math.max(time_sum - job.dueDate,0);
        }
        return cost_sum;
    }

    /**
     * This method creates the neighborhood of the solution and saves it in the neighborhood list
     * and creates a index list of promising moves that have inserted in the neighborhood.
     * The neighborhood is created by inserting the job in each position i of the vector and shifting the queue one place backward.
     * @param n
     * @return
     */
    public List<int[]> defineNeighborhood(int n) {
        int index= findIndex(n);
        List<int[]> promising_moves = new ArrayList<>();

        Job temp = new Job(current_jobs.get(index));
        for (int i = 0; i < neighborhood.size(); i++) {
            if (i != index) {
               List<Job> new_jobs = new ArrayList<>(current_jobs);

                if (i < index) {
                    promising_moves.add(new int[]{i, n}); // the move is saved as a couple of position of the job to move and the number of the
                                                        // job to move
                                                        // not all the moves in the neighborhood are promising


                    for (int j = index; j > i; j--)
                        new_jobs.set(j, new_jobs.get(j - 1));
                    new_jobs.set(i, temp);
                    neighborhood.set(i, new_jobs); // save the new solution in the neighborhood

                }
                else if (i > index) {

                    for (int j = index; j < i; j++)
                        new_jobs.set(j, new_jobs.get(j + 1));
                    new_jobs.set(i, temp);
                    neighborhood.set(i, new_jobs);
                    }

            }
        }
        return promising_moves;
    }

    /**
     * This method makes a move in the neighborhood and returns the best solution found
     *   the new solution is decided as the best in the neighborhood
     *   if there is no improvement of the solution in the neighborhood a random move (in the neighborhood) is made
     * @param current_sol
     * @return
     */
    public List<Job> makeMove(List<Job> current_sol) {
        this.current_jobs = new ArrayList<Job>(current_sol);
        int jobToMove= decideJobToMove(current_sol);    // find the id of the job to move
        List<int[]> promising_moves = defineNeighborhood(jobToMove);
        int best_value = 0;
        List<Job> best_solution = new ArrayList<Job>();
        int[] chosen_move=null;
        for (int i=0; i< promising_moves.size(); i++) {     // search best solution in the neighborhood
            int new_value = calculateValue(neighborhood.get(i));

            if (new_value <= best_value && !tabuList_to_add.contains(promising_moves.get(i))) {
                best_value = new_value;
                best_solution = neighborhood.get(i);
                chosen_move = promising_moves.get(i);
            }
        }
        int counter = 0;
        while  (chosen_move == null ) { // if no move has been chosen, make a random move in the neighborhood
            if (counter>6) {
                Random rand = new Random();
                int random_index = rand.nextInt(neighborhood.size());

                int[] new_move = new int[]{random_index, jobToMove};
                if (!tabuList_to_add.contains(new_move)) {
                    best_solution = neighborhood.get(random_index);
                    chosen_move = new int[]{random_index, jobToMove};
                  }}
            else {


            }   counter++;
            }   //makes the random move if no move has been chosen

        updateTabuListToAdd(chosen_move);
        return best_solution;
    }

    /**
     * This method decides which job to move, and then makes the move
     * currently the jobs to move is decided as the one that have the max cost
     *
     * @param current_sol
     * @return
     */
    private int decideJobToMove(List<Job>current_sol){
    List<Job> jobs=new ArrayList<Job>(current_sol);
    int n=0;
    HashMap<Integer,Integer> delta_hash= calculateAllCosts(jobs);
    do {
        n=findMax(delta_hash);
        delta_hash.remove(n); //remove the job from the hash map to reduce the search space either is it valid or not
    }while(tabuList_to_remove.contains(n));
    updateTabuListToRemove(n);
    return n;
}
/**
 * This method updates the tabu list to remove with the job that has been chosen to move
 * @param n
 */
private void updateTabuListToRemove(int n) {

    if(tabuList_to_remove.size()>tabuTenure_to_remove)
        tabuList_to_remove.remove(0); // if the tabu list is full remove the oldest element
    tabuList_to_remove.add(n);
}
 private void updateTabuListToAdd(int move []){

     if(tabuList_to_add.size()>tabuTenure_to_add)
         tabuList_to_add.remove(0);
     tabuList_to_add.add(move);
 };

  /**
     * This method calculate the cost of all realtive jobs and store them
     * in a hash map with the job number as key and the cost as value
     * @param  jobs
     * @return
     */
    private  HashMap<Integer,Integer> calculateAllCosts(List<Job> jobs) {
        HashMap<Integer,Integer> cost_hash=new HashMap<>();
         int partial_sum=0;

        for(Job job:jobs){
            partial_sum+=job.processingTime;
            cost_hash.put(job.number,job.weight* Math.max(partial_sum-job.dueDate,0));
        }

        return cost_hash;
    }

    int findIndex(int n){
        for(int i=0;i<original_jobs.size();i++){
            if(current_jobs.get(i).number==n)
                return i;
        }
        return -1;
    }

    public void sortJobsByDate(List<Job> jobs) {
        Collections.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job job1, Job job2) {
                return Integer.compare(job1.dueDate, job2.dueDate);
            }
        });
    }

    private int findMax(HashMap<Integer,Integer> cost_hash){
        int max=-1;
        int key_max=-1;
        for(int key:cost_hash.keySet()){
            if(cost_hash.get(key)>max){
                max=cost_hash.get(key);
                key_max=key;
            }

        }
        return key_max;
    }
}
