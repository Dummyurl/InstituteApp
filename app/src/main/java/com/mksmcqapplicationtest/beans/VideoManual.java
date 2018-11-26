package com.mksmcqapplicationtest.beans;

/**
 * Created by Admin on 2/28/2018.
 */

public class VideoManual {
    String VideoName,VideoID,VideoCode,Result,ForWhome;

    public void setVideoCode(String videoCode) {
        VideoCode = videoCode;
    }

    public String getVideoCode() {
        return VideoCode;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoID(String videoID) {
        VideoID = videoID;
    }

    public String getVideoID() {
        return VideoID;
    }

    public void setForWhome(String forWhome) {
        ForWhome = forWhome;
    }

    public String getForWhome() {
        return ForWhome;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getResult() {
        return Result;
    }
}
