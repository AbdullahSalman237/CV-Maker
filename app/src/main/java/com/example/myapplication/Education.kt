package com.example.myapplication

import java.io.Serializable

data class Education(
    var completionTime:String?=null,
    var Institute:String?=null,
    var Degree:String?=null

): Serializable