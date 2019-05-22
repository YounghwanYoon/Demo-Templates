package com.ray.sqlitetemplate.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ray.sqlitetemplate.repository.model.UserData
import com.ray.sqlitetemplate.repository.remote_data_source.UserData_ServerRepo

class UserDataViewModel: ViewModel() {

    private var mAllUserData: MutableLiveData<List<UserData>>

    init{
        //retrieved data from Repository
        mAllUserData = UserData_ServerRepo.getDataSet()
    }

    //LiverData will not be changed directly but only indirectly.
    //This prevent data change in such case where user leave smart phone for a period of time and come back, data still won't change
    fun getUserDatas(): LiveData<List<UserData>> {

        return mAllUserData
    }

}