package com.project4.contactmanagementapp.module.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.project4.contactmanagementapp.Constants
import com.project4.contactmanagementapp.OnItemClickListener
import com.project4.contactmanagementapp.R
import com.project4.contactmanagementapp.database.ContactDatabaseAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.content_main.*

class HomeActivity : AppCompatActivity(),View.OnClickListener, OnItemClickListener {
    private lateinit var databaseHelper: ContactDatabaseAdapter
    private var contactList= ArrayList<ContactBean>()
    private lateinit var contactAdapter : ContactAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
    }

    /**
     * This method is used to set views and clicklistener
     */
    private fun initView() {
        backIv.setVisibility(View.GONE);
        titleTv.setText(getString(R.string.contact_title))
        setClickListener()
        databaseHelper = ContactDatabaseAdapter(this)
        contactAdapter = ContactAdapter(contactList)
        contactAdapter.setOnClickListener(this)
        contactRecylerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        contactRecylerview.adapter = contactAdapter
    }

    private fun setClickListener() {
        backIv.setOnClickListener(this)
        floatingActionButton.setOnClickListener(this)
    }

    /**
     * This method is used to get data from db
     */
    private fun getDataFromDb() {
        contactList.clear()
        contactList.addAll( databaseHelper.getAllData())
        showData()
    }

    /**
     * This method will show data when list is not empty otherwise no data found ui will be shown
     */
    private fun showData() {
        if(contactList.size == 0){
            noDataFoundTv.visibility=View.VISIBLE
        }else{
            contactAdapter.notifyDataSetChanged()
            noDataFoundTv.visibility=View.GONE
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.floatingActionButton ->{
                goToAddContactActivity(-1)
            }
        }
    }

    /**
     * This method is used to navigate to Add Contact activity
     */
    private fun goToAddContactActivity(position: Int) {
        val intent = Intent(this,AddContactActivity::class.java)
        if(position == -1){
            intent.putExtra(Constants.ACTION,Constants.ADD_SCREEN)
        }else{
            intent.putExtra(Constants.ACTION,Constants.UPDATE_SCREEN)
            intent.putExtra(Constants.CONTACT_DATA,contactList.get(position))
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        getDataFromDb()
    }

    override fun itemClickListener(view: View, position: Int) {
        goToAddContactActivity(position)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // handles menu item selection. Changes color of app background depending on user selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_green -> {
                item.isChecked = !item.isChecked
                layoutView.setBackgroundColor(android.graphics.Color.rgb(153,255,153))
                return true
            }
            R.id.menu_yellow -> {
                item.isChecked = !item.isChecked
                layoutView.setBackgroundColor(android.graphics.Color.rgb(255,255,153))
                return true
            }
            R.id.menu_blue -> {
                item.isChecked = !item.isChecked
                layoutView.setBackgroundColor(android.graphics.Color.rgb(153,255,255))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
