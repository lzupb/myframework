package com.mywork.framework.spring.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 支持设置SortHandlerMethodArgumentResolver中direct（asc\desc）参数名的设置 原Resolver中有两种方式， 1：采用一个参数，包含了排序字段和direct方式，
 * 中间使用分隔符${sortParamterName}_desc 2: 采用两个参数，其中direct的参数名为：${sortParamterName}.dir方式 默认是第1种方式，没有对外的api，采用反射强制更改为第2种方式
 * 并且把参数名修改为可设置setDirectParamterName()
 *
 * @author Ranger
 * @date 2014/8/8
 * @since 1.0
 */

public class JqSortHandlerMethodArgumentResolver extends SortHandlerMethodArgumentResolver {

    Logger logger = LoggerFactory.getLogger(JqSortHandlerMethodArgumentResolver.class);
    //
    // private static final String DEFAULT_DIRECT_NAME = "direct";
    private String directParameterName;

    //
    // public JqSortHandlerMethodArgumentResolver() {
    // try {
    // /**
    // * 使用SortHandlerMethodArgumentResolver分页查询的时候按字段排序，为了兼容jdGrid的参数名
    // */
    // Method method = SortHandlerMethodArgumentResolver.class
    // .getDeclaredMethod("setLegacyMode", new Class[] {boolean.class});
    // method.setAccessible(true);
    // method.invoke(this, true);
    // } catch (Exception e) {
    // logger.error("reflect setLegacyMode error， all query can't use customer sort direct, default may be asc",
    // e);
    // }
    // }

    @Override
    public Sort resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Sort sort = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        
        String direct = "ASC";
        if (getDirectParameterName() != null) {
            direct = webRequest.getParameter(getDirectParameterName());
        }
        Direction direction = Direction.fromString(direct);
        
        if (sort != null) {
            List<Order> sortList = new ArrayList<Order>();
            Iterator<Order> it = sort.iterator();
            while (it.hasNext()) {
                Order order = it.next();
                sortList.add(order.with(direction));
            }
            Sort newSort = new Sort(sortList);
            return newSort;
        }

        return sort;
    }

    public String getDirectParameterName() {
        return directParameterName;
    }

    public void setDirectParameterName(String directParameterName) {
        this.directParameterName = directParameterName;
    }
}
