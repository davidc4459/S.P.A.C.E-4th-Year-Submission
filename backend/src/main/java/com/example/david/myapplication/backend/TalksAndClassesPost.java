package com.example.david.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by david on 23/11/2015.
 */
@Entity
public class TalksAndClassesPost {
    @Id
    String PostID;
    String SpeakerName;
    String Topic;
    String AgeGroup;
    String Date;
    String Time;
    String Information;
    String YouthClub;

    public TalksAndClassesPost() {}

    public void setPost(String post)
    {this.PostID = post;}
    public String getPost()
    {return PostID;}

    public void setSpeakerName(String SpeakerName)
    {this.SpeakerName = SpeakerName;}
    public String getSpeakerName()
    {return SpeakerName;}

    public void setTopic(String Topic)
    {this.Topic = Topic;}
    public String getTopic()
    {return Topic;}

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
