package com.appham.postlister.utils

import android.view.View
import androidx.test.espresso.IdlingResource

/**
 * @author thomas
 */

class ViewIdlingResource(private val view: View) : IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return this.toString()
    }

    /**
     * The view is idle if it is not visible. (useful for loading bars)
     * @return true if the view is currently idle
     */
    override fun isIdleNow(): Boolean {

        val idle = view.visibility != View.VISIBLE
        if (idle && resourceCallback != null) {
            resourceCallback!!.onTransitionToIdle()
        }
        return view.visibility != View.VISIBLE
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.resourceCallback = callback
    }
}