package com.tacbin.pro.react.controller;

import com.tacbin.pro.react.bean.rewrite.BaseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author EDZ
 * @description
 * @date 2020/7/15 18:51
 */
@RestController
@RequestMapping("/bean/rewrite")
public class BeanRewriteController {
    private BaseBean baseBean;

    @RequestMapping(path = "/demo1", method = RequestMethod.GET)
    public String demo1() {
        return baseBean.info();
    }

    @Autowired
    public void setBaseBean(BaseBean baseBean) {
        this.baseBean = baseBean;
    }
}
