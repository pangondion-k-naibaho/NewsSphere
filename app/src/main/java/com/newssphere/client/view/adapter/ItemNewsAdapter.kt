package com.newssphere.client.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newssphere.client.R
import com.newssphere.client.databinding.ItemNewsLayoutBinding
import com.newssphere.client.model.data_class.Article
import java.text.SimpleDateFormat
import java.util.Locale

class ItemNewsAdapter(
    var data: MutableList<Article>,
    private val listener: ItemListener
): RecyclerView.Adapter<ItemNewsAdapter.ItemHolder>() {
    interface ItemListener{
        fun onItemClicked(item: Article)
    }

    inner class ItemHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding = ItemNewsLayoutBinding.bind(itemView)
        fun bind(item: Article, listener: ItemListener)= with(itemView){
            binding.apply {
                if(item.urlToImage.isNullOrEmpty()){
                    Glide.with(itemView.context)
                        .load(R.drawable.newssphere_logo)
                        .into(ivNews)
                }else{
                    Glide.with(itemView.context)
                        .load(item.urlToImage)
                        .into(ivNews)
                }

                tvNewsTitle.text = item.title
                tvNewsAuthor.text = item.author

                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val date = inputFormat.parse(item.publishedAt!!)
                val formattedDate = outputFormat.format(date!!)

                tvNewsPublished.text = formattedDate

                root.setOnClickListener {
                    listener.onItemClicked(item)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(data.get(position), listener)
    }

    fun addItem(listArticle: List<Article>){
        val startPosition = data.size
        data.addAll(listArticle)
        notifyItemRangeInserted(startPosition, listArticle.size)
    }
}