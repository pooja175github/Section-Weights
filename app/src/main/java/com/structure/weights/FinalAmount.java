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
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.structure.weights.R;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;


public class FinalAmount extends AppCompatActivity implements OnClickListener {


    Spinner spinnerMeasurement;
    Button FindTotal;
    String spinner;
    String kgmtr;
    String kgfeet;
    Double kgfeet1, find_total_wt, find_total_wt_meter;
    String app_name = "Structure Weights";
    String type;
    String size;
    EditText EditTextMeter;
    String mtr;
    Integer inch1;
    EditText editTextinch;
    EditText editTextfeet;
    TextView TextViewUnit;
    TextView image;
    String value;
    String feet;
    String inch;
    String meter;
    Double kgmtr1;
    Integer f;
    Integer i;
    Double m;
    Float inch2;
    Integer total_inch;
    Double total_wt_per_inch;
    FrameLayout fmeter, ffeet, finch;
    private CharSequence mDrawerTitle = "Structure Weights";
    private CharSequence mTitle;
    private ListView mDrawerList;
    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
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
        setContentView(R.layout.activity_final_amount);
        mAdView = (AdView) findViewById(R.id.ad_view);
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();

        if(getString(R.string.adtype).equals("TEST")) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice(deviceId)
                    .build();

            mAdView.loadAd(adRequest);
        }
        else if(getString(R.string.adtype).equals("ON"))
        {
            AdRequest adRequest = new AdRequest.Builder()
                    .build();

            mAdView.loadAd(adRequest);

        }

        //Toolbar for actionBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Navigation Drawer
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
        ffeet.setVisibility(GONE);
        finch.setVisibility(GONE);
        fmeter.setVisibility(GONE);
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
                    Intent i2 = new Intent(FinalAmount.this, Drawer.class);

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
                    Intent intent1 = new Intent(FinalAmount.this, Evaluation.class);

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

        BaseAdapter Adapter = new DrawerAdapter_home(this, mDrawerItems, isFirstType);

        mDrawerList.setAdapter(Adapter);

        //setting TITLE of actionbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();

        ab.setTitle(" " + app_name);


        Intent intent = getIntent();
        String amt = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        kgmtr = intent.getStringExtra("kg_mtr");
        Log.e("kgmtr::::::::===", kgmtr);
        kgmtr1 = Double.parseDouble(kgmtr);
        kgfeet = intent.getStringExtra("kg_feet");
        kgfeet1 = Double.parseDouble(kgfeet);
        type = intent.getStringExtra("type");
        size = intent.getStringExtra("size");
        Log.e("kgs mtr value:", "" + kgmtr);
        Log.e("kgs feet value:", "" + kgfeet);


        spinnerMeasurement = (Spinner) findViewById(R.id.spinnerMeasurement);
        EditTextMeter = (EditText) findViewById(R.id.EditTextMeter);
        FindTotal = (Button) findViewById(R.id.FindTotal);
        editTextinch = (EditText) findViewById(R.id.editTextinch);
        editTextfeet = (EditText) findViewById(R.id.editTextfeet);
        TextViewUnit = (TextView) findViewById(R.id.TextViewUnit);
        image = (TextView) findViewById(R.id.image);
        ffeet = (FrameLayout) findViewById(R.id.feet);
        fmeter = (FrameLayout) findViewById(R.id.fmeter);
        finch = (FrameLayout) findViewById(R.id.inch);


        String[] Measurement = new String[]{
                "Select Unit",
                "Feet",
                "Meter",

        };
        final List<String> unit = new ArrayList<>(Arrays.asList(Measurement));
        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<String> adapter = ArrayAdapter.createFromResource(this,
//                unit, R.layout.simple_list_item_2)
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.simple_list_item_2,unit){



        @Override
        public boolean isEnabled(int position)
            {
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
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
            if(position == 0){
                // Set the hint text color gray
                tv.setTextColor(getResources().getColor(R.color.grey));
                //tv.setVisibility(GONE);
            }
            else {
                tv.setTextColor(getResources().getColor(R.color.textcolour));
            }
            return view;
        }
    };
// Specify the layout to use when the list of choices appears
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_list_item_1);
// Apply the adapter to the spinner
        spinnerMeasurement.setAdapter(spinnerArrayAdapter);

        // Button Listerner
        FindTotal.setOnClickListener(this);

        spinnerMeasurement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                spinner = spinnerMeasurement.getSelectedItem().toString();

if(spinner.equals("Select Unit"))
{
    //Toast.makeText(getApplication(),"Select one unit",Toast.LENGTH_SHORT).show();
}
                Log.e("Spinner's value", "" + spinner);
                if(spinner.equals("Feet"))
                {
                    ffeet.setVisibility(VISIBLE);
                    finch.setVisibility(VISIBLE);

                }

                else
                if(spinner.equals("Meter")){
fmeter.setVisibility(VISIBLE);
                }
                SharedPreferences sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("spinner", spinner);
                editor.commit();

                if (spinnerMeasurement.getSelectedItem().toString().equals("Feet")) {
                    EditTextMeter.setVisibility(GONE);
                    editTextfeet.setVisibility(VISIBLE);
                    editTextinch.setVisibility(VISIBLE);
                    fmeter.setVisibility(GONE);


                } else if (spinnerMeasurement.getSelectedItem().toString().equals("Meter")) {
                    EditTextMeter.setVisibility(VISIBLE);
                    editTextfeet.setVisibility(GONE);
                    editTextinch.setVisibility(GONE);
                    fmeter.setVisibility(VISIBLE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        DecimalFormat df = new DecimalFormat("#.###");



        //Get the value of Spinner(Unit)
        value = spinnerMeasurement.getSelectedItem().toString();

                Log.e("Unit's value:", "" + value);

        // Validation condition for feet and meter

        if (value.equals("Select Unit")) {
            Toast.makeText(getApplication(), "Please select one unit", Toast.LENGTH_SHORT).show();

        } else {


            if (value.equals("Feet")) {

                ffeet.setVisibility(VISIBLE);
               finch.setVisibility(VISIBLE);

// Validation of FEET AND INCH
                inch = editTextinch.getText().toString();


                if (editTextfeet.getText().toString().equals("")) {
                    editTextfeet.setError("Feet Value Required");
                    Log.e("pooja", "pooja");
                }
                if (!editTextfeet.getText().toString().equals("")) {

                    if (editTextinch.getText().toString().equals("")) {


                        feet = editTextfeet.getText().toString();
                        f = Integer.parseInt(feet);

                        inch = editTextinch.getText().toString();
                        inch = "0";
                        i = Integer.parseInt(inch);


                        Log.e("FinalAmount's feet:", "" + feet);
                        Log.e("FinalAmount's inch:", "" + inch);


//calculation1 ((feet*12)+inch)= ((f1*12)+0))
//calculation2 (kg/feet/12)= (kgfeet1/12)
//calculation3 calculation1*calculation2

                        total_inch = (f * 12) + i;
                        total_wt_per_inch = (kgfeet1 / 12);
                        String c2 = df.format(total_wt_per_inch);
                        Log.e("Format of feet/12 value", "" + c2);
                        find_total_wt = total_inch * total_wt_per_inch;
                        String find_total = df.format(find_total_wt);


                        Log.e("total:", "" + find_total);

                        Intent intent1 = new Intent(FinalAmount.this, TotalAmt.class);
                        intent1.putExtra("sp", spinner);
                        intent1.putExtra("kg_feet", "" + kgfeet);
                        intent1.putExtra("kg_mtr", "" + kgmtr);
                        intent1.putExtra("type", type);
                        intent1.putExtra("size", size);
                        intent1.putExtra("feet", "" + f);
                        intent1.putExtra("inch", "" + i);
                        intent1.putExtra("find_total", "" + find_total);
                        startActivity(intent1);
                    }
                }
                if (!editTextinch.getText().toString().equals("")) {
                    inch = editTextinch.getText().toString();
                    inch1 = Integer.parseInt(inch);
                    Log.e("inch1", "" + inch1);
                    if (editTextfeet.getText().toString().equals("")) {
                        editTextfeet.setError("Feet value required");
                    }

                    if (inch1 > 11) {
                        editTextinch.setError("accept between 0 to 11 ");

                        Log.e("inch1::::::::::::", "" + inch1);
                    }
                }


                if ((!editTextinch.getText().toString().equals("")) && (inch1 <= 11)) {
                    if (editTextfeet.getText().toString().equals("")) {
                        editTextfeet.setError("Feet value reuired");
                    }
                    if (!editTextfeet.getText().toString().equals("")) {
                        feet = editTextfeet.getText().toString();
                        f = Integer.parseInt(feet);

                        inch = editTextinch.getText().toString();
                        inch1 = Integer.parseInt(inch);
                        Log.e("FinalAmount's feet:", "" + f);
                        Log.e("FinalAmount's inch:::", "" + inch1);


// total_inch=((feet*12)+inch)= ((f1*12)+inch2)) //convert feet to inch
//total_wt_per_inch =(kg/feet/12)= (kgfeet1/12) //convert kg/feet to kg/inch
//find_total_wt= total_inch*total_wt_per_inch
                        total_inch = (f * 12) + inch1;
                        Log.e("calculation1", "" + total_inch);
                        total_wt_per_inch = (kgfeet1 / 12);
                        Log.e("calculation2", "" + total_wt_per_inch);
                        String c2 = df.format(total_wt_per_inch);
                        Log.e(" formatted calculation2", "" + c2);
                        find_total_wt = total_inch * total_wt_per_inch;
                        String find_total = df.format(find_total_wt);

                        Log.e("total/c3:", "" + find_total);
                        Log.e("inch2:", "" + inch2);

                        Intent intent1 = new Intent(FinalAmount.this, TotalAmt.class);
                        intent1.putExtra("sp", spinner);
                        intent1.putExtra("kg_feet", "" + kgfeet1);
                        intent1.putExtra("kg_mtr", "" + kgmtr1);
                        intent1.putExtra("type", type);
                        intent1.putExtra("size", size);
                        intent1.putExtra("feet", "" + f);
                        intent1.putExtra("inch", "" + inch1);
                        intent1.putExtra("find_total", "" + find_total);
                        startActivity(intent1);
                    }
                }


            } else {
                if (value.equals("Meter")) {
                    fmeter.setVisibility(VISIBLE);
                    mtr = EditTextMeter.getText().toString();
                    Log.e("mtr/mtr", "" + mtr);
                    if (EditTextMeter.getText().toString().equals("")) {
                        EditTextMeter.setError("Meter's Value Required");
                    } else {

                        meter = EditTextMeter.getText().toString();
                        m = Double.parseDouble(meter);
                        Log.e("meter's value:", "" + meter);
//find_total_wt_meter= meter*kg/meter

                        find_total_wt_meter = m * kgmtr1;
                        Log.e("find_total_wt_meter:", "" + find_total_wt_meter);
                        String total_wt_meter = df.format(find_total_wt_meter);
                        Log.e("total_wt_meter_format:", "" + total_wt_meter);
                        Intent intent1 = new Intent(FinalAmount.this, TotalAmt.class);
                        intent1.putExtra("sp", spinner);
                        intent1.putExtra("kg_feet", "" + kgfeet1);
                        intent1.putExtra("kg_mtr", "" + kgmtr1);
                        intent1.putExtra("type", type);
                        intent1.putExtra("size", size);
                        intent1.putExtra("meters", m);
                        intent1.putExtra("total_wt_meter", total_wt_meter);
                        intent1.putExtra("IntM", meter);

                        startActivity(intent1);
                    }
                }
            }
        }
    }


}
