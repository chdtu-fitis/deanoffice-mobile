package ua.edu.deanoffice.mobile.studentchdtu.course.selective;

import android.content.Context;

import ua.edu.deanoffice.mobile.studentchdtu.R;

public class DeadLineTimer {
    private final Context context;

    public enum TimeType {
        MINUTES(new int[]{R.string.dlt_f1_minutes, R.string.dlt_f2_minutes, R.string.dlt_f3_minutes}),
        HOURS(new int[]{R.string.dlt_f1_hours, R.string.dlt_f2_hours, R.string.dlt_f3_hours}),
        DAYS(new int[]{R.string.dlt_f1_days, R.string.dlt_f2_days, R.string.dlt_f3_days}),
        WEEKS(new int[]{R.string.dlt_f1_weeks, R.string.dlt_f2_weeks, R.string.dlt_f3_weeks});

        private final int[] resFormats;

        TimeType(int[] resFormats) {
            this.resFormats = resFormats;
        }

        public String UA(Context context, int format) {
            if (context == null) return "";
            int formatId = 2;
            if (format >= 1 && format <= 3) { //1, 2, 3
                formatId = format - 1;
            }
            return context.getResources().getString(resFormats[formatId]);
        }
    }

    public DeadLineTimer(Context context) {
        this.context = context;
    }

    public String deadLine(long timeLeft) {
        long seconds, minutes, hours, days, weeks;
        seconds = timeLeft / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        days = hours / 24;
        weeks = days / 7;

        String result;

        final int TIME_BORDER = 1;

        if (weeks > TIME_BORDER) {
            result = weeks + TimeType.WEEKS.UA(context, getFormat(weeks));
            days -=  weeks * 7;
            result += " і " + days + TimeType.DAYS.UA(context, getFormat(days));
        } else if (days > TIME_BORDER) {
            result = days + TimeType.DAYS.UA(context, getFormat(days));
            hours -= days * 24;
            result += " і " + hours + TimeType.HOURS.UA(context, getFormat(hours));
        } else if (hours > TIME_BORDER) {
            result = hours + TimeType.HOURS.UA(context, getFormat(hours));
            minutes -= hours * 60;
            result += " і " + minutes + TimeType.MINUTES.UA(context, getFormat(minutes));
        } else if (minutes > TIME_BORDER) {
            result = minutes + TimeType.MINUTES.UA(context, getFormat(minutes));
        } else if (seconds > 0) {
            result = context != null ? context.getResources().getString(R.string.dlt_seconds) : "";
        } else {
            result = "***";
        }

        return result;
    }


    /*
     * Перша форма - (хвилина, день, година, тиждень)    1, 21...
     * Друга форма - (хвилини, дні, години, тиждені)     2-4, 22-24...
     * Третя форма - (хвилин, днів, годин, тижнів)       0, 5-20, 25-30...
     * */
    private int getFormat(long time) {
        int timePart = (int) (time % 100);
        if (timePart >= 11 && timePart <= 19) {
            return 3;
        }
        switch (timePart % 10) {
            case 1:
                return 1;
            case 2:
            case 3:
            case 4:
                return 2;
            default:
                return 3;
        }
    }
}
