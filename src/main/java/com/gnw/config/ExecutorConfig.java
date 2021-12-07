package com.gnw.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

//线程池配置  注解配置类 开启异步   在调用线程池的方法上增加注解@Async(“asyncServiceExecutor”)即可使用
@Configuration
@EnableAsync
public class ExecutorConfig {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);
    @Bean
    public Executor asyncServiceExecutor(){
        logger.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核芯线程数
        executor.setCorePoolSize(4);
        //配置最大线程数
        executor.setMaxPoolSize(8);
        //配置队列大小
        executor.setQueueCapacity(99999);
        //配置线程活跃时间 秒
        executor.setKeepAliveSeconds(60);
        //配置线程池中的线程名字前缀
        executor.setThreadNamePrefix("async-service-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行  拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //自定义拒绝策略
        //executor.setRejectedExecutionHandler(new ExecutorConfig.myRejectedExecutionHandler());
        //执行初始化
        executor.initialize();
        return executor;
    }
    public static Executor newSingleThreadExecutor() {
        return new ThreadPoolExecutor(1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());
    }
//    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
//        return new FinalizableDelegatedExecutorService
//                (new ThreadPoolExecutor(1, 1,
//                        0L, TimeUnit.MILLISECONDS,
//                        new LinkedBlockingQueue<Runnable>(),
//                        threadFactory));
//    }
    //线程拒绝策略
    public static class myRejectedExecutionHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
           doLog(r,executor);
        }
        private void doLog(Runnable r, ThreadPoolExecutor executor){
           //可做日志记录
            System.out.println("线程被拒绝"+r.toString());
        }

    }

}

