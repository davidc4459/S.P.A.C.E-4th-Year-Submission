package com.example.david.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by david on 24/11/2015.
 */
@Entity
public class CompetitionPost {

    @Id
    String PostID;
    String CompetitionType;
    String Organisers;
    String AgeGroup;
    String Date;
    String Time;
    String Information;
    String YouthClub;

    public CompetitionPost() {}

    public void setPost(String post)
    {this.PostID = post;}
    public String getPost()
    {return PostID;}

    public void setCompetitionType(String CompetitionType)
    {this.CompetitionType = CompetitionType;}
    public String getCompetitionType()
    {return CompetitionType;}

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
