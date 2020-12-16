package com.example.thebloodbank.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thebloodbank.Activities.Utils.Endpoints;
import com.example.thebloodbank.Activities.Utils.VolleySingleton;
import com.example.thebloodbank.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText numberET,passwordET;
    Button submit_button;
    TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        numberET=findViewById(R.id.number);
        passwordET=findViewById(R.id.password);
        submit_button=findViewById(R.id.submit_button);
        signUpText=findViewById(R.id.sign_up_text);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberET.setError(null);
                passwordET.setError(null);
                String number =numberET.getText().toString();
                String password =passwordET.getText().toString();
                if(isValid(number,password)){
                    login(number, password);

                }

            }
        });


    }
    private void login(String number, String password){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Endpoints.register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Success")){
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    LoginActivity.this.finish();
                }else{
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "something went wrong :(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY",error.getMessage());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params =new HashMap<>();

                params.put("number",number) ;
                params.put("password",password) ;


                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private boolean  isValid(String number, String password){
        if(number.isEmpty()){
            showMessage("Empty Mobile Number");
            numberET.setError("Empty Mobile Number");
            return false;
        }else if(password.isEmpty()){

            showMessage("Empty Password");
            passwordET.setError("Empty Password");
            return false;
        }

        return true;
    }
    private void showMessage(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
}