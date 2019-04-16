package league.fantasy.psl.com.apnicricketleague.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;


import io.fabric.sdk.android.Fabric;
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
    private Button btn_submit,btn_signup;
    private String firstName,lastName,password,email,mobileNo;
    private Tracker tracker;
    private static final String TAG = "SignupActivity";
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String signupType="form";
    private RelativeLayout relative_signup_header,relative_signup_form;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN=100;
    @Override
    protected void onResume() {
        super.onResume();
        tracker=Helper.getGoogleAnalytics(getApplication());
        Helper.updateGoogleAnalytics(tracker,this.getClass().getSimpleName());
        Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)  // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();

        signInButton.setSize(SignInButton.SIZE_STANDARD);
        btn_submit.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                relative_signup_header.setVisibility(View.GONE);
                relative_signup_form.setVisibility(View.VISIBLE);
                Log.e("Facebook: ",loginResult.getAccessToken().getUserId());
                AccessToken accessToken = loginResult.getAccessToken();
                useLoginInformation(accessToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignupActivity.this, "Cancelled...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RC_SIGN_IN==100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void init() {
        LoginManager.getInstance().logOut();
        edt_first_name=findViewById(R.id.edt_first_name);
        edt_last_name=findViewById(R.id.edt_last_name);
        edt_password=findViewById(R.id.edt_password);
        edt_email=findViewById(R.id.edt_email);
        edt_mobile_no=findViewById(R.id.edt_mobile_no);
        btn_submit=findViewById(R.id.btn_submit);
        loginButton=findViewById(R.id.login_button);
        relative_signup_header=findViewById(R.id.relative_signup_header);
        relative_signup_form=findViewById(R.id.relative_signup_form);
        signInButton = findViewById(R.id.sign_in_button);
        btn_signup=findViewById(R.id.btn_signup);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager=CallbackManager.Factory.create();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signOut();
    }


    private void useLoginInformation(AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    edt_first_name.setText(object.getString("first_name"));
                    edt_last_name.setText(object.getString("last_name"));
                    edt_email.setText(object.getString("email"));
                    signupType="Facebook";

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_signup){
            relative_signup_header.setVisibility(View.GONE);
            relative_signup_form.setVisibility(View.VISIBLE);
        }
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
                    Helper.trackEvent(tracker,"Button","onClick",this.getClass().getSimpleName()+"btn_submit");

                    JSONObject object=new JSONObject();
                    object.put("first_name",firstName);
                    object.put("last_name",lastName);
                            object.put("pws",password);
                            object.put("email",email);
                            object.put("mobile_no",mobileNo);
                            object.put("app_version",BuildConfig.VERSION_NAME);
                            object.put("os","Android");
                            object.put("source",signupType);
                            object.put("pic_url","as");
                            object.put("sts",1);
                            object.put("is_updated","0");
                            object.put("method_Name",this.getClass().getSimpleName()+".btn_submit.onClick");
                    Log.e(this.getClass().getName(),object.toString());

                    ApiClient.getInstance().insertUser(Helper.encrypt(object.toString()))
                            .enqueue(new Callback<InsertResponse>() {
                                @Override
                                public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                                    if(response.isSuccessful()){
                                        if(response.body().getResponseCode().equals("1001")){
                                            Helper.showAlertNetural(SignupActivity.this,"Success","Done");
                                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                        }else{
                                            Helper.showAlertNetural(SignupActivity.this,"Error",response.body().getMessage());
                                        }
                                    }else{
                                        if (response.errorBody() != null) {
                                            try {

                                                Helper.showAlertNetural(SignupActivity.this,"Error",response.errorBody().string());
                                            } catch (IOException e) {
                                                Crashlytics.logException(e.getCause());
                                                e.printStackTrace();

                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<InsertResponse> call, Throwable t) {
                                    Crashlytics.logException(t.fillInStackTrace());
                                    t.fillInStackTrace();

                                    Helper.showAlertNetural(SignupActivity.this,"Error",t.getMessage());

                                }
                            });

                }catch (Exception e){
                    Crashlytics.logException(e.getCause());
                    e.printStackTrace();
                }
            }
        }if(v.getId()==R.id.sign_in_button){

            signIn();

        }
    }
    private void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account!=null){
                relative_signup_header.setVisibility(View.GONE);
                relative_signup_form.setVisibility(View.VISIBLE);

            String name[]=account.getDisplayName().split("\\s+");
            edt_first_name.setText(name[0]);
            edt_last_name.setText(name[1]);
            edt_email.setText(account.getEmail());
            signupType="Google";
            // Signed in successfully, show authenticated UI.
            Log.e("Google sign-in",account.toJson());
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }
}
