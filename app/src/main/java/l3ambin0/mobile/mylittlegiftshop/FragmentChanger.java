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
    private FragmentChanger(Context cntxt){
        Constants.MAP_FR_TITLES.put(FrLogin.class, cntxt.getString(R.string.title_login));
        Constants.MAP_FR_TITLES.put(FrUserRecords.class, cntxt.getString(R.string.title_username_management));
    }

    public static FragmentChanger getInstance(Context _cntxt){
        if(fc == null)
            fc = new FragmentChanger(_cntxt);
        return fc;
    }

    private void setCntxt(Context cntxt) {
        this.cntxt = cntxt;
    }

    public boolean changeFragment(Context cntxt, Fragment fr) {
        try {
            String title = cntxt.getString(R.string.app_name);

            if (fr != null) {
                FragmentTransaction ft = Constants.app.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fr);
                ft.commit();
                Constants.MAP_FR.add(fr);
            }

            if (Constants.app.getSupportActionBar() != null) {
                String second_title = Constants.MAP_FR_TITLES.get(fr.getClass());
                if(second_title != null)
                    Constants.app.getSupportActionBar().setTitle(title + ((second_title.compareTo("") != 0) ? " - " + second_title : ""));
            }

            DrawerLayout drawer = Constants.app.findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
