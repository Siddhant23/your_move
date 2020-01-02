package com.coroutinedispatcher.yourmove.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.coroutinedispatcher.yourmove.utils.DIFF_UTIL_CARDS
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CardAdapter @Inject constructor(
    private val picasso: Picasso,
    private val cardAdapterContract: CardAdapterContract
) :
    ListAdapter<YuGiOhCard, CardAdapter.CardViewHolder>(DIFF_UTIL_CARDS), Filterable {
    private lateinit var fullListOfData: MutableList<YuGiOhCard>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_item_card,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position), picasso, cardAdapterContract)
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(
            item: YuGiOhCard?,
            picasso: Picasso,
            cardAdapterContract: CardAdapterContract
        ) {
            with(itemView) {
                val tvCardName = findViewById<TextView>(R.id.tv_card_name)
                val tvCardType = findViewById<TextView>(R.id.tv_card_type)
                val ivYuGiOhImage = findViewById<ImageView>(R.id.iv_single_yugioh_image)
                val cardViewHolder = findViewById<MaterialCardView>(R.id.cv_holder)
                tvCardName.text = item?.name
                tvCardType.text = "${item?.type}, ${item?.race}"
//                picasso.load(item?.imageUrlSmall.toString())
//                    .resize(280, 410)
//                    .placeholder(R.drawable.yugioh_facedown_card)
//                    .error(R.drawable.yugioh_facedown_card)
//                    .into(ivYuGiOhImage)
                cardViewHolder.setOnClickListener {
                    //item?.id?.let { it1 -> cardAdapterContract.onCardClick(it1) }
                }
            }
        }
    }

    override fun getFilter(): Filter = object : Filter() {
        @SuppressLint("DefaultLocale")
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val stringForFilter = constraint.toString()
            val fullList: MutableList<YuGiOhCard>
            fullList = if (stringForFilter.isNotEmpty()) {
                val filteredList = mutableListOf<YuGiOhCard>()
                fullListOfData.forEach {
                    if (it.name?.toLowerCase()?.contains(stringForFilter)!!) {
                        filteredList.add(it)
                    }
                }
                filteredList
            } else {
                fullListOfData
            }
            val filterResults = FilterResults()
            filterResults.values = fullList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val cardListFiltered: MutableList<YuGiOhCard> =
                results?.values as MutableList<YuGiOhCard>
            submitList(cardListFiltered)
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<YuGiOhCard>,
        currentList: MutableList<YuGiOhCard>
    ) {
        cardAdapterContract.scrollToTop()
        super.onCurrentListChanged(previousList, currentList)
    }

    fun saveListState(listOfData: List<YuGiOhCard>?) {
        fullListOfData = listOfData?.toMutableList() ?: mutableListOf()
    }
}