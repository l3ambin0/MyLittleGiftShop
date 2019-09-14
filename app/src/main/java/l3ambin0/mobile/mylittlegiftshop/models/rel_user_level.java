package l3ambin0.mobile.mylittlegiftshop.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable()
public class rel_user_level {
    @DatabaseField(generatedId = true)
    private  int id;

    @DatabaseField(index = true)
    private int user_id;

    @DatabaseField(index = true)
    private int user_level_id;

    public rel_user_level() {
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_level_id() {
        return user_level_id;
    }

    public void setUser_level_id(int user_level_id) {
        this.user_level_id = user_level_id;
    }
}
