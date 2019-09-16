package l3ambin0.mobile.mylittlegiftshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import l3ambin0.mobile.mylittlegiftshop.models.cat_categories;
import l3ambin0.mobile.mylittlegiftshop.models.cat_products;
import l3ambin0.mobile.mylittlegiftshop.models.cat_users;
import l3ambin0.mobile.mylittlegiftshop.models.cat_users_levels;

public class FrProductNew extends Fragment {
    View rootView;
    String operation;
    int main_id = 0;
    cat_products rec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.frm_products, container, false);
            Constants.MAP_FR.add(this);
            Constants.MAP_FR_TITLES.put(this.getClass(), getString(R.string.title_products_management));
            Constants.fragmentActiveClass = this.getClass();
            super.onCreateView(inflater,container,savedInstanceState);
            setDefaults();
            return rootView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setDefaults() throws SQLException {
        TextView tv;
        tv = rootView.findViewById(R.id.title);
        tv.setText(getString(R.string.title_new, "product"));
        if(operation.toLowerCase().compareTo("edit") == 0){
            tv.setText(getString(R.string.title_edit, "product"));
        }

        List<cat_categories> lst = Constants.dbH.get_cat_categories_DAO().queryForAll();
        String[] names = new String[lst.size()];
        int i = 0;
        for (cat_categories itm : lst){
            names[i++] = itm.getName();
        }
        Spinner sp;
        sp = rootView.findViewById(R.id.cbo_categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), R.layout.spinner_item, names
        );
        sp.setAdapter(adapter);

        if(main_id != 0){
            List<cat_products> lst2 = Constants.dbH.get_cat_products_DAO().queryForEq("id", main_id);
            rec = lst2.get(0);
            setOldValues();
        }

        setButtonListeners();

    }

    private void setOldValues() throws SQLException {
        EditText et;
        Spinner sp;
        et = rootView.findViewById(R.id.txt_product_name);
        et.setText(rec.getName());
        et = rootView.findViewById(R.id.txt_description);
        et.setText(rec.getDescription());
        et = rootView.findViewById(R.id.txt_price);
        et.setText(rec.getPrice().toString());

//       Set selection on spinner
//        sp = rootView.findViewById(R.id.cbo_categories);
//        ArrayAdapter<String> adapter;
//        adapter = (ArrayAdapter<String>) sp.getAdapter();
//        List<cat_categories> lst = Constants.dbH.get_cat_categories_DAO().queryForEq("id", rec.get());
//        cat_users_levels frst = lst.get(0);
//        int pos = adapter.getPosition(frst.getName());
//        sp.setSelection(pos);

    }

    private boolean validateEmptyEditTextOnLinearLayout(LinearLayout ll){
        for(int i = 0; i < ll.getChildCount(); i++){
            View v;
            v = ll.getChildAt(i);
            if(v instanceof LinearLayout){
                if(!validateEmptyEditTextOnLinearLayout((LinearLayout) v)){
                    return false;
                }
            }
            else if(v instanceof EditText){
                if(((EditText) v).getText().toString().compareTo("") == 0){
                    Utils.alert(getString(R.string.validation_verify_value, v.getResources().getResourceEntryName(v.getId())));
                    return false;
                }
            }
        }
        return true;
    }

    private void save(View v) throws SQLException {
        LinearLayout ll = rootView.findViewById(R.id.main_ll);

        if(validateEmptyEditTextOnLinearLayout(ll)){
            cat_products obj = new cat_products();
            if(rec != null){
                obj = rec;
            }
            EditText et;
            Spinner sp;
            et = rootView.findViewById(R.id.txt_product_name);
            obj.setName(et.getText().toString());
            et = rootView.findViewById(R.id.txt_description);
            obj.setDescription(et.getText().toString());
            et = rootView.findViewById(R.id.txt_price);
            obj.setPrice(Float.valueOf(et.getText().toString()));

            if(operation.toLowerCase().compareTo("add") == 0){
                Constants.dbH.get_cat_products_DAO().create(obj);
                Utils.alert(getString(R.string.msg_created));
            }
            else if(operation.toLowerCase().compareTo("edit") == 0){
                Constants.dbH.get_cat_products_DAO().update(obj);
                Utils.alert(getString(R.string.msg_updated));
            }

        }
    }

    private void setButtonListeners(){
        Button btn;
        btn = rootView.findViewById(R.id.btn_save);
        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    save(view);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setMain_id(int main_id) {
        this.main_id = main_id;
    }
}
