public class Job {
    private int startTime;
    private int finishTime;
    private int weight;
    private String name;

    public Job(String name_, int start, int finish, int w) {
        name = name_;
        startTime = start;
        finishTime = finish;
        weight = w;
    }

    public String getName() {
        return name;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getWeight() {
        return weight;
    }

    public boolean __equals__(Object other) {
        return (other instanceof Job && getName() == ((Job) other).getName() &&
                getStartTime() == ((Job) other).getStartTime() &&
                getFinishTime() == ((Job) other).getFinishTime() &&
                weight == ((Job)other).getWeight());

    }
}
