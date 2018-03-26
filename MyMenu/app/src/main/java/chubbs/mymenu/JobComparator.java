package chubbs.mymenu;

import java.util.Comparator;

import chubbs.mymenu.models.Job;

class JobComparator implements Comparator<Job>
{
    public int compare(Job a, Job b)
    {
        return a.getFinishTime() < b.getFinishTime() ? -1 : a.getFinishTime() == b.getFinishTime() ? 0 : 1;
    }
}