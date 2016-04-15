package com.example.david.myapplication.backend;

/**
 * Created by david on 22/01/2016.
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by david on 23/11/2015.
 */
@Entity
public class AdminLogin {
    @Id
    String AdminID;
    String AdminPasword;


    public AdminLogin() {}

    public void setAdminID(String AdminID)
    {this.AdminID = AdminID;}
    public String getAdminID()
    {return AdminID;}

    public void setAdminPasword(String AdminPasword)
    {this.AdminPasword = AdminPasword;}
    public String getAdminPasword()
    {return AdminPasword;}

}
