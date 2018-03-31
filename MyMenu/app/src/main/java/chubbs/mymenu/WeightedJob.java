package chubbs.mymenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import chubbs.mymenu.models.Assessment;
import chubbs.mymenu.models.Job;

public class WeightedJob {
    /* A Binary Search based function to find the latest job
      (before current job) that doesn't conflict with current
      job.  "index" is index of the current job.  This function
      returns -1 if all jobs before index conflict with it.
      The array jobs[] is sorted in increasing order of finish
      time. */

    static public int binarySearch(Job jobs[], int index)
    {
        // Initialize 'lo' and 'hi' for Binary Search
        int lo = 0, hi = index - 1;

        // Perform binary Search iteratively
        while (lo <= hi)
        {
            int mid = (lo + hi) / 2;
            if (jobs[mid].getFinishTime() <= jobs[index].getStartTime())
            {
                if (jobs[mid + 1].getFinishTime() <= jobs[index].getStartTime())
                    lo = mid + 1;
                else
                    return mid;
            }
            else
                hi = mid - 1;
        }

        return -1;
    }


    // The main function that returns the maximum possible
    // profit from given array of jobs
    static ArrayList<Job> schedule(Job jobs[])
    {
        // Sort jobs according to finish time
        Arrays.sort(jobs, new JobComparator());

        // Create an array to store solutions of subproblems.
        // table[i] stores the profit for jobs till jobs[i]
        // (including jobs[i])
        int n = jobs.length;
        int table[] = new int[n];
        table[0] = jobs[0].getWeight();
        ArrayList<Job> optimalJobs = new ArrayList<>();
        optimalJobs.add(jobs[0]);

        // Fill entries in M[] using recursive property
        for (int i=1; i<n; i++)
        {
            // Find profit including the current job
            int inclProf = jobs[i].getWeight();
            int l = binarySearch(jobs, i);
            if (l != -1)
                inclProf += table[l];

            // Store maximum of including and excluding
            table[i] = Math.max(inclProf, table[i-1]);
            if (l < i - 1 && table[i-1] < inclProf) {

                int m = 0;
                Job[] subarr = new Job[l + 1];
                for (int k = 0; k < l + 1; k++) {
                    subarr[k] = jobs[k];
                }

                while (m < optimalJobs.size()) {
                    if (!Arrays.asList(subarr).contains(optimalJobs.get(m))) {
                        break;
                    }
                    m++;
                }



                optimalJobs.subList(m, optimalJobs.size()).clear();
                optimalJobs.add(jobs[i]);
            }
            else if (l == i - 1) {
                optimalJobs.add(jobs[i]);
            }

        }

        return optimalJobs;
    }

    public static Job[] convert(Assessment assessments[]){
        // Convert assessments with dates in string format to start and end times in integer format.

        int day, month, year, start, finish;
        int todayNum = 0;

        Job result[] = new Job[assessments.length];
        for (int i = 0; i < assessments.length; i++){
            String [] tokenizedDate = assessments[i].deadline.split("/");
            day = Integer.parseInt(tokenizedDate[0]);
            month = Integer.parseInt(tokenizedDate[1]) - 1;
            year = Integer.parseInt(tokenizedDate[2]);

            Calendar myCalendar = new GregorianCalendar(year, month, day);
            Date myDate = myCalendar.getTime();
            myCalendar.setTime(myDate);
            finish = myCalendar.get(Calendar.DAY_OF_YEAR);

            // As default, use half the time till the deadline as the start time
            Date today = new Date();
            myCalendar.setTime(today);
            todayNum = myCalendar.get(Calendar.DAY_OF_YEAR);

            start = (int) (todayNum + Math.ceil((double)(finish - todayNum)/2));
            //System.out.println(start);
            //System.out.println(finish);


            Job j = new Job(assessments[i].name, start, finish, assessments[i].weight);
            result[i] = j;
        }

        int min_start = Integer.MAX_VALUE;
        int time_left = Integer.MIN_VALUE;

        // check for extra time space
        for (int i = 0; i < result.length; i++){
            Arrays.sort(result, new JobComparator());
            if (result[i].getStartTime() < min_start){
                min_start = result[i].getStartTime();
                time_left = min_start - todayNum;
            }
        }

        // re-assign jobs into open space to resolve conflicts
        for (int i = 0; i < result.length; i++){
            Arrays.sort(result, Collections.reverseOrder(new JobComparator()));
            int job_length = result[i].getFinishTime() - result[i].getStartTime();
            if (job_length <= time_left){
                result[i].setFinishTime(min_start);
                result[i].setStartTime(min_start - job_length);
                min_start = result[i].getStartTime();
                time_left = todayNum - min_start;
            }
        }

        return result;
    }

    // Driver method to test above
    public static void main(String[] args)
    {
        Job jobs[] = {new Job("Job1", 1, 2, 50), new Job("Job2", 3, 5, 20),
                new Job("Job3",6, 19, 100), new Job("Job4",2, 100, 200)};

        System.out.println("Optimal profit is: " );
        ArrayList<Job> optimal = schedule(jobs);
        for (int i = 0; i < optimal.size(); i++) {
            System.out.println(optimal.get(i).getName());
        }

        Job jobs2[] = {new Job("Job1", 0, 6, 3), new Job("Job2", 1, 4, 5),
                new Job("Job3",3, 5, 5), new Job("Job4",3, 8, 8),
                new Job("Job5",4, 7, 3), new Job("Job6",5, 9, 7),
                new Job("Job7",6, 10, 3), new Job("Job8",8, 11, 4)};

        System.out.println("Optimal profit is: " );
        ArrayList<Job> optimal2 = schedule(jobs2);
        for (int i = 0; i < optimal2.size(); i++) {
            System.out.println(optimal2.get(i).getName());
        }




        Job jobs3[] = {new Job("Job1", 1, 2, 50), new Job("Job2", 1, 2, 20),
                new Job("Job3",3, 6, 30), new Job("Job4",0, 1, 20)};
        System.out.println("Optimal profit is: " );
        ArrayList<Job> optimal3 = schedule(jobs3);
        for (int i = 0; i < optimal3.size(); i++) {
            System.out.println(optimal3.get(i).getName());
        }


        Assessment assessments[] = {new Assessment("CSC301", "Job1", 50, "02/04/2018" ),
            new Assessment("CSC301", "Job2", 20, "02/04/2018"),
            new Assessment("CSC301", "Job3", 100, "06/04/2018"),
            new Assessment("CSC301", "Job4", 20, "30/03/2018")
    };
        Job jobs4[] = convert(assessments);

        System.out.println("Optimal profit is: " );
        ArrayList<Job> optimal4 = schedule(jobs4);
        for (int i = 0; i < optimal4.size(); i++) {
            System.out.println(optimal4.get(i).getName());
        }

        Assessment assessments2[] = {new Assessment("CSC301", "Deliverable 3", 10, "01/04/2018" ),
                new Assessment("CSC373", "CSC373 A3", 5, "03/04/2018"),
                new Assessment("CSC369", "CSC369 A4", 10, "06/04/2018"),
                new Assessment("CSC373", "CSC373 EXAM!", 20, "11/04/2018")
        };
        Job jobs5[] = convert(assessments2);

        System.out.println("Optimal profit is: " );
        ArrayList<Job> optimal5 = schedule(jobs5);
        for (int i = 0; i < optimal5.size(); i++) {
            System.out.println(optimal5.get(i).getName());
        }
    }

}
