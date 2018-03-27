package chubbs.mymenu.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Course {

    public String cid;
    public Course() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Course(String cid) {
        this.cid = cid;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Course> toMap() {
        HashMap<String, Course> result = new HashMap<>();
        result.put(cid, this);
        return result;
    }
    // [END post_to_map]

}
