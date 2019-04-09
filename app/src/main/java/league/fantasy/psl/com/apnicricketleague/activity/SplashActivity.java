package league.fantasy.psl.com.apnicricketleague.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.DbHelper;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.request.ConfigBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.request.TestBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.response.Config.ConfigBeanResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity implements Callback<ConfigBeanResponse> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.v("OkHttp",Helper.encrypt("1")[0]+"\n"+Helper.encrypt("1")[1]);
        ApiClient.getInstance().testService(new TestBeanRequest(Helper.encrypt("Hello")[0],Helper.encrypt("Hello")[1].toString()))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(SplashActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(SplashActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
      /*  ApiClient.getInstance().getConfig(new ConfigBeanRequest("GF","1"))
                .enqueue(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),ProductLeadActivity.class);
                startActivity(intent);
            }
        },500);*/
    }

    @Override
    public void onResponse(Call<ConfigBeanResponse> call, Response<ConfigBeanResponse> response) {
        if(response.isSuccessful()){
            if(response.body().getResponseCode().equals("1001")){
                DbHelper dbHelper=new DbHelper(SplashActivity.this);
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
