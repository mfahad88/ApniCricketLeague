package league.fantasy.psl.com.apnicricketleague.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.IOException;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.request.ContactBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsFragment extends Fragment implements View.OnClickListener, Callback<ContactBean> {
    private View mView;
    private EditText edt_name,edt_contact,edt_email,edt_question;
    private Button btn_submit;
    private String name,mobile,email,question;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_contact_us, container, false);
        init();

        btn_submit.setOnClickListener(this);
        return mView;
    }

    private void init() {

        edt_name=mView.findViewById(R.id.edt_name);
        edt_contact=mView.findViewById(R.id.edt_contact);
        edt_email=mView.findViewById(R.id.edt_email);
        edt_question=mView.findViewById(R.id.edt_question);
        btn_submit=mView.findViewById(R.id.btn_submit);
    }


    @Override
    public void onClick(View v) {
        name=edt_name.getText().toString();
        mobile=edt_contact.getText().toString();
        email=edt_email.getText().toString();
        question=edt_question.getText().toString();
        if(v.getId()==R.id.btn_submit){
            if(TextUtils.isEmpty(name) && TextUtils.isEmpty(mobile) && TextUtils.isEmpty(email) && TextUtils.isEmpty(question)){
                Helper.showAlertNetural(mView.getContext(),"Error", "Empty Fields not allowed");
            }
            else if(TextUtils.isEmpty(name) || name.trim().length()<4){
                Helper.showAlertNetural(mView.getContext(),"Error", "Please enter name");
            }
            else if(TextUtils.isEmpty(mobile) || mobile.trim().length()<11){
                Helper.showAlertNetural(mView.getContext(),"Error", "Please enter contact number");
            }
            else if(TextUtils.isEmpty(email) || !Helper.validateEmail(email)){
                Helper.showAlertNetural(mView.getContext(),"Error", "Please enter email address");
            }
            else if(TextUtils.isEmpty(question) || question.trim().length()<3){
                Helper.showAlertNetural(mView.getContext(),"Error", "Please provide question");
            }

            else{

                try{
                    JSONObject object=new JSONObject();
                    object.put("user_id",1001);
                    object.put("name",name);
                    object.put("email",email);
                    object.put("mobile",mobile);
                    object.put("comments",question);
                    object.put("method_Name",this.getClass().getSimpleName()+".btn_submit.onClick");
                    ApiClient.getInstance().insertComplaint(Helper.encrypt(object.toString()))
                            .enqueue(this);
                }catch (Exception e){
                    e.printStackTrace();
                    Helper.showAlertNetural(mView.getContext(),"Error",e.getMessage());
                }
            }
        }
    }

    @Override
    public void onResponse(Call<ContactBean> call, Response<ContactBean> response) {
        if(response.code()==200){
            Helper.showAlertNetural(mView.getContext(),"Success","Submitted");
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
                Helper.showAlertNetural(mView.getContext(),"Error",response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    public void onFailure(Call<ContactBean> call, Throwable t) {
        t.fillInStackTrace();
        Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
    }
}
