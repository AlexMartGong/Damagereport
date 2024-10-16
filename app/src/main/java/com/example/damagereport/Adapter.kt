package com.example.damagereport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter (private var homeFragment: HomeFragment)
    : RecyclerView.Adapter<Adapter.ViewHolderConcat>() {

    class ViewHolderConcat(item: View) : RecyclerView.ViewHolder(item) {
        var txtTitle: TextView = item.findViewById(R.id.txtTitle)
        var txtDescription: TextView = item.findViewById(R.id.txtDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderConcat {
        val layoutItem = LayoutInflater.from(parent.context).inflate(
            R.layout.item_report, parent, false)
        return ViewHolderConcat(layoutItem)
    }

    override fun getItemCount(): Int = Data.listReport.size

    override fun onBindViewHolder(holder: ViewHolderConcat, position: Int) {
        val contact = Data.listReport[position]
        holder.txtTitle.text = contact.id.toString()
        holder.txtDescription.text = contact.damageDescription
        holder.itemView.setOnClickListener {
            homeFragment.clickItem(position)
        }
    }

}