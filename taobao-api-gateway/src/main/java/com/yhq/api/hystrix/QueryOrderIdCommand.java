package com.yhq.api.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: YHQ
 * @Date: 2020/9/11 11:36
 */
public class QueryOrderIdCommand extends HystrixCommand<Integer> implements InitializingBean , BeanPostProcessor {

    public QueryOrderIdCommand(HystrixCommandGroupKey group) {
        super(group);
    }

    @Override
    protected Integer run() throws Exception {
        ReentrantLock lock = new ReentrantLock();
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
