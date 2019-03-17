package com.ray.sqlitetemplate

class LoginData(private var id:String, private var pw:String){
    var mLoginID:String = id
        get() = field
        set(value) {
            field = value
        }
    var mLoginPW:String =pw
        get() = field
        set(value) {
            field = value
        }

    init{
        mLoginID = id
        mLoginPW = pw
    }


}