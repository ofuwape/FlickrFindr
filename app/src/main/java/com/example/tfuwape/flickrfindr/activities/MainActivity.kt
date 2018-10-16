package com.example.tfuwape.flickrfindr.activities

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import butterknife.ButterKnife
import com.example.tfuwape.flickrfindr.R
import com.example.tfuwape.flickrfindr.adapter.PhotoSearchAdapter

class MainActivity : InjectableBaseActivity() {


    private lateinit var photoSearchAdapter:PhotoSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)
        photoSearchAdapter = PhotoSearchAdapter(this, api)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        configureSearchMenu(menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun configureSearchMenu(menu: Menu?) {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManagerObject = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        val searchItemObject = menu?.findItem(R.id.action_search)

        searchItemObject?.let { searchItem ->
            searchManagerObject?.let { searchManager ->

                val searchView = searchItem.actionView as SearchView
                searchView.setSearchableInfo(searchManager.getSearchableInfo(ComponentName(this,
                        SearchActivity::class.java)))
                handleTitleView(searchView)

                searchView.suggestionsAdapter = photoSearchAdapter
                addSearchViewListener(searchManager,searchView,photoSearchAdapter)

            }
        }

    }

    private fun addSearchViewListener(searchManager: SearchManager, searchView: SearchView,
                                      suggestionAdapterPhoto: PhotoSearchAdapter) {
        val activity = this
        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionClick(position: Int): Boolean {
                val query = suggestionAdapterPhoto.getTextAtPosition(position)
                searchView.setSearchableInfo(searchManager
                        .getSearchableInfo(ComponentName(activity, SearchActivity::class.java)))
                searchView.setQuery(query, true)
                return false
            }

            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }
        })
    }

    private fun handleTitleView(searchView: SearchView) {
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            supportActionBar?.setDisplayShowTitleEnabled(!hasFocus)
        }
    }

}