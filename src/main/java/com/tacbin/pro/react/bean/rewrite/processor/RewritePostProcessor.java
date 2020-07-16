package com.tacbin.pro.react.bean.rewrite.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author EDZ
 * @description
 * @date 2020/7/15 19:04
 */
//@Component
@Slf4j
public class RewritePostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;
    // 替换bean的hashmap，key为被替换的beanName，value为替换key的beanName
    private HashMap<String, String> replaceBeanMap = new HashMap<>();

    public RewritePostProcessor() {
        replaceBeanMap.put("baseBean", "subBean");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (replaceBeanMap.containsKey(beanName)) {
            log.info("替换beanName，{} 被替换为 {}", beanName, replaceBeanMap.get(beanName));
            return applicationContext.getBean(replaceBeanMap.get(beanName));
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
