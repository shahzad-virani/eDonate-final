package com.example.android.edonate;

public class User {
    private String fName;
    private String lName;
    private String email;
    private String cellNo;
    private String password;
    private String type;
    private String uID;

    public User(String fName, String lName, String email, String cellNo, String password, String type, String uID) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.cellNo = cellNo;
        this.password = password;
        this.type = type;
        this.uID = uID;
    }

    public String getfname() {
        return fName;
    }

    public void setfname(String fName) {
        this.fName = fName;
    }

    public String getlname() {
        return lName;
    }

    public void setlname(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String lName) {
        this.email = email;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String password) {
        this.type = type;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }


}
