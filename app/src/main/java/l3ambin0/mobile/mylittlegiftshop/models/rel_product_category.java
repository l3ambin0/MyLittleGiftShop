package l3ambin0.mobile.mylittlegiftshop.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable()
public class rel_product_category {
    @DatabaseField(generatedId = true)
    private  int id;

    @DatabaseField(index = true)
    private int product_id;

    @DatabaseField(index = true)
    private int category_id;

    public rel_product_category() {
    }

    public int getId() {
        return id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
