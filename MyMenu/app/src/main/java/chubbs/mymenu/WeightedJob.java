package chubbs.mymenu;

import java.util.ArrayList;
import java.util.Arrays;

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

    // Driver method to test above
    public static void main(String[] args)
    {
        Job jobs[] = {new Job("Job1", 1, 2, 50), new Job("Job2", 3, 5, 20),
                new Job("Job3",6, 19, 100), new Job("Job4",2, 100, 200)};

        System.out.println("Optimal profit is " );
        ArrayList<Job> optimal = schedule(jobs);
        for (int i = 0; i < optimal.size(); i++) {
            System.out.println(optimal.get(i).getName());
        }

        Job jobs2[] = {new Job("Job1", 0, 6, 3), new Job("Job2", 1, 4, 5),
                new Job("Job3",3, 5, 5), new Job("Job4",3, 8, 8),
                new Job("Job5",4, 7, 3), new Job("Job6",5, 9, 7),
                new Job("Job7",6, 10, 3), new Job("Job8",8, 11, 4)};

        System.out.println("Optimal profit is " );
        ArrayList<Job> optimal2 = schedule(jobs2);
        for (int i = 0; i < optimal2.size(); i++) {
            System.out.println(optimal2.get(i).getName());
        }
    }

}
