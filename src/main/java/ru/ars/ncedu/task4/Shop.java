package ru.ars.ncedu.task4;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Shop {
    @XmlAttribute
    private String shop_id;
    private String name;
    private String address;
    private String phone_number;
    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    private List<Products> product_id = new ArrayList<>();
    @XmlElementWrapper(name = "customers")
    @XmlElement(name = "customer")
    private List<Customers> order_id = new ArrayList<>();

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Products> getProduct_id() {
        return product_id;
    }

    public void setProduct_id(List<Products> product_id) {
        this.product_id = product_id;
    }

    public List<Customers> getOrder_id() {
        return order_id;
    }

    public void setOrder_id(List<Customers> order_id) {
        this.order_id = order_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shop_id='" + shop_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", product_id=" + product_id +
                ", order_id=" + order_id +
                '}';
    }
}