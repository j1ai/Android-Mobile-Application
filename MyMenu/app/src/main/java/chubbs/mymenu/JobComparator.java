import java.util.Comparator;

class JobComparator implements Comparator<Job>
{
    public int compare(Job a, Job b)
    {
        return a.getFinishTime() < b.getFinishTime() ? -1 : a.getFinishTime() == b.getFinishTime() ? 0 : 1;
    }
}