package com.redhat.getting.started;
import java.util.ArrayList;
import io.quarkus.logging.Log;

public class OrderAggregate {

    public String customer;
    public long total;
    public ArrayList<Order> orders;

    public OrderAggregate(ArrayList<Order> orders, String customer){
        Log.info(customer);
        this.orders=orders;
   
        this.customer=customer;
        
        this.total =orders.stream().count();

    }
}
