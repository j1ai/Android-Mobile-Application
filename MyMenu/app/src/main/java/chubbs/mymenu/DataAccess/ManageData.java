package chubbs.mymenu.DataAccess;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import chubbs.mymenu.models.Job;
import chubbs.mymenu.models.User;


public class ManageData extends BaseActivity{

    private static FirebaseFirestore mFirestore;
    private static String uid;
    private static final String TAG = "ManageData";
    private static List<Course> all_course;
    private static List<Assessment> all_assessment;

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

    // Delete a data instance of a Course/ Assessment Object
    public void DeleteCourseField(String name) {
        // [START update_delete_field]
        DocumentReference docRef = mFirestore.collection(uid).document("COURSES");

        // Remove the 'capital' field from the document
        Map<String,Object> updates = new HashMap<>();
        updates.put(name, FieldValue.delete());

        docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            // [START_EXCLUDE]
            @Override
            public void onComplete(@NonNull Task<Void> task) {}
            // [START_EXCLUDE]
        });
        // [END update_delete_field]
    }

    public void DeleteAssessmentField(String course_name,String name) {
        // [START update_delete_field]
        DocumentReference docRef = mFirestore.collection(uid).document("COURSES");

        // Remove the 'capital' field from the document
        Map<String,Object> updates = new HashMap<>();
        updates.put(name, FieldValue.delete());

        docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            // [START_EXCLUDE]
            @Override
            public void onComplete(@NonNull Task<Void> task) {}
            // [START_EXCLUDE]
        });
        // [END update_delete_field]
    }

    public void updateCourse(Course upCourse) {
        // [START update_document]
        DocumentReference courseRef = mFirestore.collection(uid).document("COURSES");
        courseRef
                .update("name", upCourse.getCid())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
        // [END update_document]
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


    // Return an ArrayList of Custom Objects in a specified documents
    // Return all Course Objects in "COURSES"
    public static List<Course> getAllCourses() {
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
                    Course newCourse = new Course(cid);
                    all_course.add(newCourse);
                }
            }
        });
        return all_course;
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
        });
    }



    public static List<Assessment> getAll_assessment(){
        return all_assessment;
    }


}
