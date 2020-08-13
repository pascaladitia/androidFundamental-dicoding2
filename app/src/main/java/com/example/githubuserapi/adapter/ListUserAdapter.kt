package com.example.githubuserapi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapi.R
import com.example.githubuserapi.main.DetailUserActivity
import com.pascal.githubuserapi.data.DataUser
import kotlinx.android.synthetic.main.item_row_user.view.*

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {

    private val mData = ArrayList<DataUser>()

    fun setData(items: ArrayList<DataUser>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return UserViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: DataUser) {
            with(itemView) {
                Glide.with(context)
                    .load(user.avatars)
                    .into(item_row_avatar)
                item_row_name.text = user.login

                itemView.setOnClickListener {
                    Intent(context, DetailUserActivity::class.java).apply {
                        putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
                    }.run {
                        context.startActivity(this)
                    }
                }
            }
        }
    }
}