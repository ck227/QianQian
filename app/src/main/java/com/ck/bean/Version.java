package com.ck.bean;

/**
 * Created by chendaye on 2017/8/1.
 */

public class Version {


    /**
     * isVersion : 1
     * versioNumber : 1.0
     * versionContent : 最新版本
     * versionUrl :
     */

    private int isVersion;//1是强制更新
    private String versioNumber;
    private String versionContent;
    private String versionUrl;

    public int getIsVersion() {
        return isVersion;
    }

    public void setIsVersion(int isVersion) {
        this.isVersion = isVersion;
    }

    public String getVersioNumber() {
        return versioNumber;
    }

    public void setVersioNumber(String versioNumber) {
        this.versioNumber = versioNumber;
    }

    public String getVersionContent() {
        return versionContent;
    }

    public void setVersionContent(String versionContent) {
        this.versionContent = versionContent;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }
}
