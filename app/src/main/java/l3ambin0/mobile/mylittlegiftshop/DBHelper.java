package l3ambin0.mobile.mylittlegiftshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.HashSet;

import l3ambin0.mobile.mylittlegiftshop.models.*;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    =  Constants.app_name + ".db";
    private static final int    DATABASE_VERSION = Constants.DB_VERSION;

    private Dao<cat_categories, Integer> cat_categories_DAO = null;
    private RuntimeExceptionDao<cat_categories, Integer> cat_categories_runtime_DAO = null;
    private Dao<cat_products, Integer> cat_products_DAO = null;
    private RuntimeExceptionDao<cat_products, Integer> cat_products_runtime_DAO = null;
    private Dao<cat_users, Integer> cat_users_DAO = null;
    private RuntimeExceptionDao<cat_users, Integer> cat_users_runtime_DAO = null;
    private Dao<cat_users_levels, Integer> cat_users_levels_DAO = null;
    private RuntimeExceptionDao<cat_users_levels, Integer> cat_users_levels_runtime_DAO = null;
    private Dao<rel_product_category, Integer> rel_product_category_DAO = null;
    private RuntimeExceptionDao<rel_product_category, Integer> rel_product_category_runtime_DAO = null;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource conn) {
        createOrDrop("create", conn);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource conn, int oldVersion, int newVersion) {
        createOrDrop("drop", conn);
        onCreate(db, conn);
    }

    @Override
    public void close() {
        cat_categories_DAO = null;
        cat_products_DAO = null;
        cat_users_DAO = null;
        cat_users_levels_DAO = null;
        rel_product_category_DAO = null;
        super.close();
    }

    private void createOrDrop(String opt, ConnectionSource conn){
        switch (opt.toLowerCase()){
            case "create":
                try {
                    TableUtils.createTable(conn, cat_categories.class);
                    TableUtils.createTable(conn, cat_products.class);

                    TableUtils.createTable(conn, cat_users.class);
                    cat_users def = new cat_users();
                    def.setUsername("admin");
                    def.setPassword("admin");
                    def.setFull_name("admin");
                    def.setUser_level(2);
                    get_cat_users_DAO().create(def);

                    def.setUsername("user");
                    def.setPassword("user");
                    def.setFull_name("user");
                    def.setUser_level(1);
                    get_cat_users_DAO().create(def);

                    TableUtils.createTable(conn, cat_users_levels.class);
                    cat_users_levels ul = new cat_users_levels();
                    ul.setName("User");
                    get_cat_users_levels_DAO().create(ul);
                    ul.setName("Admin");
                    get_cat_users_levels_DAO().create(ul);

                    TableUtils.createTable(conn, rel_product_category.class);
                }
                catch (SQLException e){
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                break;
            case "drop":
                try {
                    TableUtils.dropTable(conn, cat_categories.class, true);
                    TableUtils.dropTable(conn, cat_products.class, true);
                    TableUtils.dropTable(conn, cat_users.class, true);
                    TableUtils.dropTable(conn, cat_users_levels.class, true);
                    TableUtils.dropTable(conn, rel_product_category.class, true);

                }
                catch (SQLException e){
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                break;
        }

    }

    //    cat_categories
    public Dao<cat_categories, Integer> get_cat_categories_DAO() throws SQLException {
        if(cat_categories_DAO==null) cat_categories_DAO = getDao(cat_categories.class);
        return cat_categories_DAO;
    }

    public RuntimeExceptionDao<cat_categories, Integer> get_cat_categories_runtime_DAO() {
        if(cat_categories_runtime_DAO == null) cat_categories_runtime_DAO = getRuntimeExceptionDao(cat_categories.class);
        return cat_categories_runtime_DAO;
    }

    //    cat_products
    public Dao<cat_products, Integer> get_cat_products_DAO() throws SQLException {
        if(cat_products_DAO==null) cat_products_DAO = getDao(cat_products.class);
        return cat_products_DAO;
    }

    public RuntimeExceptionDao<cat_products, Integer> get_cat_products_runtime_DAO() {
        if(cat_products_runtime_DAO == null) cat_products_runtime_DAO = getRuntimeExceptionDao(cat_products.class);
        return cat_products_runtime_DAO;
    }

    //    cat_users
    public Dao<cat_users, Integer> get_cat_users_DAO() throws SQLException {
        if(cat_users_DAO==null) cat_users_DAO = getDao(cat_users.class);
        return cat_users_DAO;
    }

    public RuntimeExceptionDao<cat_users, Integer> get_cat_users_runtime_DAO() {
        if(cat_users_runtime_DAO == null) cat_users_runtime_DAO = getRuntimeExceptionDao(cat_users.class);
        return cat_users_runtime_DAO;
    }

    //    cat_users_levels
    public Dao<cat_users_levels, Integer> get_cat_users_levels_DAO() throws SQLException {
        if(cat_users_levels_DAO==null) cat_users_levels_DAO = getDao(cat_users_levels.class);
        return cat_users_levels_DAO;
    }

    public RuntimeExceptionDao<cat_users_levels, Integer> get_cat_users_levels_runtime_DAO() {
        if(cat_users_levels_runtime_DAO == null) cat_users_levels_runtime_DAO = getRuntimeExceptionDao(cat_users_levels.class);
        return cat_users_levels_runtime_DAO;
    }

    //    rel_product_category
    public Dao<rel_product_category, Integer> rel_product_category_DAO() throws SQLException {
        if(rel_product_category_DAO==null) rel_product_category_DAO = getDao(rel_product_category.class);
        return rel_product_category_DAO;
    }

    public RuntimeExceptionDao<rel_product_category, Integer> rel_product_category_runtime_DAO() {
        if(rel_product_category_runtime_DAO == null) rel_product_category_runtime_DAO = getRuntimeExceptionDao(rel_product_category.class);
        return rel_product_category_runtime_DAO;
    }

}