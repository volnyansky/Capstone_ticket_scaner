package ua.dp.vedanta.ticketchecker.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import ua.dp.vedanta.ticketchecker.R;
import ua.dp.vedanta.ticketchecker.ui.history.HistoryFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int SCREEN_SCAN=0;
    private static final int SCREEN_HISTORY=1;
    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
    private int currentScreen=0;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState!=null){
            currentScreen=savedInstanceState.getInt("screen");
        }
        switch (currentScreen){
            case SCREEN_SCAN:
                startBarcodeScaner();
                break;
            case SCREEN_HISTORY:
                navigationView.setCheckedItem(R.id.nav_history);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new HistoryFragment()).commit();
                break;
        }


        try {
            TextView textView= (TextView) navigationView.getHeaderView(0).findViewById(R.id.app_version);
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            textView.setText(pInfo.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scan) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new BarcodeScanFragment()).commit();
            currentScreen=SCREEN_SCAN;
        } else if (id == R.id.nav_history) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HistoryFragment()).commit();
            currentScreen=SCREEN_HISTORY;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("screen",currentScreen);
    }
    public void startBarcodeScaner(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.camera_request_description))
                        .setPositiveButton(R.string.grant_access, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.CAMERA},
                                        PERMISSIONS_REQUEST_CAMERA);

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                MainActivity.this.finish();
                            }
                        })
                        .show();


            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSIONS_REQUEST_CAMERA);
            }
        }else{
            initBarcodeScanfragment();
        }
    }
    protected void initBarcodeScanfragment(){
        navigationView.setCheckedItem(R.id.nav_scan);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new BarcodeScanFragment()).commitAllowingStateLoss();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initBarcodeScanfragment();
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage(getString(R.string.camera_access_not_granted))
                            .setPositiveButton(getString(R.string.close_app), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    MainActivity.this.finish();
                                }
                            });
                }
            }

        }
    }
}
