package com.garmin.garminkaptain.model.mapper

// TODO: Implement Mapper interface
interface Mapper<in FROM, out TO> {
    fun map(from: FROM): TO
}