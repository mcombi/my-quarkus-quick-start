package com.redhat.getting.started;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.util.ArrayList;

public class OrderAggregate {
    @ConfigProperty(name = "qs.customer.name")
    public String custome;
    public String customer;
    public long total;
    public ArrayList<Order> orders;

    public OrderAggregate(ArrayList<Order> orders){
        
        this.orders=orders;
        if (custome!=null &&custome.isEmpty()){
            this.customer="DEFAULT";
        }
        else this.customer=custome;
        
        this.total =orders.stream().count();

    }
}
