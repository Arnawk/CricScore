package com.arnaw.cricscore;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import android.widget.LinearLayout.LayoutParams;


import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {



TextView tv[],tv1[],tv11[],tv12[];
    String tmp,tmp2,tmp3;
    int m_id[],oldm_id[],newm_id[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        TabHost mTabHost = (TabHost)findViewById(R.id.tabHost);
        mTabHost.setup();
        //Lets add the first Tab
        TabHost.TabSpec mSpec = mTabHost.newTabSpec("New Games");
        mSpec.setContent(R.id.first_Tab);
        mSpec.setIndicator("New Games");
        mTabHost.addTab(mSpec);
        //Lets add the second Tab
        mSpec = mTabHost.newTabSpec("Old Games");
        mSpec.setContent(R.id.second_Tab);
        mSpec.setIndicator("Old Games");
        mTabHost.addTab(mSpec);
        //Lets add the third Tab
        mSpec = mTabHost.newTabSpec("Calendar");
        mSpec.setContent(R.id.third_Tab);
        mSpec.setIndicator("Time Line");
        mTabHost.addTab(mSpec);



        new NewsTask().execute();
        new MyAsyncTask().execute();

        new OldTask().execute();
        new CalendarTask().execute();

    }




    View.OnClickListener onclicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
//            if(v == tv1[0]){
//                //do whatever you want....
//            }

            for(int i=0;i<tv1.length;i++)
            {
                //Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
                if(v==tv1[i])
                {
                 //   Toast.makeText(getApplicationContext(), Integer.toString(m_id[i]), Toast.LENGTH_SHORT).show();
                    Intent inten = new Intent(MainActivity.this,ScoreActivity.class);
                    inten.putExtra("uid", Integer.toString(m_id[i]));
                    startActivity(inten);
                }
            }
        }
    };

    View.OnClickListener onclicklistener2 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
//            if(v == tv1[0]){
//                //do whatever you want....
//            }

            for(int i=0;i<tv11.length;i++)
            {
                if(v==tv11[i])
                {
                    // Toast.makeText(getApplicationContext(), Integer.toString(m_id[i]), Toast.LENGTH_LONG).show();
                    Intent inten = new Intent(MainActivity.this,ScoreActivity.class);
                    inten.putExtra("uid", Integer.toString(oldm_id[i]));
                    startActivity(inten);
                }
            }
        }
    };

    View.OnClickListener onclicklistener3 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
//            if(v == tv1[0]){
//                //do whatever you want....
//            }

            for(int i=0;i<tv12.length;i++)
            {
                if(v==tv12[i])
                {
                    // Toast.makeText(getApplicationContext(), Integer.toString(m_id[i]), Toast.LENGTH_LONG).show();
                    Intent inten = new Intent(MainActivity.this,ScoreActivity.class);
                    inten.putExtra("uid", Integer.toString(newm_id[i]));
                    startActivity(inten);
                }
            }
        }
    };







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

                URL url = new URL("http://cricapi.com/api/matches?apikey=jPRfOjW8GKcgEgYWK6Z7qVk9poB3"); // here is your URL path

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


            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.InflatorLayout);

            TextView loaddone;
            String uid="", t1="",t2="",started, date,tmper;
//            int start;
            try {
                JSONObject jsonObj = new JSONObject(tmp);

                // Getting JSON Array node
                JSONArray matches = jsonObj.getJSONArray("matches");
                tv1= new TextView[matches.length()];
                m_id=new int[matches.length()];


                loaddone=(TextView)findViewById(R.id.loading);
                loaddone.setText("Latest News : ");

                loaddone=(TextView)findViewById(R.id.data);
                loaddone.setText("Current Matches : ");

                for (int i = 0; i < matches.length(); i++) {
                    JSONObject c = matches.getJSONObject(i);

                     uid = c.getString("unique_id");
                    m_id[i]=Integer.parseInt(uid);
                     t1 = c.getString("team-1");
                     t2 = c.getString("team-2");
                    date=c.getString("date");
                    started=c.getString("matchStarted");
                    if(started.equals("true"))
                        started="Game Started";
                    else
                        started="Game Not Started";



                    String htm;
                    htm="<h1 style=\"color : #4d4d4d; font-size : 110%\">"+t1+" Vs. "+t2+"</h1><p>"+started+"</p>";
                    tv1[i] = new TextView(MainActivity.this);
                    tv1[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        tv1[i].setText(Html.fromHtml(htm, Html.FROM_HTML_MODE_LEGACY)); }
                    else
                    { tv1[i].setText(Html.fromHtml(htm)); }
//                    tv1[i].setText(t1+" vs. "+t2+"\n Date of match : "+date+"\n "+started);
                    tv1[i].setBackgroundColor(0x000000); // hex color 0xAARRGGBB
                    tv1[i].setPadding(10, 10, 10, 10);// in pixels (left, top, right, bottom)
                    tv1[i].setMaxLines(5);

                    tv1[i].setOnClickListener(onclicklistener);


                    linearLayout.addView(tv1[i]);

                }


               // tv = (TextView) (findViewById(R.id.tv));
               // tv.setText("id : "+uid+" team 1 : "+t1+" team 2 : "+t2);
                // return tmp;

            }
            catch(JSONException e)
            {
                Toast.makeText(getApplicationContext(), "Error Loading Values! Please Retry", Toast.LENGTH_LONG).show();
            }
        }

    }


    private class OldTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                URL url = new URL("http://cricapi.com/api/cricket?apikey=jPRfOjW8GKcgEgYWK6Z7qVk9poB3"); // here is your URL path

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
                    tmp2=sb.toString();
                    return sb.toString();

                }
                else {
                    tmp2="Error";
                }
            } catch (Exception e) {
                tmp2=e.toString();
                // TODO Auto-generated catch block
            }
            return null;
        }

        protected void onPostExecute(String result){
            //  pb.setVisibility(View.GONE);
            // Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_LONG).show();


            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.InflatorLayoutOld);

            TextView loaddone;
            String uid="", t1="",t2="",started, date,tmper;
//            int start;
            try {
                JSONObject jsonObj = new JSONObject(tmp2);

                // Getting JSON Array node
                JSONArray matches = jsonObj.getJSONArray("data");
                tv11= new TextView[matches.length()];
                oldm_id=new int[matches.length()];




                for (int i = 0; i < matches.length(); i++) {
                    JSONObject c = matches.getJSONObject(i);

                    uid = c.getString("unique_id");
                    oldm_id[i]=Integer.parseInt(uid);

                    t2 = c.getString("description");
                    date=c.getString("title");
                    String htm;
                    htm="<h1 style=\"color : #4d4d4d; font-size : 110%\">"+t2+"</h1><p>"+date+"</p>";
                    tv11[i] = new TextView(MainActivity.this);
                    tv11[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        tv11[i].setText(Html.fromHtml(htm, Html.FROM_HTML_MODE_LEGACY)); }
                    else
                    { tv11[i].setText(Html.fromHtml(htm)); }
//                    tv11[i].setText(t1+" vs. "+t2+"\n Date of match : "+date+"\n "+started);
                    tv11[i].setBackgroundColor(0x000000); // hex color 0xAARRGGBB
                    tv11[i].setPadding(10, 10, 10, 10);// in pixels (left, top, right, bottom)
                    tv11[i].setMaxLines(5);

                    tv11[i].setOnClickListener(onclicklistener2);


                    linearLayout.addView(tv11[i]);

                }


                // tv = (TextView) (findViewById(R.id.tv));
                // tv.setText("id : "+uid+" team 1 : "+t1+" team 2 : "+t2);
                // return tmp;

            }
            catch(JSONException e)
            {
                Toast.makeText(getApplicationContext(), "Error Loading Values! Please Retry", Toast.LENGTH_LONG).show();
            }
        }

    }

    private class CalendarTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                URL url = new URL("http://cricapi.com/api/matchCalendar?apikey=jPRfOjW8GKcgEgYWK6Z7qVk9poB3"); // here is your URL path

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
                    tmp3=sb.toString();
                    return sb.toString();

                }
                else {
                    tmp3="Error";
                }
            } catch (Exception e) {
                tmp3=e.toString();
                // TODO Auto-generated catch block
            }
            return null;
        }

        protected void onPostExecute(String result){
            //  pb.setVisibility(View.GONE);
            // Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_LONG).show();


            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.InflatorLayoutCalendar);

            TextView loaddone;
            String uid="", t1="",t2="",started, date,tmper;
//            int start;
            try {
                JSONObject jsonObj = new JSONObject(tmp3);

                // Getting JSON Array node
                JSONArray matches = jsonObj.getJSONArray("data");
                tv12= new TextView[matches.length()];
                newm_id=new int[matches.length()];




                for (int i = 0; i < matches.length(); i++) {
                    JSONObject c = matches.getJSONObject(i);

                    uid = c.getString("unique_id");
                    if(uid.matches("[-+]?\\d*\\.?\\d+"))
                    {
                        newm_id[i]=Integer.parseInt(uid);
                    }


                    date=c.getString("date");
                    started=c.getString("name");




                    String htm;
                    htm="<h1 style=\"color : #4d4d4d; font-size : 110%\">"+started+"</h1><p>"+date+"</p>";
                    tv12[i] = new TextView(MainActivity.this);
                    tv12[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        tv12[i].setText(Html.fromHtml(htm, Html.FROM_HTML_MODE_LEGACY)); }
                    else
                    { tv12[i].setText(Html.fromHtml(htm)); }
//                    tv12[i].setText(t1+" vs. "+t2+"\n Date of match : "+date+"\n "+started);
                    tv12[i].setBackgroundColor(0x000000); // hex color 0xAARRGGBB
                    tv12[i].setPadding(10, 10, 10, 10);// in pixels (left, top, right, bottom)
                    tv12[i].setMaxLines(5);

                    if(uid.matches("[-+]?\\d*\\.?\\d+")) {
                        tv12[i].setOnClickListener(onclicklistener3);
                    }

                    linearLayout.addView(tv12[i]);

                }


                // tv = (TextView) (findViewById(R.id.tv));
                // tv.setText("id : "+uid+" team 1 : "+t1+" team 2 : "+t2);
                // return tmp;

            }
            catch(JSONException e)
            {
                Toast.makeText(getApplicationContext(), "Error Loading Values! Please Retry", Toast.LENGTH_LONG).show();
            }
        }

    }


    private class NewsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                URL url = new URL("http://cricapi.com/api/cricketNews?apikey=jPRfOjW8GKcgEgYWK6Z7qVk9poB3"); // here is your URL path

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


            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.InflatorLayoutNews);


            String uid="", title="",description="",started, date,tmper;
//            int start;
            try {
                JSONObject jsonObj = new JSONObject(tmp);

                // Getting JSON Array node
                JSONArray data = jsonObj.getJSONArray("data");
                tv= new TextView[data.length()];
                //m_id=new int[data.length()];

                for (int i = 0; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);

                    uid = c.getString("unique_id");
                    //m_id[i]=Integer.parseInt(uid);
                    title = c.getString("title");
                    description = c.getString("description");


                    String htm;
                    htm="<h1 style=\"color : #1e1e1e; font-size : 110%\">"+title+"</h1><p>"+description+"</p>";
                    tv[i] = new TextView(MainActivity.this);

                    int width_full;
//                    int Measuredwidth = 0;
//                    int Measuredheight = 0;
//                    Point size = new Point();
//                    WindowManager w = getWindowManager();
//
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)    {
//                        w.getDefaultDisplay().getSize(size);
//                        Measuredwidth = size.x;
//                        Measuredheight = size.y;
//                    }else{
//                        Display d = w.getDefaultDisplay();
//                        Measuredwidth = d.getWidth();
//                        Measuredheight = d.getHeight();
//                    }
//                    width_full=Measuredwidth;
//                    width_full=width_full-52;

                    LinearLayout ll2=(LinearLayout)findViewById(R.id.InflatorLayout);
                    width_full=ll2.getWidth();
                    width_full=width_full-20;
                 //   Toast.makeText(getApplicationContext(), Integer.toString(width_full), Toast.LENGTH_SHORT).show();

                    float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
                    tv[i].setLayoutParams(new LayoutParams((int)px, LayoutParams.WRAP_CONTENT));

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        tv[i].setText(Html.fromHtml(htm, Html.FROM_HTML_MODE_LEGACY)); }
                    else
                    { tv[i].setText(Html.fromHtml(htm)); }
//                    tv[i].setText(t1+" vs. "+t2+"\n Date of match : "+date+"\n "+started);
                    tv[i].setBackgroundColor(0x000000); // hex color 0xAARRGGBB
                    tv[i].setPadding(10, 10, 10, 10);// in pixels (left, top, right, bottom)
                    tv[i].setMaxLines(10);

                    //tv1[i].setOnClickListener(onclicklistener);


                    linearLayout.addView(tv[i]);

                }


                // tv = (TextView) (findViewById(R.id.tv));
                // tv.setText("id : "+uid+" team 1 : "+t1+" team 2 : "+t2);
                // return tmp;

            }
            catch(JSONException e)
            {
                Toast.makeText(getApplicationContext(), "Error Loading Values! Please Retry", Toast.LENGTH_LONG).show();
            }
        }

    }

}
