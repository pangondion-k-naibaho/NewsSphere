package com.newssphere.client.view.activity.Home.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newssphere.client.R
import com.newssphere.client.databinding.FragmentNewsBinding
import com.newssphere.client.model.data_class.Article
import com.newssphere.client.view.activity.Detail.DetailActivity
import com.newssphere.client.view.activity.Home.CategoriesHomeCommunicator
import com.newssphere.client.view.activity.Home.HomeCategoriesCommunicator
import com.newssphere.client.view.adapter.ItemNewsAdapter
import com.newssphere.client.view.advanced_ui.PopUpDialogListener
import com.newssphere.client.view.advanced_ui.showPopUpDialog
import com.newssphere.client.viewmodel.HomeViewModel

class ScienceFragment : Fragment(), HomeCategoriesCommunicator {
    private val TAG = ScienceFragment::class.java.simpleName
    private lateinit var binding: FragmentNewsBinding
    private var deliveredCategory: String?= null
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var categoriesHomeCommunicator: CategoriesHomeCommunicator
    private var currentPage: Int? = null
    private var itemNewsAdapter: ItemNewsAdapter? = null

    companion object{
        private const val DELIVERED_CATEGORY = "DELIVERED_CATEGORY"

        fun newInstance(deliveredCategory: String?= null): ScienceFragment{
            val fragment = ScienceFragment()
            val bundle = Bundle()
            bundle.putString(DELIVERED_CATEGORY, deliveredCategory)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.bind(
            inflater.inflate(
                R.layout.fragment_news,
                container,
                false
            )
        )

        deliveredCategory = arguments?.getString(DELIVERED_CATEGORY).toString()
        Log.d(TAG, "delivered Category: $deliveredCategory")

        categoriesHomeCommunicator = activity as CategoriesHomeCommunicator

        setUpView()

        return binding.root
    }

    private fun setUpView(){
        currentPage = 1
        homeViewModel.getNewsCollection(category = deliveredCategory, page = currentPage!!)

        homeViewModel.isLoading.observe(this@ScienceFragment.requireActivity(), {
            if(it) categoriesHomeCommunicator.startLoading() else categoriesHomeCommunicator.stopLoading()
        })

        homeViewModel.isFail.observe(this@ScienceFragment.requireActivity(), {
            if(it){
                this@ScienceFragment.requireActivity().showPopUpDialog(
                    getString(R.string.tvPopUpDescription),
                    R.drawable.earth_cry,
                    object: PopUpDialogListener {
                        override fun onClickListener() {
                            this@ScienceFragment.requireActivity().closeOptionsMenu()
                        }

                    }
                )
            }
        })

        homeViewModel.newsCollection.observe(this@ScienceFragment.requireActivity(), {collectionNews->
            binding.apply {
                rvItemNews.apply {
                    itemNewsAdapter = ItemNewsAdapter(
                        collectionNews.articles!!.toMutableList(),
                        object: ItemNewsAdapter.ItemListener{
                            override fun onItemClicked(item: Article) {
                                this@ScienceFragment.requireActivity().startActivity(
                                    DetailActivity.newIntent(
                                        this@ScienceFragment.requireActivity(),
                                        deliveredCategory!!,
                                        item
                                    )
                                )
                                this@ScienceFragment.requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            }
                        }
                    )

                    val myLayoutManager = LinearLayoutManager(this@ScienceFragment.requireActivity())
                    adapter = itemNewsAdapter
                    layoutManager = myLayoutManager

                    addOnScrollListener(object: RecyclerView.OnScrollListener(){
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)

                            val visibleItemCount = myLayoutManager.childCount
                            val totalItemCount = myLayoutManager.itemCount
                            val firstVisibleItemPosition = myLayoutManager.findFirstVisibleItemPosition()

                            if((visibleItemCount+firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0
                                && totalItemCount >= collectionNews.articles!!.size
                            ){
                                currentPage = currentPage?.plus(1)
                                homeViewModel.getNewsCollectionMore(category = deliveredCategory, page = currentPage!!)
                                homeViewModel.newsCollection2.observe(this@ScienceFragment.requireActivity(), {collectionNews2->
                                    if(collectionNews2.articles != null){
                                        itemNewsAdapter!!.addItem(collectionNews2.articles!!)
                                    }
                                })
                            }
                        }
                    })
                }
            }
        })
    }

    override fun searchOnSelectedCategories(inputString: String) {
        Log.d(TAG, "searchOnSelectedCategories: $inputString")
        currentPage = 1

        homeViewModel.getNewsCollectionMore(category = deliveredCategory, searchInput = inputString, page = currentPage!!)

        homeViewModel.newsCollection2.observe(this@ScienceFragment, {collectionNews ->
            Log.d(TAG, "result : ${collectionNews.articles}")
            if(!collectionNews.articles.isNullOrEmpty()){
                itemNewsAdapter!!.updateItem(collectionNews.articles!!)
            }else{
                Toast.makeText(this@ScienceFragment.requireActivity(), "Shows no result", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun clearSearching() {
        currentPage = 1
        homeViewModel.getNewsCollectionMore(category = null, searchInput = null, page = currentPage!!)

        homeViewModel.newsCollection2.observe(this@ScienceFragment.requireActivity(), {collectionNews ->
            if(!collectionNews.articles.isNullOrEmpty()){
                itemNewsAdapter!!.updateItem(collectionNews.articles!!)
            }else{
                Toast.makeText(this@ScienceFragment.requireActivity(), "Couldn't fetch data", Toast.LENGTH_SHORT).show()
            }
        })
    }

}