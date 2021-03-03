package com.yintu.rixing.config.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: mlf
 * @Date: 2021/1/29 19:12:44
 * @Version: 1.0
 */
@Configuration
public class MyBatisPlusConfiguration {


    /***
     *
     *
     * 旧版本配置
     * @Bean public PaginationInterceptor paginationInterceptor() {
     * PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
     *  // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
     *  paginationInterceptor.setOverflow(false);
     * // 设置最大单页限制数量，默认 500 条，-1 不受限制
     * paginationInterceptor.setLimit(500);
     * //开启 count 的 join 优化,只针对部分 left join
     * paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
     * return paginationInterceptor;
     * }
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //乐观锁的实现
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
//        //防止全表更新跟删除
//        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }


}
