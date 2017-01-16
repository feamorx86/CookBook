package com.feamorx.cookebook.test;

/**
 * Created by x86 on 11.01.2017.
 */

public class Tester {

    private static Tester instance;
    private Test currentTest;

    public static void initialize() {
        instance = new Tester();
    }

    private Tester() {
        currentTest = new Test();
    }

    public static Tester get() {
        return instance;
    }

    public void setTest(Test test) {
        synchronized (this) {
            if (test != null) {
                this.currentTest = test;
            } else {
                this.currentTest = new Test();
            }
        }
    }

    public void point(int point, Object ... args) {
        Test test;
        synchronized (this) {
            test = currentTest;
        }
        test.point(point, args);
    }

    public Object model(int point, Object data, Object...args) {
        Test test;
        synchronized (this) {
            test = currentTest;
        }
        Object result = test.model(point, data, args);
        return result;
    }
}
