package com.example.david.myapplication.backend;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by david on 23/11/2015.
 */
@Entity
public class ArtsAndCraftsPost {
    @Id
    String PostID;
    String ActivityType;
    String Organisers;
    String AgeGroup;
    String Date;
    String Time;
    String Information;
    String YouthClub;

    public ArtsAndCraftsPost() {}

    public void setPostID(String post)
    {this.PostID = post;}
    public String getPostID()
    {return PostID;}

    public void setActivityType(String ActivityPost)
    {this.ActivityType = ActivityPost;}
    public String getActivityType()
    {return ActivityType;}

    public void setOrganisers(String Organisers)
    {this.Organisers = Organisers;}
    public String getOrganisers()
    {return Organisers;}

    public void setAgeGroup(String AgeGroup)
    {this.AgeGroup = AgeGroup;}
    public String getAgeGroup()
    {return AgeGroup;}

    public void setDate(String Date)
    {this.Date = Date;}
    public String getDate()
    {return Date;}

    public void setTime(String Time)
    {this.Time = Time;}
    public String getTime()
    {return Time;}

    public void setInformation(String Information)
    {this.Information = Information;}
    public String getInformation()
    {return Information;}

    public void setYouthClub(String YouthClub)
    {this.YouthClub = YouthClub;}
    public String getYouthClub()
    {return YouthClub;}

}
