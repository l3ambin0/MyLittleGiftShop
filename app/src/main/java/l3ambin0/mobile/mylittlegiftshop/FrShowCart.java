package l3ambin0.mobile.mylittlegiftshop;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import l3ambin0.mobile.mylittlegiftshop.models.cat_products;

public class FrShowCart extends Fragment {
    DBHelper dbH;

    private SwipeRefreshLayout refreshLayout;
    private ProductListAdapter listAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.show_products, container, false);
            setDefaults();
            return rootView;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDefaults() throws SQLException {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        listAdapter = new ProductListAdapter(new ArrayList<ProductCard>());
        setListeners();
        fillWithDB();

    }

    public void setListeners(){
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        try {
                            fillWithDB();
                            refreshLayout.setRefreshing(false);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void fillWithDB() throws SQLException {
        if (listAdapter != null) {
            listAdapter.clear();
        }
        Dao dao = Constants.dbH.get_cat_products_DAO();
        List<cat_products> mList = dao.queryForAll();
        List<ProductCard> lista = new ArrayList<>();
        for (ProductCard pc : Constants.shoppingCart){
            lista.add(pc);
        }
        listAdapter.addAll(lista);
        recyclerView.setAdapter(listAdapter);
    }
}