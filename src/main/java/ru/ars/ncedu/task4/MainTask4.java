package ru.ars.ncedu.task4;

import javax.xml.bind.JAXBException;

public class MainTask4 {
    public static void main(String[] args) {
        Shops shops = new Shops();
        Shop shop = new Shop();
        shop.setShop_id("1");
        shop.setAddress("Lenincev");
        shop.setName("Oldi");
        shop.setPhone_number("8(909)413-11-12");
        shops.getShops().add(shop);
        {
            Customers customers = new Customers();
            customers.setOrder_id("customer_1");
            customers.setFirst_name("Darya");
            customers.setLast_name("Vlasova");
            customers.setEmail("sobaka@sobaka.com");
            customers.setPhone_number("8(912)444-15-11");
            customers.setOrder_date("25.10.2017");
            shop.getOrder_id().add(customers);
        }
        {
            Products products = new Products();
            products.setProduct_id("product_1");
            products.setName_product("intel-i7");
            products.setManufacture_country("USA");
            products.setPrice(1000);
            products.setDate_of_manufacture("25.10.2016");
            shop.getProduct_id().add(products);
        }

        JacksonWorker.serializable(shops, "Jackson.json");
        System.out.println("Jackson " + JacksonWorker.deserializable(shops, "Jackson.json"));

        GsonWorker.serializable(shops, "Gson.json");
        System.out.println("Gson " + GsonWorker.deserializable(shops, "Gson.json"));

        try {
            JaxbWorker.serializable(shops, "Jaxb.xml");
            System.out.println(JaxbWorker.deserializable(shops, "Jaxb.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}