package com.thinkful.timeful;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.Date;

@ParseClassName("Tasks")
public class Tasks extends ParseObject  implements Serializable
{


    public String getName() {
        return getString("name");
    }

    public void setName (String name) {
        put("name", name);
    }

    public String getDesript() {
        return getString("descript");
    }

    public void setDesript (String desript) {
        put("descript", desript);
    }

    public String getNote(){ // this is weather the user wants a notification or not only yes or no in this column
        return getString("note");
    }

    public void setNote (String note) {
        put("note", note);
    }


    public Date getEnd () {
        return getDate("end");
   }

   public void setEnd (Date date) {
        put("date", date);
   }

    public boolean getCompleted()
    {
        return this.getBoolean("Completed");
    }

    public void setCompleted(boolean b)
    {
        this.put("Completed", b);
    }

    public ParseUser getUser () {
        return getParseUser("createdBy");
    }

    public void setUser (ParseUser parseUser) {
        put("createdBy", parseUser);
    }




}
