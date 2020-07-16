package com.tacbin.pro.react.bean.rewrite;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author EDZ
 * @description
 * @date 2020/7/15 18:50
 */
@Component("baseBean")
public class SubBean extends BaseBean {

    @Override
    public String info() {
        return "SUB_BEAN";
    }
}
