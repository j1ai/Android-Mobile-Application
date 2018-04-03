package chubbs.mymenu.models;

public class Job {
    private int startTime;
    private int finishTime;
    private int weight;
    private String name;
    private String courseCode;

    public Job(){}

    public Job(String name_, int start, int finish, int w, String c) {
        name = name_;
        startTime = start;
        finishTime = finish;
        weight = w;
        courseCode = c;
    }

    public void setStartTime(int startTime) {
         this.startTime = startTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public String getName() {
        return name;
    }

    public String getCourseCode() {
        return courseCode;
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
