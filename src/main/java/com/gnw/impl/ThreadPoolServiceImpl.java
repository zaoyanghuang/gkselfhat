package com.gnw.impl;

import com.gnw.service.ThreadPoolService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadPoolServiceImpl implements ThreadPoolService {
    @Override
    @Async("asyncServiceExecutor")
    public void task1(int i) {
        System.out.println("当前线程"+Thread.currentThread().getName()+"task1执行异步任务"+i);
    }

    @Override
    @Async("asyncServiceExecutor")
    public void task2(int i) {
        System.out.println("当前线程"+Thread.currentThread().getName()+"task2执行异步任务"+i);
    }
}
