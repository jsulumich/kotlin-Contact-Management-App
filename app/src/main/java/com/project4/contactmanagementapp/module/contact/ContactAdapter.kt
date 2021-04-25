package com.project4.contactmanagementapp.module.contact


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project4.contactmanagementapp.OnItemClickListener
import com.project4.contactmanagementapp.R
import kotlinx.android.synthetic.main.contact_row.view.*

class ContactAdapter(data:ArrayList<ContactBean>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context
    private var contactList = data
    private lateinit var clickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.contact_row,parent,false)
        return  ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ContactViewHolder){
            val item= contactList[position]
            holder.nameTv.setText(item.name)
            holder.phoneTv.setText(item.phone.toString())
            holder.email.setText(item.email)
        }
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val nameTv = itemView.nameTv
        val  phoneTv = itemView.phoneTv
        val email = itemView.emailTv
        val container = itemView.rowContainer.setOnClickListener(this)
        override fun onClick(view: View?) {
            clickListener.itemClickListener(view!!,position)
        }
    }

    fun setOnClickListener(listener : OnItemClickListener){
        clickListener = listener
    }
}