package com.lint0t.openeye.model

import com.lint0t.openeye.bean.CalenderBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CalenderService {
    @GET("v7/roamingCalendar/index")
    fun getCalender(@Query("date") date:String):Call<CalenderBean>
}