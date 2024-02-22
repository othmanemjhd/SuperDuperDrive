package com.udacity.jwdnd.course1.cloudstorage.models;



public class File {
    private Integer fileId;
    private String username;
    private String contenttype;
    private String filesize;
    private Integer userId;
    private byte[] fileData;

    public File(Integer fileId, String username, String contenttype, String filesize, Integer userId, byte[] fileData) {
        this.fileId = fileId;
        this.username = username;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userId = userId;
        this.fileData = fileData;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
