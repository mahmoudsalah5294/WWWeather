package com.mahmoudsalah.wwweather.model

data class AlertDetails(val description: String,
                        val end: Int,
                        val event: String,
                        val sender_name: String,
                        val start: Int)
