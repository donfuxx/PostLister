package com.appham.postlister.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.azimolabs.conditionwatcher.Instruction

class ViewDisappearInstruction(private val activity: AppCompatActivity,
                               private val resId: Int) : Instruction() {

    override fun getDescription(): String = "ProgressBar should have disappeared"

    override fun checkCondition(): Boolean {
        return activity.window.findViewById<View>(android.R.id.content).findViewById<View>(resId)?.visibility != View.VISIBLE
    }
}