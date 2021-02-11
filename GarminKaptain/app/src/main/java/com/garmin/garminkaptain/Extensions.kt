package com.garmin.garminkaptain

inline val <reified T> T.TAG: String
    get() = T::class.java.simpleName