import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: YHQ
 * @Date: 2020/7/9 18:28
 */
public class test  {

    public static void main(String args[]){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(7, 10,
                0, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(7));
        for (int i = 0; i < 20; i++) {
            threadPoolExecutor.submit(()->{
                System.out.println(Thread.currentThread().getName()+"wadwa"+threadPoolExecutor.getCompletedTaskCount());
            });
        }
    }
}
