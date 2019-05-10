package com.ray.sqlitetemplate.repository.model

 class LoginData(/*private var uniqueId:Long, */private var id:String, private var pw:String){
     constructor(ggEmail:String) : this(id = ggEmail, pw = "verifiedByGoogle") {
   }

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


}