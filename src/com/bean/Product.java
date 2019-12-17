package com.bean;

import java.io.Serializable;

public class Product implements Serializable {
    private Integer pid;//商品编号
    private String pname;
    private float price;
    private int num;//商品数量
    private float acount;//商品小计

    public Product() {
    }

    public Product(Integer pid, String pname, float price, int num, float acount) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.num = num;
        this.acount = acount;
    }

    public Product(String pname, float price, int num, float acount) {
        this.pname = pname;
        this.price = price;
        this.num = num;
        this.acount = acount;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getAcount() {
        acount=price*num;
        return acount;
    }

    public void setAcount(float acount) {
        this.acount = acount;
    }
}
