package ru.githubusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_favorite.view.*
import ru.githubusers.db.UserData

class FavoritesAdapter(var repoList: ArrayList<UserData>) : RecyclerView.Adapter<FavoritesAdapter.CustomViewHolder>() {
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.textView7
        val desc = itemView.textView8
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(p0.context).inflate(R.layout.card_favorite, p0,false)
        )
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.name.setText("Логин: " + repoList[position].userLogin)
        holder.desc.setText(repoList[position].data.size.toString() + " репозиториев")
    }

    fun reloadData(newList: ArrayList<UserData>){
        repoList = newList
        notifyDataSetChanged()
    }
}