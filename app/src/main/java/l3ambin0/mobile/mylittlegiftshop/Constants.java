package l3ambin0.mobile.mylittlegiftshop;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

final public class Constants {
    public static Toast mToast;
    public static Toast lastMToast;
    public static final String app_name = "MyLittleGiftShop";
    public static final int DB_VERSION = 4;
    public static AppCompatActivity app;
    public static HashSet<Fragment> MAP_FR;
    public static Map<Object, String> MAP_FR_TITLES;
    public static Object fragmentActiveClass;
    public static MenuItem selectedItem;
    public static DBHelper dbH;
    public static Map<String, String> MAP_SESSION;


    public static String session_fld_logged = "is_logged";
    public static String session_fld_level = "user_level";
    public static String session_fld_user_id = "user_id";
    public static String session_fld_username = "username";

}
