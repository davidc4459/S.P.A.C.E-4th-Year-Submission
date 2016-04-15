package com.example.david.myapplication.backend;

/**
 * Created by david on 04/03/2016.
 */import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class FollowingList {
    @Id
    String FollowID;
    String UserID;
    String YouthClub;

    public FollowingList() {}

    public void setFollowID(String FollowID)
    {this.FollowID = FollowID;}
    public String getFollowID()
    {return FollowID;}

    public void setUserID(String UserID)
    {this.UserID = UserID;}
    public String getUserID()
    {return UserID;}

    public void setYouthClub(String YouthClub)
    {this.YouthClub = YouthClub;}
    public String getYouthClub()
    {return YouthClub;}

}
