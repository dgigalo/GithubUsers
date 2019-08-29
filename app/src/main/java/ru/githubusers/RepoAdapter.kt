package ru.githubusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_repo.view.*
import ru.githubusers.model.UserRepo

class RepoAdapter (var repoList: ArrayList<UserRepo>) : androidx.recyclerview.widget.RecyclerView.Adapter<RepoAdapter.CustomViewHolder>() {

    class CustomViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val name = itemView.textView4
        val desc = itemView.textView5
        val lang = itemView.textView6
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.card_repo,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.name.setText(repoList[position].name)
        holder.desc.setText(repoList[position].description)
        holder.lang.setText(repoList[position].language)
    }

    fun reloadData(newList: ArrayList<UserRepo>){
        repoList = newList
        notifyDataSetChanged()
    }
}