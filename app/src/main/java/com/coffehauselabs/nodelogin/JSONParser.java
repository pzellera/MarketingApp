package com.coffehauselabs.nodelogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    //constructor
    public JSONParser() {

    }

    public JSONObject getJSONFromUrl(final String url){

        //MAKE HTTP REQUEST
        try{
            //CONSTRUCT THE CLIENT AND THE HTTP REQUEST
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            //EXECUTE THE POST REQUEST AND STORE THE RESPONSE LOCALLY

            HttpResponse httpResponse = httpClient.execute(httpPost);
            //EXECUTE DATA FROM THE RESPONSE
            HttpEntity httpEntity = httpResponse.getEntity();
            //OPEN AN INPUT STREAM WITH THE DATA CONTENT
            is = httpEntity.getContent();
        }

        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch(ClientProtocolException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try{
            // Create a BufferedReader to parse through the inputStream.
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            //DECLARE A STRING BUILDER TO HELP WITH THE PARSING
            StringBuilder sb = new StringBuilder();
            //DECLARE A STRING TO STORE THE JSON OBJECT DATA IN STRING FORM
            String line = null;

            //Build the string until null
            while((line = reader.readLine()) !=null){
                sb.append(line + "\n");
            }

            //CLOSE THE INPUT STREAM
            is.close();
            //CONVERT THE STRING BUILDER DATA TO AN ACTUAL STRING
            json = sb.toString();
        }
        catch(Exception e){
            Log.e("Buffer Error", "Error converting the result "+ e.toString());
        }

        //TRY TO PARSE THE STRING TO A JSON object
        try {
            jObj = new JSONObject(json);
        }
        catch (JSONException e){
            Log.e("JSON Parser", "Error parsing data "+e.toString());
        }

        //RETURN THE JSON OBJECT
        return jObj;
    }

    //FUNCTION GET JSON FROM URL BY MAKING HTTP POST OR GET METHOD

    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params){

        //MAKING HTTP REQUEST
        try{
            //CHECK FOR REQUEST METHOD
            if(method == "POST"){
                //REQUEST METHOD IS POST
                //defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
            else if(method == "GET"){
                //request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch(ClientProtocolException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        }
        catch(Exception e){
            Log.e("Buffer Error", "Error converting result "+ e.toString());
        }

        //TRY TO PARSE THE STRING IN TO A JSON OBJECT
        try{
            jObj = new JSONObject(json);
        }
        catch(JSONException e){
            Log.e("JSON Parser", "Error parsing data" + e.toString());
        }

        //RETURN JSON STRING
        return jObj;
    }
}
