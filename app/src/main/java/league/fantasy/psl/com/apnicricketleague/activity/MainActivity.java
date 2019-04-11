package league.fantasy.psl.com.apnicricketleague.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;

import league.fantasy.psl.com.apnicricketleague.AnalyticsApplication;
import league.fantasy.psl.com.apnicricketleague.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        Toast.makeText(getApplicationContext(), this.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
        mTracker.setScreenName(this.getClass().getSimpleName());
        mTracker.enableAutoActivityTracking(true);
    }

    public void showProduct(View view) {
        startActivity(new Intent(getApplicationContext(),ProductLeadActivity.class));
    }

    public void showContactus(View view) {
        startActivity(new Intent(getApplicationContext(),Contactus.class));
    }

    public void showAgent(View view) {
        startActivity(new Intent(getApplicationContext(),AgentLocatorActivity.class));
    }
}
