package chubbs.mymenu.DataAccess;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chubbs.mymenu.BaseActivity;
import chubbs.mymenu.CourseActivity;
import chubbs.mymenu.SignInActivity;
import chubbs.mymenu.models.Assessment;
import chubbs.mymenu.models.Course;
import chubbs.mymenu.models.Task;
import chubbs.mymenu.models.Job;
import chubbs.mymenu.models.User;


public class ManageData extends BaseActivity{

    private static FirebaseFirestore mFirestore;
    private static String uid;
    private static final String TAG = "ManageData";
    private static List<Course> all_course;
    private static List<Assessment> all_assessment;
    private static List<Task> all_task;

    public ManageData(){
        all_course = new ArrayList<>();
        all_assessment = new ArrayList<>();
        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);
        // Firestore
        this.mFirestore = FirebaseFirestore.getInstance();
        this.uid = getUid();
    }

    public void addDocument(String doc){
        if (doc == "COURSES"){
            Map<String, Object> data = new HashMap<>();
            mFirestore.collection(uid).document(doc)
                    .set(data, SetOptions.merge());
        }
        else if (doc == "ASSESSMENTS"){
            Map<String, Object> data = new HashMap<>();
            mFirestore.collection(uid).document(doc)
                    .set(data, SetOptions.merge());
        }
        else if (doc == "TASKS"){
            Map<String, Object> data = new HashMap<>();
            mFirestore.collection(uid).document(doc)
                    .set(data, SetOptions.merge());
        }

    }

    public void addCourse(Course newCourse){
        Map<String, Map<String, Object>> data = new HashMap<>();
        data.put(newCourse.cid,newCourse.toMap());
        mFirestore.collection(uid).document("COURSES")
                .set(data, SetOptions.merge());
    }

    public void addAssessment(Assessment newAssessment){
        Map<String, Map<String,Map<String, Object>>> data = new HashMap<>();
        Map<String, Map<String, Object>> assementData = new HashMap<>();
        assementData.put(newAssessment.name,newAssessment.toMap());
        data.put(newAssessment.course,assementData);
        mFirestore.collection(uid).document("ASSESSMENTS")
                .set(data, SetOptions.merge());
    }

    public void addTask(Task newTask){
        Map<String, Map<String, Object>> data = new HashMap<>();
        data.put(newTask.name,newTask.toMap());
        mFirestore.collection(uid).document("TASKS")
                .set(data, SetOptions.merge());
    }





    // Delete all related Course documents
    // (i.e including all assessments related to this course)
    public void deleteCourseField(String name) {
        mFirestore.collection(uid).document("COURSES")
                .update(
                    name,FieldValue.delete());
        mFirestore.collection(uid).document("ASSESSMENTS")
                .update(
                        name,FieldValue.delete());
        //deleteCourseFromList(name);
        all_course.clear();
        all_assessment.clear();
        getAllCourses();
        getAllAssessments();
    }

    public void deleteAssessmentField(String course_name,String assess_name) {
        // [START update_delete_field]
        String path = course_name + "." + assess_name;
        mFirestore.collection(uid).document("ASSESSMENTS")
                .update(
                        path,FieldValue.delete()
                );
        // [END update_delete_field]
        deleteAssessmentFromList(course_name,assess_name);
    }

    public void deleteAssessmentFromList(String course_name,String assess_name){
        for (Assessment assessment: all_assessment){
            if (assessment.course.equals(course_name) && assessment.name.equals(assess_name)){
                all_assessment.remove(assessment);
            }
        }
    }

    public void deleteRelatedAssessments(String course_name){
        for (Assessment assessment: all_assessment){
            if (assessment.course.equals(course_name)){
                all_assessment.remove(assessment);
            }
        }
    }

    public void deleteCourseFromList(String course_name){
        for (Course course: all_course){
            if (course.cid.equals(course_name)){
                all_course.remove(course);
            }
        }
    }

    public void deleteTaskFromList(String task_name){
        for (Task task: all_task){
            if (task.name.equals(task_name)){
                all_task.remove(task);
            }
        }
    }

    // Delete all related Task documents
    public void deleteTaskField(String name) {
        mFirestore.collection(uid).document("TASKS")
                .update(
                        name,FieldValue.delete());
        all_task.clear();
        getAllTasks();
    }







    // Only Update Assessment Attributes: weight, deadline
    // Since the name of the Assessment and the course name are stored as a key,
    // which is not preferred to be modified
    public void updateAssessment(Assessment assessment){
        String path = assessment.course + "." + assessment.name;
        String weight_path = path + "." + "weight";
        String w = String.valueOf(assessment.weight);
        String deadline_path = path + "." + "deadline";
        mFirestore.collection(uid).document("ASSESSMENTS")
                .update(
                        weight_path,w,
                        deadline_path,assessment.deadline
                );
        // [END update_delete_field]
        modifyAssessmentFromList(assessment);
    }

    public void modifyAssessmentFromList(Assessment assess){
        for (Assessment assessment: all_assessment){
            if (assessment.course.equals(assess.course) && assessment.name.equals(assess.name)){
                assessment.weight = assess.weight;
                assessment.deadline = assess.deadline;
            }
        }
    }

    // Only Update Task Attributes: due_date, due_time,priority
    // Since the name of the Task is stored as a key,
    // which is not preferred to be modified
    public void updateTask(Task task){
        String path = task.name;
        String priority_path = path + "." + "priority";
        String startdate_path = path + "." + "start_date";
        String starttime_path = path + "." + "start_time";
        String duration_path = path + "." + "duration";
        mFirestore.collection(uid).document("ASSESSMENTS")
                .update(
                        startdate_path,task.start_date,
                        starttime_path,task.start_time,
                        priority_path,task.priority,
                        duration_path,task.duration
                );
        // [END update_delete_field]
        modifyTaskFromList(task);
    }

    public void modifyTaskFromList(Task newTask){
        for (Task task: all_task){
            if (task.name.equals(newTask.name)){
                task.start_time = newTask.start_time;
                task.start_date = newTask.start_date;
                task.priority = newTask.priority;
                task.duration = newTask.duration;
            }
        }
    }

    // User Friendly method to simply change the name of the course all related docs
    // If the user needs to change a course name, and which it has already has related assessments
    public void updateCourse(String old_course_name,String new_course_name) {
    }






    // Return the Specified Objects
    // For example, return CSC301 course in document ("COURSES")
    public Course getCourse(final String name) {
        for (Course course: all_course){
            if (course.cid.equals(name)){
                return course;
            }
        }
        return null;
    }

    public Assessment getAssessment(final String course_name,final String asses_name) {
        // [START get_document]
        for (Assessment assessment: all_assessment){
            if (assessment.course.equals(course_name) && assessment.name.equals(asses_name)){
                return assessment;
            }
        }
        return null;
    }

    public Task getTask(final String taskid) {
        for (Task task: all_task){
            if (task.name.equals(taskid)){
                return task;
            }
        }
        return null;
    }






    public static boolean checkContainsCourse(String courseid){
        for (Course course: all_course){
            if (course.cid.equals(courseid)){
                return false;
            }
        }
        return true;
    }

    public static boolean checkContainsAssessment(String assessname){
        for (Assessment assessment: all_assessment){
            if (assessment.name.equals(assessname)){
                return false;
            }
        }
        return true;
    }

    public static boolean checkContainsTask(String taskid){
        for (Task task: all_task){
            if (task.name.equals(taskid)){
                return false;
            }
        }
        return true;
    }





    // Return an ArrayList of Custom Objects in a specified documents
    // Return all Course Objects in "COURSES"
    public static void getAllCourses() {
        // [START get_multiple_all]
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection(uid).document("COURSES");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> forms = documentSnapshot.getData();
                for (Map.Entry<String, Object> form: forms.entrySet()) {
                    Map<String, Object> values = (Map<String, Object>)form.getValue();
                    String cid = values.get("cid").toString();
                    if (checkContainsCourse(cid)){
                        Course newCourse = new Course(cid);
                        all_course.add(newCourse);
                    }
                }
            }
        });
    }

    public static List<Course> getAll_course(){
        return all_course;
    }







    // Return an ArrayList of Custom Objects in a specified documents
    // Return all Course Objects in "ASSESSMENT"
    public static void getAllAssessments() {
        // [START get_multiple_all]
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection(uid).document("ASSESSMENTS");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> forms = documentSnapshot.getData();
                for (Map.Entry<String, Object> form : forms.entrySet()) {
                    String course_name = form.getKey();
                    Map<String, Object> values = (Map<String, Object>) form.getValue();
                    for (Map.Entry<String, Object> entry : values.entrySet()) {
                        String assessment_name = entry.getKey();
                        if (checkContainsAssessment(assessment_name)){
                            Map<String, Object> assessment_list = (Map<String, Object>) entry.getValue();
                            String asses_name = assessment_list.get("name").toString();
                            String weight = assessment_list.get("weight").toString();
                            int w = Integer.parseInt(weight);
                            String deadline = assessment_list.get("deadline").toString();
                            Assessment assessment = new Assessment(course_name, asses_name, w, deadline);
                            all_assessment.add(assessment);
                        }
                    }
                }
            }
        });
    }



    public static List<Assessment> getAll_assessment(){
        return all_assessment;
    }






    // Return an ArrayList of Custom Objects in a specified documents
    // Return all Task Objects in "TASKS"
    public static void getAllTasks() {
        // [START get_multiple_all]
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection(uid).document("TASKS");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> forms = documentSnapshot.getData();
                for (Map.Entry<String, Object> form: forms.entrySet()) {
                    Map<String, Object> values = (Map<String, Object>)form.getValue();
                    String name = values.get("name").toString();
                    if (checkContainsTask(name)){
                        String start_date = values.get("start_date").toString();
                        String start_time = values.get("start_time").toString();
                        String priority = values.get("priority").toString();
                        String duration = values.get("duration").toString();
                        int d = Integer.parseInt(duration);
                        Task newTask = new Task(name,priority,start_date,start_time,d);
                        all_task.add(newTask);
                    }
                }
            }
        });
    }

    public static List<Task> getAll_task(){
        return all_task;
    }


}
