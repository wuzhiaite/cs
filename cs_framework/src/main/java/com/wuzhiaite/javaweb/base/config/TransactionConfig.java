package com.wuzhiaite.javaweb.base.config;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

@Setter
@Getter
@Aspect
@EnableTransactionManagement
@Configuration
public class TransactionConfig {

    /**
     * 设置事务拦截器
     * @param dataSourceTransactionManager
     * @return
     */
    @Bean
    public TransactionInterceptor mysqlCoreDataSourceTxAdvice(PlatformTransactionManager dataSourceTransactionManager) {
        // 默认事务
        DefaultTransactionAttribute defAttr = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        // 查询只读事务
        DefaultTransactionAttribute queryAttr = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
        queryAttr.setReadOnly(true);
        // 设置拦截的方法
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("save*", defAttr);
        source.addTransactionalMethod("insert*", defAttr);
        source.addTransactionalMethod("delete*", defAttr);
        source.addTransactionalMethod("update*", defAttr);
        source.addTransactionalMethod("exec*", defAttr);
        source.addTransactionalMethod("set*", defAttr);
        source.addTransactionalMethod("add*", defAttr);
        source.addTransactionalMethod("get*", queryAttr);
        source.addTransactionalMethod("query*", queryAttr);
        source.addTransactionalMethod("find*", queryAttr);
        source.addTransactionalMethod("list*", queryAttr);
        source.addTransactionalMethod("count*", queryAttr);
        source.addTransactionalMethod("is*", queryAttr);

        return new TransactionInterceptor(dataSourceTransactionManager, source);
    }


}