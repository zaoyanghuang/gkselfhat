package com.gnw.delaytask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;

@Component
@Slf4j
@Async
public class DelayQueueManager implements CommandLineRunner {
    //延迟队列
      private DelayQueue<DelayTask> delayTasks = new DelayQueue<>();

      //加入到延时任务
    public void put(DelayTask delayTask){
        log.info("加入延时任务：{}",delayTask);
        delayTasks.put(delayTask);
    }
    //根据task对象 取消延时任务
    public boolean remove(DelayTask delayTask){
        log.info("取消延时任务：{}",delayTask);
        return delayTasks.remove(delayTask);
    }
    //根据 id取消延时任务
    public boolean remove(String taskId){
        return remove(new DelayTask(new TaskBase(taskId),0));
    }
    //初始化延时队列 单线程池
    @Override
    public void run(String... args) throws Exception {
        log.info("初始化延时队列");
        Executors.newSingleThreadExecutor().execute(new Thread(this::excuteThread));
    }
    //延时任务执行线程
    private void excuteThread() {
        while (true) {
            try {
                DelayTask task = delayTasks.take();
                processTask(task);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    //内部执行延时任务
    private void processTask(DelayTask task) {
        log.info("开始执行延时任务：{}", task);
        //根据task中的data自定义数据来处理相关逻辑，例 if (task.getData() instanceof XXX) {}

    }
}
