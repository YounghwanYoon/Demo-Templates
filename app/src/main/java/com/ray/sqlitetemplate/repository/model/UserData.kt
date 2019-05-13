package com.ray.sqlitetemplate.repository.model

 data class UserData(private var uniqueId:Long =999999, private var id:String="unknown_id", private var pw:String="unknown_pw", private var email:String="unknown_email"){

     constructor( imageURL: String, uniqueId: Long, id:String, pw:String, email: String):this(uniqueId, id, pw,email){
        mImage = imageURL
     }
     constructor(id:String, pw:String) : this() {

     }

     constructor(email:String):this(){
         this.id="google sign in"
         this.email = email
     }

     var mUniqueId:Long = uniqueId
         get() = field
         set(value) {
             field = value
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
     var mEmail: String = email
         get() = field
         set(value) {
             field = value
         }


 }