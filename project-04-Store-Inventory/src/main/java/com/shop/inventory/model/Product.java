package com.shop.inventory.model;

public  record Product(String sku, String name, String category, int price, boolean clearance) {

}
