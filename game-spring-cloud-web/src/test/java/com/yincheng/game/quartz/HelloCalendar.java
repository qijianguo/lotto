package com.yincheng.game.quartz;

import org.quartz.Calendar;

public class HelloCalendar implements Calendar {
    @Override
    public void setBaseCalendar(Calendar baseCalendar) {

    }

    @Override
    public Calendar getBaseCalendar() {
        return null;
    }

    @Override
    public boolean isTimeIncluded(long timeStamp) {
        return false;
    }

    @Override
    public long getNextIncludedTime(long timeStamp) {
        return 0;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public Object clone() {
        return null;
    }
}
