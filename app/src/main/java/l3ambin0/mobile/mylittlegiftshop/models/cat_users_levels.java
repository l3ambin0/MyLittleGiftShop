package l3ambin0.mobile.mylittlegiftshop.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable()
public class cat_users_levels {
    @DatabaseField(generatedId = true)
    private  int id;

    @DatabaseField(index = true)
    private String name;

    public cat_users_levels() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
