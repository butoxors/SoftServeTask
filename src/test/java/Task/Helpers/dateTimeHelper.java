package Task.Helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class dateTimeHelper {
    private static String[] timePattern = {"HH:mm", "hh:mm a"};
    private static String[] datePattern = {"EEE MMM dd yyyy HH:mm", "dd MMM yyyy"};

    public static String makeCommentInfo(String info){
        String date = parseString(info, datePattern[0], datePattern[1]);
        String time = parseString(info.substring(info.length() - 5), timePattern[0], timePattern[1]).toLowerCase();

        return date + " " + time;
    }

    private static String parseString(String actual, String actualPattern, String expectedPattern){
        DateFormat inputFormat = new SimpleDateFormat(actualPattern, Locale.US);
        DateFormat outFormat = new SimpleDateFormat(expectedPattern, Locale.US);
        Date date = null;

        try{
            date = inputFormat.parse(actual);

            return outFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.US);
        Date date = new Date(System.currentTimeMillis());

        String result = format.format(date);

        String time = result.substring(result.length() - 2).toLowerCase();

        result = result.substring(0, result.length() - 2);

        return result.concat(time);
    }
}
