package com.example.iconnect.Models;

public class ModelGroupChatList {

    String groupId, groupTitle, groupDescription, timestamp, createdBy;

    public ModelGroupChatList() {

    }

    public ModelGroupChatList(String groupId, String groupTitle, String groupDescription, String timestamp, String createdBy) {
        this.groupId = groupId;
        this.groupTitle = groupTitle;
        this.groupDescription = groupDescription;
        this.timestamp = timestamp;
        this.createdBy = createdBy;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
