package com.example.tfuwape.flickrfindr.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.tfuwape.flickrfindr.R

class SearchActivity : InjectableBaseActivity() {

    private var extras: Bundle? = null
    private lateinit var mFragment: Fragment

    @BindView(R.id.toolbar_title)
    lateinit var mToolbarTitle: TextView

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
        ButterKnife.bind(this)
        val intent = intent
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            query?.let {
                setUpToolBar(query)
            }
        }

        val fragmentManager = supportFragmentManager
        mFragment = Fragment()
        mFragment.arguments = extras
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, mFragment)
        fragmentTransaction.commit()
    }

    private fun setUpToolBar(query: String) {
        setSupportActionBar(mToolbar)
        supportActionBar?.let { mSupportActionBar ->
            mSupportActionBar.setDisplayHomeAsUpEnabled(true)
            mSupportActionBar.title = ""
        }
        mToolbarTitle.text = String.format("\"%s\"", query)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val i = item.itemId
        if (i == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}