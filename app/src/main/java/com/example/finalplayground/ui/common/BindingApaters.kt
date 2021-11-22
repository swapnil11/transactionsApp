package com.example.finalplayground.ui.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.finalplayground.R
import com.example.finalplayground.domain.model.Category

/**
 * Defines binding adapter downloadURL which can be used in the xml directly along with
 * data binding to specify download url which is passed along to this function when the XML is
 * inflated and the corresponding image get downloaded using glide and added to the imageview.
 */
@BindingAdapter("setDrawableAsPerCategory")
fun loadImage(imageView: ImageView, category: Category) {
    when (category) {
        Category.BUSINESS -> imageView.setImageResource(R.drawable.icon_category_business)
        Category.CARDS -> imageView.setImageResource(R.drawable.icon_category_cards)
        Category.CASH -> imageView.setImageResource(R.drawable.icon_category_cash)
        Category.CATEGORIES -> imageView.setImageResource(R.drawable.icon_category_categories)
        Category.EATING_OUT -> imageView.setImageResource(R.drawable.icon_category_eating_out)
        Category.EDUCATION -> imageView.setImageResource(R.drawable.icon_category_education)
        Category.ENTERTAINMENT -> imageView.setImageResource(R.drawable.icon_category_entertainment)
        Category.GROCERIES -> imageView.setImageResource(R.drawable.icon_category_groceries)
        Category.SHOPPING -> imageView.setImageResource(R.drawable.icon_category_shopping)
        Category.TRANSPORTATION -> imageView.setImageResource(R.drawable.icon_category_transportation)
        Category.TRAVEL -> imageView.setImageResource(R.drawable.icon_category_travel)
        Category.UNCATEGORISED -> imageView.setImageResource(R.drawable.icon_category_uncategorised)
    }
}
