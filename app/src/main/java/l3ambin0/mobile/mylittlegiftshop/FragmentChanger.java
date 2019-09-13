package l3ambin0.mobile.mylittlegiftshop;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

public class FragmentChanger {
    private static FragmentChanger fc;
    private Context cntxt;
    private FragmentChanger(){
        Constants.MAP_FR_TITLES.put(FrLogin.class, cntxt.getString(R.string.title_login));

    };

    public static FragmentChanger getInstance(Context cntxt){
        cntxt = cntxt;
        if(fc == null)
            fc = new FragmentChanger();
        return fc;
    }

    public boolean changeFragment(Context cntxt, MyFragment fr) {
        try {
            final AppCompatActivity activity = (AppCompatActivity) cntxt;
            String title = cntxt.getString(R.string.app_name);

            if (fr != null) {
                FragmentTransaction ft = Constants.app.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fr);
                ft.commit();
                Constants.MAP_FR.put(fr.getClass(), fr);
            }

            if (Constants.app.getSupportActionBar() != null) {
                Constants.app.getSupportActionBar().setTitle(title + ((fr.title.toString().compareTo("") != 0) ? " - " + fr.title : ""));
            }

            DrawerLayout drawer = (DrawerLayout) Constants.app.findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
