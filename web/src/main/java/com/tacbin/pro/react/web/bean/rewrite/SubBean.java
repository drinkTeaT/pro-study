package com.tacbin.pro.react.web.bean.rewrite;

import org.springframework.stereotype.Component;

/**
 * @author EDZ
 * @description
 * @date 2020/7/15 18:50
 */
@Component
public class SubBean extends BaseBean {

    @Override
    public String info() {
        return "SUB_BEAN";
    }
}
