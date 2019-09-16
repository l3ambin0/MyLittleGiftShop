package l3ambin0.mobile.mylittlegiftshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.List;

import l3ambin0.mobile.mylittlegiftshop.models.cat_users;

public class FrLogin extends Fragment{
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fr_login, container, false);
            Constants.MAP_FR.add(this);
            Constants.MAP_FR_TITLES.put(this.getClass(), getString(R.string.title_login));
            Constants.fragmentActiveClass = this.getClass();
            super.onCreateView(inflater,container,savedInstanceState);
            setDefaults();
            return rootView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    void setDefaults(){
        Button btn;
        btn = rootView.findViewById(R.id.btn_login);
        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(validate()){
                    EditText et;
                    TextView tv;
                    tv = rootView.findViewById(R.id.lbl_info);
                    String user, pass;
                    et = rootView.findViewById(R.id.txt_username);
                    user = et.getText().toString();
                    et = rootView.findViewById(R.id.txt_password);
                    pass = et.getText().toString();
                    try {
                        if(Utils.login(user,pass)){
                            Utils.alert("Logged In");
                        }
                        else{
                            tv.setText(getString(R.string.validation_error_3));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    boolean validate(){
        EditText et;
        TextView tv;

        tv = rootView.findViewById(R.id.lbl_info);
        tv.setText(null);

        et = rootView.findViewById(R.id.txt_username);
        if(et.getText().toString().compareTo("") == 0){
            Utils.alert(getString(R.string.validation_error_1));
            return false;
        }
        et = rootView.findViewById(R.id.txt_password);
        if(et.getText().toString().compareTo("") == 0){
            Utils.alert(getString(R.string.validation_error_2));
            return false;
        }

        return true;

    }
}
