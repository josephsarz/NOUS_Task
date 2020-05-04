package codegene.femicodes.noustask.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import codegene.femicodes.noustask.R
import codegene.femicodes.noustask.models.User
import com.bumptech.glide.Glide

class ItemListAdapter(private val clickListener: (User, ImageView) -> Unit) :
    ListAdapter<User, ItemListAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTV: TextView = itemView.findViewById(R.id.title)
        private val artworkIV: ImageView = itemView.findViewById(R.id.artwork)

        fun bind(
            item: User,
            clickListener: (User, ImageView) -> Unit
        ) = with(itemView) {

            artworkIV.apply {
                transitionName = item.imageUrl
                Glide.with(context)
                    .load(item.imageUrl)
                    .into(this)
            }


            titleTV.text = item.title

            setOnClickListener {
                clickListener(item, artworkIV)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}