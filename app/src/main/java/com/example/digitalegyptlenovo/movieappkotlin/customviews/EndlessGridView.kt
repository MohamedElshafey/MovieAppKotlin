package com.example.digitalegyptlenovo.movieappkotlin.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

/**
 * Created by Mohamed Elshafey on 4/18/2018.
 */
class EndlessGridView : GridView {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

    }
}