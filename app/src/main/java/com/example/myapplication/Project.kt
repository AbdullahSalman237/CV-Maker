package com.example.myapplication

import java.io.Serializable

data class Project(
    var title:String?=null,
    var description:String?=null
): Serializable
