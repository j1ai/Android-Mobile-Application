# Team 16


## Iteration 02 - Review & Retrospect

 * When: March 9, 2018
 * Where: Tutorial in BA2185

#### Decisions that turned out well

List process-related (i.e. team organization) decisions that, in retrospect, turned out to be successful.


 * Drawing a storyboard displaying all the pages and how they interact with each other. This turned out to be a good decision because it helped format how the different pages would connect with each other. Also, it made it easier to delegate tasks to each member when the app was organized by pages.

 * Paper Prototype Artifact: https://github.com/csc301-winter-2018/project-team-16/blob/master/deliverables/Paper%20prototype%20storyboard.jpg
 
 * Delegating each page to one member of the group and then assigning the connection of the individual pages to one person. This was a successful decision as it allowed for equal distribution of work among members. We assigned the task of connecting all the pages to the member with the least overall workload
 
 * Deciding to collaborate on the demo script while all of us were in one room. This decision was successful because it made writing the script more straightforward and minimized the risk of creative blocks with seven people contributing to the script.
 
 * Algorithm To-do List artifact: https://github.com/csc301-winter-2018/project-team-16/blob/master/deliverables/algorithm%20artifact%20.jpg|alt=octocat
 
#### Decisions that did not turn out as well as we hoped

List process-related (i.e. team organization) decisions that, in retrospect, were not as successful as you thought they would be.

* Deciding to not include a database to store user information for each unique user. 
* Trying to complete too many features for one deliverable. We tried to do too much with designing both the front end and back end, and were not able to get to coding the back end of our app.

#### Planned changes

List any process-related changes you are planning to make (if there are any)

* We plan to use JSON in order to keep track of all information that needs to persist. We originally thought that we would not need to separate users' info into separate elements, thus removing the need for a database, but we were wrong. So, we plan to use JSON in order to create a object-oriented database in order for each unique user to have their own information and schedule saved under their name.


## Product - Review

#### Goals and/or tasks that were met/completed:

 * Finished login page for app. Refer to project-team-16/MyMenu/app/src/main/java/chubbs/mymenu/LoginActivity.java for implementation
 * Finished course activity page, which allows you to add courses to your schedule to later be optimized. Refer to project-team-16/MyMenu/app/src/main/java/chubbs/mymenu/CourseActivity.java for implementation
 * Finished drop down menu for app. Refer to project-team-16/MyMenu/app/src/main/java/chubbs/mymenu/MenuActivity.java for implementation
 * Finished syllabus update function, where you are able to schedule assignments and tests in accordance to course syllabi.Refer to project-team-16/MyMenu/app/src/main/java/chubbs/mymenu/UpdateSyllabusActivity.java
* Finished home page of app. Refer to project-team-16/MyMenu/app/src/main/java/chubbs/mymenu/MainActivity.java for implementation
* Finished task scheduler, where the app keeps track of any extracurricular activities that the user would like to add to his/her schedule. refer to project-team-16/MyMenu/app/src/main/java/chubbs/mymenu/TaskActivitiy.java

 
#### Goals and/or tasks that were planned but not met/completed:

* We were unable to start coding the back end of our app due to time constraints, and a majority of members having mid terms and other big assignments to take care of. This part of the app will be addressed for the next deliverable

## Meeting Highlights

Going into the next iteration, our main insights are:

* Database Design: We plan to utilize the flexibile structure of JSON to implement our app's Database Design. This allows a designer to embed lists within lists within a document, and also creates freedoms for the designer to create different collections to seperate different types of data. 

* User Friendly Registrations: Our current User's Registration procedure can be improved into a more user friendly way. 
For now, the user has to input their basic information (i.e their current tasks, courses, extra-curricular activities schedule) by hand, which might cause inconvenience for them. Going into the next iteration, we can implement a SEARCH & FILTER function in our registration procedure. We will utilize the data we collected from the UOFT API, and provide a more user friendly way for new users to input their courses/tasks schedule. 
