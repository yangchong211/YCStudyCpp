package com.yc.audioadapter;

import java.util.concurrent.atomic.AtomicLong;


public class TimePeriod {

    /**
     * period length, in milliseconds, use {@link AtomicLong} rather than volatile long avoid long64 sync problem
     */
    private AtomicLong periodLength;
    private long initTime;

    /**
     * current period time, in milliseconds, use {@link AtomicLong} rather than volatile long avoid long64 sync problem
     */
    private AtomicLong currentPeriodTime;

    /**
     * @param periodLength period length, in milliseconds
     */
    public TimePeriod(long periodLength) {
//        initTime = TimeUtils.currentTimeMillis();
        initTime = System.currentTimeMillis();
        currentPeriodTime = new AtomicLong(initTime);
        this.periodLength = new AtomicLong(periodLength);
    }

    /**
     * Judge whether enter new period, if true, use {@link #getCurrentPeriodTime()} to get current period time and
     * save it
     *
     * @param lastPeriodTime return by {@link #getCurrentPeriodTime()}, can be 0 first time
     * @return
     */
    public boolean isNewPeriod(long lastPeriodTime) {
        if (lastPeriodTime != currentPeriodTime.get()) {
            return true;
        }

        // if not exceed length, no need to sync, for better performance
//        long currentTime = TimeUtils.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        if (currentTime - currentPeriodTime.get() < periodLength.get()) {
            return false;
        }

        // inner sync, minimize sync zone, for better performance
        synchronized (TimePeriod.class) {
            // check again, because of another thread may have changed currentPeriodTime
            if (lastPeriodTime == currentPeriodTime.get()) {
                currentPeriodTime.set(currentTime);
            }
            return true;
        }
    }

    /**
     * Get current period time
     *
     * @return in milliseconds
     */
    public long getCurrentPeriodTime() {
        return currentPeriodTime.get();
    }

    /**
     * Reset current period time
     */
    public TimePeriod reset() {
//        currentPeriodTime.set(TimeUtils.currentTimeMillis());
        currentPeriodTime.set(System.currentTimeMillis());
        return this;
    }

    public long getPeriodLength() {
        return periodLength.get();
    }

    /**
     * Set period length
     *
     * @param periodLength period length, in milliseconds
     * @return
     */
    public TimePeriod setPeriodLength(long periodLength) {
        this.periodLength.set(periodLength);
        return this;
    }

    public long getTotalLength() {
//        return TimeUtils.currentTimeMillis() - initTime;
        return System.currentTimeMillis() - initTime;
    }
}