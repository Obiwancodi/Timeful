package com.thinkful.timeful;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.UUID;

@ParseClassName("Users")
public class Users extends ParseUser{

    public Users() {
        put("Exp", 0);
    }

    public int getExp() {
        return getInt("Exp");
    }

        public void addExp (int exp)
        {
        exp = getInt("Exp") + exp;
        put("Exp", exp);

    }






}
