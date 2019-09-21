package l3ambin0.mobile.mylittlegiftshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentChanger fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        resetGlobals();

        fc = FragmentChanger.getInstance(this);
        Fragment fr = new FrLogin();
        fc.changeFragment(this, fr);

    }

    private void resetGlobals(){
        Constants.app = (AppCompatActivity) this;
        Constants.MAP_FR = new HashSet<>();
        Constants.MAP_FR_TITLES = new HashMap<>();
        Constants.MAP_SESSION = new HashMap<>();
        Constants.shoppingCart = new ArrayList<>();

        Constants.dbH = new DBHelper(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setTitle(getString(R.string.msg_exit))
                    .setMessage(getString(R.string.msg_sure_exit))
                    .setPositiveButton(getString(R.string.lbl_yes), new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton(getString(R.string.lbl_no), null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        if (Constants.selectedItem != null) {
            Constants.selectedItem.setChecked(false);
        }
        int id = item.getItemId();
        Constants.selectedItem = item;
        Constants.selectedItem.setChecked(true);

        Fragment fr;
        fc = FragmentChanger.getInstance(this);
        switch (id) {
            case R.id.nav_admin_users:
                fr = new FrUserRecords();
                fc.changeFragment(this, fr);
                break;

            case R.id.nav_admin_products:
                fr = new FrProductsRecords();
                fc.changeFragment(this, fr);
                break;

            case R.id.nav_about:
                Utils.showAbout();
                break;

            case R.id.nav_login:
                fr = new FrLogin();
                fc.changeFragment(this, fr);
                break;

            case R.id.nav_show_products:
                fr = new FrShowProducts();
                fc.changeFragment(this, fr);
                break;

            case R.id.nav_show_cart:
                fr = new FrShowCart();
                fc.changeFragment(this, fr);
                break;

            case R.id.nav_exit:
                finish();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            String path = "", path2 = "";

            switch (requestCode) {
                case (Constants.CAMERA_CODE + 1):
                    if(resultCode != RESULT_CANCELED && resultCode == RESULT_OK){
                        if(!Constants.tmpImageName.trim().equals("")){
                            File imgFile = new File(Constants.tmpImageName);
                            if(imgFile.exists()){
                                Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                path = Utils.saveImage(bmp);
                                Constants.productNewImage = path;
                                Utils.getCameraPhotoOrientation(imgFile.getAbsolutePath());
                                float rotation = Utils.getCameraPhotoOrientation(imgFile.getAbsolutePath());
                                Utils.putOnImageView(Constants.iv_new_product, bmp, rotation);
                            }
                        }
                    }
                    break;

                case (Constants.GALLERY_CODE + 1):
                    if(resultCode != RESULT_CANCELED) {
                        if (intent != null) {
                            Uri contentURI = intent.getData();
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                                path = Utils.saveImage(bitmap);
                                Constants.productNewImage = path;
                                float rotation = Utils.getCameraPhotoOrientation(path);
                                Utils.putOnImageView(Constants.iv_new_product, path, rotation);
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    break;

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.PERMISSION_GALLERY_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Utils.openGallery(Constants.GALLERY_CODE + 1);
            }
        }
        else if (requestCode == Constants.PERMISSION_CAMERA_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Utils.openCamera(Constants.CAMERA_CODE + 1);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Constants.dbH != null){
            OpenHelperManager.releaseHelper();
            Constants.dbH = null;
        }
    }
}
