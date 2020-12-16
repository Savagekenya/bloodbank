package com.example.thebloodbank.Activities;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.thebloodbank.Activities.Utils.Endpoints.register_url;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameET,cityET,numberET,blood_groupET,passwordET;
    private Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameET=findViewById(R.id.name);
        cityET=findViewById(R.id.city);
        numberET=findViewById(R.id.number);
        blood_groupET=findViewById(R.id.blood_group);
        passwordET=findViewById(R.id.password);
        submit_button=findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,city,number,blood_group,password;
                name =nameET.getText().toString();
                city =cityET.getText().toString();
                number =numberET.getText().toString();
                blood_group =blood_groupET.getText().toString();
                password =passwordET.getText().toString();
                showMessage(name+"\n"+city+"\n"+number+"\n"+blood_group+"\n"+password+"\n");
                if(isValid(name,city,number,blood_group,password)){
                    register(name,city,number,blood_group,password);

                };

            }
        });


    }
    private void register(String name, String city,String number,String blood_group,String password){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Endpoints.register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Success")){
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    RegisterActivity.this.finish();
                }else{
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                }

            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "something went wrong :(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY",error.getMessage());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params =new HashMap<>();
                params.put("name",name);
                params.put("city",city) ;
                params.put("number",number) ;
                params.put("blood_group",blood_group) ;
                params.put("password",password) ;


                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    private boolean  isValid(String name,String city,String number,String blood_group,String password){
        List<String> valid_blood_groups=new ArrayList<>();
        valid_blood_groups.add("A+");
        valid_blood_groups.add("A-");
        valid_blood_groups.add("B+");
        valid_blood_groups.add("B-");
        valid_blood_groups.add("AB+");
        valid_blood_groups.add("AB-");
        valid_blood_groups.add("O+");
        valid_blood_groups.add("O-");
        if(name.isEmpty()){
            showMessage("Name is empty ");
            return false;
        }else if(city.isEmpty()){
            showMessage("City field is required");
            return false;
        }else if(!valid_blood_groups.contains(blood_group)){
            showMessage("Blood group Invalid choose from"+valid_blood_groups);
            return false;
        }else if(number.length() !=10){
            showMessage("Invalid Mobile Number, number should be of 10 digits");
            return false;
        }
        return true;
    }
    private void showMessage(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
}