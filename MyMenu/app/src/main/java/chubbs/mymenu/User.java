package chubbs.mymenu;
/**
 * Created by j1ai on 22/03/18.
 */
import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}