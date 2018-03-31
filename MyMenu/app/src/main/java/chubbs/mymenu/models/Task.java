package chubbs.mymenu.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Task {

    public String name;
    public String priority;
    public String due_date;
    public String due_time;

    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Task(String name,String priority,String due_date,String due_time) {
        this.name = name;
        this.priority = priority;
        this.due_date = due_date;
        this.due_time = due_time;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> taskMap  = new HashMap<>();
        taskMap.put("name",this.name);
        taskMap.put("priority",this.priority);
        taskMap.put("due_date",this.due_date);
        taskMap.put("due_time",this.due_time);
        return taskMap;
    }
    // [END post_to_map]

}
