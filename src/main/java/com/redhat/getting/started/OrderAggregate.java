package com.redhat.getting.started;

import java.util.ArrayList;

public class OrderAggregate {
    public long total;
    public ArrayList<Order> orders;

    public OrderAggregate(ArrayList<Order> orders){
        this.orders=orders;

        this.total =orders.stream().count();

    }
}
