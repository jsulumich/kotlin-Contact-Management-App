package com.project4.contactmanagementapp.module.contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.project4.contactmanagementapp.Constants
import com.project4.contactmanagementapp.R
import com.project4.contactmanagementapp.database.ContactDatabaseAdapter
import com.project4.contactmanagementapp.database.Message
import kotlinx.android.synthetic.main.activity_add_contact.*
import kotlinx.android.synthetic.main.toolbar.*

class AddContactActivity : AppCompatActivity(),View.OnClickListener{

    private var contactData: ContactBean? = null
    private var actionScreen: String? = ""
    private lateinit var databaseHelper: ContactDatabaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        getDataFromIntent()
        initView()
    }

    /**
     * This method is used to init the views and add clicklisteners
     */
    private fun initView() {
        backIv.setOnClickListener(this)
        saveBtn.setOnClickListener(this)
        databaseHelper = ContactDatabaseAdapter(this)
        titleTv.setText(getString(R.string.add_data_title))
    }

    /**
     * This method is responsible to get data from intent
     */
    private fun getDataFromIntent() {
        if(intent.extras!!.containsKey(Constants.ACTION)){
            actionScreen =  intent.extras!!.getString(Constants.ACTION)
            Log.e("action_screen",actionScreen!!)
            if(actionScreen.equals(Constants.UPDATE_SCREEN)){
                contactData =  intent.getSerializableExtra(Constants.CONTACT_DATA) as? ContactBean
                setData(contactData!!)
            }
        }
    }

    /**
     * This method is responsible to set data in ui
     */
    private fun setData(contactData: ContactBean) {
        nameEt.setText(contactData.name)
        emailEt.setText(contactData.email)
        phoneEt.setText(contactData.phone.toString())
        saveBtn.setText(getString(R.string.update))
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.saveBtn ->{
                if(actionScreen!!.equals(Constants.ADD_SCREEN)){
                    if(dataIsValid()){
                        saveDataInDb()
                    }
                }else{
                    if(dataIsValid())
                        updateInDb()
                }
            }
            R.id.backIv ->{
                finish()
            }
        }
    }

    private fun dataIsValid(): Boolean {
        val name  =    nameEt.text.toString()
        val email  = emailEt.text.toString()
        val phone  = phoneEt.text.toString()
        if(name.isNullOrEmpty() || email.isNullOrEmpty() || phone.isNullOrEmpty()){
            Message.message(this,"Please fill all the fields")
            return false
        }else{
            return  true
        }
    }

    /**
     * This method is responsible to update data in db
     */
    private fun updateInDb() {
        val name  =    nameEt.text.toString()
        val email  = emailEt.text.toString()
        val phone  = phoneEt.text.toString()
        val id =  databaseHelper.updateData(name,email,phone,contactData!!.id)
        if(id>0){
            Message.message(this,"Updated successfully")
            finish()
        }else{
            Message.message(this,"error while updating")
        }
    }

    /**
     * This method is used to save data in db
     */
    private fun saveDataInDb() {
        val name  =    nameEt.text.toString()
        val email  = emailEt.text.toString()
        val phone  = phoneEt.text.toString()
        val id = databaseHelper.insertData(name,email,phone)
        if(id>0){
            Message.message(this,"successfully inserted a row")
            finish()
        }else{
            Message.message(this,"Unsuccessfull")
        }
    }
}