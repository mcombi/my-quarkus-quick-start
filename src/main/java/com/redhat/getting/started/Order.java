package com.redhat.getting.started;

public class Order {
    public String id;
    public String amount;

    public String type;

    public Order(String id,String amount,String type){
        this.id=id;
        this.amount=amount;
        this.type=type;
    }

    public Order(){};
}
