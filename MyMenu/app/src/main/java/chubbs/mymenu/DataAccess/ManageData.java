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

    private FirebaseFirestore mFirestore;
    private BaseActivity activity;
    private String uid;
    private static final String TAG = "ManageData";
    private List<Course> all_course;
    private List<Assessment> all_assessment;

    public ManageData(BaseActivity activity){
        this.activity = activity;
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
        Map<String, Map<String, Object>> data = new HashMap<>();
        data.put(newAssessment.name,newAssessment.toMap());
        mFirestore.collection(uid).document("ASSESSMENTS")
                .set(data, SetOptions.merge());
    }

    // Delete a data instance of a Course/ Assessment Object
    public void DeleteField(String doc, String name) {
        // [START update_delete_field]
        DocumentReference docRef = mFirestore.collection(uid).document(doc);

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
        // [START get_document]
        final List<Course> courseContainer = new ArrayList<>();
        DocumentReference docRef = mFirestore.collection(uid).document("COURSES");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> forms = documentSnapshot.getData();
                for (Map.Entry<String, Object> form: forms.entrySet()) {
                    String key = form.getKey();
                    if (key.equals(name)){
                        Map<String, Object> values = (Map<String, Object>)form.getValue();
                        String cid = values.get("cid").toString();
                        Course newCourse = new Course(cid);
                        courseContainer.add(newCourse);
                    }
                }
            }
        });
        // [END get_document]
        return courseContainer.get(0);
    }

    public Assessment getAssessment(final String name) {
        // [START get_document]
        final List<Assessment> assessmentContainer = new ArrayList<>();
        DocumentReference docRef = mFirestore.collection(uid).document("ASSESSMENTS");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> forms = documentSnapshot.getData();
                for (Map.Entry<String, Object> form: forms.entrySet()) {
                    String key = form.getKey();
                    if (key.equals(name)){
                        Map<String, Object> values = (Map<String, Object>)form.getValue();
                        String name = values.get("name").toString();
                        String weight = values.get("weight").toString();
                        int w = Integer.parseInt(weight);
                        String deadline = values.get("deadline").toString();
                        Assessment assessment = new Assessment(name,w,deadline);
                        assessmentContainer.add(assessment);
                    }
                }
            }
        });
        // [END get_document]
        return assessmentContainer.get(0);
    }


    // Return an ArrayList of Custom Objects in a specified documents
    // Return all Course Objects in "COURSES"
    public void getAllCourses() {
        // [START get_multiple_all]
        all_course = new ArrayList<>();
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
    }

    public List<Course> getAll_course(){
        return this.all_course;
    }

    // Return an ArrayList of Custom Objects in a specified documents
    // Return all Course Objects in "ASSESSMENT"
    public void getAllAssessments() {
        // [START get_multiple_all]
        all_assessment = new ArrayList<>();
        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection(uid).document("ASSESSMENTS");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> forms = documentSnapshot.getData();
                for (Map.Entry<String, Object> form: forms.entrySet()) {
                    Map<String, Object> values = (Map<String, Object>)form.getValue();
                    String name = values.get("name").toString();
                    String weight = values.get("weight").toString();
                    int w = Integer.parseInt(weight);
                    String deadline = values.get("deadline").toString();
                    Assessment assessment = new Assessment(name,w,deadline);
                    all_assessment.add(assessment);
                }
            }
        });
    }

    public List<Assessment> getAll_assessment(){
        return this.all_assessment;
    }


}
