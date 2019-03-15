package com.ray.sqlitetemplate

class LoginData{

    fun constructor(id:String, pw:String){
        mLoginID = id
        mLoginPW = pw
    }
}

private  var LoginData.mLoginPW:String
    get() = mLoginPW
    set(value) {
        mLoginPW = value
    }

private var LoginData.mLoginID:String
    get() = this.mLoginID
    set(value) {
        mLoginID = value
    }

