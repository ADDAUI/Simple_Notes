package simple_notes;

/**
 * Created by ADDAUI on 5/13/2016.
 * Logging Stuff :p
 */
class Log {

    private static char state = Config.getLogLevel();

    static void setState(char newState) {
        state = newState;
    }

    static void v(String tag, String verbose) {
        if (state == 'v')
            System.out.println(tag + ", verbose :\t" + verbose);
    }

    static void i(String tag, String info) {
        if (state == 'v' || state == 'i')
            System.out.println(tag + ", info :\t" + info);
    }

    static void d(String tag, String debug) {
        if (state == 'v' || state == 'i' || state == 'd')
            System.out.println(tag + ", debug :\t" + debug);
    }

    static void w(String tag, String warning) {
        if (state == 'v' || state == 'i' || state == 'd' || state == 'w')
            System.err.println(tag + ", warning :\t" + warning);
    }

    static void e(String tag, String error) {
        System.err.println(tag + ", error :\t" + error);
    }
}
