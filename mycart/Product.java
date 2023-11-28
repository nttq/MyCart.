package edu.hanu.mycart;

public class Product  {
    private int id;
    private String thumbnail;
    private String name;
    private String unitPrice;
    private long quantity;

    public Product(String thumbnail, String name, String unitPrice, long quantity){
        this.id = id;
        this.thumbnail = thumbnail;
        this.name=name;
        this.unitPrice=unitPrice;
        this.quantity=quantity;
    }

    public int getId() {
        return id;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
