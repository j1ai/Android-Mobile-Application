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
    public String start_date;
    public int duration;
    public String start_time;

    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Task(String name,String priority,String start_date,String start_time,int duration) {
        this.name = name;
        this.priority = priority;
        this.start_date = start_date;
        this.start_time = start_time;
        this.duration = duration;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        Map<String, Object> taskMap  = new HashMap<>();
        taskMap.put("name",this.name);
        taskMap.put("priority",this.priority);
        taskMap.put("start_date",this.start_date);
        taskMap.put("start_time",this.start_time);
        taskMap.put("duration",this.duration);
        return taskMap;
    }
    // [END post_to_map]

}
