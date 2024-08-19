package com.openin.openinapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openin.openinapp.databinding.ItemLinkBinding
import com.openin.openinapp.model.Link

class LinksAdapter : RecyclerView.Adapter<LinksAdapter.LinkViewHolder>() {

    private var links: List<Link> = emptyList()

    fun setLinks(newLinks: List<Link>) {
        links = newLinks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val binding = ItemLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        holder.bind(links[position])
    }

    override fun getItemCount(): Int = links.size

    class LinkViewHolder(private val binding: ItemLinkBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(link: Link) {
            binding.link = link
            binding.executePendingBindings()

            Glide.with(binding.imageView.context)
                .load(link.original_image)
                .into(binding.imageView)
        }
    }
}