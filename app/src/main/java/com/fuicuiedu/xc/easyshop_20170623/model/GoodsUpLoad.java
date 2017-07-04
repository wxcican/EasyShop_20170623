package com.fuicuiedu.xc.easyshop_20170623.model;

/**
 * 作者：王小超
 * 邮箱：wxcican@qq.com
 */

//"description": "诚信商家，非诚勿扰",     //商品描述
//        "master": "android",                    //商品发布者
//        "name": "礼物，鱼丸，鱼翅，火箭，飞机",   //商品名称
//        "price": "88",                          //商品价格
//        "type": "gift"                          //商品类型

public class GoodsUpLoad {

    /*商品名称*/
    private String name;
    /*商品价格*/
    private String price;
    /*商品描述*/
    private String description;
    /*商品类型*/
    private String type;
    /*商品发布人*/
    private String master;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
