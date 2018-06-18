package ru.ars.ncedu.task4.table;

import javax.xml.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@XmlAccessorType(XmlAccessType.FIELD)
public class Products {
    @XmlAttribute
    private String product_id;
    private String name_product;
    private Integer price;
    private String manufacture_country;
    private String date_of_manufacture;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getManufacture_country() {
        return manufacture_country;
    }

    public void setManufacture_country(String manufacture_country) {
        this.manufacture_country = manufacture_country;
    }

    public String getDate_of_manufacture() {
        return date_of_manufacture;
    }

    public void setDate_of_manufacture(String date_of_manufacture) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = sdf.parse(date_of_manufacture);
            this.date_of_manufacture = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Products{" +
                "product_id='" + product_id + '\'' +
                ", name_product='" + name_product + '\'' +
                ", price=" + price +
                ", manufacture_country='" + manufacture_country + '\'' +
                ", date_of_manufacture='" + date_of_manufacture + '\'' +
                '}';
    }
}
