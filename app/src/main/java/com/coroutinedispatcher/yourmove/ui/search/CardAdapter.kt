package com.coroutinedispatcher.yourmove.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coroutinedispatcher.yourmove.R
import com.coroutinedispatcher.yourmove.model.YuGiOhCard
import com.coroutinedispatcher.yourmove.utils.DIFF_UTIL_CARDS
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CardAdapter @Inject constructor(
    private val picasso: Picasso
) :
    ListAdapter<YuGiOhCard, CardAdapter.CardViewHolder>(DIFF_UTIL_CARDS) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_item_card,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position), picasso)
    }

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: YuGiOhCard?,
            picasso: Picasso
        ) {
            with(itemView) {
                val tvCardName = findViewById<TextView>(R.id.tv_card_name)
                val tvCardType = findViewById<TextView>(R.id.tv_card_type)
                val ivYuGiOhImage = findViewById<ImageView>(R.id.iv_single_yugioh_image)
                tvCardName.text = item?.name
                tvCardType.text = item?.type
                item?.cardImages?.let {
                    if (it.isNotEmpty()) {
                        val url = it[0].imageUrlSmall
                        picasso.load(url.toString()).fit().centerCrop()
                            .placeholder(R.drawable.yugioh_facedown_card)
                            .error(R.drawable.yugioh_facedown_card)
                            .into(ivYuGiOhImage)
                    } else {
                        picasso.load(R.drawable.yugioh_facedown_card)
                            .fit()
                            .centerCrop()
                            .into(ivYuGiOhImage)
                    }
                }
            }

        }
    }
}