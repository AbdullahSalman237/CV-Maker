package com.example.myapplication

import android.media.Image
import android.net.Uri
import android.widget.ImageView
import java.io.Serializable

data class Resume(
    var name:String?=null,
    var email:String?=null,
    var phoneNo:String?=null,
    var skill:ArrayList<String>? =null,
    var interest:ArrayList<String>?=null,
    var experienceList: ArrayList<Experience>?=null,
    var educationList: ArrayList<Education>? =null,
    var projectList: ArrayList <Project>? =null

):Serializable
