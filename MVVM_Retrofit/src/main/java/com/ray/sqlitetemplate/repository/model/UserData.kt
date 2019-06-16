package com.ray.sqlitetemplate.repository.model

 data class UserData(private var id:String="unknown_id", private var pw:String="unknown_pw", private var email:String="unknown_email"){

     constructor( imageURL: String, id:String, pw:String, email: String):this( id, pw,email){
        mImage = imageURL
        unique_id++
     }
     constructor(id:String, pw:String) : this() {
         unique_id++

     }

     constructor(email:String):this(){
         this.id="google sign in"
         this.email = email
         unique_id++
     }
     companion object {
         private var unique_id:Long = 0
     }

     var mUniqueId:Long = unique_id
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