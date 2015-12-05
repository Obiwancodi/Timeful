package com.thinkful.timeful;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.UUID;

@ParseClassName("Users")
public class Users extends ParseUser{

    public Users() {
        put("exp", 0);
    }

    public int getWallet() {
        return getInt("exp");
    }

        public void addWallet(int exp) {
        exp = getInt("exp") + exp;
        put("exp", exp
        );

    }



}
