package com.ray.sqlitetemplate.repository.model

import com.google.gson.annotations.SerializedName

data class MovieData(
        var name: String,
        var realname: String,
        var firstappearance: String,
        var createdby: String,
        var publisher: String,
        var imageurl: String,
        var bio: String){

    @SerializedName("name")
    private var mName:String = name
        get() = field

    @SerializedName("realname")
    private var mRealname:String = realname
        get() = field
    @SerializedName("firstappearance")
    private var mFirstappearance:String= firstappearance
        get() = field
    @SerializedName("createdby")
    private var mCreatedby:String= createdby
        get() = field
    @SerializedName("publisher")
    private var mPublisher:String=publisher
        get() = field
    @SerializedName("imageurl")
    private var mImageurl:String=imageurl
        get() = field
    private var mBio:String=bio
        get() = field
}