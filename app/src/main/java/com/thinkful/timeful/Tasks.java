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
        return getDate("date");
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

    public String getSkill () {
        return getString("skill");
    }

    public void setSkill (String string)
    {
        put("skill", string);
    }

    public Date getRepeat()
    {
        return  getDate("repeat");
    }

    public void setRepeat (Date date)
    {
        put("repeat", date);
    }

    public void setReType (String string)

    {
        put("repeatType", string);
    }

    public String getReType()

    {
        return getString("repeatType");
    }

    public void setStart (Date date)
    {
        put("start", date);
    }

    public Date getStart()
    {
        return getDate("start");
    }

    public int getSkillPoints()
    {
        return getInt("skillPoints");
    }

    public void setSkillPoints(int skill)
    {
        put("skillPoints", skill);
    }


    public int getExp()
    {
        return getInt("exp");
    }

    public void setExp(int exp)
    {
        put("exp", exp);
    }

    public boolean getExpired()
    {
        return getBoolean("expired");
    }

    public void setExpired(boolean expired)
    {
        put("expired", expired);
    }

    public boolean getCanceled()
    {
        return getBoolean("canceled");
    }

    public void setCanceled(boolean canceled)
    {
        put("canceled", canceled);
    }

    public boolean getEdited() {
        return getBoolean("edited");
    }

    public void setEdited(boolean edited) {
        put("edited", edited);
    }
}
