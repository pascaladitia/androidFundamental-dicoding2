package com.example.githubuserapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapi.R
import com.pascal.githubuserapi.data.DataUser
import kotlinx.android.synthetic.main.item_detail_user.view.*

class DetailUserAdapter : RecyclerView.Adapter<DetailUserAdapter.DetailViewHolder>() {

    private val mData = ArrayList<DataUser>()

    fun setData(items: ArrayList<DataUser>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_user, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userDetail: DataUser) {
            with(itemView) {
                Glide.with(context)
                    .load(userDetail.avatars)
                    .into(detail_avatar)
                detail_name.text = userDetail.name
                detail_username.text = userDetail.login
                detail_location.text = userDetail.location
                detail_company.text = userDetail.company
                detail_repository.text = userDetail.repository.toString()
                detail_followers.text = userDetail.followers.toString()
                detail_following.text = userDetail.following.toString()
            }
        }
    }
}