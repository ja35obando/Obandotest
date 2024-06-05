package com.obando.test.core.models

data class WeatherModel (
    val latitude: Double?,
    val longitude: Double?,
    val generationtime_ms: Double?,
    val utc_offset_seconds: Double?,
    val timezone: String?,
    val timezone_abbreviation: String?,
    val elevation: Int?,
    val current_units: CurrentUnitsModel?,
    val current: CurrentUnitsModel?,
    val daily_units: DailyUnitsModel?,
    val daily: DailyModel?
)