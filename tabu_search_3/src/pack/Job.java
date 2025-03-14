package pack;

public class Job {
    int number;
    int weight;
    int processingTime;
    int dueDate;

    public Job(int number, int weight, int processingTime, int dueDate) {
        this.weight = weight;
        this.number = number;
        this.processingTime = processingTime;
        this.dueDate = dueDate;
    }

    public Job(Job job) {
        this.weight = job.weight;
        this.number = job.number;
        this.processingTime = job.processingTime;
        this.dueDate = job.dueDate;
    }
}
