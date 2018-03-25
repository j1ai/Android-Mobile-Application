package chubbs.mymenu.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Task {

    public String name;
    public int weight;
    public String description;
    public Map<String, Boolean> stars = new HashMap<>();

    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Task(String name,int weight,String description) {
        this.name = name;
        this.weight = weight;
        this.description = description;

    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", name);

        return result;
    }
    // [END post_to_map]

}
