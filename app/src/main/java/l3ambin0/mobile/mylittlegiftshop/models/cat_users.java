package l3ambin0.mobile.mylittlegiftshop.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable()
public class cat_users {
    @DatabaseField(generatedId = true)
    private  int id;

    @DatabaseField(index = true)
    private String username;

    @DatabaseField()
    private String password;

    @DatabaseField()
    private String full_name;

    public cat_users() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
