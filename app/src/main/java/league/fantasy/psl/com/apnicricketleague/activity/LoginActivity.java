package league.fantasy.psl.com.apnicricketleague.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import league.fantasy.psl.com.apnicricketleague.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_register,btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    private void init() {
        btn_register=findViewById(R.id.btn_register);
        btn_login=findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_register){
            startActivity(new Intent(getApplicationContext(),SignupActivity.class));
        }if(v.getId()==R.id.btn_login){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}
