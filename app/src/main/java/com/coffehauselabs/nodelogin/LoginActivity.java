package com.coffehauselabs.nodelogin;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{

    private EditText user, pass;
    private Button loginButton, regButton;

    //PROGRESS DIALOG
    private ProgressDialog pDialog;

    //JSON PARSER CLASS
    JSONParser jsonParser = new JSONParser();

    //PHP LOGIN SCRIPT (CHECK OTHER IP ADDRESS!!)
    private static final String LOGIN_URL = "http://192.168.1.103:80/webService/login.php";

    //JSON ELEMENT IDS FROM RESPONSE OF PHP SCRIPTS
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);    //Get content view from activity_login

        TextView txtView = (TextView) findViewById(R.id.loginView);
        Typeface font = Typeface.createFromAsset(getAssets(), "ArchitectsDaughter.ttf");
        txtView.setTypeface(font);

        //setup input fields
        user= (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);

        //setup buttons
        loginButton = (Button)findViewById(R.id.loginBtn);
        regButton = (Button)findViewById(R.id.regBtn);

        //register listeners
        loginButton.setOnClickListener(this);
        regButton.setOnClickListener(this);
    }

    //LOOK FOR WHICH BUTTON IS PRESSED
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.loginBtn:
                new AttemptLogin().execute();
                break;
            case R.id.regBtn:
                Intent i = new Intent(this, RegisterActivity.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }

    class AttemptLogin extends AsyncTask<String, String, String>{

        //SHOW PROGRESS DIALOG
        boolean failure = false;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args){
            //CHECK FOR SUCCESS TAG
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();

            try{
                //BUILDING PARAMETERS
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting!");
                //GET PRODUCT DETAILS BY MAKING HTTP REQUEST
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);

                //CHECK LOG FOR JSON RESPONSE
                Log.d("Login attempt", json.toString());

                //json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1){
                    Log.d("Login Successful!", json.toString());
                    Intent i  =new Intent(LoginActivity.this, WelcomeActivity.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                }
                else{
                    Log.d("Login Failure", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        //AFTER COMPLETING BACKGROUND TASK DISMISS PROGRESS DIALOG

        protected void onPostExecute(String file_url){
            //DISMISS DIALOG ONCE PRODUCT DELTED
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
