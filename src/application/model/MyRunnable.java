package application.model;

public abstract class MyRunnable implements Runnable {
    //    private CheckoutTill checkoutTill;
//    private Customer customer;
//
//    public MyRunnable(Customer customer) {
//        this.customer = customer;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public MyRunnable() {
//    }
//
//    public MyRunnable(CheckoutTill checkoutTill){
//
//        this.checkoutTill = checkoutTill;
//    }
//
//    public MyRunnable(CheckoutTill checkoutTill, Customer customer) {
//        this.checkoutTill = checkoutTill;
//        this.customer = customer;
//    }
//
//    public CheckoutTill getCheckoutTill() {
//        return checkoutTill;
//    }
    private Ballon ballon;

    public MyRunnable(Ballon ballon) {
        this.ballon = ballon;
    }


    public Ballon getBallon() {
        return ballon;
    }

    public void setBallon(Ballon ballon) {
        this.ballon = ballon;
    }
}
