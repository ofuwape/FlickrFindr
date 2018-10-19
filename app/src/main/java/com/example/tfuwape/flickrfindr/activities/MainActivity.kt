package com.example.tfuwape.flickrfindr.activities

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.tfuwape.flickrfindr.R
import com.example.tfuwape.flickrfindr.adapters.PhotoSearchAdapter
import com.example.tfuwape.flickrfindr.adapters.SuggestionAdapter
import com.example.tfuwape.flickrfindr.builders.SearchParamsBuilder
import com.example.tfuwape.flickrfindr.core.APIService
import com.example.tfuwape.flickrfindr.fragments.DetailDialogFragment
import com.example.tfuwape.flickrfindr.holder.PhotoItemViewHolder
import com.example.tfuwape.flickrfindr.holder.SuggestionItemViewHolder
import com.example.tfuwape.flickrfindr.listeners.FetchSearchTermsListener
import com.example.tfuwape.flickrfindr.listeners.FoundSearchTermListener
import com.example.tfuwape.flickrfindr.listeners.PagingScrollListener
import com.example.tfuwape.flickrfindr.models.PhotoItem
import com.example.tfuwape.flickrfindr.models.containers.PhotoSearchContainer
import com.example.tfuwape.flickrfindr.util.*
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class MainActivity : InjectableBaseActivity(),
        PagingScrollListener.OnPageStateListener,
        FetchSearchTermsListener, FoundSearchTermListener {

    @BindView(R.id.searchView)
    lateinit var searchView: SearchView

    @BindView(R.id.searchRecyclerView)
    lateinit var searchRecyclerView: RecyclerView

    @BindView(R.id.suggestionRecyclerView)
    lateinit var suggestionRecyclerView: RecyclerView

    lateinit var photoSearchAdapter: PhotoSearchAdapter
    lateinit var suggestionAdapter: SuggestionAdapter
    private var mScrollListener: PagingScrollListener = PagingScrollListener()
    private var currentQuery: String = ""
    private var disposableListener: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)
        photoSearchAdapter = PhotoSearchAdapter(this,
                PhotoItemViewHolder.PhotoItemClickListener { position: Int ->
                    onPhotoClick(position)
                })
        suggestionAdapter = SuggestionAdapter(this,
                SuggestionItemViewHolder.SuggestionClickListener { position: Int ->
                    onSuggestionClick(position)
                })
        configureViews()
    }

    /**
     * Configure user interface for activity
     */
    private fun configureViews() {
        //SearchView
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.addItemDecoration(LineItemDecoration(ContextCompat.getDrawable(this, android.R.color.darker_gray)))
        searchRecyclerView.adapter = photoSearchAdapter
        addSearchViewListener()

        mScrollListener.setOnChangeStateListener(this)
        searchRecyclerView.addOnScrollListener(mScrollListener)

        //Action Bar
        supportActionBar?.let { mSupportActionBar: ActionBar ->
            val actionBarTitle: String = mSupportActionBar.title.toString() + " - explore for photos..."
            mSupportActionBar.title = actionBarTitle
        }

        //Suggestion
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        suggestionRecyclerView.layoutManager = linearLayoutManager
        suggestionRecyclerView.adapter = suggestionAdapter
    }

    /**
     * Listen to search query text changes
     */
    private fun addSearchViewListener() {
        disposableListener = CompositeDisposable()
        disposableListener?.add(
                RxSearchView.queryTextChanges(searchView)
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { charSequence: CharSequence ->
                            val query = charSequence.toString()
                            handleQuery(query)
                            saveTermToDB(query)
                        })
    }

    private fun saveTermToDB(searchTerm: String) {
        CheckSearchTermTask(searchTerm.toLowerCase(), this).execute()
    }

    private fun handleQuery(query: String) {
        val cleanQuery = query.trim()
        if (!cleanQuery.isEmpty()) {
            currentQuery = cleanQuery
            retrievePhotoItems(cleanQuery, 1)
            suggestionAdapter.resetTerms()
            suggestionRecyclerView.visibility = View.GONE
        } else {
            FetchSearchTermsTask(this).execute()
            photoSearchAdapter.resetPhotoItems()
        }
    }


    //API Helpers
    /**
     * Fires api search for photos based on query.
     */
    private fun retrievePhotoItems(text: String, pageNumber: Int) {
        val context: Context = this
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
                    Toast.makeText(context, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
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
    private fun onPhotoClick(position: Int) {
        val selectedItem: PhotoItem? = photoSearchAdapter.getPhotoItem(position)
        if (selectedItem != null) {

            val detailDialogFragment: DetailDialogFragment = DetailDialogFragment.newInstance()
            val bundle = Bundle()
            bundle.putSerializable(DetailDialogFragment.PHOTO_ITEM_KEY, selectedItem)
            detailDialogFragment.arguments = bundle

            val transaction = supportFragmentManager.beginTransaction()
            val prevFragment = supportFragmentManager.findFragmentByTag("detail_dialog")
            if (prevFragment != null) {
                transaction.remove(prevFragment)
            }
            transaction.addToBackStack(null)
            detailDialogFragment.show(transaction, "detail_dialog")
        }
    }

    private fun onSuggestionClick(position: Int) {
        val searchTerm: String? = suggestionAdapter.getTerm(position)
        if (searchTerm != null) {
            searchView.setQuery(searchTerm, false)
        }
    }

    //Pagination Listener
    override fun onLoadNextPage(nextPageNumber: Int) {
        retrievePhotoItems(currentQuery, nextPageNumber)
    }

    // FetchSearchTermsListener
    override fun foundTerms(terms: List<String>) {
        suggestionAdapter.setTerms(terms)
        suggestionRecyclerView.visibility = if (terms.isEmpty())
            View.GONE else View.VISIBLE
    }

    // Found Previous SearchTerm Listener
    override fun didFindTerm(result: Boolean, query: String) {
        if (!result && !query.isEmpty()) {
            SaveSearchTermTask(query).execute()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableListener?.dispose()
    }


}