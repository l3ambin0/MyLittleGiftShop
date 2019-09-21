package l3ambin0.mobile.mylittlegiftshop;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

final public class Constants {
    public static Toast mToast;
    public static Toast lastMToast;
    public static final String app_name = "MyLittleGiftShop";
    public static final int DB_VERSION = 6;
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

    public static final int CAMERA_CODE = 300;
    public static final int GALLERY_CODE = 400;

    public static final String IMAGE_VIEW_EXTRA_PARAM = "image_view_id";

    public static final int PERMISSION_CAMERA_CODE = 3000;
    public static final int PERMISSION_GALLERY_CODE = 4000;

    public static ImageView iv_new_product;
    public static ImageView iv_card;

    public static String tmpImageName = "";
    public static String productNewImage = "";

    public static List<ProductCard> shoppingCart;

}
