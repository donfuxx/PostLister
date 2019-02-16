package com.appham.postlister

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.appham.postlister.utils.ViewIdlingResource
import com.appham.postlister.utils.atPosition
import com.appham.postlister.view.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PostListTest {

    private val progressBar by lazy {
        ViewIdlingResource(activityRule.activity.findViewById(R.id.progressBarPosts))
    }

    @get:Rule
    val activityRule by lazy {
        ActivityTestRule<MainActivity>(MainActivity::class.java)
    }

    @Before
    fun setup() {
        IdlingPolicies.setMasterPolicyTimeout(4000, TimeUnit.MILLISECONDS)
        IdlingPolicies.setIdlingResourceTimeout(2000, TimeUnit.MILLISECONDS)

        IdlingRegistry.getInstance().register(progressBar) //wait for loading spinner to disappear
    }

    @After
    fun cleanup() {
        IdlingRegistry.getInstance().unregister(progressBar)
    }

    /**
     * Test that post list is displayed
     */
    @Test
    fun testPostListDisplayed() {
        onView(withId(R.id.listPosts)).check(matches(isDisplayed()))
    }

    /**
     * Test that "sunt aut.." comes first
     */
    @Test
    fun testPostListShowsFirst() {
        val firstTitle = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
        onView(withId(R.id.listPosts))
            .check(matches(atPosition(0, hasDescendant(withText(firstTitle)))))
    }

    /**
     * Test that click on first Item starts details activity
     */
    @Test
    fun testPostListClickFirstOpensDetails() {
        onView(withId(R.id.listPosts))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.txtDetailsUser)).check(matches(allOf(isDisplayed(), withText("Leanne Graham"))))
    }

    /**
     * Test that list item number 100 can be scrolled to an is visible
     */
    @Test
    fun testPostListShowHundredItems() {

        onView(withId(R.id.listPosts))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(99, scrollTo()))

        onView(withId(R.id.listPosts))
            .check(matches(atPosition(99, isDisplayed())))
    }

}