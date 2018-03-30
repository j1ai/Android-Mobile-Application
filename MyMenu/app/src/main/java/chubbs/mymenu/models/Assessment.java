package chubbs.mymenu.models;

import java.util.HashMap;
import java.util.Map;


public class Assessment {

    public Assessment(){}

    public String course;
    public String name;
    public int weight;
    public String deadline;

    public Assessment(String course, String name,int weight,String deadline) {
        this.course = course;
        this.name = name;
        this.weight = weight;
        this.deadline = deadline;
    }

    // [START post_to_map]

    public Map<String, Object> toMap() {
        Map<String, Object> assessmentMap  = new HashMap<>();
        assessmentMap.put("name",this.name);
        assessmentMap.put("weight",this.weight);
        assessmentMap.put("deadline",this.deadline);
        return assessmentMap;
    }
    // [END post_to_map]
}
