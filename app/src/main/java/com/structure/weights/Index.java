package com.structure.weights;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.structure.weights.R;

import java.util.ArrayList;
import java.util.List;

public class Index extends AppCompatActivity {
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
//        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
//        setContentView(R.layout.list_view_item_navigation_drawer_1);

        //Toolbar for actionBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //setting TITLE of actionbar
        android.support.v7.app.ActionBar ab=getSupportActionBar();
        // ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Pooja_test");
        ab.setSubtitle("This is subtitle");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.nav_header, mDrawerList, false);
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


    private void prepareNavigationDrawerItems() {
        mDrawerItems = new ArrayList<>();
        //DrawerItem(id,image,name);

        mDrawerItems.add(new DrawerItem("0",R.drawable.about_us_male_48,"About Us"));
        //mDrawerItems.add(new DrawerItem("1",R.drawable.abtus,"Home"));
        mDrawerItems.add(new DrawerItem("2", R.drawable.reply_52, "Contact us"));
        mDrawerItems.add(new DrawerItem("3",R.drawable.share_52,"Share Via"));
        mDrawerItems.add(new DrawerItem("4", R.drawable.rating_filled_50, "Rate Us"));
        mDrawerItems.add(new DrawerItem("5",R.drawable.like_filled_50,"Like us"));
        mDrawerItems.add(new DrawerItem("6", R.drawable.calculator_fille_50, "Evaluation"));
        Log.d("drawer items",mDrawerItems.get(1).getTitle());

    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id1) {



            switch (position) {



                case 1:
                    //Toast.makeText(getApplication(),"Select thayu",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Index.this, Drawer.class);
                    startActivity(intent);
                    break;


                case 2:
                    String os= System.getProperty("os.version"); // OS version
                    String version= android.os.Build.VERSION.SDK ;     // API Level
                    String device=android.os.Build.DEVICE;           // Device
                    String model=android.os.Build.MODEL ;           // Model
                    String product=android.os.Build.PRODUCT;          // Product


                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setType("plain/text");
                    sendIntent.setData(Uri.parse("support@pragmainfotech.co.in"));
                    sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@pragmainfotech.co.in"});
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact Enquiry From HealthMonitor App ");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Device Information:\nOs:"+os+"\nVersion:"+version+"\nDevice Model:"+model+"\nMessage:");
                    startActivity(sendIntent);

                    break;
                case 3:
                    // [START custom_event]

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Hey,I found new way to monitor your health.You must download it.\nHealthMonitor-https://goo.gl/rmYgxw";
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share Health Monitor");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    break;
                case 4:
                    Uri uri = Uri.parse("market://details?id=" + "com.pragma.healthmonitor&hl=en");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(goToMarket);
                    } catch (Exception e) {
                        //  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case 5:
                    String facebookUrl = "https://www.facebook.com/Health-Monitor-227599587609528/?skip_nax_wizard=true";
                    try {
                        int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                        if (versionCode >= 3002850) {
                            Uri uri1 = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                            startActivity(new Intent(Intent.ACTION_VIEW, uri1));;
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
                    Intent intent1=new Intent(Index.this,Evaluation.class);
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
        Log.d("drawer items",mDrawerItems.get(1).getTitle());

        BaseAdapter adapter = new DrawerAdapter_home(this, mDrawerItems, isFirstType);

        mDrawerList.setAdapter(adapter);
    }
    }

