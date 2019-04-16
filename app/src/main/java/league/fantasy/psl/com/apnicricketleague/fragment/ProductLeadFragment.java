package league.fantasy.psl.com.apnicricketleague.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.analytics.Tracker;

import org.json.JSONObject;

import java.io.IOException;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.DbHelper;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.response.ProductLead.ProductLeadResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductLeadFragment extends Fragment implements View.OnClickListener, Callback<ProductLeadResponse> {
    private View mView;
    private EditText edt_name,edt_contact,edt_email,edt_city;
    private Spinner spinner_comment;
    private Button btn_submit;
    private String name,mobile,email,city;
    private DbHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private Tracker tracker;
    public ProductLeadFragment() {
        // Required empty public constructor
    }



    @Override
    public void onResume() {
        super.onResume();
        tracker=Helper.getGoogleAnalytics(getActivity().getApplication());
        Helper.updateGoogleAnalytics(tracker,this.getClass().getSimpleName());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_product_lead, container, false);
        init();
        btn_submit.setOnClickListener(this);
        return mView;
    }

    private void init() {
        edt_name=mView.findViewById(R.id.edt_name);
        edt_contact=mView.findViewById(R.id.edt_contact);
        edt_email=mView.findViewById(R.id.edt_email);
        edt_city=mView.findViewById(R.id.edt_city);
        spinner_comment=mView.findViewById(R.id.spinner_comment);
        btn_submit=mView.findViewById(R.id.btn_submit);
        dbHelper=new DbHelper(mView.getContext());
        adapter=new ArrayAdapter<String>(mView.getContext(),android.R.layout.simple_spinner_item,dbHelper.getConfig());
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
                    Helper.showAlertNetural(mView.getContext(), "Error", "Empty Fields not allowed");
                } else if (TextUtils.isEmpty(name) || name.trim().length() < 4) {
                    Helper.showAlertNetural(mView.getContext(), "Error", "Please enter name");
                } else if (TextUtils.isEmpty(mobile) || mobile.trim().length() < 11) {
                    Helper.showAlertNetural(mView.getContext(), "Error", "Please enter contact number");
                } else if (TextUtils.isEmpty(email) || !Helper.validateEmail(email)) {
                    Helper.showAlertNetural(mView.getContext(), "Error", "Please enter email address");
                } else if (TextUtils.isEmpty(city) || city.trim().length() < 3) {
                    Helper.showAlertNetural(mView.getContext(), "Error", "Please provide city");
                }else{
                    try{
                        JSONObject object=new JSONObject();
                        object.put("user_id",1001);
                        object.put("name",name);
                        object.put("contact",mobile);
                        object.put("user_comment",spinner_comment.getSelectedItem().toString());
                        object.put("city",city);
                        object.put("channel_id","Mobile");
                        object.put("prod_sts","1");
                        object.put("method_Name",this.getClass().getName()+".btn_submit.onClick");
                        Helper.trackEvent(tracker,"Button","onClick",this.getClass().getSimpleName()+".btn_submit");

                        ApiClient.getInstance().insertProductLead(Helper.encrypt(object.toString()))
                                .enqueue(this);
                    }catch (Exception e){
                        e.printStackTrace();
                        Helper.showAlertNetural(mView.getContext(),"Error",e.getMessage());
                    }
                }

            }
        }
    }

    @Override
    public void onResponse(Call<ProductLeadResponse> call, Response<ProductLeadResponse> response) {
        if(response.isSuccessful()){
            if(response.body().getResponseCode().equals("1001")){
                Helper.showAlertNetural(mView.getContext(),"Success","Submitted");
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
                Helper.showAlertNetural(mView.getContext(),"Error",response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    public void onFailure(Call<ProductLeadResponse> call, Throwable t) {
        t.fillInStackTrace();
        Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
    }
}
