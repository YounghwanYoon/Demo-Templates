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
    private var mRealname:String = realname
        get() = field
    private var mFirstappearance:String= firstappearance
        get() = field
    private var mCreatedby:String= createdby
        get() = field
    private var mPublisher:String=publisher
        get() = field
    private var mImageurl:String=imageurl
        get() = field
    private var mBio:String=bio
        get() = field
}