package league.fantasy.psl.com.apnicricketleague.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.DbHelper;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.response.Login.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_register,btn_login;
    private EditText edt_mobile_no,edt_password;
    private SharedPreferences sharedpreferences;
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void init() {
        edt_mobile_no=findViewById(R.id.edt_mobile_no);
        edt_password=findViewById(R.id.edt_password);
        btn_register=findViewById(R.id.btn_register);
        btn_login=findViewById(R.id.btn_login);
        sharedpreferences = getSharedPreferences(Helper.SHARED_PREF, Context.MODE_PRIVATE);
        dbHelper=new DbHelper(this);
    }

    @Override
    public void onClick(View v) {

        String mobileNo=edt_mobile_no.getText().toString();
        String password=edt_password.getText().toString();
        if(v.getId()==R.id.btn_register){
            Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }if(v.getId()==R.id.btn_login){
            if((!TextUtils.isEmpty(mobileNo) && mobileNo.length()==11) &&(!TextUtils.isEmpty(password) && password.length()>4)){
                JSONObject obj=new JSONObject();
                try {
                    obj.put("mobile_no",mobileNo);
                     obj.put("pws",password);
                    ApiClient.getInstance().login(Helper.encrypt(obj.toString())).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().getResponseCode().equals("1001")){
                                    Helper.putUserSession(sharedpreferences,Helper.MY_USER,response.body().getData().getMyUser());
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }else{
                                    Helper.showAlertNetural(LoginActivity.this,"Error",response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            t.fillInStackTrace();
                            Helper.showAlertNetural(LoginActivity.this,"Error",t.getMessage());
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                Helper.showAlertNetural(LoginActivity.this,"Error","Please enter correct Mobile Number and Password");
            }

        }

        //finish();
    }

    @Override
    public void onBackPressed() {
        dbHelper.deleteConfig();
        dbHelper.deletePlayer();
        super.onBackPressed();
    }
}
