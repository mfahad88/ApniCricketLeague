package league.fantasy.psl.com.apnicricketleague.activity;

import android.content.Intent;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONObject;

import java.io.IOException;


import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.DbHelper;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.request.AgentBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.request.ConfigBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.request.TestBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.response.Config.ConfigBeanResponse;
import league.fantasy.psl.com.apnicricketleague.model.response.Player.PlayerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity implements Callback<ConfigBeanResponse> {
    Tracker tracker;
    DbHelper dbHelper;
    @Override
    protected void onResume() {
        super.onResume();
        tracker=Helper.getGoogleAnalytics(getApplication());
        Helper.updateGoogleAnalytics(tracker,this.getClass().getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       //Helper.printKeyHash(this);
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        FacebookSdk.setAutoLogAppEventsEnabled(true);
        dbHelper=new DbHelper(this);
        JSONObject obj=new JSONObject();
       try{
           obj.put("param_type","GF");
           obj.put("userId","1001");
           obj.put("method_Name",this.getClass().getSimpleName()+".onCreate");
           System.out.println(obj.toString());

           ApiClient.getInstance().getPlayersMatches().enqueue(new Callback<PlayerResponse>() {
               @Override
               public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> response) {
                   if(response.isSuccessful()){
                       if(response.body().getResponseCode().equals("1001")){

                           dbHelper.savePlayers(response.body().getData());
                       }else{
                           Helper.showAlertNetural(SplashActivity.this,"Error",response.body().getMessage());
                       }
                   }
               }

               @Override
               public void onFailure(Call<PlayerResponse> call, Throwable t) {
                   t.fillInStackTrace();
                   Helper.showAlertNetural(SplashActivity.this,"Error",t.getMessage());
               }
           });

           ApiClient.getInstance().getConfig(Helper.encrypt(obj.toString()))
                   .enqueue(this);
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                   Helper.trackEvent(tracker,"Intent",intent.getAction(),MainActivity.class.getSimpleName());
                   startActivity(intent);
                   finish();
               }
           },500);

       }catch (Exception e){
           e.printStackTrace();
       }


    }

    @Override
    public void onResponse(Call<ConfigBeanResponse> call, Response<ConfigBeanResponse> response) {
        if(response.isSuccessful()){
            if(response.body().getResponseCode().equals("1001")){

                dbHelper.saveConfig(response.body().getData());
            }else{
                Helper.showAlertNetural(SplashActivity.this,"Error",response.body().getMessage());
            }
        }else{
            try {
                Helper.showAlertNetural(SplashActivity.this,"Error",response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<ConfigBeanResponse> call, Throwable t) {
        t.fillInStackTrace();
        Helper.showAlertNetural(SplashActivity.this,"Error",t.getMessage());
    }
}
