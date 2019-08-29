package ru.githubusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.githubusers.db.UserData
import ru.githubusers.db.UserViewModel
import ru.githubusers.model.UserRepo
import java.lang.Exception

class DetailsActivity : AppCompatActivity() {
    lateinit var userLogin: TextView
    lateinit var userName: TextView
    lateinit var userImage: ImageView
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var  bundle : Bundle
    lateinit var adapter : RepoAdapter
    var repoList = ArrayList<UserRepo>()
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        bundle = intent.extras
        userLogin = findViewById(R.id.textView3)
        userName = findViewById(R.id.textView2)
        userImage = findViewById(R.id.imageView2)
        toolbar = findViewById(R.id.toolbar2)
        recyclerView = findViewById(R.id.rv2)
        userLogin.text = bundle.get("userLogin").toString()
        Glide.with(this)
            .load(bundle.get("userAvatar").toString())
            .apply(RequestOptions.circleCropTransform())
            .into(userImage)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setTitle("")
        toolbar.setNavigationOnClickListener { finish() }

        adapter = RepoAdapter(repoList)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        if(bundle.getBoolean("Offline")){
            repoList = bundle.getSerializable("userData") as ArrayList<UserRepo>
            adapter.reloadData(repoList)
        }else{
            getUserInformation()
            getUserRepos(bundle.get("userLogin").toString())
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return (when(item!!.itemId) {
            R.id.item_favorite -> {
                val list = ArrayList<UserData>()
                list.add(UserData(bundle.get("userLogin").toString(),bundle.get("userAvatar").toString(), repoList))
                userViewModel.insert(list)
                Toast.makeText(this,"Добавлено в избранное",Toast.LENGTH_SHORT).show()
                true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        })
    }

    private fun getUserInformation(){
        GlobalScope.launch(Dispatchers.Main){
            val postRequest = Api.getUsers.getUserDetails(bundle.get("userLogin").toString())
            try{
                val responce = postRequest.await()
                if(responce.body() != null){
                   userName.text = responce.body()!!.name
                }
            }catch (e: Exception){
                Log.e("Exception", "Error getting user information")
            }
        }
    }

    private fun getUserRepos(userLogin:String){
        GlobalScope.launch(Dispatchers.Main){
            val postRequest = Api.getUsers.getUserRepos(userLogin)
            try{
                val responce = postRequest.await()
                if(responce.body() != null){
                    repoList = responce.body()!!
                    adapter.reloadData(repoList)
                }else{
                    repoList.clear()
                    adapter.notifyDataSetChanged()
                }
            }catch (e: Exception){
                Log.e("Exception", "Error getting user repo information")
            }
        }
    }
}
