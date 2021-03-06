package com.github.yiuman.citrus.support.crud.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 查询参数注解，用于列表查询，默认为eq
 *
 * @author yiuman
 * @date 2020/4/5
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParam {

    /**
     * 参数处理类型
     *
     * @return 对应mybatis-plus中的wrapper类型
     */
    String type() default "eq";

    /**
     * 用于映射查询名称
     * 比如此字段是userId   映射到表的是user_id 此值为user_id
     *
     * @return 映射的查询名称
     */
    String mapping() default "";

    /**
     * 为空时是否拼接条件
     *
     * @return true/false
     */
    boolean condition() default true;

    /**
     * 查询参数处理器
     *
     * @return 查询参数处理器的类型，实现QueryParamHandler,处理查询条件
     */
    Class<? extends QueryParamHandler> handler() default DefaultQueryParamHandler.class;

    class DefaultQueryParamHandler implements QueryParamHandler {

        @Override
        public void handle(QueryParam queryParam, Object object, Field field, QueryWrapper<?> queryWrapper) throws Exception {
            field.setAccessible(true);
            Object value = field.get(object);
            if (ObjectUtils.isEmpty(value)) {
                return;
            }

            Method conditionMethod = queryWrapper
                    .getClass()
                    .getMethod(queryParam.type(), boolean.class, Object.class, getParameterClass(field));
            conditionMethod.setAccessible(true);
            String fieldName = org.springframework.util.StringUtils.hasText(queryParam.mapping()) ? queryParam.mapping() : field.getName();
            conditionMethod.invoke(queryWrapper, queryParam.condition(), StringUtils.camelToUnderline(fieldName), field.get(object));
        }

        private Class<?> getParameterClass(Field field) {
            Class<?> fieldType = field.getType();
            if (fieldType.isArray()) {
                return Object[].class;
            }

            if (Collection.class.isAssignableFrom(fieldType)) {
                return Collection.class;
            }

            return Object.class;
        }
    }
}