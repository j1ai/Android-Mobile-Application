package chubbs.mymenu.DataAccess;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import chubbs.mymenu.BaseActivity;
import chubbs.mymenu.models.Assessment;
import chubbs.mymenu.models.Course;
import chubbs.mymenu.models.Job;


public class ManageData extends BaseActivity{

    private FirebaseFirestore mFirestore;
    private BaseActivity activity;
    private String uid;
    private static final String TAG = "ManageData";

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
            Map<String, Course> data = new HashMap<>();
            mFirestore.collection(uid).document(doc)
                    .set(data, SetOptions.merge());
        }
        else if (doc == "ASSESSMENTS"){
            Map<String, Job> data = new HashMap<>();
            mFirestore.collection(uid).document(doc)
                    .set(data, SetOptions.merge());
        }

    }

    public void addCourse(Course newCourse){
        Map<String, Course> data = newCourse.toMap();
        mFirestore.collection(uid).document("COURSES")
                .set(data, SetOptions.merge());
    }

    public void addAssessment(Assessment newAssessment){
        Map<String, Assessment> data = newAssessment.toMap();
        mFirestore.collection(uid).document("ASSESSMENTS")
                .set(data, SetOptions.merge());
    }

    public void getDocument(String doc) {
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

    public void getAllDocs() {
        // [START get_multiple_all]
        mFirestore.collection(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        // [END get_multiple_all]
    }

}
