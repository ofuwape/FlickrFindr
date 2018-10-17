package com.example.tfuwape.flickrfindr.activities

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.tfuwape.flickrfindr.R
import com.example.tfuwape.flickrfindr.adapters.PhotoSearchAdapter
import com.example.tfuwape.flickrfindr.builders.SearchParamsBuilder
import com.example.tfuwape.flickrfindr.core.APIService
import com.example.tfuwape.flickrfindr.holder.InjectableBaseRecyclerViewHolder
import com.example.tfuwape.flickrfindr.listeners.PagingScrollListener
import com.example.tfuwape.flickrfindr.models.PhotoItem
import com.example.tfuwape.flickrfindr.models.containers.PhotoSearchContainer
import com.example.tfuwape.flickrfindr.util.LineItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : InjectableBaseActivity(), InjectableBaseRecyclerViewHolder.OnClickListener, PagingScrollListener.OnPageStateListener {

    @BindView(R.id.searchView)
    lateinit var searchView: SearchView

    @BindView(R.id.searchRecyclerView)
    lateinit var searchRecyclerView: RecyclerView

    private lateinit var photoSearchAdapter: PhotoSearchAdapter
    private var mScrollListener: PagingScrollListener = PagingScrollListener()

    private var currentQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)
        photoSearchAdapter = PhotoSearchAdapter(this, this)
        configureViews()
    }

    private fun configureViews() {
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.addItemDecoration(LineItemDecoration(ContextCompat.getDrawable(this, android.R.color.darker_gray)))
        searchRecyclerView.adapter = photoSearchAdapter
        addSearchViewListener()

        mScrollListener.setOnChangeStateListener(this)
        searchRecyclerView.addOnScrollListener(mScrollListener)

        supportActionBar?.let { mSupportActionBar: ActionBar ->
            val actionBarTitle: String = mSupportActionBar.title.toString() + " - explore for photos..."
            mSupportActionBar.title = actionBarTitle
        }

    }

    private fun addSearchViewListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                val cleanQuery = newText.trim()
                if (!cleanQuery.isEmpty()) {
                    currentQuery = cleanQuery
                    retrievePhotoItems(cleanQuery, 1)
                } else {
                    photoSearchAdapter.resetPhotoItems()
                }
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                //do nothing
                return true
            }
        })
        //TODO: open full screen dialog with image.
    }


    //API Helpers
    /**
     * Fires api search for photos based on query.
     */
    private fun retrievePhotoItems(text: String, pageNumber: Int) {
        api?.let { apiService: APIService ->
            val apiKey = resources.getString(R.string.api_key)
            val searchParams = SearchParamsBuilder().addSearchTerm(text).addPageNumber(pageNumber)
                    .addAPIKey(apiKey).toParams()

            apiService.searchTerm(searchParams).enqueue(object : Callback<PhotoSearchContainer> {
                override fun onResponse(call: Call<PhotoSearchContainer>,
                                        response: Response<PhotoSearchContainer>) {
                    handleQueryResponse(response, pageNumber)
                }

                override fun onFailure(call: Call<PhotoSearchContainer>, t: Throwable) {
                    photoSearchAdapter.resetPhotoItems()
                }
            })
        }
    }

    /**
     * Updates adapter with photoItems.
     */
    private fun handleQueryResponse(response: Response<PhotoSearchContainer>, pageNumber: Int) {
        val container = response.body()
        if (pageNumber == 1) {
            photoSearchAdapter.resetPhotoItems()
        }
        if (response.isSuccessful && container != null && container.photos != null) {
            val mPhotoItems: ArrayList<PhotoItem> = container.photos.photoItems
            if (mPhotoItems.isEmpty()) {
                Toast.makeText(this, getString(R.string.empty_search), Toast.LENGTH_SHORT).show()
            } else {
                photoSearchAdapter.addPhotoItems(mPhotoItems)
                mScrollListener.setPaginator(container.photos.paginator)
            }
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
        }
    }

    // Search Item Click Listeners
    override fun onClick(position: Int) {

    }

    //Pagination Listener
    override fun onLoadNextPage(nextPageNumber: Int) {
        retrievePhotoItems(currentQuery, nextPageNumber)
    }


}