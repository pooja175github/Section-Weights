package com.structure.weights;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.structure.weights.R;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculateWGT extends ActionBarActivity {
    String app_name = "Structure Weights";
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private AdView mAdView;
    static public final String MyPREFERENCES = "mypref";
    String spinner, kg_feet, kg_mtr, feet1, inch, size, type, meter, price, total_wt_feet, total_wt_meter, total_amt_format, total_amt1;
    TextView TextViewtyp, TextViewtype, TextViewsiz, TextViewsize, TextViewkg_fee, TextViewkg_feet, TextViewkg_met, TextViewkg_meter, TextViewMet, TextViewMeter, TextViewfee, textViewfeet, TextViewInc, textViewInch, textViewTotal, textViewMeter, textViewTotalWGT, TextViewTW;
    LinearLayout LinearLayoutKgfeet, LinearLayoutKgmtr, LinearLayoutMeter, LinearLayoutFeet, LinearLayoutInch, LinearLayoutWGT;
    Double total_amt, T_wt_feet, T_wt_meter, today_price;
Button TextViewBackHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_wgt);

        TextViewtyp = (TextView) findViewById(R.id.TextViewtyp);
        TextViewtype = (TextView) findViewById(R.id.TextViewtype);
        TextViewsiz = (TextView) findViewById(R.id.TextViewsiz);
        TextViewsize = (TextView) findViewById(R.id.TextViewsize);
        TextViewkg_fee = (TextView) findViewById(R.id.TextViewkg_fee);
        TextViewkg_feet = (TextView) findViewById(R.id.TextViewkg_feet);
        TextViewkg_met = (TextView) findViewById(R.id.TextViewkg_met);
        TextViewkg_meter = (TextView) findViewById(R.id.TextViewkg_meter);
        TextViewMet = (TextView) findViewById(R.id.TextViewMet);
        TextViewMeter = (TextView) findViewById(R.id.TextViewMeter);
        TextViewfee = (TextView) findViewById(R.id.TextViewfee);
        textViewfeet = (TextView) findViewById(R.id.textViewfeet);
        //TextViewInc = (TextView) findViewById(R.id.TextViewInc);
        textViewInch = (TextView) findViewById(R.id.textViewInch);
        textViewTotal = (TextView) findViewById(R.id.textViewTotal);
        textViewMeter = (TextView) findViewById(R.id.textViewMeter);
        TextViewTW = (TextView) findViewById(R.id.TextViewTW);
        textViewTotalWGT = (TextView) findViewById(R.id.textViewTotalWGT);
        TextViewBackHome = (Button) findViewById(R.id.TextViewBackHome);

        LinearLayoutKgfeet = (LinearLayout) findViewById(R.id.LinearLayoutKgfeet);
        LinearLayoutKgmtr = (LinearLayout) findViewById(R.id.LinearLayoutKgmtr);
        LinearLayoutMeter = (LinearLayout) findViewById(R.id.LinearLayoutMeter);
        LinearLayoutFeet = (LinearLayout) findViewById(R.id.LinearLayoutFeet);
        LinearLayoutInch = (LinearLayout) findViewById(R.id.LinearLayoutInch);
        LinearLayoutWGT = (LinearLayout) findViewById(R.id.LinearLayoutWGT);


        //GET THE VALUE FROM INTENT FOR FEET
        Intent i2 = getIntent();
        kg_feet = i2.getStringExtra("kg_feet");
        kg_mtr = i2.getStringExtra("kg_mtr");
        size = i2.getStringExtra("size");
        type = i2.getStringExtra("type");
        feet1 = i2.getStringExtra("feet");
        inch = i2.getStringExtra("inch");
        meter = i2.getStringExtra("meter");
        price = i2.getStringExtra("price");
        total_wt_feet = i2.getStringExtra("find_total");
        total_wt_meter = i2.getStringExtra("total_wt_meter");

        Log.e("shah", "pooja");
        Log.e("kg_feet", "" + kg_feet);
        Log.e("kg_mtr", "" + kg_mtr);
        Log.e("size", "" + size);
        Log.e("type", "" + type);
        Log.e("feet", "" + feet1);
        Log.e("inch", "" + inch);
        Log.e("meter", "" + meter);
        Log.e("price", "" + price);
        Log.e("find_total", "" + total_wt_feet);
        Log.e("total_wt_meter", "" + total_wt_meter);

       TextViewBackHome.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i1=new Intent(CalculateWGT.this,MainActivity.class);
               //i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
               finish();
               i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);

               startActivity(i1);
           }
       });

//DECIMAL FORMATE
        DecimalFormat df = new DecimalFormat("#.###");

        // Toolbar for actionbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //setting TITLE of actionbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(" " + app_name);
        mAdView = (AdView) findViewById(R.id.ad_view);
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();

        if(getString(R.string.adtype).equals("ON"))
        {
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mAdView.loadAd(adRequest);


        }
        else if(getString(R.string.adtype).equals("TEST"))
        {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice(deviceId)
                    .build();
            mAdView.loadAd(adRequest);


        }
        //Navigation drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);


        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.nav_header, mDrawerList, false);
        mDrawerList.addHeaderView(header, null, false);
        prepareNavigationDrawerItems();
        setAdapter();
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar,
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


        //GET THE VALUE OF SPINNER
        SharedPreferences sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        spinner = sp.getString("spinner", "abc");
        Log.e("spinner", "" + spinner);

        if (spinner.equals("Feet")) {


            LinearLayoutKgmtr.setVisibility(View.GONE);
            LinearLayoutMeter.setVisibility(View.GONE);
            LinearLayoutKgfeet.setVisibility(View.VISIBLE);
            LinearLayoutFeet.setVisibility(View.VISIBLE);
           // LinearLayoutInch.setVisibility(View.VISIBLE);
           // LinearLayoutInch.setVisibility(View.GONE);

            //CALCULATION
            //calculation Today's rate*total_wt_feet
            today_price = Double.parseDouble(price);
            T_wt_feet = Double.parseDouble(total_wt_feet);


            total_amt = T_wt_feet * today_price;
            Log.e("total amount of feet", "" + total_amt);

            total_amt_format = df.format(total_amt);


            TextViewtype.setText("" + type);
            TextViewsize.setText(size);
            TextViewkg_feet.setText(kg_feet);
            if(type.equals("M S SHEET") || type.equals("M S PLATE")) {
                TextViewkg_fee.setText("Kg/Sq.Feet");
            }
            else {
                TextViewkg_fee.setText("Kg/Feet");
            }
            textViewfeet.setText(feet1 + "'" + " " + inch + "''");
            textViewTotal.setText("Rs." + " " + " " + "" + total_amt_format);
            textViewTotalWGT.setText(total_wt_feet + " " + " " + "kg");

        } else {
            if (spinner.equals("Meter")) {
                LinearLayoutKgfeet.setVisibility(View.GONE);
                LinearLayoutFeet.setVisibility(View.GONE);
                //LinearLayoutInch.setVisibility(View.GONE);
                LinearLayoutKgmtr.setVisibility(View.VISIBLE);
                LinearLayoutMeter.setVisibility(View.VISIBLE);

                // CALCULATION
                //calculation = total_weight_meter*today's price


                T_wt_meter = Double.parseDouble(total_wt_meter);
                today_price = Double.parseDouble(price);
                total_amt = today_price * T_wt_meter;
                Log.e("total of meter", "" + total_amt);

                total_amt_format = df.format(total_amt);

                String position = meter.substring(0, 1);
                if (position.equals(".")) {
                    String position_1 = "0";
                    textViewMeter.setText(position_1 + meter);
                } else {
                    textViewMeter.setText(meter);
                }
                TextViewtype.setText("" + type);
                TextViewsize.setText(size);
                TextViewkg_meter.setText(kg_mtr);
                textViewTotal.setText("Rs." + " " + " " + total_amt_format);
                textViewTotalWGT.setText(total_wt_meter + " " + " " + "kg");
                if(type.equals("M S SHEET") || type.equals("M S PLATE")) {
                    TextViewkg_met.setText("Kg/Sq.Meter");
                }
                else {
                    TextViewkg_met.setText("Kg/Meter");
                }

            }
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
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
                    //ABOUT US
                    Intent i2 = new Intent(CalculateWGT.this, Drawer.class);

                    startActivity(i2);
                    break;

                case 2:
                    //CONTACT US
                    String os = System.getProperty("os.version"); // OS version
                    String version = android.os.Build.VERSION.SDK;     // API Level
                    String device = android.os.Build.DEVICE;           // Device
                    String model = android.os.Build.MODEL;           // Model
                    String product = android.os.Build.PRODUCT;          // Product


                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setType("plain/text");
                    sendIntent.setData(Uri.parse("structureweights@gmail.com"));
                    sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"structureweights@gmail.com"});
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Enquiry From Structure Weights App ");
                    //sendIntent.putExtra(Intent.EXTRA_TEXT, "Device Information:\nOs:" + os + "\nVersion:" + version + "\nDevice Model:" + model + "\nMessage:");

                    startActivity(sendIntent);

                    break;
                case 3:
                    //SHARE VIA
                    // [START custom_event]

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Hey,I found new way to calculate and estimate various steel structure please try out .You must download it.\nhttps://goo.gl/5ICNE1";
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Structure Weights");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    break;
                case 4:
                    //RATE US
                    Uri uri = Uri.parse("market://details?id=" + "com.structure.weights&hl=en");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {

                        startActivity(goToMarket);
                    } catch (Exception e) {
                        //  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case 5:
                    //LIKE US
                    String facebookUrl = "https://www.facebook.com/StructureWeights/";
                    try {
                        int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                        if (versionCode >= 3002850) {
                            Uri uri1 = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);

                            startActivity(new Intent(Intent.ACTION_VIEW, uri1));

                        } else {
                            finish();
                            // open the Facebook app using the old method (fb://profile/id or fb://page/id)
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/336227679757310")));
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        // Facebook is not installed. Open the browser

                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                    }
                    break;
                case 6:
                    //EVALUATION
                    Intent intent1 = new Intent(CalculateWGT.this, Evaluation.class);

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
    }

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

}
