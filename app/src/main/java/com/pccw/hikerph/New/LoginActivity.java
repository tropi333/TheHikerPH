package com.pccw.hikerph.New;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pccw.hikerph.MainActivity;
import com.pccw.hikerph.R;

public class LoginActivity extends ParentActivity implements View.OnClickListener {

    EditText etUname;
    EditText etPassword;
    Button btnLogin;
    Button btnCreateAcct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initFields();
    }


    private void initFields(){
        etUname = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnCreateAcct = findViewById(R.id.btnCreateAcct);
        btnCreateAcct.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnLogin:

//                if(validate()){

                    showMainActivity();
//                }
                break;
            case R.id.btnCreateAcct:
                break;
        }
    }


    private boolean validate(){

        if(etUname.getText().toString().trim().isEmpty()){
            showToastMessage(getApplicationContext(), getString(R.string.message_empty_uname));
            return false;
        }
        else if(etPassword.getText().toString().trim().isEmpty()){
            showToastMessage(getApplicationContext(), getString(R.string.message_empty_password));
            return false;
        }

        return true;
    }

    private void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
