package com.thinkful.timeful;

import android.app.Application;
import com.parse.*;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;

public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Users.class);
        ParseObject.registerSubclass(Tasks.class);
        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(this, "emYaQufF2hj0NivpQQeRSQgxp14DOi94aUuQVmg9", "AD50YpxRLvWDxhvV3zzxjhc13GV0o3v2rvqWc4JO");
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

        /*ParseCurrentInstallationController controller = ParseCorePlugins.getInstance().getCurrentInstallationController();
        controller.clearFromDisk();
        controller.clearFromMemory();
        */

        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("Timeful");



    }
}