package ru.githubusers

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.githubusers.db.UserData
import ru.githubusers.db.UserViewModel
import ru.githubusers.model.UserEntry
import ru.githubusers.utils.RecycleViewTouchListener
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var mDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var actionBar: ActionBar
    lateinit var searchView: SearchView
    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    var users = ArrayList<UserEntry>()
    lateinit var adapter: UsersAdapter
    lateinit var imageView: ImageView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navview)
        searchView = findViewById(R.id.searchview)
        recyclerView = findViewById(R.id.rv)
        imageView = findViewById(R.id.imageView3)

        setSupportActionBar(toolbar)
        actionBar = supportActionBar!!
        setDrawer()

        searchView.queryHint = "Поиск пользователей"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.length>3)
                    complicatedSearch(newText)
                else{
                    imageView.visibility = View.VISIBLE
                    users.clear()
                    adapter.notifyDataSetChanged()
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })
        adapter = UsersAdapter(users,this)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addOnItemTouchListener(RecycleViewTouchListener(this,recyclerView, object : RecycleViewTouchListener.TouchListener {
            override fun onClick(view: View, position: Int) {
                var bundle = Bundle()
                bundle.putBoolean("Offline", false)
                bundle.putString("userLogin", users[position].login)
                bundle.putString("userAvatar", users[position].avatar_url)
                val intent = Intent("android.intent.action.DetailsActivity")
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }))
        navigationView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.itemRatings ->{
                        val intent = Intent("android.intent.action.FavoritesActivity")
                        startActivity(intent)
                    }
                }
                drawerLayout.closeDrawers()
                return false
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setDrawer(){
        mDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                setStatusBarTransparent()
            }
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
        }
        mDrawerToggle.setDrawerIndicatorEnabled(true)
        toolbar.setNavigationOnClickListener(View.OnClickListener { drawerLayout.openDrawer(Gravity.LEFT) })
        drawerLayout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        mDrawerToggle.getDrawerArrowDrawable().setColor(MainActivity@this.getColor(R.color.colorWhite))
    }

    private fun complicatedSearch(newText: String) {
        imageView.visibility = View.INVISIBLE
        GlobalScope.launch(Dispatchers.Main){
            val postRequest = Api.getUsers.getUsers(newText)
            try{
                val responce = postRequest.await()
                if(responce.body()?.items != null){
                    users = responce.body()!!.items!!
                    adapter.reloadData(users)
                }else{
                    users.clear()
                    adapter.notifyDataSetChanged()
                }
            }catch (e: Exception){
                Log.e("Exception", "Error getting new users")
            }
        }
    }

    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.getWindow()
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
        }
    }
}
