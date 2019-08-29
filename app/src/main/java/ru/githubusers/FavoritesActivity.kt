package ru.githubusers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.githubusers.R
import ru.githubusers.db.UserData
import ru.githubusers.db.UserViewModel
import ru.githubusers.utils.RecycleViewTouchListener

class FavoritesActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var favAdapter:FavoritesAdapter
    lateinit var userViewModel: UserViewModel
    lateinit var recyclerView: RecyclerView
    var usersList = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        toolbar = findViewById(R.id.toolbar3)
        recyclerView = findViewById(R.id.rv3)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setTitle("Сохраненные")
        toolbar.setNavigationOnClickListener { finish() }

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.allUsers.observe(this, Observer {list ->
            usersList = list as ArrayList<UserData>
            favAdapter = FavoritesAdapter(usersList)
            recyclerView.adapter = favAdapter
            recyclerView.hasFixedSize()
            recyclerView.layoutManager = LinearLayoutManager(this)
        })

        recyclerView.addOnItemTouchListener(RecycleViewTouchListener(this,recyclerView, object : RecycleViewTouchListener.TouchListener {
            override fun onClick(view: View, position: Int) {
                var bundle = Bundle()
                bundle.putBoolean("Offline", true)
                bundle.putString("userLogin", usersList[position].userLogin)
                bundle.putString("userAvatar", usersList[position].userAvatar)
                bundle.putSerializable("userData", usersList[position].data)
                val intent = Intent("android.intent.action.DetailsActivity")
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }))
    }
}
