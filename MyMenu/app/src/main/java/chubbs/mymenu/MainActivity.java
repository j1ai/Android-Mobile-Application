package chubbs.mymenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import chubbs.mymenu.models.Assessment;
import chubbs.mymenu.models.Job;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Assessment> assessments = db.getAll_assessment();
        Assessment [] listOfAssess = new Assessment[assessments.size()];
        listOfAssess = assessments.toArray(listOfAssess);
        Job[] jobs = WeightedJob.convert(listOfAssess);
        ArrayList<Job> schedule = WeightedJob.schedule(jobs);
        TextView taskText = (TextView)findViewById(R.id.dailyTask);
        if (schedule.size()!=0){
            taskText.setText(schedule.get(0).getCourseCode() + " " + schedule.get(0).getName());
        }


        createToolbar();
    }

    /*
    * method to create toolbars
    * this main activity will be inherited by all subsequent activities
    * use super.createToolbar()*/
    public void createToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_schedule) {
            startActivity(new Intent(this, ViewScheduleActivity.class));
        } else if (id == R.id.nav_courses) {
            startActivity(new Intent(this, ManageCoursesActivity.class));
        } else if (id == R.id.nav_tasks) {

        } else if (id == R.id.nav_task) {
            startActivity(new Intent(this, TaskActivity.class));
        } else if (id == R.id.nav_stats) {

        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_about) {

        }
        //doesn't actually sign out (i dont believe), just brings back to starter screen
        //so the user is still cached in the system
        else if (id == R.id.nav_signout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
