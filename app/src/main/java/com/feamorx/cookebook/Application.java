package com.feamorx.cookebook;

import com.feamorx.cookebook.test.Tester;

/**
 * Created by x86 on 11.01.2017.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Tester.initialize();
    }
}
