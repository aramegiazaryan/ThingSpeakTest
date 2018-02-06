
package com.example.aram.thingspeaktest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Feed {

    @SerializedName("created_at")
    @Expose
    private Date createdAt;
    @SerializedName("entry_id")
    @Expose
    private Integer entryId;
    @SerializedName("field1")
    @Expose
    private String field1;

    public Date getCreatedAt() {
        return createdAt;
    }

    public Integer getEntryId() {
        return entryId;
    }

    public String getField1() {
        return field1;
    }



}
