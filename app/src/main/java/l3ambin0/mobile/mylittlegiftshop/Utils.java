package l3ambin0.mobile.mylittlegiftshop;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import l3ambin0.mobile.mylittlegiftshop.models.cat_users;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class Utils {
    private static Context cntxt = MyApp.getAppContext();

    public static void alert(String msg) {
        try {
            LayoutInflater inflater = (LayoutInflater) cntxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.toast_layout, null);
            TextView tstText = (TextView) layout.findViewById(R.id.txtToast);
            tstText.setText(msg);
            if ((Constants.lastMToast instanceof Toast)) {
                if (Constants.lastMToast.getView().getWindowVisibility() == View.VISIBLE) {
                    Constants.lastMToast.cancel();
                }
            }
            if (!(Constants.mToast instanceof Toast)) {
                Constants.mToast = new Toast(cntxt);
            }
            Constants.mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            Constants.mToast.setDuration(Toast.LENGTH_LONG);
            Constants.mToast.setView(layout);
            Constants.mToast.show();
            Constants.lastMToast = Constants.mToast;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBHelper getHelper() {
        if (Constants.dbH == null) {
            Constants.dbH = OpenHelperManager.getHelper(cntxt, DBHelper.class);
        }
        return Constants.dbH;
    }


    //    METHODS FOR CREATE TABLE-LAYOUTS LIKE DATATABLES FOR EACH TABLE
// TODO: 15-Sep-19 Refactorize better 
    public static void fill_table_layout(TableLayout tl, String tbl, Boolean edit, Boolean delete) throws SQLException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Object> mList;
        Dao dao;
        TableRow.LayoutParams mLayoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams mLayoutParamsTD = new TableRow.LayoutParams(0,
                TableLayout.LayoutParams.WRAP_CONTENT, 1);
        TableRow.LayoutParams mLayoutParamsTDAction = new TableRow.LayoutParams(0,
                TableLayout.LayoutParams.WRAP_CONTENT, 2);
        mLayoutParamsTD.gravity = Gravity.CENTER_VERTICAL;

        switch (tbl.toLowerCase()) {
            case "cat_users":
                dao = Constants.dbH.get_cat_users_DAO();
                break;
            case "cat_categories":
                dao = Constants.dbH.get_cat_categories_DAO();
                break;
            case "cat_users_levels":
                dao = Constants.dbH.get_cat_users_levels_DAO();
                break;
            default:
            case "cat_products":
                dao = Constants.dbH.get_cat_products_DAO();
                break;
        }
        mList = dao.queryForAll();
        boolean isFirst = true;
        boolean actions = false;
        tl.removeAllViews();
        int counter = 0;
        int rec_id = 0;
        for (Object o : mList) {
//            if(isFirst) {
            counter++;
            TableRow th = new TableRow(cntxt);
            th.setLayoutParams(mLayoutParams);
            th.setBackgroundColor(cntxt.getColor(R.color.colorPrimaryDark));
            TableRow tr = new TableRow(cntxt);
            tr.setLayoutParams(mLayoutParams);
            int color = (counter % 2 == 0) ? Color.LTGRAY : Color.TRANSPARENT;
            tr.setBackgroundColor(color);
            for (Field field : o.getClass().getDeclaredFields()) {
                String fld = field.getName();
                if (!field.isSynthetic() && !fld.equals("serialVersionUID")) {
                    field.setAccessible(true);
                    if (fld.equals("id")) {
                        actions = true;
                        Method meth_get_id = o.getClass().getMethod("getId");
                        rec_id = (int) meth_get_id.invoke(o,null);
                    } else {
                        TextView td = new TextView(cntxt);
                        td.setLayoutParams(mLayoutParamsTD);
                        Class cl = o.getClass();
                        String method_call = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                        Method m = cl.getMethod(method_call, null);
                        String value = "";
                        if(m.invoke(o, null) != null) {
                            value = m.invoke(o, null).toString();
                        }
                        td.setText(value);
                        td.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11);
                        td.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                        tr.addView(td);
                        if (isFirst) {
                            TextView tv = new TextView(cntxt);
                            tv.setLayoutParams(mLayoutParamsTD);
                            tv.setText(field.getName().replace("_", " ").toUpperCase());
                            tv.setTextColor(Color.WHITE);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                            tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                            th.addView(tv);
                        }
                    }
                }
            }
            if (actions) {
                if (isFirst) {
                    TextView tv = new TextView(cntxt);
                    tv.setLayoutParams(mLayoutParamsTD);
                    tv.setText(cntxt.getString(R.string.lbl_actions).toUpperCase());
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                    th.addView(tv);
                }

                LinearLayout ll;
                ll = getButtons(tbl, rec_id, edit, delete);
                ll.setLayoutParams(mLayoutParamsTD);
                ll.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
                tr.addView(ll);
            }

            if (isFirst)
                tl.addView(th);
            isFirst = false;
            tl.addView(tr);
        }
    }

    public static Button.OnClickListener factoryForButtonListener(final String method, String table, final int id) throws SQLException {
        final FragmentChanger fc = FragmentChanger.getInstance(cntxt);
        Button.OnClickListener list = null;
        switch (method.toLowerCase()){
            case "add":
                switch (table.toLowerCase()){
                    case "cat_users":
                        list = new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fr;
                                fr = new FrUserNew();
                                ((FrUserNew) fr).setOperation(method.toLowerCase());
                                fc.changeFragment(cntxt, fr);
                            }
                        };
                        break;
                    case "cat_products":
                        list = new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fr;
                                fr = new FrProductNew();
                                ((FrProductNew) fr).setOperation(method.toLowerCase());
                                fc.changeFragment(cntxt, fr);
                            }
                        };
                        break;
                }
                break;
            case "edit":
                switch (table.toLowerCase()){
                    case "cat_users":
                        list = new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fr;
                                fr = new FrUserNew();
                                ((FrUserNew) fr).setMain_id(id);
                                ((FrUserNew) fr).setOperation(method.toLowerCase());
                                fc.changeFragment(cntxt, fr);
                            }
                        };
                        break;
                    case "cat_products":
                        list = new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fr;
                                fr = new FrProductNew();
                                ((FrProductNew) fr).setMain_id(id);
                                ((FrProductNew) fr).setOperation(method.toLowerCase());
                                fc.changeFragment(cntxt, fr);
                            }
                        };
                        break;
                }
                break;
            case "delete":
                final Dao mdao;
                switch (table.toLowerCase()){
                    case "cat_products":
                        mdao = Constants.dbH.get_cat_products_DAO();
                        break;
                    default:
                    case "cat_users":
                        mdao = Constants.dbH.get_cat_users_DAO();
                        break;
                }
                list = new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(Constants.app)
                                .setIconAttribute(android.R.attr.alertDialogIcon)
                                .setTitle(cntxt.getString(R.string.title_deleting))
                                .setMessage(cntxt.getString(R.string.msg_sure_delete))
                                .setPositiveButton(cntxt.getString(R.string.lbl_yes), new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            mdao.deleteById(id);
                                            alert(cntxt.getString(R.string.msg_deleted));
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                })
                                .setNegativeButton(cntxt.getString(R.string.lbl_no), null)
                                .show();
                    }
                };
                break;
        }
        return list;
    }

    private static LinearLayout getButtons(String table, int id, boolean edit, boolean delete) throws SQLException {
        LinearLayout.LayoutParams mLayoutParamsLL = new LinearLayout.LayoutParams(0,
                TableLayout.LayoutParams.WRAP_CONTENT, 1);
        LinearLayout ll = new LinearLayout(cntxt);
        if (edit) {
            Button btn = new Button(cntxt);
            btn.setLayoutParams(mLayoutParamsLL);
            btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            btn.setText(cntxt.getString(R.string.icon_edit));
            btn.setBackgroundColor(Color.parseColor("#ffffbb33"));
            btn.setScaleX(.8f);
            btn.setScaleY(.6f);
            btn.setOnClickListener(factoryForButtonListener("edit", table, id));
            ll.addView(btn);
        }
        if (delete) {
            Button btn = new Button(cntxt);
            btn.setLayoutParams(mLayoutParamsLL);
            btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            btn.setText(cntxt.getString(R.string.icon_delete));
            btn.setBackgroundColor(Color.parseColor("#ffcc0000"));
            btn.setScaleX(.8f);
            btn.setScaleY(.6f);
            btn.setOnClickListener(factoryForButtonListener("delete", table, id));
            ll.addView(btn);
        }
        return ll;
    }

    public static void addToCart(ProductCard pr){
        Constants.shoppingCart.add(pr);
    }

    public static void removeFromCart(ProductCard pr){
        Constants.shoppingCart.remove(pr);
    }

    protected static void showAbout() {
        // Inflate the about message contents
        try {

            LayoutInflater inflater = (LayoutInflater) cntxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View messageView = inflater.inflate(R.layout.about, null, false);

            TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
            int defaultColor = textView.getTextColors().getDefaultColor();
            textView.setTextColor(defaultColor);

            PackageInfo pInfo = cntxt.getPackageManager().getPackageInfo(cntxt.getPackageName(), 0);
            String version = pInfo.versionName;

            AlertDialog.Builder builder = new AlertDialog.Builder(Constants.app);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle(R.string.app_name);
            TextView text;
            text = (TextView) messageView.findViewById(R.id.about_text);
            text.setText(cntxt.getString(R.string.app_descrip, cntxt.getString(R.string.app_name)));
            text = (TextView) messageView.findViewById(R.id.about_credits);
            text.setText(cntxt.getString(R.string.app_credits, cntxt.getString(R.string.app_name), version));
            builder.setView(messageView);
            builder.create();
            builder.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // IMAGE VIEW METHODS

    public static boolean putOnImageView(ImageView iv, Bitmap bitmap, float rotation){
        try{
            if(bitmap.getWidth() > 4096 || bitmap.getHeight() > 4096){
                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                iv.setImageBitmap(scaled);
                iv.setRotation(rotation);

            }
            else{
                iv.setImageBitmap(bitmap);
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static float getCameraPhotoOrientation(String imagePath){
        int rotate = 0;
        try {
//            cntxt.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
            return rotate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }


    public static boolean putOnImageView(ImageView iv, String file, float rotation){
        try{
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(file, options);
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            if(bitmap.getWidth() > 4096 || bitmap.getHeight() > 4096){
                int nh = (int) (bitmap.getHeight() * (800.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 800, nh, true);
                iv.setImageBitmap(scaled);
                iv.setRotation(rotation);
            }
            else{
                iv.setImageBitmap(bitmap);
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // CAMERA AND GALLERY METHODS
    public static void showPictureDialog(final int holder){
        android.app.AlertDialog.Builder pictureDialog = new android.app.AlertDialog.Builder(Constants.app);
        pictureDialog.setTitle(cntxt.getString(R.string.msg_action_select));
        String[] pictureDialogItems = {
                cntxt.getString(R.string.msg_from_gallery),
                cntxt.getString(R.string.msg_from_camera) };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery(holder);
                                break;
                            case 1:
                                takePhotoFromCamera(holder);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public static void choosePhotoFromGallery(int holder) {
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(Constants.app,
                    READ_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        Constants.app,
                        new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                        Constants.PERMISSION_GALLERY_CODE
                );
                return;
            }
            openGallery(holder);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void openGallery(int holder){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Constants.app.startActivityForResult(galleryIntent, (Constants.GALLERY_CODE + holder));
    }

    private static void takePhotoFromCamera(int holder) {
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(Constants.app, Manifest.permission.CAMERA);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        Constants.app,
                        new String[]{Manifest.permission.CAMERA, CAMERA},
                        Constants.PERMISSION_CAMERA_CODE
                );
                return;
            }
            openCamera(holder);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void openCamera(int holder){
        try {
            String pictureImagePath = "";
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = timeStamp + ".jpg";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
            Constants.tmpImageName = pictureImagePath;
            File file = new File(pictureImagePath);
//            Uri outputFileUri = Uri.fromFile(file);
            Uri outputFileUri = FileProvider.getUriForFile(Constants.app, Constants.app.getApplicationContext().getPackageName() + ".fileprovider", file);

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            Constants.app.startActivityForResult(intent, (Constants.CAMERA_CODE + holder));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        try {
            PackageManager m = cntxt.getPackageManager();
            String s = cntxt.getPackageName();
            try {
                PackageInfo p = m.getPackageInfo(s, 0);
                s = p.applicationInfo.dataDir;
            } catch (PackageManager.NameNotFoundException pe) {
                Log.w(Constants.app_name.replace(" ", "_"), "Error Package name not found ", pe);
            }

            File f = new File(s, Calendar.getInstance()
                    .getTimeInMillis() + ".png");
            File dirFile = new File(f.getParent());
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(cntxt,
                    new String[]{f.getPath()},
                    new String[]{"image/png"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



//    LOGIN AND LOGOUT METHODS

    public static boolean login(String username, String password) throws SQLException {
        Dao usersDao = Constants.dbH.get_cat_users_DAO();
        List<cat_users> userList = usersDao.query(
                usersDao.queryBuilder()
                        .where()
                        .eq("username", username)
                        .and()
                        .eq("password", password)
                        .prepare()
        );
        if(userList.size() > 0){
            Constants.MAP_SESSION.put(Constants.session_fld_logged, "1");
            Constants.MAP_SESSION.put(Constants.session_fld_user_id, String.valueOf(userList.get(0).getId()));
            Constants.MAP_SESSION.put(Constants.session_fld_username, userList.get(0).getUsername());
            Constants.MAP_SESSION.put(Constants.session_fld_level, String.valueOf(userList.get(0).getUser_level()));
            return true;
        }
        else {
            return false;
        }
    }



    public static void logout(){
        Constants.MAP_SESSION.clear();
    }

}
