package l3ambin0.mobile.mylittlegiftshop;

import android.content.Context;

public class FrProductsRecords extends FrRecordsList {
    Context cntxt;
    public FrProductsRecords() {
        this.table = "cat_products";
        this.add = true;
        this.delete = true;
        this.edit = true;
        cntxt = Constants.app.getApplicationContext();
        this.title = cntxt.getString(R.string.title_products_management);
    }
}
