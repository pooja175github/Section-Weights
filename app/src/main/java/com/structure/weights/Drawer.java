package com.structure.weights;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;


public class Drawer extends AppCompatActivity {

    private CharSequence mDrawerTitle = "Structure Weights";
    private CharSequence mTitle = "Structure Weights";
    private ListView mDrawerList;
    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    TextView TextViewAbout;
    ImageView imageView;
    Button Start;
    String app_name = "Structure Weights";
    InterstitialAd iad ;
    Boolean isInternetPresent;
    ConnectionDetector cd;

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
        setContentView(R.layout.drawer_layout1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        //check  INTERNET connectivity
        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet(); // true or false


        iad = new InterstitialAd(this);
        iad.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        iad.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();

                if (!isInternetPresent) {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(Drawer.this, "No Internet Connection",
                            "You don't have internet connection.", false);

                } else {

                    Intent i1 = new Intent(Drawer.this, MainActivity.class);
                    startActivity(i1);

                }

            }
        });

//VALIDATION WHEN APP 1ST TYM LOAD-CHECKING INTERNET CONNECTION
        requestNewInterstitial();
        Start = (Button) findViewById(R.id.Start);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Drawer.this, MainActivity.class);
                startActivity(i1);

// check for Internet status
               if (iad.isLoaded()) {
                        iad.show();
                    }
            }
        });


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

        //setting TITLE of actionbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(" " + app_name);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        TextViewAbout = (TextView) findViewById(R.id.TextViewAbout);
        imageView = (ImageView) findViewById(R.id.ImageView);


        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.nav_header, mDrawerList, false);
        mDrawerList.addHeaderView(header, null, false);
        prepareNavigationDrawerItems();
        setAdapter();
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
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

    private void requestNewInterstitial() {
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = md5(android_id).toUpperCase();

        if(getString(R.string.adtype).equals("TEST")) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(deviceId)
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();

            iad.loadAd(adRequest);
        }
       else if(getString(R.string.adtype).equals("ON"))
        {
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

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    //BACK BUTTON PRESS
    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(),"Back button press",Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finishAffinity();
            }


        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
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
                    //Home
                    if (!isInternetPresent) {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        showAlertDialog(Drawer.this, "No Internet Connection",
                                "You don't have internet connection.", false);

                    } else {

                    }
                    break;

                case 2:
                    //CONTACT US
                    if (!isInternetPresent) {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        showAlertDialog(Drawer.this, "No Internet Connection",
                                "You don't have internet connection.", false);

                    } else {

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
                    }
                    break;
                case 3:
                    // [START custom_event]
                    //SHARE VIA
                    if (!isInternetPresent) {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        showAlertDialog(Drawer.this, "No Internet Connection",
                                "You don't have internet connection.", false);

                    } else {
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Hey,I found new way to calculate and estimate various steel structure please try out.You must download it.\nhttps://goo.gl/5ICNE1";
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Structure Weights");
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    }
                    break;
                case 4:
                    // RATE US
                    if (!isInternetPresent) {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        showAlertDialog(Drawer.this, "No Internet Connection",
                                "You don't have internet connection.", false);

                    } else {
                        Uri uri = Uri.parse("market://details?id=" + "com.structure.weights&hl=en");
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        try {

                            startActivity(goToMarket);
                        } catch (Exception e) {
                            //  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                    break;
                case 5:
                    //LIKE US
                    if (!isInternetPresent) {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        showAlertDialog(Drawer.this, "No Internet Connection",
                                "You don't have internet connection.", false);

                    } else {
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
                    }
                    break;
                case 6:
                    //EVALUATION
                    if (!isInternetPresent) {
                        // Internet connection is not present
                        // Ask user to connect to Internet
                        showAlertDialog(Drawer.this, "No Internet Connection",
                                "You don't have internet connection.", false);
                        //finish();
                    } else {
                        Intent intent1 = new Intent(Drawer.this, Evaluation.class);

                        startActivity(intent1);
                    }
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

}
