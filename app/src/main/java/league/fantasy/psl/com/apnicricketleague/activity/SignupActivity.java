package league.fantasy.psl.com.apnicricketleague.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import league.fantasy.psl.com.apnicricketleague.BuildConfig;
import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.response.Insert.InsertResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_first_name,edt_last_name,edt_password,edt_email,edt_mobile_no;
    private Button btn_submit;
    private String firstName,lastName,password,email,mobileNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        btn_submit.setOnClickListener(this);
    }

    private void init() {
        edt_first_name=findViewById(R.id.edt_first_name);
        edt_last_name=findViewById(R.id.edt_last_name);
        edt_password=findViewById(R.id.edt_password);
        edt_email=findViewById(R.id.edt_email);
        edt_mobile_no=findViewById(R.id.edt_mobile_no);
        btn_submit=findViewById(R.id.btn_submit);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_submit){
            firstName=edt_first_name.getText().toString();
            lastName=edt_last_name.getText().toString();
            password=edt_password.getText().toString();
            email=edt_email.getText().toString();
            mobileNo=edt_mobile_no.getText().toString();

            if(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(password)
                    && TextUtils.isEmpty(email) && TextUtils.isEmpty(mobileNo)){
                Helper.showAlertNetural(this,"Error","Empty Fields not allowed");
            }
            else if(TextUtils.isEmpty(firstName) || firstName.length()<3){
                Helper.showAlertNetural(this,"Error","Please enter firstname");
            }
            else if(TextUtils.isEmpty(lastName) || lastName.length()<3){
                Helper.showAlertNetural(this,"Error","Please enter lastname");
            }
            else if(TextUtils.isEmpty(password) || password.length()<5){
                Helper.showAlertNetural(this,"Error","Please enter password");
            }else if(TextUtils.isEmpty(email) || Helper.validateEmail(email)){
                Helper.showAlertNetural(this,"Error","Please enter email address");
            }else if(TextUtils.isEmpty(mobileNo) || mobileNo.length()<11){
                Helper.showAlertNetural(this,"Error","Please enter Mobile No");
            }else{
                try{
                    JSONObject object=new JSONObject();
                    object.put("first_name",firstName);
                    object.put("last_name",lastName);
                            object.put("pws",password);
                            object.put("email",email);
                            object.put("mobile_no",mobileNo);
                            object.put("app_version",BuildConfig.VERSION_NAME);
                            object.put("os","Android");
                            object.put("source","asa");
                            object.put("pic_url","as");
                            object.put("sts",1);
                            object.put("is_updated","0");
                            object.put("method_Name",this.getClass().getSimpleName()+".btn_submit.onClick");
                    ApiClient.getInstance().insertUser(Helper.encrypt(object.toString()))
                            .enqueue(new Callback<InsertResponse>() {
                                @Override
                                public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                                    if(response.isSuccessful()){
                                        if(response.body().getResponseCode().equals("1001")){
                                            Helper.showAlertNetural(SignupActivity.this,"Success","Done");
                                            startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                        }else{
                                            Helper.showAlertNetural(SignupActivity.this,"Error","Something went wrong");
                                        }
                                    }else{
                                        if (response.errorBody() != null) {
                                            try {
                                                Helper.showAlertNetural(SignupActivity.this,"Error",response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<InsertResponse> call, Throwable t) {
                                    t.fillInStackTrace();
                                    Helper.showAlertNetural(SignupActivity.this,"Error",t.getMessage());

                                }
                            });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
