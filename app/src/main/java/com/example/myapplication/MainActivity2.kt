package com.example.myapplication

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream


class MainActivity2 : AppCompatActivity() {

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSION_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    }
    ///////////////////////////////////
    lateinit var bitmap: Bitmap
    lateinit var scaledBitmap:Bitmap
//    var pageHeight:Int?=null
    var PERMISSION_CODE = 101
    var pageHeight = 2100
    var pageWidth = 1080
//    var pageWidth:Int?=null
    var btn2:Button?=null
///////////////////////////////////
    var layout:LinearLayout?=null
    var imageUri: Uri? = null
    var imageView: ImageView?=null
    var projectList =ArrayList<Project>()
    var educationList =ArrayList<Education>()
    var experienceList =ArrayList<Experience>()
    var skill = ArrayList<String>()
    var interest = ArrayList<String>()
    lateinit var interestRecyclerView:RecyclerView
    lateinit var projectRecyclerView : RecyclerView
    lateinit var skillRecyclerView : RecyclerView
    lateinit var educationRecyclerView: RecyclerView
    lateinit var experienceRecyclerView: RecyclerView
    var fileName:String?=null
    var fullName:String?=null
    var PhoneNumber:String?=null
    var EmailAddress:String?=null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        /////////////



////////////////////////////////////////////////
        bitmap=Bitmap.createBitmap(pageWidth,pageHeight,Bitmap.Config.ARGB_8888)
        scaledBitmap = Bitmap.createScaledBitmap(bitmap, pageWidth, pageHeight ,false)

        layout=findViewById(R.id.layout)
        var btn=findViewById<Button>(R.id.btn)
        btn.setOnClickListener {
            if (fullName == ""||fullName==" ") {
                Toast.makeText(this,"Provide Name",Toast.LENGTH_SHORT).show()
            }else if (EmailAddress=="" && PhoneNumber=="") {
                Toast.makeText(this, "Provide Contact Information", Toast.LENGTH_SHORT).show()
            }
            else if (skill.isEmpty()) {
                Toast.makeText(this,"Provide some skills",Toast.LENGTH_SHORT).show()

            }
            else
            {
                if (checkPermissions()) {
                    // if permission is granted we are displaying a toast message.
                    Toast.makeText(this,"Creating Your Resume",Toast.LENGTH_SHORT).show()
                } else {
                    requestPermission()
                }
            //////////////////////////////
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

                generatePDF()
            }
        }

            /////////////


        }

////////////////////////////////////////////////

        title="CV Maker-Resume Maker"
        ///////////////////////////////////
        ////////// Back Button ////////////
        ///////////////////////////////////
        var actionBar = getSupportActionBar()

        if (actionBar != null) {

            // Customize the back button
            //actionBar.setHomeAsUpIndicator(R.drawable.mybutton);

            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ////////////////////////////////////////////////////
///Intializing recycler view of interest from its ID
        interestRecyclerView=findViewById(R.id.InterestRecyclerView)
        interestRecyclerView.layoutManager=LinearLayoutManager(this)
        interestRecyclerView.setHasFixedSize(true)
///Intializing recycler view of experience from its ID
        experienceRecyclerView=findViewById(R.id.ExperienceRecyclerView)
        experienceRecyclerView.layoutManager=LinearLayoutManager(this)
        experienceRecyclerView.setHasFixedSize(true)

///Intializing recycler view of skill from its ID
        skillRecyclerView = findViewById(R.id.skillRecyclerView)
        skillRecyclerView.layoutManager = LinearLayoutManager(this)
        skillRecyclerView.setHasFixedSize(true)
/// Initializing recycler of projects from its ID
        projectRecyclerView = findViewById(R.id.projectRecyclerView)
        projectRecyclerView.layoutManager = LinearLayoutManager(this)
        projectRecyclerView.setHasFixedSize(true)
/// Intitializing recycler of education
        educationRecyclerView = findViewById(R.id.educationRecyclerView)
        educationRecyclerView.layoutManager = LinearLayoutManager(this)
        educationRecyclerView.setHasFixedSize(true)




        val resume = intent.getSerializableExtra("Resume") as Resume?

        val name= findViewById<TextView>(R.id.Name)

        val email=findViewById<TextView>(R.id.Emial)
        val phoneNo=findViewById<TextView>(R.id.NO)



            name.text=resume!!.name!!.toString()
            fullName=resume!!.name.toString()
            email.text= resume!!.email!!.toString()
            EmailAddress=resume!!.email!!.toString()
            phoneNo.text=resume!!.phoneNo!!.toString()
            PhoneNumber=resume!!.phoneNo!!.toString()
            skill= resume.skill!!
            interest=resume.interest!!
            educationList= resume!!.educationList!!
            imageView=findViewById<ImageView>(R.id.profilePic)
            experienceList= resume.experienceList!!
            projectList=resume.projectList!!


            if (experienceList.isEmpty()){
                var experienceHeading=findViewById<TextView>(R.id.experienceHeading)
                experienceHeading.text=""
            }
            if (projectList.isEmpty()) {
                var projectHeading = findViewById<TextView>(R.id.projectHeading)
                projectHeading.text = ""
            }
            if (educationList.isEmpty())
            {
                var educationHEading=findViewById<TextView>(R.id.EducationHeading)
                educationHEading.text=""
            }
            if (skill.isEmpty())
            {
                var SkillHeading=findViewById<TextView>(R.id.skillHeading)
                SkillHeading.text=""
            }
            if (interest.isEmpty()) {
                var InterestHeading = findViewById<TextView>(R.id.interestHeading)
                InterestHeading.text=""

            }

        //imageUri=intent.getSerializableExtra("uri")as Uri?
        imageUri=intent.getParcelableExtra("uri")

        Log.d("helo2",""+imageUri)
        imageView!!.setImageURI(imageUri)







        var adapter4=EducationAdapter(educationList)
        educationRecyclerView.adapter=adapter4
        adapter4.setOnItemClickListener(object : EducationAdapter.onItemClickListener {

            override fun onItemClick(position:Int)
            {

            }
        })

        var adapter=SkillAdapter(skill)
        skillRecyclerView.adapter=adapter
        adapter.setOnItemClickListener(object : SkillAdapter.onItemClickListener {

            override fun onItemClick(position:Int)
            {

            }
        })

        var adapter5=ExperienceAdapter(experienceList)
        experienceRecyclerView.adapter=adapter5
        adapter5.setOnItemClickListener(object : ExperienceAdapter.onItemClickListener{

            override fun onItemClick(position:Int)
            {

            }
        })



        var adapter2=SkillAdapter(interest)
        interestRecyclerView.adapter=adapter2
        adapter2.setOnItemClickListener(object : SkillAdapter.onItemClickListener {

            override fun onItemClick(position:Int)
            {

            }
        })

        var adapter3 = ProjectAdapter(projectList)
        projectRecyclerView.adapter=adapter3
        adapter3.setOnItemClickListener(object : ProjectAdapter.onItemClickListener {

            override fun onItemClick(position:Int)
            {

            }
        })

    }

    // this event will enable the back
    // function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun generatePDF() {

        // creating an object variable
        // for our PDF document.
        var pdfDocument: PdfDocument = PdfDocument()

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        var paint: Paint = Paint()
        var title: Paint = Paint()

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        var myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        // below line is used for setting
        // start page for our PDF file.
        var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)

        // creating a variable for canvas
        // from our page of PDF.
        var canvas: Canvas = myPage.canvas

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.


        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.textSize = 15F

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.purple_200))

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.


//        canvas.drawText("A portal for IT professionals.", 209F, 100F, title)
//        canvas.drawText("Geeks for Geeks", 209F, 80F, title)


        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.setColor(ContextCompat.getColor(this, R.color.purple_200))
        title.textSize = 15F

        // below line is used for setting
        // our text to center of PDF.
        title.textAlign = Paint.Align.CENTER
        //canvas.drawText("This is sample document which we have created.", 396F, 560F, title)
        layout!!.draw(canvas)
        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage)

        // below line is used to set the name of
        // our PDF file and its path.
        fileName=fullName+".pdf"
        Toast.makeText(this,"pdf generated with the name of "+fileName+" and saved in phone storage",Toast.LENGTH_SHORT).show()
        val file: File = File(Environment.getExternalStorageDirectory(), fileName)

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(FileOutputStream(file))



            // on below line we are displaying a toast message as PDF file generated..
           // Toast.makeText(applicationContext, "PDF file generated..", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // below line is used
            // to handle error
            e.printStackTrace()

            // on below line we are displaying a toast message as fail to generate PDF
            Toast.makeText(applicationContext, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                .show()
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close()

    //viewFile()
        openPDF()
    }

    fun checkPermissions(): Boolean {
        // on below line we are creating a variable for both of our permissions.

        // on below line we are creating a variable for
        // writing to external storage permission
        var writeStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        // on below line we are creating a variable
        // for reading external storage permission
        var readStoragePermission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        // on below line we are returning true if both the
        // permissions are granted anf returning false
        // if permissions are not granted.
        return writeStoragePermission == PackageManager.PERMISSION_GRANTED
                && readStoragePermission == PackageManager.PERMISSION_GRANTED
    }

    // on below line we are creating a function to request permission.
    fun requestPermission() {

        // on below line we are requesting read and write to
        // storage permission for our application.
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), PERMISSION_CODE
        )
    }

    // on below line we are calling
    // on request permission result.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // on below line we are checking if the
        // request code is equal to permission code.
        if (requestCode == PERMISSION_CODE) {

            // on below line we are checking if result size is > 0
            if (grantResults.size > 0) {

                // on below line we are checking
                // if both the permissions are granted.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED) {

                    // if permissions are granted we are displaying a toast message.
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show()

                } else {

                    // if permissions are not granted we are
                    // displaying a toast message as permission denied.
                    Toast.makeText(this, "Permission Denied..", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
fun viewFile() {
 if (checkPermissions()) {
     val file = File(Environment.getExternalStorageDirectory().absolutePath + "/" + fileName)
     val target = Intent(Intent.ACTION_VIEW)
     target.setDataAndType(Uri.fromFile(file), "application/pdf")

     target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY

     val intent = Intent.createChooser(target, "Open File")

     try {
         //Toast.makeText(this, fileName, Toast.LENGTH_SHORT).show()
             startActivity(intent)
     } catch (e: ActivityNotFoundException) {
         // Instruct the user to install a PDF reader here, or something
     }
 }
 else{
     Toast.makeText(this,"This is the issuse",Toast.LENGTH_SHORT).show()
 }
}
    /////////////////////////////////////////////////////////


    private fun openPDF() {

        val file = File(Environment.getExternalStorageDirectory().absolutePath + "/"+fileName)
        val intent = Intent(Intent.ACTION_VIEW)
        val photoURI = FileProvider.getUriForFile(
            this,
            this.getApplicationContext().getPackageName() + ".provider",
            file
        )
        intent.setDataAndType(photoURI, "application/pdf")

        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent)
    }

    }