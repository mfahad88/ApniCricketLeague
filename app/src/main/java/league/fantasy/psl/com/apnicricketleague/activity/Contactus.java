package league.fantasy.psl.com.apnicricketleague.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.request.ContactBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Contactus extends AppCompatActivity implements View.OnClickListener,Callback<ContactBean> {
    private EditText edt_name,edt_contact,edt_email,edt_question;
    private Button btn_submit;
    private String name,mobile,email,question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        init();

        btn_submit.setOnClickListener(this);
    }

    private void init() {
        edt_name=findViewById(R.id.edt_name);
        edt_contact=findViewById(R.id.edt_contact);
        edt_email=findViewById(R.id.edt_email);
        edt_question=findViewById(R.id.edt_question);
        btn_submit=findViewById(R.id.btn_submit);
    }

    @Override
    public void onClick(View v) {
        name=edt_name.getText().toString();
        mobile=edt_contact.getText().toString();
        email=edt_email.getText().toString();
        question=edt_question.getText().toString();
        if(v.getId()==R.id.btn_submit){
            if(TextUtils.isEmpty(name) && TextUtils.isEmpty(mobile) && TextUtils.isEmpty(email) && TextUtils.isEmpty(question)){
                Helper.showAlertNetural(this,"Error", "Empty Fields not allowed");
            }
            else if(TextUtils.isEmpty(name) || name.trim().length()<4){
                Helper.showAlertNetural(this,"Error", "Please enter name");
            }
           else if(TextUtils.isEmpty(mobile) || mobile.trim().length()<11){
                Helper.showAlertNetural(this,"Error", "Please enter contact number");
            }
            else if(TextUtils.isEmpty(email) || !Helper.validateEmail(email)){
                Helper.showAlertNetural(this,"Error", "Please enter email address");
            }
            else if(TextUtils.isEmpty(question) || question.trim().length()<3){
                Helper.showAlertNetural(this,"Error", "Please provide question");
            }

            else{

                try{
                    ApiClient.getInstance().insertComplaint(new ContactBean(1,name,email,mobile,question,this.getClass().getSimpleName()+".btn_submit.onClick"))
                            .enqueue(this);
                }catch (Exception e){
                    e.printStackTrace();
                    Helper.showAlertNetural(this,"Error",e.getMessage());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        System.exit(0);
        finish();
    }

    @Override
    public void onResponse(Call<ContactBean> call, Response<ContactBean> response) {
        if(response.code()==200){
            Helper.showAlertNetural(Contactus.this,"Success","Submitted");
            edt_name.getText().clear();
            edt_contact.getText().clear();
            edt_email.getText().clear();
            edt_question.getText().clear();

            edt_name.clearFocus();
            edt_contact.clearFocus();
            edt_email.clearFocus();
            edt_question.clearFocus();
        }else{
            try {
                Helper.showAlertNetural(Contactus.this,"Error",response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    public void onFailure(Call<ContactBean> call, Throwable t) {
        t.fillInStackTrace();
        Helper.showAlertNetural(Contactus.this,"Error",t.getMessage());
    }
}
