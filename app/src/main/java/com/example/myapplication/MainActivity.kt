package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    var imageView: ImageView?=null

    private val pickImage = 100

    var interest = ArrayList<String>()
    var skill = ArrayList<String>()
    var projectList =ArrayList<Project>()
    var educationList :ArrayList<Education> = ArrayList()
    var experienceList :ArrayList<Experience> = ArrayList()

    lateinit var interestRecyclerView: RecyclerView
    lateinit var experienceRecyclerView: RecyclerView
    lateinit var projectRecyclerView : RecyclerView
    lateinit var skillRecyclerView : RecyclerView
    lateinit var educationRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title="CV Maker-Resume Maker"
//
//        supportActionBar?.setTitle("Resume Generator")
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//////////////////////////////////////////////


//////////////////////////////////////////////////////
        val preview=findViewById<Button>(R.id.preview)
        preview.setOnClickListener {
            var phoneNo:String ?=null
            var name:String = findViewById<EditText>(R.id.firstName).text.toString()+" "+findViewById<EditText>(R.id.lastName).text.toString()
            var email:String = findViewById<EditText>(R.id.emailAddress).text.toString()
            var n:String=findViewById<TextView>(R.id.Phone_No).text.toString()

            if (name==""||name==" ")
            {
                Toast.makeText(this,"Provide Name",Toast.LENGTH_SHORT).show()
            }else if (email == "" && phoneNo == "") {
                Toast.makeText(this, "Provide Contact Information", Toast.LENGTH_SHORT).show()
            }
            else if (skill.isEmpty()) {
                Toast.makeText(this,"Provide some skills",Toast.LENGTH_SHORT).show()

            }else {

                if (n == "") {
                    phoneNo = ""
                } else {
                    phoneNo = "+92" + n
                }
                val resume = Resume(
                    name,
                    email,
                    phoneNo,
                    skill,
                    interest,
                    experienceList,
                    educationList,
                    projectList
                )
                val intent = Intent(this, MainActivity2::class.java)

                Log.d("helo", "" + imageUri)
                intent.putExtra("Resume", resume)
                //  intent.putExtra("uri",imageUri.toString())
                intent.putExtra("uri", imageUri)


                startActivity(intent)
            }
        }
///Intializing recycler view of experience from its ID
        experienceRecyclerView=findViewById(R.id.ExperienceRecyclerView)
        experienceRecyclerView.layoutManager=LinearLayoutManager(this)
        experienceRecyclerView.setHasFixedSize(true)
///Intializing recycler view of interest from its ID
        interestRecyclerView=findViewById(R.id.InterestRecyclerView)
        interestRecyclerView.layoutManager=LinearLayoutManager(this)
        interestRecyclerView.setHasFixedSize(true)

///Intializing recycler view of skill from its ID
        skillRecyclerView = findViewById(R.id.skillRecyclerView)
        skillRecyclerView.layoutManager = LinearLayoutManager(this)
        skillRecyclerView.setHasFixedSize(true)
/// Initializing recycler of projects from its ID
        projectRecyclerView = findViewById(R.id.projectRecyclerView)
        projectRecyclerView.layoutManager =LinearLayoutManager(this)
        projectRecyclerView.setHasFixedSize(true)
/// Intitializing recycler of education
        educationRecyclerView = findViewById(R.id.educationRecyclerView)
        educationRecyclerView.layoutManager = LinearLayoutManager(this)
        educationRecyclerView.setHasFixedSize(true)
/////// Add Experience Button
        val addExperience=findViewById<TextView>(R.id.addNewExperience)
        addExperience.setOnClickListener{
            addNewExperience()
            viewExperience()
        }
/////// Add Interest Button
val addInterest = findViewById<TextView>(R.id.addNewInterest)
addInterest.setOnClickListener{
    addNewInterest()
    viewInterest()
}

/////// Add Skill Button
    val addSkill=findViewById<TextView>(R.id.addNewSkill)
    addSkill.setOnClickListener{
        addNewSkill()
        viewSkills()
    }
//////  Add Project Button
        val addProject = findViewById<TextView>(R.id.addNewProject)
        addProject.setOnClickListener{
            addNewProject()
            viewProjects()
        }
//////  Add Education Button
        val addEducation = findViewById<TextView>(R.id.addNewEducation)
        addEducation.setOnClickListener{
            addNewEduction()
            viewEducation()
            }
//////  Add Image
        imageView=findViewById(R.id.profile_pic)
        val addImage = findViewById<TextView>(R.id.imageButton)
        addImage.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)

        }
        val  removeImage=findViewById<TextView>(R.id.imageRemoveButton)
        removeImage.setOnClickListener {
        imageUri=null
            imageView!!.setImageResource(R.drawable.empty)

            if (imageUri==null)
                addImage.text="Add Image"
        }
//////  view Skills
        viewSkills()
//////  view Interests
        viewInterest()
////// view Projects
        viewProjects()
////// view Education
        viewEducation()
/////   view Experience
        viewExperience()

    }

    private fun viewExperience() {
        var adapter=ExperienceAdapter(experienceList)
        experienceRecyclerView.adapter=adapter
        adapter.setOnItemClickListener(object : ExperienceAdapter.onItemClickListener{

            override fun onItemClick(position:Int)
            {
                val conformationDialog=Dialog(this@MainActivity,R.style.DialogStyle)
                conformationDialog.setContentView(R.layout.conformation_box)
                conformationDialog.window!!.setBackgroundDrawableResource(R.drawable.draw)
                conformationDialog.show()
                val yesButton=conformationDialog.findViewById<TextView>(R.id.yes_button)
                val noButton=conformationDialog.findViewById<TextView>(R.id.no_button)
                yesButton.setOnClickListener{
                var x:Experience=experienceList[position]
                experienceList.remove(x)

                Toast.makeText(this@MainActivity,"Removing item $position",Toast.LENGTH_SHORT).show()
                viewExperience()
                    conformationDialog.dismiss()
                }
            }
        })
    }

    private fun viewEducation() {
        var adapter=EducationAdapter(educationList)
        educationRecyclerView.adapter=adapter
        adapter.setOnItemClickListener(object : EducationAdapter.onItemClickListener {

            override fun onItemClick(position:Int)
            {
                val conformationDialog=Dialog(this@MainActivity,R.style.DialogStyle)
                conformationDialog.window!!.setBackgroundDrawableResource(R.drawable.draw)
                conformationDialog.setContentView(R.layout.conformation_box)
                conformationDialog.show()
                val yesButton=conformationDialog.findViewById<TextView>(R.id.yes_button)
                val noButton=conformationDialog.findViewById<TextView>(R.id.no_button)
                yesButton.setOnClickListener{
                var x:Education=educationList[position]
                educationList.remove(x)

                Toast.makeText(this@MainActivity,"Removing item $position",Toast.LENGTH_SHORT).show()

                viewEducation()
                conformationDialog.dismiss()
                }
                noButton.setOnClickListener {
                    conformationDialog.dismiss()
                }
            }
        })
    }

    private fun viewProjects() {
        var adapter = ProjectAdapter(projectList)
        projectRecyclerView.adapter=adapter
        adapter.setOnItemClickListener(object : ProjectAdapter.onItemClickListener {

            override fun onItemClick(position:Int)
            {

                val conformationDialog=Dialog(this@MainActivity,R.style.DialogStyle)
                conformationDialog.setContentView(R.layout.conformation_box)
                conformationDialog.window!!.setBackgroundDrawableResource(R.drawable.draw)





                conformationDialog.show()
                val yesButton=conformationDialog.findViewById<TextView>(R.id.yes_button)
                val noButton=conformationDialog.findViewById<TextView>(R.id.no_button)
                yesButton.setOnClickListener {
                    var x: Project = projectList[position]
                    projectList.remove(x)
                    //projectList.drop(position)
                    Toast.makeText(this@MainActivity, "Removing item $position", Toast.LENGTH_SHORT)
                        .show()
                    viewProjects()
                    conformationDialog.dismiss()
                }
                noButton.setOnClickListener{
                    conformationDialog.dismiss()
                }


            }
        })
    }

    private fun viewInterest() {
        var     adapter = SkillAdapter(interest)
        interestRecyclerView.adapter=adapter
        adapter.setOnItemClickListener(object : SkillAdapter.onItemClickListener {

            override fun onItemClick(position:Int)
            {
                val conformationDialog=Dialog(this@MainActivity,R.style.DialogStyle)
                conformationDialog.setContentView(R.layout.conformation_box)
                conformationDialog.show()
                val yesButton=conformationDialog.findViewById<TextView>(R.id.yes_button)
                val noButton=conformationDialog.findViewById<TextView>(R.id.no_button)
                yesButton.setOnClickListener{
                var x:String?=interest[position]
                interest.remove(x)
                Toast.makeText(this@MainActivity,"Removing item $x",Toast.LENGTH_SHORT).show()
                viewInterest()
                conformationDialog.dismiss()
            }
                noButton.setOnClickListener {
                    conformationDialog.dismiss()
                }
            }
        })
    }



    private fun viewSkills() {
        var adapter=SkillAdapter(skill)
        skillRecyclerView.adapter=adapter
        adapter.setOnItemClickListener(object : SkillAdapter.onItemClickListener {

            override fun onItemClick(position:Int)
            {
                val conformationDialog=Dialog(this@MainActivity,R.style.DialogStyle)
                conformationDialog.setContentView(R.layout.conformation_box)
                conformationDialog.window!!.setBackgroundDrawableResource(R.drawable.draw)
                conformationDialog.show()
                val yesButton=conformationDialog.findViewById<TextView>(R.id.yes_button)
                val noButton=conformationDialog.findViewById<TextView>(R.id.no_button)
                yesButton.setOnClickListener{
                    var x:String?=skill[position]
                    skill.remove(x)
                    Toast.makeText(this@MainActivity,"Removing item $x",Toast.LENGTH_SHORT).show()
                    viewSkills()
                    conformationDialog.dismiss()
                }
                noButton.setOnClickListener{
                    conformationDialog.dismiss()
                }
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data

            imageView!!.setImageURI(imageUri)
        }
    }

    private fun addNewExperience() {
        val ExperienceDialog = Dialog(this,R.style.DialogStyle)
        ExperienceDialog.setContentView(R.layout.new_experience)
        ExperienceDialog.window!!.setBackgroundDrawableResource(R.drawable.draw)
        ExperienceDialog.show()
        val addButton=ExperienceDialog.findViewById<TextView>(R.id.add_button)
        val cancelButton=ExperienceDialog.findViewById<TextView>(R.id.cancel_button)
        addButton.setOnClickListener {
            var company:String=ExperienceDialog.findViewById<TextView>(R.id.companyName).text.toString()
            var time:String=ExperienceDialog.findViewById<TextView>(R.id.Time).text.toString()
            var jobTitle:String = ExperienceDialog.findViewById<TextView>(R.id.jobTitle).text.toString()
            var l = ""
            l=l+company+" "+time+" "+jobTitle
            Toast.makeText(this,l,Toast.LENGTH_SHORT).show()
            experienceList.add(Experience(company,time,jobTitle))
            //Toast.makeText(this,"Experience added",Toast.LENGTH_SHORT).show()
            ExperienceDialog.dismiss()
        }
        cancelButton.setOnClickListener{
       ExperienceDialog.dismiss()
        }
}

    private fun addNewProject() {
        val ProjectDialog = Dialog(this,R.style.DialogStyle)
        ProjectDialog.setContentView(R.layout.new_project)
        ProjectDialog.window!!.setBackgroundDrawableResource(R.drawable.draw)
        ProjectDialog.show()
                val addButton=ProjectDialog.findViewById<TextView>(R.id.add_button)
        val cancelButton=ProjectDialog.findViewById<TextView>(R.id.cancel_button)
        addButton.setOnClickListener{

            var title:String= ProjectDialog.findViewById<TextView>(R.id.new_title).text.toString()
            var desc:String = ProjectDialog.findViewById<TextView>(R.id.new_description).text.toString()


                if(title==""||desc=="")
                {
                    Toast.makeText(this,"Fill all the entities to add a project",Toast.LENGTH_SHORT).show()
                }

                else{
                    projectList.add(Project(title, desc))
                    Toast.makeText(this,title+" is added.",Toast.LENGTH_SHORT).show()
                  //  Toast.makeText(this, "Project added", Toast.LENGTH_SHORT).show()

                    ProjectDialog.dismiss()
                }

        }
        cancelButton.setOnClickListener{
            ProjectDialog.dismiss()
        }
    }
    ///////////////////////////////////////////////////////////////////////

    private fun addNewInterest() {
        val interestDialog = Dialog(this,R.style.DialogStyle)

        interestDialog.setContentView(R.layout.new_interest)
        interestDialog.window!!.setBackgroundDrawableResource(R.drawable.draw)
        interestDialog.show()

        val addButton=interestDialog.findViewById<TextView>(R.id.add_button)
        val cancelButton=interestDialog.findViewById<TextView>(R.id.cancel_button)
        addButton.setOnClickListener{
            val newInterest=interestDialog.findViewById<EditText>(R.id.interest).text.toString()
            if (newInterest=="") {
                Toast.makeText(this,"Fill the entity to add new skill",Toast.LENGTH_SHORT).show()

            }else{
                interest.add(newInterest)

                Toast.makeText(this,newInterest+" is added",Toast.LENGTH_SHORT).show()
                interestDialog.dismiss()

        }
        }

        cancelButton.setOnClickListener{
            interestDialog.dismiss()
        }

    }

/////////////////////////////////////////////////////////////////////////////
    private fun addNewEduction() {
        val educationDialog = Dialog(this,R.style.DialogStyle)
        educationDialog.setContentView(R.layout.new_education)
        educationDialog.window!!.setBackgroundDrawableResource(R.drawable.draw)
        educationDialog.show()

    val addButton=educationDialog.findViewById<TextView>(R.id.add_button)
    val cancelButton=educationDialog.findViewById<TextView>(R.id.cancel_button)
    addButton.setOnClickListener{

        val time = educationDialog.findViewById<EditText>(R.id.completion_year).text.toString()
        val institute=educationDialog.findViewById<EditText>(R.id.institue).text.toString()
        val degree=educationDialog.findViewById<EditText>(R.id.degree).text.toString()
        Toast.makeText(this,time+" "+institute+" "+degree,Toast.LENGTH_SHORT).show()
        educationList.add(Education(time,institute,degree))


        educationDialog.dismiss()
    }
    cancelButton.setOnClickListener{
        educationDialog.dismiss()
    }
    }

//
    ///////////////////////////////////////////////////////////////////
    private fun addNewSkill() {
        val skillDialog = Dialog(this,R.style.DialogStyle)

        skillDialog.setContentView(R.layout.new_skill)

        skillDialog.window!!.setBackgroundDrawableResource(R.drawable.draw)
        skillDialog.show()

        val addButton=skillDialog.findViewById<TextView>(R.id.add_button)
        val cancelButton=skillDialog.findViewById<TextView>(R.id.cancel_button)
        addButton.setOnClickListener{
            val newSkill=skillDialog.findViewById<EditText>(R.id.skill).text.toString()
            if (newSkill=="") {
                Toast.makeText(this,"Fill the entity to add new skill",Toast.LENGTH_SHORT).show()

            }else{
            skill.add(newSkill)

            Toast.makeText(this,"Skill added",Toast.LENGTH_SHORT).show()
            skillDialog.dismiss()

        }
        }
        cancelButton.setOnClickListener{
            skillDialog.dismiss()
        }
    }
    //////////////////////////////////////////////////////////////////////////////

}