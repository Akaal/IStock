package com.mb.electronics.istock;

import java.io.Serializable;

/**
 * Created by baljinder1.singh on 30-09-2016.
 */
public class Product implements Serializable {

    public String ARTICLE_NUMBER;
    public String ARTICLE_NAME;
    public String Selling_Price;
    private String quantity;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getARTICLE_NUMBER() {
        return ARTICLE_NUMBER;
    }

    public void setARTICLE_NUMBER(String ARTICLE_NUMBER) {
        this.ARTICLE_NUMBER = ARTICLE_NUMBER;
    }

    public String getARTICLE_NAME() {
        return ARTICLE_NAME;
    }

    public void setARTICLE_NAME(String ARTICLE_NAME) {
        this.ARTICLE_NAME = ARTICLE_NAME;
    }

    public String getSelling_Price() {
        return Selling_Price;
    }

    public void setSelling_Price(String selling_Price) {
        Selling_Price = selling_Price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
