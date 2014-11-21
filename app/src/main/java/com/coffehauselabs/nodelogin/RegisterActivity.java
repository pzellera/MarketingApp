package com.coffehauselabs.nodelogin;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class    RegisterActivity extends Activity implements OnClickListener{

    private EditText user, pass;
    private Button regBtn;

    //PROGRESS DIALOG
    private ProgressDialog pDialog;

    //JSON PARSER CLASS
    JSONParser jsonParser = new JSONParser();


    //PHP LOGIN SCRIPT
    private static final String LOGIN_URL = "http://henrywinn.com/MarketingApp/server/register.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_register
        setContentView(R.layout.activity_register);

        user = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);

        regBtn = (Button)findViewById(R.id.regBtn);
        regBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        new CreateUser().execute();
    }

    class CreateUser extends AsyncTask<String, String, String>{

        boolean failure = false;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args){

            //CHECK FOR SUCCESS
            int success;
            String username = user.getText().toString();
            String password = pass.getText().toString();

            try{
                //BUILDING PARAMETERS
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");

                //POSTING USER DATA TO SCRIPT
               JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);


                //FULL JSON RESPONSE
                Log.d("Login attempt", json.toString());

                //JSON SUCCESS ELEMENT
                success = json.getInt(TAG_SUCCESS);
                if(success == 1){
                    Log.d("User created!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                }
                else{
                    Log.d("LOGIN FAILURE!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            }

            catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        //DISMISS THE PROGRESS DIALOG
        protected void onPostExecute(String file_url){
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(RegisterActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
