package l3ambin0.mobile.mylittlegiftshop;

import android.content.Context;

public class FrUserRecords extends FrRecordsList {
    Context cntxt;
    public FrUserRecords() {
        this.table = "cat_users";
        this.add = true;
        this.delete = true;
        this.edit = true;
        cntxt = Constants.app.getApplicationContext();
        this.title = cntxt.getString(R.string.title_username_management);
    }
}
