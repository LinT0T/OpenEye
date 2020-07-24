package com.lint0t.openeye.bean

class CalenderData(
    val weeklyDestination: String,
    val dailyDestination: String,
    val recReason: String,
    val imageUrl: String,
    val weeklyDestination2: String,
    val recReason2:String,
    val listCommunityRecData: MutableList<CommunityRecData>
) {
}