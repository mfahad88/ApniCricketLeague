package league.fantasy.psl.com.apnicricketleague.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private EditText edt_first_name,edt_last_name,edt_email,edt_mobile_no;
    private Button btn_save;
    private ImageView img_profile;
    private String firstname,lastname,email,mobile;
    private SharedPreferences preferences;
    private int REQUEST_CAMERA=200;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_profile, container, false);
        init();


        try {
            JSONObject object=new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
            edt_first_name.setText(object.getString("first_name"));
            edt_last_name.setText(object.getString("last_name"));
            edt_email.setText(object.getString("email"));
            edt_mobile_no.setText(object.getString("mobile_no"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        btn_save.setOnClickListener(this);
        img_profile.setOnClickListener(this);
        return mView;
    }

    private void init(){
        edt_first_name=mView.findViewById(R.id.edt_first_name);
        edt_last_name=mView.findViewById(R.id.edt_last_name);
        edt_email=mView.findViewById(R.id.edt_email);
        edt_mobile_no=mView.findViewById(R.id.edt_mobile_no);
        img_profile=mView.findViewById(R.id.img_profile);
        btn_save=mView.findViewById(R.id.btn_save);
        preferences = mView.getContext().getSharedPreferences(Helper.SHARED_PREF, Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_save){
            firstname=edt_first_name.getText().toString();
            lastname=edt_last_name.getText().toString();
            email=edt_email.getText().toString();
            mobile=edt_mobile_no.getText().toString();

            if(TextUtils.isEmpty(firstname) && TextUtils.isEmpty(lastname) && TextUtils.isEmpty(email) && TextUtils.isEmpty(mobile)){
                Helper.showAlertNetural(mView.getContext(),"Error","Empty fields not allowed");
            }
            else if(TextUtils.isEmpty(firstname) || firstname.trim().length()<2){
                Helper.showAlertNetural(mView.getContext(),"Error","Invalid firstname");
            }
            else if(TextUtils.isEmpty(lastname) || lastname.trim().length()<2){
                Helper.showAlertNetural(mView.getContext(),"Error","Invalid lastname");
            }
            else if(TextUtils.isEmpty(mobile) || mobile.trim().length()<11){
                Helper.showAlertNetural(mView.getContext(),"Error","Invalid mobile number");
            }
            else if(TextUtils.isEmpty(email) || Helper.validateEmail(email)){
                Helper.showAlertNetural(mView.getContext(),"Error","Invalid email address");
            }else {
                //ApiClient.getInstance().updateUser()
            }


        } else if(v.getId()==R.id.img_profile){
                selectImage();

        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (Helper.permissionAlreadyGranted(mView.getContext(),Manifest.permission.CAMERA)) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    }else{
                            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){

                            }
                            requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
                    }
                } else if (items[item].equals("Choose from Library")) {
                    if (Helper.permissionAlreadyGranted(mView.getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CAMERA){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Fragment",data.getExtras().get("data").toString());
        switch (requestCode){
            case 0:

                    Bitmap image=(Bitmap)data.getExtras().get("data");
                    if(image!=null){
                        BitmapDrawable drawable=new BitmapDrawable(image);
                        img_profile.setBackground(drawable);
                    }

                break;
        }
    }
}
