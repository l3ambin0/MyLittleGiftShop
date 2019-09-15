package l3ambin0.mobile.mylittlegiftshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FrLogin extends MyFragment{

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fr_login, container, false);
            Constants.MAP_FR.add(this);
            Constants.FragmentActiveClass = this.getClass();
            title = this.getString(R.string.title_login);
            return rootView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
