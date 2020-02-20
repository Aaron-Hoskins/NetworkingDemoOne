package com.examples.coding.networkingdemoone.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.examples.coding.networkingdemoone.R
import com.examples.coding.networkingdemoone.model.User.Result
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter(val resultList: List<Result>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false))

    override fun getItemCount() = resultList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.populateItem(resultList[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun populateItem(result: Result) {
            itemView.tvName.text = "${result.name.first} ${result.name.last}"
            itemView.tvEmail.text = result.email
            Glide
                .with(itemView)
                .load(result.picture.thumbnail)
                .into(itemView.imgUserImage)

        }
    }
}