package com.example.githubuserapi.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapi.R
import com.example.githubuserapi.adapter.DetailUserAdapter
import com.example.githubuserapi.adapter.SectionPagerAdapter
import com.example.githubuserapi.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.tab_layout.*

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var adapter: DetailUserAdapter

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        adapter = DetailUserAdapter()

        val username = intent.getStringExtra(EXTRA_USERNAME)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        detailViewModel.setUserDetail(username!!)
        detailViewModel.getDetailUser().observe(this, Observer { detailItemUser ->
            if (detailItemUser != null) {
                adapter.setData(detailItemUser)
            }
        })
        recyclerViewOptions()
        initToolbar()
        pagerAdapter()
    }

    private fun recyclerViewOptions() {
        recyclerView_detail.layoutManager = LinearLayoutManager(this)
        recyclerView_detail.adapter = adapter
    }

    private fun pagerAdapter() {
        val sectionsPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tab_layout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.changeLanguageSetting) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initToolbar() {
        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.title_detail)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

}