# OptiPlan/Team 16

## Iteration 3

 * Start date: March 22, 2018
 * End date: March 29, 2018

## Process

#### Changes from previous iteration

List the most significant changes you made to your process (if any).

 1) The team imposed solid deadlines on when members should be done their tasks, as opposed to the last deliverable where members finished their parts at their own pace.
 * This change should make it so that the parts of the app that need to be finished first are completed. Also, it makes connecting pages more streamlined because the parts which are going to be connected will be have relatively the same deadline date. Additionally, it allows for clear prioritization of tasks. 
 
 
#### Roles & responsibilities

 * Gurkaran -- Front End Developer (Main Page and Menu) and Meeting Lead -- Responsible for implementing 'add' and 'delete' capabilities for the majors, courses, and assignments parts of the main menu. Also is responsible for fixing the 'modify' function for assignments. Other responsibilities include leading the team meetings and facilitating the other members towards clear goals.   
 * Alexander -- Lead Designer and Quality Control -- Responsible for connecting all the parts of the front end together, and adding detection capabilities to the login page which includes invalid login and repeated registration detection. Also responsible for standardizing inputs for dates, weight, and time in the task page as well as standardizing front end layout with the persisting menu. 
 * Michael -- Front End Developer (Main Page) and Algorithm Helper -- Responsible for modifying the main page to only display the most important task, which is determined by priority, and help the Algorithm Lead craft and test the main algorithm for the schedule optimizer of the app. 
 * James -- Algorithm Helper and Front End Developer (Registration Page 1) -- Responsible for assisting the Algorithm Lead in crafting and testing the main algorithm and fixing any issues that the 1st registration page has from the previous deliverable
 * Justn -- Database Manager -- Responsible for designing data models to provide a framwork for users' data to be used within the app by providing a specific definition and format. Also responsible for implementing a systematic database with FireBase to ensure user data persists
 * Sujith -- Algorithm Lead -- Respnosible for crafting and testing the schedule optimizer algorithm such that it correctly takes in all the user input from the app and outputs an optimized work schedule. Implement weighted interval scheduling and apply it to our specific problem.
 * Kyle -- Front End Developer (Task Page) and Meeting Minutes Taker -- Responsible for cleaning up task page and adding options to include priority, start date-time, and task duration when a new task is added. Also responsible for keeping minutes for all team meetings and writing up the planning and review meeting overviews. 

#### Events

Describe meetings (and other events) you are planning to have:
 * Mid-Week Update Meeting (online meeting): Discuss how close each person is to meeting the deadline on their part of the deliverable, determine whether parts of the app are ready to be connected, and updates on the general progress of the deliverable.
 * Review Meeting (In Person Tutorial): March 29, Establish which tasks were completed on time, which tasks still need to be done and why they were not completed by the deadline. Also iron out any remaining issues with the app and plan the fixes as a group. Finally discuss how to film the final demo video.
 
 
#### Artifacts

List/describe the artifacts you will produce in order to organize your team.       

 * Written To Do List: Reference in GitHub = project-team-16/deliverables/To Do List and Roles
   * This list contains the responsibilities of all the members of the group, and it is put in order of priority, we want to prioritize serializing all the data first because that is the backbone of our app's functionality. If the data cannot persist, then there will be no way for us to preserve an optimized schedule. After that, the interval scheduling algorithm which will be how our optimized schedule gets produced will be the next priority, and this is because this algorithm is key to a fundamental part of our MVP. All the tasks after that have to do with cleaning up code from the last deliverable, standardizing everything, and connecting all the parts of the app.
 * Meeting Minutes: Reference in GitHub = project-team-15/deliverables/Planning Meeting Minutes and project-team-15/deliverables/Review Meeting Minutes
   * This artifact is to help write up the planning and review meeting document as part of our deliverable

#### Git / GitHub workflow

* Everyone works on their own local copies of the repository that they shouldve cloned from their personal fork. When any changes want to be made, it will be pushed to the fork, then a pull request will be submitted.
* Members of the group who have time will test the new code in order to determine that it is okay to be added to the main product repo. Final say whether the code should make it onto the repo will come from our Lead Designer, Alexander
* Any conflicts in merging will be handled by Alexander as well.

## Product

#### Goals and tasks

 * Make sure all user data persists when using the app
 * Complete the schedule optimizer algorithm which will be able to take the data from user input and output a good work schedule
 * Clean up and standardize front end pages and make sure they are all connected properly
 
#### Artifacts

List/describe the artifacts you will produce in order to present your project idea.

 1) Schedule Optimizer Algorithm: Reference on GitHub = project-team-16/deliverables/Algorithm.txt
   * This is the most important product artifact because without it, our app would just be another scheduler app. This automated optimizer is what makes the app unique and solves the problem of lack of work organization
 2) Cleaned up the pages of the app, and added more functionality to all the pages which included add, delete, and update capabilities to assignments, courses, etc. Also added priorities to tasks. Reference on GitHub = project-team-16/MyMenu/app/src/main/res and project-team-16/MyMenu/app/src/main/java/chubbs/mymenu
   * This is just a cleaner version of the code, implements the algorithm, and is the final version of the app.
