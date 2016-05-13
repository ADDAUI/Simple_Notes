package sample;

/**
 * Created by ADDAUI on 5/13/2016.
 * Logging Stuff :p
 */
public class Log {

    private static char state = 'v';

    public static void setState(char newState){
        state = newState;
    }

    public static void v(String tag, String verbose){
        if(state == 'v')
        System.out.println(tag+" :\t"+verbose);
    }

    public static void i(String tag, String info){
        if(state == 'v' || state == 'i')
        System.out.println(tag+" :\t"+info);
    }

    public static void d(String tag, String debug){
        if(state == 'v' || state == 'i' || state == 'd')
            System.out.println(tag+" :\t"+debug);
    }

    public static void w(String tag, String warning){
        if(state == 'v' || state == 'i' || state == 'd' || state == 'w')
            System.err.println(tag+" :\t"+warning);
    }

    public static void e(String tag, String error){
        System.err.println(tag+" :\t"+error);
    }
}
