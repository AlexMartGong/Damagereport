package com.example.damagereport

data class Report(
    val id: Int,
    val damageDate: String,
    val damageTime: String,
    val damageLocation: String,
    val damageType: String,
    val damageDescription: String,
    val damageSeverity: String,
    val damageStatus: String,
    val damageProbableCause: String,
    val idUser: Int
)