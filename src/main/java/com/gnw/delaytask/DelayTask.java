package com.gnw.delaytask;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayTask implements Delayed {
    final private TaskBase taskBase;//业务数据
    final private long expire;//业务延时时间ms

    public DelayTask(TaskBase taskBase, long expire) {
        super();
        this.taskBase = taskBase;
        this.expire = expire+System.currentTimeMillis();
    }

    public TaskBase getTaskBase() {
        return taskBase;
    }

    public long getExpire() {
        return expire;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  DelayTask){
            return this.taskBase.getIdentifier().equals(((DelayTask)obj).getTaskBase().getIdentifier());
        }
        return false;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire-System.currentTimeMillis(), unit);
    }

    @Override
    public int compareTo(Delayed o) {
        long delta = getDelay(TimeUnit.NANOSECONDS)-o.getDelay(TimeUnit.NANOSECONDS);
        return (int)delta;
    }

    @Override
    public String toString() {
        return "{"+"taskBase"+ taskBase.toString()+","+"expire"+new Date(expire)+"}";
    }
}
