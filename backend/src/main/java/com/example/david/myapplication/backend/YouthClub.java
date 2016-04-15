package com.example.david.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by david on 05/02/2016.
 */

@Entity
public class YouthClub {
    @Id
    String YouthClub;
    String Area;
    String AdminName;


    public YouthClub() {}

    public void setYouthClub(String YouthClub)
    {this.YouthClub = YouthClub;}
    public String getYouthClub()
    {return YouthClub;}

    public void setArea(String Area)
    {this.Area = Area;}
    public String getArea()
    {return Area;}

    public void setAdminName(String AdminName)
    {this.AdminName = AdminName;}
    public String getAdminName()
    {return AdminName;}

}
