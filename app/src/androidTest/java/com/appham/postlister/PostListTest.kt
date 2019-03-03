package com.appham.postlister

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.appham.postlister.utils.ViewDisappearInstruction
import com.appham.postlister.utils.atPosition
import com.appham.postlister.view.MainActivity
import com.azimolabs.conditionwatcher.ConditionWatcher.waitForCondition
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PostListTest {

//    private val progressBarPosts by lazy {
//        ViewIdlingResource(activityRule.activity.findViewById(R.id.progressBarPosts))
//    }

    @get:Rule
    val activityRule by lazy {
        ActivityTestRule<MainActivity>(MainActivity::class.java)
    }

    @Before
    fun setup() {
        waitForCondition(ViewDisappearInstruction(activityRule.activity, R.id.progressBarPosts))
    }

    /**
     * Test that post list is displayed
     */
    @Test
    fun testPostListDisplayed() {
        onView(withId(R.id.listPosts)).check(matches(isDisplayed()))
    }

    /**
     * Test that "sunt aut.." comes as first title
     */
    @Test
    fun testPostListShowsFirstTitle() {
        val firstTitle = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
        onView(withId(R.id.listPosts))
            .check(matches(atPosition(0, hasDescendant(withText(firstTitle)))))
    }

    /**
     * Test that "quia et suscipit" comes as first body
     */
    @Test
    fun testPostListShowsFirstBody() {
        val firstBody = "quia et suscipit\n" +
                "suscipit recusandae consequuntur expedita et cum\n" +
                "reprehenderit molestiae ut ut quas totam\n" +
                "nostrum rerum est autem sunt rem eveniet architecto"
        onView(withId(R.id.listPosts))
            .check(matches(atPosition(0, hasDescendant(withText(firstBody)))))
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
