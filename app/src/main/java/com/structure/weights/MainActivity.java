package com.structure.weights;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.structure.weights.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int counter=0;
    InterstitialAd iad ;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    Spinner sp1;
    ConnectionDetector cd;
    Spinner sp2;
    TextView feet;
    TextView mtr;
    String weightpermtr;
    String weightperfeet, guage;
    String size;
    TextView image;
    TextView wpermtr, TextViewKGMETER, TextViewKGFEET;
    Button chkamt;
    String IPAdd = "192.168.1.163";
    private AdView mAdView;
    ArrayList<String> arrayListtype = new ArrayList<>();
    ArrayList<String> arrayListsize = new ArrayList<>();
    ArrayList<String> arrayListid = new ArrayList<>();
    ArrayList<String> arrayListmtr = new ArrayList<>();
    ArrayList<String> arrayListfeet = new ArrayList<>();
    ArrayList<String> arrayListguage = new ArrayList<>();
    ProgressDialog pdia;
    String t;
    String s;
    String app_name = "Structure Weights";
    LinearLayout layout, MainView;

    Boolean isInternetPresent;

    static public String permtr;


    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public JSONObject makeHttpRequest2(String url, String method,
                                       List<NameValuePair> params) {
        InputStream is = null;
        JSONObject jObj = null;
        String json = "";
        JSONArray jArr = null;

        // Making HTTP request
        try {

            // check for request method
            if (method.equals("POST")) {
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } else if (method.equals("GET")) {
                // request method is GET
                HttpParams params1 = new BasicHttpParams();
                params1.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
                DefaultHttpClient httpClient = new DefaultHttpClient(params1);
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Log.e("first", "Firs");

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();

            Log.e("json", json);

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }

    @Override
    public void onClick(View v) {


        //VALIDATION ON TYPE AND SIZE

         if (!isInternetPresent) {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(MainActivity.this, "You don't have internet connection.",
                    "Data Not Loaded", false);

        }

else if(sp1.getSelectedItem().toString().equals("Select Type") || sp2.getSelectedItem().toString().equals("Select Size")) {
        Toast.makeText(getApplicationContext(), "Select value", Toast.LENGTH_SHORT).show();

        }

else if (TextViewKGFEET.getText().toString().equals("Select Meter")){
            Toast.makeText(getApplicationContext(), "Select value", Toast.LENGTH_SHORT).show();
        }
//sp1.getSelectedItem().toString();
           else {
                Intent intent = new Intent(MainActivity.this, FinalAmount.class);
                String tp = intent.putExtra("type", t).toString();


                intent.putExtra("size", s);
                intent.putExtra("kg_mtr", arrayListmtr.get(sp2.getSelectedItemPosition()).toString());
                intent.putExtra("kg_feet", arrayListfeet.get(sp2.getSelectedItemPosition()).toString());
                intent.putExtra("guage", arrayListguage.get(sp2.getSelectedItemPosition()).toString());

                intent.putExtra("type", t);
                Log.e("weightpermtr ::::::", "" + arrayListmtr.get(sp2.getSelectedItemPosition()).toString());
                Log.e("weightperfeet ::::::", "" + arrayListfeet.get(sp2.getSelectedItemPosition()).toString());
                Log.e("guage ::::::", "" + arrayListguage.get(sp2.getSelectedItemPosition()).toString());
                startActivity(intent);
        }
        if (iad.isLoaded()) {
            iad.show();
        }

    }

    class DoCategory extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                Log.d("docategory", "called");
                //JSONObject obj = makeHttpRequest2("http://" + IPAdd + "/poojatest/android_webservice.php?action=selecttype", "POST", data);
                JSONObject obj = makeHttpRequest2("http://jayshenmare.info/ashish/android_webservice.php?action=selecttype", "POST", data);
                Log.d("url", "http://jayshenmare.info/ashish/android_webservice.php?action=selecttype");
                Log.d("Json data:",
                        "" + obj.toString());
                JSONArray json2 = obj.getJSONArray("data");
                Log.d("Json data 1:", "" + json2);
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject commentObject = json2.getJSONObject(i);


                    String type = commentObject.getString("Name").toString();
                    String id = commentObject.getString("id").toString();

                    arrayListtype.add(type);
                    arrayListid.add(id);


                }

                arrayListtype.add(0,"Select Type");
               // arrayListsize.add(0,"Select Size");
                arrayListid.add(0,"Select Type");

                return "true";

            } catch (Exception e) {
                Log.d("error", e.toString());

                return "fail";

            }


        }


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            pdia.show();

            super.onPreExecute();

        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(MainActivity.this, R.layout.simple_list_item_2, arrayListtype){

            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if(position == 0){
                        // Set the hint text color gray
                        tv.setTextColor(getResources().getColor(R.color.grey));
                    }
                    else {
                        tv.setTextColor(getResources().getColor(R.color.textcolour));
                    }
                    return view;
                }
            };

            adapter_state1
                    .setDropDownViewResource(R.layout.simple_list_item_1);

            sp1.setAdapter(adapter_state1);
            pdia.dismiss();

        }

    }


    class SpinnerSecond extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub

            try {
                ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
                Log.e("URL SELECT SIZE", "http://jayshenmare.info/ashish/android_webservice.php?action=selectsize&typ=" + arrayListid.get(sp1.getSelectedItemPosition()).toString() + "");
                //JSONObject obj = makeHttpRequest2("http://" + IPAdd + "/poojatest/android_webservice.php?action=selectsize&typ=" + arrayListid.get(sp1.getSelectedItemPosition()).toString() + "", "POST", data);
                JSONObject obj = makeHttpRequest2("http://jayshenmare.info/ashish/android_webservice.php?action=selectsize&typ=" + arrayListid.get(sp1.getSelectedItemPosition()).toString() + "", "POST", data);


                JSONArray json2 = obj.getJSONArray("data");
                Log.e("json2:", "" + json2);
                arrayListsize.clear();
                arrayListmtr.clear();
                arrayListfeet.clear();
                arrayListguage.clear();
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject commentObject = json2.getJSONObject(i);


                    size = commentObject.getString("Size").toString().replace("&quot;", "\"");
                    weightpermtr = commentObject.getString("weightPerMtr").toString().replace("&quot;", "\"");
                    weightperfeet = commentObject.getString("weightPerFeet").toString();
                    guage = commentObject.getString("guage").toString();
                    if (!guage.equals("")) {

                        guage = " / " + guage;
                    }


                    Log.e("weightpermtr13", "" + weightpermtr);
                    Log.e("weightperfeet13", "" + weightperfeet);
                    Log.e("guage13", "" + guage);
                    arrayListfeet.add(weightperfeet);
                    arrayListsize.add(size + guage);
                    arrayListmtr.add(weightpermtr);
                    arrayListguage.add(guage);


                }
arrayListsize.add(0,"Select Size");
arrayListmtr.add(0,"Select Meter");
arrayListfeet.add(0,"Select Feet");
arrayListguage.add(0,"Select Guage");

                return "true";

            } catch (Exception e) {
                Log.d("error", e.toString());

                return "fail";

            }

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            ArrayAdapter<String> adapter_state2 = new ArrayAdapter<String>(MainActivity.this, R.layout.simple_list_item_3, arrayListsize){

                @Override
                public boolean isEnabled(int position){
                    if(position == 0)
                    {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    }
                    else
                    {
                        return true;
                    }
                }
                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if(position == 0){
                        // Set the hint text color gray
                        tv.setTextColor(getResources().getColor(R.color.grey));
                    }
                    else {
                        tv.setTextColor(getResources().getColor(R.color.textcolour));
                    }
                    return view;
                }
            };

            adapter_state2
                    .setDropDownViewResource(R.layout.simple_list_item_1);

            sp2.setAdapter(adapter_state2);
            pdia.dismiss();


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia.show();

        }
    }


    class Lable extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

                Log.e("weightpermtr", "http://" + IPAdd + "/poojatest/android_webservice.php?action=selectsize1&typ=" + arrayListid.get(sp2.getSelectedItemPosition()).toString() + "&size=" + arrayListsize.get(sp2.getSelectedItemPosition()).toString() + "");
                //JSONObject obj = makeHttpRequest2("http://" + IPAdd + "/poojatest/android_webservice.php?action=selectsize1&typ=" + arrayListid.get(sp2.getSelectedItemPosition()).toString() + "&size=" + arrayListsize.get(sp2.getSelectedItemPosition()).toString() + "", "POST", data);
                JSONObject obj = makeHttpRequest2("http://jayshenmare.info/ashish/android_webservice.php?action=selectsize1&typ=" + arrayListid.get(sp2.getSelectedItemPosition()).toString() + "&size=" + arrayListsize.get(sp2.getSelectedItemPosition()).toString() + "", "POST", data);

                Log.e("weightpermtr12", "http://" + IPAdd + "/poojatest/android_webservice.php?action=selectsize1&typ=" + arrayListid.get(sp2.getSelectedItemPosition()).toString() + "&size=" + arrayListsize.get(sp2.getSelectedItemPosition()).toString() + "");
                Log.e("weightpermtr23:", arrayListsize.get(sp2.getSelectedItemPosition()).toString() + "");

                JSONArray json2 = obj.getJSONArray("data");
                Log.e("json2:", "" + json2);
                arrayListsize.clear();
                arrayListmtr.clear();
                arrayListguage.clear();
                String weightpermtr = "";
                String guage = "";
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject commentObject = json2.getJSONObject(i);


                    String size = commentObject.getString("Size").toString().replace("&quot", "\"");
                    weightpermtr = commentObject.getString("weightPerMtr").toString();
                    guage = commentObject.getString("guage").toString();


                    Log.e("weightpermtr :", "" + weightpermtr);
                    Log.e("guage :", "" + guage);


                }

                return weightpermtr;

            } catch (Exception e) {
                Log.d("error", e.toString());

                return "fail";

            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdia.show();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdia.dismiss();
            mtr.setText(s);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp2 = (Spinner) findViewById(R.id.spinner2);
        mtr = (TextView) findViewById(R.id.wpermtr);
        feet = (TextView) findViewById(R.id.wperfeet);
        chkamt = (Button) findViewById(R.id.chkamt);
        image = (TextView) findViewById(R.id.image);
        MainView = (LinearLayout) findViewById(R.id.MainView);
        mAdView = (AdView) findViewById(R.id.ad_view);
        TextViewKGMETER = (TextView) findViewById(R.id.TextViewKG);
        TextViewKGFEET = (TextView) findViewById(R.id.TextViewKGFEET);

        iad = new InterstitialAd(this);
        iad.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        requestNewInterstitial();
        iad.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

                if (!isInternetPresent) {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(MainActivity.this, "No Internet Connection",
                            "You don't have internet connection.", false);


                }
            }
        });



        pdia = new ProgressDialog(MainActivity.this);
        pdia.setMessage("loading");
        pdia.setCanceledOnTouchOutside(false);
        pdia.setCancelable(false);


        arrayListsize.add(0,"Select Size");
        ArrayAdapter<String> adapter_state2 = new ArrayAdapter<String>(MainActivity.this, R.layout.simple_list_item_3, arrayListsize);

        adapter_state2
                .setDropDownViewResource(R.layout.simple_list_item_1);

        sp2.setAdapter(adapter_state2);
        if(getString(R.string.adtype).equals("TEST"))
            {

                String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                String deviceId = md5(android_id).toUpperCase();
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice(deviceId)
                        .build();
                mAdView.loadAd(adRequest);

            }
        else if(getString(R.string.adtype).equals("ON"))
            {

                String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                String deviceId = md5(android_id).toUpperCase();
                AdRequest adRequest = new AdRequest.Builder()
                        .build();
                mAdView.loadAd(adRequest);

            }

//Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.nav_header, mDrawerList, false);
        mDrawerList.addHeaderView(header, null, false);
        prepareNavigationDrawerItems();
        setAdapter();
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    protected void onResume() {
        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    private void prepareNavigationDrawerItems() {
        mDrawerItems = new ArrayList<>();
        //DrawerItem(id,image,name);
        mDrawerItems.add(new DrawerItem("0", R.drawable.home_52, "Home"));
        mDrawerItems.add(new DrawerItem("1", R.drawable.reply_52, "Contact us"));
        mDrawerItems.add(new DrawerItem("2", R.drawable.share_52, "Share Via"));
        mDrawerItems.add(new DrawerItem("3", R.drawable.rating_filled_50, "Rate Us"));
        mDrawerItems.add(new DrawerItem("4", R.drawable.like_filled_50, "Like us"));
        mDrawerItems.add(new DrawerItem("5", R.drawable.calculator_fille_50, "Evaluation"));
        Log.d("drawer items", mDrawerItems.get(1).getTitle());

    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id1) {


            switch (position) {


                case 1:
                    // HOME
                    Intent i1 = new Intent(MainActivity.this, Drawer.class);
                    startActivity(i1);
                    break;


                case 2:
                    // CONTACT US
                    String os = System.getProperty("os.version"); // OS version
                    String version = android.os.Build.VERSION.SDK;     // API Level
                    String device = android.os.Build.DEVICE;           // Device
                    String model = android.os.Build.MODEL;           // Model
                    String product = android.os.Build.PRODUCT;          // Product


                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setType("plain/text");
                    sendIntent.setData(Uri.parse("structureweights@gmail.com"));//TO
                    sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"structureweights@gmail.com"});//TO
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Enquiry From Structure Weights App ");
                    //sendIntent.putExtra(Intent.EXTRA_TEXT, "Device Information:\nOs:"+os+"\nVersion:"+version+"\nDevice Model:"+model+"\nMessage:");

                    startActivity(sendIntent);

                    break;
                case 3:
                    // [START custom_event]
                    // SHARED VIA

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Hey,I found new way to calculate and estimate various steel structure please try out.\nhttps://goo.gl/5ICNE1";
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Structure Weights");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    break;
                case 4:
                    // RATE US
                    Uri uri = Uri.parse("market://details?id=" + "com.structure.weights&hl=en");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {

                        startActivity(goToMarket);
                    } catch (Exception e) {
                        //  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case 5:
                    // LIKE US
                    String facebookUrl = "https://www.facebook.com/StructureWeights/";
                    try {
                        int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                        if (versionCode >= 3002850) {
                            Uri uri1 = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);

                            startActivity(new Intent(Intent.ACTION_VIEW, uri1));
                            ;
                        } else {

                            // open the Facebook app using the old method (fb://profile/id or fb://page/id)
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/336227679757310")));
                        }
                    } catch (PackageManager.NameNotFoundException e) {

                        // Facebook is not installed. Open the browser
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                    }
                    break;
                case 6:
                    // EVALUATION
                    Intent intent1 = new Intent(MainActivity.this, Evaluation.class);

                    startActivity(intent1);
                    break;


            }
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setAdapter() {


        boolean isFirstType = true;
        Log.d("drawer items", mDrawerItems.get(1).getTitle());

        BaseAdapter adapter = new DrawerAdapter_home(this, mDrawerItems, isFirstType);

        mDrawerList.setAdapter(adapter);


//check  INTERNET connectivity
        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet(); // true or false

        // check for Internet status
        if (isInternetPresent) {
            // Internet Connection is Present
            // make HTTP requests

            //Toast.makeText(getApplication(), "Internet Connection is Present", Toast.LENGTH_SHORT).show();

        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            //Toast.makeText(getApplication(), "Internet Connection is not Present ", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setMessage("No Internet Connection");
            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            // Create the AlertDialog object and return it
            alertDialogBuilder.create();
            alertDialogBuilder.show();

        }


        //Toolbar for actionBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //setting TITLE of actionbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();

        ab.setTitle(" " + app_name);


        chkamt.setOnClickListener(this);
        new DoCategory().execute();


        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Toast.makeText(getApplicationContext(),"First Called",Toast.LENGTH_LONG).show();

                t = sp1.getSelectedItem().toString();
                Log.e("Selected Type", "" + t);

                if(t.equals("Select"))
                {
                    sp2.setPrompt("Select Size");
                }
                if (t.equals("M S SHEET") || t.equals("M S PLATE")) {
                    TextViewKGMETER.setText("Kg/Sq.Meter");
                    TextViewKGFEET.setText("Kg/Sq.Feet");

                    new SpinnerSecond().execute();
                    counter++;
                    Log.e("counter's value",""+counter);
                    if((counter)%4==0) {
                        Log.e("here","show ad");

                        if (iad.isLoaded()) {
                            iad.show();
                        }
                    }

                } else {
                    TextViewKGMETER.setText("Kg/Meter");
                    TextViewKGFEET.setText("Kg/Feet");
                    new SpinnerSecond().execute();
                    counter++;
                    Log.e("counter's value",""+counter);
                    if((counter)%4==0) {
                        Log.e("here","show ad");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (iad.isLoaded()) {
                                    iad.show();
                                    Log.d("ad load", "true");
                                    Log.d("ad load", "failed");
                            }
                        }
                    }, 5000);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(sp2.getSelectedItemPosition()!=0) {
                    mtr.setText(arrayListmtr.get(sp2.getSelectedItemPosition()).toString());
                    permtr = arrayListmtr.get(sp2.getSelectedItemPosition()).toString();
                    feet.setText(arrayListfeet.get(sp2.getSelectedItemPosition()).toString());
                    s = sp2.getSelectedItem().toString();
                    Log.e("Size :", "" + s);
                    counter++;
                    Log.e("counter's value",""+counter);
                    if((counter-1)%4==0) {
                        Log.e("here","show ad");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (iad.isLoaded()) {
                                    iad.show();
                                    Log.d("ad load", "true");
                                    Log.d("ad load", "failed");
                            }
                        }
                    }, 5000);
                    }

                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {

            Intent i2 = new Intent(MainActivity.this, Drawer.class);
            i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i2);
            super.onBackPressed();


    }

    private void displayMtrInformation(int count) {
        wpermtr = (TextView) findViewById(R.id.wpermtr);

        wpermtr.setText(weightpermtr);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
    private void requestNewInterstitial() {
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();

        if (getString(R.string.adtype).equals("TEST")) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(deviceId)
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();

            iad.loadAd(adRequest);
        } else if (getString(R.string.adtype).equals("ON")) {
            AdRequest adRequest = new AdRequest.Builder()
                    .build();

            iad.loadAd(adRequest);

        }
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog.Builder alertDialogbuilder = new AlertDialog.Builder(context);

        alertDialogbuilder.setTitle(title);
        alertDialogbuilder.setMessage(message);
        alertDialogbuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        alertDialogbuilder.setIcon(android.R.drawable.ic_dialog_alert);

        AlertDialog obj = alertDialogbuilder.create();
        obj.show();

        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon(android.R.drawable.ic_delete);


    }


}


