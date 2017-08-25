package com.structure.weights;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.structure.weights.R;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class TotalAmt extends AppCompatActivity {


    TextView textViewTotal;
    TextView TextViewsize;
    TextView TextViewtype;
    TextView TextViewInch;
    TextView textViewfeet;
    String app_name = "Structure Weights";
    TextView TextViewF;
    String s, s1, s2;
    String spinner;
    String type;
    String size;
    String kgmtr;
    String kgfeet;
    String feet;
    String inch;
    TextView textViewMeter;
    TextView TextViewMeter;
    TextView TextViewfeet;
    TextView textViewInch;
    String m, total_wt_meter;
    String total, calWGT;
    TextView image;
    LinearLayout LinearLayoutFeet;
    LinearLayout LinearLayoutInch;
    LinearLayout LinearLayoutKgfeet;
    LinearLayout LinearLayoutKgmtr;
    LinearLayout LinearLayoutMeter;
    LinearLayout LinearLayoutPrice;
    LinearLayout LinearLayoutSize;
    LinearLayout LinearLayoutType;
    LinearLayout LinearLayoutUnit;
    EditText EditTextMeter;
    TextView Textf;
    TextView Textmtr, mtr;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    Button AMT;
    static public final String MyPREFERENCES = "mypref";
    private AdView mAdView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_amt);


        //textViewS = (TextView) findViewById(R.id.textViewS);
        textViewTotal = (TextView) findViewById(R.id.textViewTotal);
        TextViewtype = (TextView) findViewById(R.id.TextViewtype);
        TextViewsize = (TextView) findViewById(R.id.TextViewsize);
        TextViewMeter = (TextView) findViewById(R.id.TextViewMeter);
        textViewMeter = (TextView) findViewById(R.id.textViewMeter);
        TextViewfeet = (TextView) findViewById(R.id.TextViewfeet);
        textViewfeet = (TextView) findViewById(R.id.textViewfeet);
        TextViewInch = (TextView) findViewById(R.id.TextViewInch);
        textViewInch = (TextView) findViewById(R.id.textViewInch);
        LinearLayoutFeet = (LinearLayout) findViewById(R.id.LinearLayoutFeet);
        LinearLayoutInch = (LinearLayout) findViewById(R.id.LinearLayoutInch);
        LinearLayoutKgfeet = (LinearLayout) findViewById(R.id.LinearLayoutKgfeet);
        LinearLayoutKgmtr = (LinearLayout) findViewById(R.id.LinearLayoutKgmtr);
        LinearLayoutMeter = (LinearLayout) findViewById(R.id.LinearLayoutMeter);
        LinearLayoutPrice = (LinearLayout) findViewById(R.id.LinearLayoutPrice);
        LinearLayoutSize = (LinearLayout) findViewById(R.id.LinearLayoutSize);
        LinearLayoutType = (LinearLayout) findViewById(R.id.LinearLayoutType);
        LinearLayoutUnit = (LinearLayout) findViewById(R.id.LinearLayoutUnit);
        image = (TextView) findViewById(R.id.image);
        EditTextMeter = (EditText) findViewById(R.id.EditTextMeter);
        Textf = (TextView) findViewById(R.id.Textf);
        Textmtr = (TextView) findViewById(R.id.Textmtr);
        mtr = (TextView) findViewById(R.id.mtr);
        TextViewF = (TextView) findViewById(R.id.TextViewF);
        AMT = (Button) findViewById(R.id.AMT);

        //GET THE VALUE FROM INTENT
        Intent intent = getIntent();
        final String kg_feet = intent.getStringExtra("kg_feet");
        final String kg_mtr = intent.getStringExtra("kg_mtr");
        final String type = intent.getStringExtra("type");
        final String size = intent.getStringExtra("size");
        final String feet = intent.getStringExtra("feet");
        final String inch = intent.getStringExtra("inch");
        final String meter = intent.getStringExtra("IntM");
        final String find_total = intent.getStringExtra("find_total");
        final String total_wt_meter = intent.getStringExtra("total_wt_meter");


        Log.e("kg_feet", "" + kg_feet);
        Log.e("kg_mtr", "" + kg_mtr);
        Log.e("type", "" + type);
        Log.e("size", "" + size);
        Log.e("feet", "" + feet);
        Log.e("inch", "" + inch);
        Log.e("meter", "" + meter);
        Log.e("find_total", "" + find_total);
        Log.e("total_wt_meter", "" + total_wt_meter);
        Log.e("shah", "pooja shah");
        AMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ALERT DIALOGBOX
                // get prompt.xml view
                LayoutInflater li = LayoutInflater.from(TotalAmt.this);
                View promptsView = li.inflate(R.layout.prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        TotalAmt.this);
                alertDialogBuilder.setTitle("Enter Today's Price (\u20B9)");
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        calWGT = userInput.getText().toString();

                                        //VALIDATION ON EDITTEXT_TODAY'S PRICE
                                        if (calWGT.equals("")) {

                                            Toast.makeText(TotalAmt.this, "Please Enter Today's Price", Toast.LENGTH_SHORT).show();

                                        } else if (calWGT.substring(0, 1).equals(".")) {
                                            String zero = "0";
                                            calWGT = zero + calWGT;
                                            Log.e("cal:-", "" + calWGT);
                                            //PUT VALUE IN INTENT
                                            Intent i1 = new Intent(TotalAmt.this, CalculateWGT.class);
                                            i1.putExtra("kg_feet", "" + kg_feet);
                                            i1.putExtra("kg_mtr", "" + kg_mtr);
                                            i1.putExtra("size", "" + size);
                                            i1.putExtra("type", "" + type);
                                            i1.putExtra("feet", "" + feet);
                                            i1.putExtra("inch", "" + inch);
                                            i1.putExtra("meter", "" + meter);
                                            i1.putExtra("price", "" + calWGT);
                                            i1.putExtra("find_total", "" + find_total);
                                            i1.putExtra("total_wt_meter", "" + total_wt_meter);


                                            startActivity(i1);
                                        } else {
                                            //PUT VALUE IN INTENT
                                            Intent i1 = new Intent(TotalAmt.this, CalculateWGT.class);
                                            i1.putExtra("kg_feet", "" + kg_feet);
                                            i1.putExtra("kg_mtr", "" + kg_mtr);
                                            i1.putExtra("size", "" + size);
                                            i1.putExtra("type", "" + type);
                                            i1.putExtra("feet", "" + feet);
                                            i1.putExtra("inch", "" + inch);
                                            i1.putExtra("meter", "" + meter);
                                            i1.putExtra("price", "" + calWGT);
                                            i1.putExtra("find_total", "" + find_total);
                                            i1.putExtra("total_wt_meter", "" + total_wt_meter);

                                            startActivity(i1);
                                        }

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                alertDialogBuilder.create();
                alertDialogBuilder.show();


            }
        });
        mAdView = (AdView) findViewById(R.id.ad_view);
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();
        if(getString(R.string.adtype).equals("ON")) {
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
// Toolbar for actionbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //setting TITLE of actionbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(" " + app_name);


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
                    Intent i2 = new Intent(TotalAmt.this, Drawer.class);

                    startActivity(i2);
                    break;

                case 2:
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
                    // [START custom_event]

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Hey,I found new way to calculate and estimate various steel structure please try out.\nhttps://goo.gl/5ICNE1";
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Structure Weights");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    break;
                case 4:
                    Uri uri = Uri.parse("market://details?id=" + "com.structure.weights&hl=en");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {

                        startActivity(goToMarket);
                    } catch (Exception e) {
                        //  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case 5:
                    String facebookUrl = "https://www.facebook.com/StructureWeights/";
                    try {
                        int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                        if (versionCode >= 3002850) {
                            Uri uri1 = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);

                            startActivity(new Intent(Intent.ACTION_VIEW, uri1));

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
                    Intent intent1 = new Intent(TotalAmt.this, Evaluation.class);

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


//write the data to SharedPreference
        SharedPreferences sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        spinner = sp.getString("spinner", "abc");
        Intent i = getIntent();
        Log.e("spinner", spinner);
        type = i.getStringExtra("type");
        size = i.getStringExtra("size");
        kgmtr = i.getStringExtra("kg_mtr");
        kgfeet = i.getStringExtra("kg_feet");
        feet = i.getStringExtra("feet");
        inch = i.getStringExtra("inch");
        total = i.getStringExtra("find_total");
        m = i.getStringExtra("IntM");
        total_wt_meter = i.getStringExtra("total_wt_meter");

        Log.e("spinner", "" + spinner);
        Log.e("type", "" + type);
        Log.e("size", "" + size);
        Log.e("kgmtr", "" + kgmtr);
        Log.e("kgfeet", "" + kgfeet);
        Log.e("feet", "" + feet);
        Log.e("inch", "" + inch);
        Log.e("total", "" + total);
        Log.e("meter", "" + m);
        Log.e("total_wt_meter", "" + total_wt_meter);
        if (spinner.equals("Feet")) {

            s1 = feet.substring(0, 1);
            s2 = inch.substring(0, 1);

            textViewMeter.setVisibility(View.GONE);
            TextViewMeter.setVisibility(View.GONE);
            textViewfeet.setVisibility(View.VISIBLE);
            TextViewfeet.setVisibility(View.VISIBLE);
            TextViewInch.setVisibility(View.VISIBLE);
            textViewInch.setVisibility(View.VISIBLE);
            LinearLayoutMeter.setVisibility(View.GONE);
            LinearLayoutFeet.setVisibility(View.VISIBLE);
            LinearLayoutInch.setVisibility(View.VISIBLE);
            LinearLayoutKgfeet.setVisibility(View.VISIBLE);
            LinearLayoutKgmtr.setVisibility(View.GONE);
            LinearLayoutInch.setVisibility(View.GONE);
            LinearLayoutUnit.setVisibility(View.GONE);


            try {


                Textf.setText(kgfeet);
                TextViewsize.setText(size);
                TextViewtype.setText(type);
                Textmtr.setText(kgmtr);

                if (s1.equals(".")) {
                    s1 = "0";

                    textViewfeet.setText(s1 + feet + "'" + " " + inch + "''");
                } else {
                    textViewfeet.setText(feet + "'" + " " + inch + "''");
                }

                textViewTotal.setText(total + " " + " " + "kg");

                if(type.equals("M S SHEET") || type.equals("M S PLATE")) {
                    TextViewF.setText("Kg/Sq.Feet");
                }
                else {
                    TextViewF.setText("Kg/Feet");
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Eception", "" + e.toString());
            }

        } else if (spinner.equals("Meter")) {
            Log.e("Pooja:=", "shah");
            s = m.substring(0, 1);

            textViewMeter.setVisibility(View.VISIBLE);
            TextViewMeter.setVisibility(View.VISIBLE);
            LinearLayoutMeter.setVisibility(View.VISIBLE);
            textViewfeet.setVisibility(View.GONE);
            TextViewfeet.setVisibility(View.GONE);
            TextViewInch.setVisibility(View.GONE);
            textViewInch.setVisibility(View.GONE);
            LinearLayoutFeet.setVisibility(View.GONE);
            LinearLayoutInch.setVisibility(View.GONE);
            LinearLayoutKgfeet.setVisibility(View.GONE);
            LinearLayoutKgmtr.setVisibility(View.VISIBLE);
            LinearLayoutUnit.setVisibility(View.GONE);


            try {


                Textf.setText(kgfeet);
                TextViewsize.setText(size);
                TextViewtype.setText(type);
                Textmtr.setText(kgmtr);
                if (s.equals(".")) {
                    s = "0";

                    textViewMeter.setText(s + m);
                } else {
                    textViewMeter.setText(m);
                }

                textViewTotal.setText(total_wt_meter + " " + " " + "kg");

                if(type.equals("M S SHEET") || type.equals("M S PLATE")) {
                    mtr.setText("Kg/Sq.Meter");
                }
                else {
                    mtr.setText("Kg/Meter");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Eception", "" + e.toString());
            }

        }

    }

}

