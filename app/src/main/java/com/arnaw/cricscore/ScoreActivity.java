package com.arnaw.cricscore;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class ScoreActivity extends AppCompatActivity {

    String uid, tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        uid = getIntent().getStringExtra("uid");
      //  Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_LONG).show();

        new MyAsyncTask().execute();
        new MyAsyncTaskComment().execute();
    }



    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                URL url = new URL("http://cricapi.com/api/cricketScore?"+"unique_id="+uid+"&apikey=jPRfOjW8GKcgEgYWK6Z7qVk9poB3"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                // postDataParams.put("apikey", " jPRfOjW8GKcgEgYWK6Z7qVk9poB3");
                //  postDataParams.put("email", "abc@gmail.com");
                //  Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    tmp=sb.toString();
                    return sb.toString();

                }
                else {
                    tmp="Error";
                }
            } catch (Exception e) {
                tmp=e.toString();
                // TODO Auto-generated catch block
            }
            return null;
        }

        protected void onPostExecute(String result){
            //  pb.setVisibility(View.GONE);
            // Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_LONG).show();

            String test="";

            //        Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_LONG).show();
            String score="", t1="",t2="",inn="", tem="";
//            int start;
            try {
                JSONObject c = new JSONObject(tmp);

                // Getting JSON Array node


                tem = c.getString("score");

                String[] splited = tem.split("\\s+");
                score=splited[1];
                t1 = c.getString("team-1");
                t2 = c.getString("team-2");
                inn=c.getString("innings-requirement");
                // test="Score : "+score+"\n team 1 : "+t1+"\n team 2 : "+t2+"\nInnings Requirement : "+inn;


            }
            catch(JSONException e)
            {
                Toast.makeText(getApplicationContext(), "Error Loading Values! Please Retry", Toast.LENGTH_LONG).show();
            }
            // Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
            TextView sc=(TextView) (findViewById(R.id.scard));
            //sc.setTextColor(Color.BLACK);
            sc.setText(tem.toString());
             sc=(TextView) (findViewById(R.id.scoreshort));
            //sc.setTextColor(Color.BLACK);
            sc.setText(score.toString());
            sc=(TextView) findViewById(R.id.teams);
            sc.setText(t1.toString()+" Vs. "+t2.toString());
//            sc=(TextView) (findViewById(R.id.teama));
//            //sc.setTextColor(Color.BLACK);
//            sc.setText(t1.toString());
//            sc=(TextView) (findViewById(R.id.teamb));
//            //sc.setTextColor(Color.BLACK);
//            sc.setText(t2.toString());
            sc=(TextView) (findViewById(R.id.innr));
            //sc.setTextColor(Color.BLACK);
            sc.setText(inn.toString());


        }

    }



    private class MyAsyncTaskComment extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                URL url = new URL("http://cricapi.com/api/cricketCommentary?"+"unique_id="+uid+"&apikey=jPRfOjW8GKcgEgYWK6Z7qVk9poB3"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                // postDataParams.put("apikey", " jPRfOjW8GKcgEgYWK6Z7qVk9poB3");
                //  postDataParams.put("email", "abc@gmail.com");
                //  Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    tmp=sb.toString();
                    return sb.toString();

                }
                else {
                    tmp="Error";
                }
            } catch (Exception e) {
                tmp=e.toString();
                // TODO Auto-generated catch block
            }
            return null;
        }

        protected void onPostExecute(String result){
            //  pb.setVisibility(View.GONE);
            // Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_LONG).show();

            String test="";

    //        Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_LONG).show();
            String score="", t1="",t2="",inn="";
//            int start;
            JSONObject comment=new JSONObject();
            try {
                JSONObject c = new JSONObject(tmp);
           //     comment = c.getJSONObject("commentary");
                score=c.getString("commentary");
                //score=c.get
                // Getting JSON Array node


                  //  score = comment.getString("score");
                  //  t1 = c.getString("team-1");
                  //  t2 = c.getString("team-2");
                  //  inn=c.getString("innings-requirement");
               // test="Score : "+score+"\n team 1 : "+t1+"\n team 2 : "+t2+"\nInnings Requirement : "+inn;


            }
            catch(JSONException e)
            {
                Toast.makeText(getApplicationContext(), "Error Loading Values! Please Retry", Toast.LENGTH_LONG).show();
            }
   //         Toast.makeText(getApplicationContext(), score.substring(score.length() - 150), Toast.LENGTH_LONG).show();
            WebView webview = (WebView) findViewById(R.id.comment_wv);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl("about:blank");
            String s="<head><style>.commentary-event p{color:#830b0b;} .commentary-event span{color:#830b0b;}</style><meta name='viewport' content='target-densityDpi=device-dpi'/></head>";
            webview.loadDataWithBaseURL("", s+score, "text/html", "UTF-8", "");
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    //use the param "view", and call getContentHeight in scrollTo
                    view.scrollTo(0, view.getContentHeight());
                }
            });
           // webview.clearView();


        }

    }
}
