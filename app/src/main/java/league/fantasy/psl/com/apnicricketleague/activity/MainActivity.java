package league.fantasy.psl.com.apnicricketleague.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;

import org.json.JSONObject;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.fragment.AboutFragment;
import league.fantasy.psl.com.apnicricketleague.fragment.AgentLocator;
import league.fantasy.psl.com.apnicricketleague.fragment.ContactUsFragment;
import league.fantasy.psl.com.apnicricketleague.fragment.PrizesFragment;
import league.fantasy.psl.com.apnicricketleague.fragment.ProductLeadFragment;
import league.fantasy.psl.com.apnicricketleague.fragment.RulesFragment;
import league.fantasy.psl.com.apnicricketleague.model.response.Login.MyUser;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Tracker tracker;
    SharedPreferences preferences;
    @Override
    protected void onResume() {
        super.onResume();
        tracker=Helper.getGoogleAnalytics(getApplication());
        Helper.updateGoogleAnalytics(tracker,this.getClass().getSimpleName());
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            preferences=getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
            JSONObject object=new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());

            Log.e("Helper===>",object.toString());

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            Menu menu=navigationView.getMenu();
            menu.add(0,1,0,"Home");
            menu.add(0,2,0,"Profile");
            menu.add(0,3,0,"Rules");
            menu.add(0,4,0,"Edit Team");
            menu.add(0,5,0,"Inventory");
            menu.add(0,6,0,"Shop");
            menu.add(0,7,0,"Boosters");
            menu.add(0,8,0,"Wallet");
            menu.add(0,9,0,"About");
            menu.add(0,10,0,"Prizes");
            menu.add(0,11,0,"Contact us");
            menu.add(0,12,0,"Gold Finance");
            menu.add(0,13,0,"Agent Locator");
            menu.add(0,14,0,"Logout");
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            //super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        int id = item.getItemId();
        if(id==3){
            fragment=new RulesFragment();
        }
        if(id==9){
            fragment=new AboutFragment();
        }
        if(id==10){
            fragment=new PrizesFragment();
        }
        if(id==11){
            fragment=new ContactUsFragment();
        }
        if(id==12){
            fragment=new ProductLeadFragment();
        }
        if(id==13){

            fragment=new AgentLocator();
        }
        if(fragment!=null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            Helper.trackEvent(tracker,"Fragment Replace","FragmentTransaction",this.getClass().getSimpleName()+"."+fragment.toString());
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
