package com.xiyou3g.xiyouhelper.model;

public class Essential {

    private String total;
    private String pass;
    private String nopass;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNopass() {
        return nopass;
    }

    public void setNopass(String nopass) {
        this.nopass = nopass;
    }

    @Override
    public String toString() {
        return "Essential{" +
                "total='" + total + '\'' +
                ", pass='" + pass + '\'' +
                ", nopass='" + nopass + '\'' +
                '}';
    }
}
