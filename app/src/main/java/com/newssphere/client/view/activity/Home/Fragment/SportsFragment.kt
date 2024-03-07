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
import com.newssphere.client.view.adapter.ItemNewsAdapter
import com.newssphere.client.view.advanced_ui.PopUpDialogListener
import com.newssphere.client.view.advanced_ui.showPopUpDialog
import com.newssphere.client.viewmodel.HomeViewModel

class SportsFragment : Fragment() {
    private val TAG = SportsFragment::class.java.simpleName
    private lateinit var binding: FragmentNewsBinding
    private var deliveredCategory: String?= null
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var categoriesHomeCommunicator: CategoriesHomeCommunicator
    private var currentPage: Int? = null

    companion object{
        private const val DELIVERED_CATEGORY = "DELIVERED_CATEGORY"

        fun newInstance(deliveredCategory: String?= null): SportsFragment{
            val fragment = SportsFragment()
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

        homeViewModel.isLoading.observe(this@SportsFragment.requireActivity(), {
            if(it) categoriesHomeCommunicator.startLoading() else categoriesHomeCommunicator.stopLoading()
        })

        homeViewModel.isFail.observe(this@SportsFragment.requireActivity(), {
            if(it){
                this@SportsFragment.requireActivity().showPopUpDialog(
                    getString(R.string.tvPopUpDescription),
                    R.drawable.earth_cry,
                    object: PopUpDialogListener {
                        override fun onClickListener() {
                            this@SportsFragment.requireActivity().closeOptionsMenu()
                        }

                    }
                )
            }
        })

        homeViewModel.newsCollection.observe(this@SportsFragment.requireActivity(), {collectionNews->
            binding.apply {
                rvItemNews.apply {
                    val itemNewsAdapter = ItemNewsAdapter(
                        collectionNews.articles!!.toMutableList(),
                        object: ItemNewsAdapter.ItemListener{
                            override fun onItemClicked(item: Article) {
                                this@SportsFragment.requireActivity().startActivity(
                                    DetailActivity.newIntent(
                                        this@SportsFragment.requireActivity(),
                                        deliveredCategory!!,
                                        item
                                    )
                                )
                                this@SportsFragment.requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            }
                        }
                    )

                    val myLayoutManager = LinearLayoutManager(this@SportsFragment.requireActivity())
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
                                homeViewModel.getNewsCollectionMore(category = deliveredCategory, currentPage!!)
                                homeViewModel.newsCollection2.observe(this@SportsFragment.requireActivity(), {collectionNews2->
                                    if(collectionNews2.articles != null){
                                        itemNewsAdapter.addItem(collectionNews2.articles!!)
                                    }
                                })
                            }
                        }
                    })
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        currentPage = null
    }
}