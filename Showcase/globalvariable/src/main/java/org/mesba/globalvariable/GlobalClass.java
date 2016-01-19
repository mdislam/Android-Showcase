package org.mesba.globalvariable;

import android.app.Application;

/**
 * Created by mis on 12/30/2015.
 */
public class GlobalClass extends Application {

    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
