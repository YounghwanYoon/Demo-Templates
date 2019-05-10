package com.ray.sqlitetemplate.repository.model

 data class LoginData(/*private var uniqueId:Long, */private var id:String, private var pw:String){
     constructor(ggEmail:String) : this(id = ggEmail, pw = "verifiedByGoogle") {
   }
     constructor( imageURL: String, id:String, pw:String):this(id, pw){

     }

     var  mImage:String = "Unknown URL"
         get()=field
        set(value){
            field=value
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