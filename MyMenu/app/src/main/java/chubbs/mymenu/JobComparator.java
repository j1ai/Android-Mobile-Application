package chubbs.mymenu;

import java.util.Comparator;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

import chubbs.mymenu.models.Assessment;
import chubbs.mymenu.models.Job;

class JobComparator implements Comparator<Job>
{
    public int compare(Job a, Job b)
    {
        return a.getFinishTime() < b.getFinishTime() ? -1 : a.getFinishTime() == b.getFinishTime() ? 0 : 1;
    }

    public Job[] convert(Assessment assessments[]){
        // Convert assessments with dates in string format to start and end times in integer format.

        int day, month, year, start, finish;

        Job result[] = new Job[assessments.length];

        for (int i = 0; i < assessments.length; i ++){

            day = Integer.parseInt(assessments[i].deadline.substring(0,2));
            month = Integer.parseInt(assessments[i].deadline.substring(3,5));
            year = Integer.parseInt(assessments[i].deadline.substring(6,10));

            Calendar myCalendar = new GregorianCalendar(year, month, day);
            Date myDate = myCalendar.getTime();
            myCalendar.setTime(myDate);

            finish = myCalendar.get(Calendar.DAY_OF_YEAR);

            // As default, use half the time till the deadline as the start time
            Date today = new Date();
            myCalendar.setTime(today);
            int todayNum = myCalendar.get(Calendar.DAY_OF_YEAR);
            start = (finish - todayNum)/2;

            Job j = new Job(assessments[i].name, start, finish, assessments[i].weight);
            result[i] = j;
        }


        return result;
    }

}