package chubbs.mymenu.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String password;
    private Map<String, Course> courseMap;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setCourseMap(Map<String,Course> courseMap){
        this.courseMap = courseMap;
    }

    public Map<String,Course> getCourseMap(){
        return this.courseMap;
    }

}
// [END blog_user_class]
