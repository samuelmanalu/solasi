package id.ac.ui.cs.mobileprogramming.samuel.solasi.util;

public class JniUtil {

    public static native String Method();

    static {
        System.loadLibrary("native-lib");
    }
}
