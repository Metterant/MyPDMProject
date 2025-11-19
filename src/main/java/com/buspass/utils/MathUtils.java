package com.buspass.utils;

import java.sql.Time;
import java.time.LocalTime;

public class MathUtils {
    public static Time convertToTimeString(int durationInMinutes) {
        // Convert duration in minutes to SQL TIME (HH:MM:SS)
		int hours = durationInMinutes / 60;
		int minutes = durationInMinutes % 60;
		// LocalTime requires hour < 24; durations longer than 24h are not expected for routes.
		LocalTime lt = LocalTime.of(hours, minutes);
		Time sqlTime = Time.valueOf(lt);

        return sqlTime;
    }
}
