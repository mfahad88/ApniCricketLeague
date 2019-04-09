package league.fantasy.psl.com.apnicricketleague.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.DbHelper;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.request.ProductLeadRequest;
import league.fantasy.psl.com.apnicricketleague.model.response.Config.Datum;
import league.fantasy.psl.com.apnicricketleague.model.response.ProductLead.ProductLeadResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductLeadActivity extends AppCompatActivity implements View.OnClickListener, Callback<ProductLeadResponse> {
    private EditText edt_name,edt_contact,edt_email,edt_city;
    private Spinner spinner_comment;
    private Button btn_submit;
    private String name,mobile,email,comment,city;
    private DbHelper dbHelper;
    private ArrayAdapter<Datum> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_lead);
        init();
        btn_submit.setOnClickListener(this);
    }

    private void init() {
        edt_name=findViewById(R.id.edt_name);
        edt_contact=findViewById(R.id.edt_contact);
        edt_email=findViewById(R.id.edt_email);
        edt_city=findViewById(R.id.edt_city);
        spinner_comment=findViewById(R.id.spinner_comment);
        btn_submit=findViewById(R.id.btn_submit);
        dbHelper=new DbHelper(this);
        adapter=new ArrayAdapter<Datum>(this,android.R.layout.simple_spinner_item,dbHelper.getConfig());
        spinner_comment.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            name = edt_name.getText().toString();
            mobile = edt_contact.getText().toString();
            email = edt_email.getText().toString();
            city = edt_city.getText().toString();
            if (v.getId() == R.id.btn_submit) {
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(mobile) && TextUtils.isEmpty(email) && TextUtils.isEmpty(city)) {
                    Helper.showAlertNetural(this, "Error", "Empty Fields not allowed");
                } else if (TextUtils.isEmpty(name) || name.trim().length() < 4) {
                    Helper.showAlertNetural(this, "Error", "Please enter name");
                } else if (TextUtils.isEmpty(mobile) || mobile.trim().length() < 11) {
                    Helper.showAlertNetural(this, "Error", "Please enter contact number");
                } else if (TextUtils.isEmpty(email) || !Helper.validateEmail(email)) {
                    Helper.showAlertNetural(this, "Error", "Please enter email address");
                } else if (TextUtils.isEmpty(city) || city.trim().length() < 3) {
                    Helper.showAlertNetural(this, "Error", "Please provide city");
                }else{
                    try{
                        ApiClient.getInstance().insertProductLead(
                                new ProductLeadRequest(1001,name,email,mobile,"",city,"Mobile","1",this.getClass().getName()+".btn_submit.onClick"))
                                .enqueue(this);
                    }catch (Exception e){
                        e.printStackTrace();
                        Helper.showAlertNetural(getApplicationContext(),"Error",e.getMessage());
                    }
                }

            }
        }
    }

    @Override
    public void onResponse(Call<ProductLeadResponse> call, Response<ProductLeadResponse> response) {
        if(response.isSuccessful()){
            if(response.body().getResponseCode().equals("1001")){
                Helper.showAlertNetural(ProductLeadActivity.this,"Success","Submitted");
                edt_name.getText().clear();
                edt_contact.getText().clear();
                edt_email.getText().clear();
                edt_city.getText().clear();


                edt_name.clearFocus();
                edt_contact.clearFocus();
                edt_email.clearFocus();
                edt_city.clearFocus();
            }}else{
            try {
                Helper.showAlertNetural(ProductLeadActivity.this,"Error",response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    public void onFailure(Call<ProductLeadResponse> call, Throwable t) {
        t.fillInStackTrace();
        Helper.showAlertNetural(ProductLeadActivity.this,"Error",t.getMessage());
    }
}
