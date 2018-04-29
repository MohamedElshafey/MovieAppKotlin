package com.example.digitalegyptlenovo.movieappkotlin.datamanager

/**
 * Created by Mohamed Elshafey on 4/29/2018.
 */
class CategoryManager {

    enum class Category {
        POPULAR, TOP_RATED, NOW_PLAYING, FAVORITE;
    }

    companion object {
        var selectedCategory: Category = Category.POPULAR

        fun setCategory(category: Category) {
            selectedCategory = category
        }

        fun getCategory(): Category {
            return selectedCategory
        }
    }
}