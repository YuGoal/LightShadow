package com.caoyu.lightshadow.api.model;

import java.util.List;

/**
 * one 列表Id
 * Created by caoyu on 2017/8/14.
 */

public class IdList {

    /**
     * res : 0
     * data : ["4388","4387","4382","4386","4381","4375","4376","4364","4273","4363"]
     */

    private int res;
    private List<String> data;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
