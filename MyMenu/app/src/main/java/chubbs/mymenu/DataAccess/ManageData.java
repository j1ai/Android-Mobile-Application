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

    public void deleteDocument() {
        // [START delete_document]
        mFirestore.collection(uid).document("DC")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
        // [END delete_document]
    }

    // Return the Specified Objects
    // For example, return CSC301 course in document ("COURSES")
    public void getDocument(String doc,String name) {
        // [START get_document]
        DocumentReference docRef = mFirestore.collection(uid).document(doc);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        // [END get_document]
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
