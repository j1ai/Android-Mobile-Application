package chubbs.mymenu.models;

import java.util.HashMap;
import java.util.Map;


public class Assessment {

    public Assessment(){}

    public String name;
    public int weight;
    public String deadline;

    public Assessment(String name,int weight,String deadline) {
        this.name = name;
        this.weight = weight;
        this.deadline = deadline;
    }

    // [START post_to_map]

    public Map<String, Assessment> toMap() {
        HashMap<String, Assessment> result = new HashMap<>();
        result.put(name, this);
        return result;
    }
    // [END post_to_map]
}
