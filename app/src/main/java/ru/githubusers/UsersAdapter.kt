package ru.githubusers

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.card_user.view.*
import ru.githubusers.model.UserEntry

class UsersAdapter(var usersList: ArrayList<UserEntry>, val ctx : Context) : androidx.recyclerview.widget.RecyclerView.Adapter<UsersAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val name = itemView.textView
        val image = itemView.imageView
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
       return CustomViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.card_user,p0,false))
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        Glide.with(ctx).load(usersList[position].avatar_url).apply(RequestOptions.circleCropTransform()).into(holder.image)
        holder.name.setText(usersList[position].login)
    }

    fun reloadData(newList: ArrayList<UserEntry>){
        usersList = newList
        notifyDataSetChanged()
    }
}