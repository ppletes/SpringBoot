package com.example.demo;

import com.example.demo.heist.HeistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//implements CommandLineRunner
@SpringBootApplication
@EnableAsync
public class DemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

   /* @Value("${levelUpTime}")
    private String levelUpTime;

    @Autowired
    private HeistService heistService;

    @Bean("threadPoolTaskExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(1000);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Async-");
        return executor;
    }
*/
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
/*
    @Override
    public void run(String... args) throws Exception {
        while (true) {
            heistService.setHeistStatusThread();

            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
            service.scheduleAtFixedRate(() -> {
                try {
                    heistService.setMemberLevelThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, 0, Long.parseLong(getLevelUpTime()), TimeUnit.SECONDS);
        }
    }

    public String getLevelUpTime() {
        return levelUpTime;
    }
 */
}
