package com.example.damagereport

import java.sql.Time
import java.util.Date

data class Report(
    val id: Int,
    val damageDate: Date,
    val damageTime: Time,
    val damageLocation: String,
    val damageType: String,
    val damageDescription: String,
    val damageSeverity: String,
    val damageStatus: String,
    val damageProbableCause: String
)