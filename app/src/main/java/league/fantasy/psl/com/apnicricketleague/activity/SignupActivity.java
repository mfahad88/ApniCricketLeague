package league.fantasy.psl.com.apnicricketleague.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;

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
                    
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
