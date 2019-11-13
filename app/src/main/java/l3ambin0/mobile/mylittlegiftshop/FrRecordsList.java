package l3ambin0.mobile.mylittlegiftshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class FrRecordsList extends Fragment {
    String title;
    String table;
    Boolean add = true, edit = true, delete = true;

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.frm_record_list, container, false);
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

    void setDefaults() throws NoSuchFieldException, SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(title != null){
            TextView tv = rootView.findViewById(R.id.lbl_title);
            tv.setText(title);
        }
        if(!add){
            Button btn = rootView.findViewById(R.id.btn_add);
            btn.setVisibility(View.GONE);
        }
        if(table != null) {
            Utils.fill_table_layout((TableLayout) rootView.findViewById(R.id.tbl_records), table, edit, delete);
            Button btn;
            btn = rootView.findViewById(R.id.btn_add);
            btn.setOnClickListener(
                    Utils.factoryForButtonListener("add", table, 0)
            );
        }
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setTable(String table) {
        this.table = table;
    }

    private void setAdd(Boolean add) {
        this.add = add;
    }

    private void setEdit(Boolean edit) {
        this.edit = edit;
    }

    private void setDelete(Boolean delete) {
        this.delete = delete;
    }

}
