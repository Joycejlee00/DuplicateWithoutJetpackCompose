package com.example.bottomnavwfab


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class checkForExistingRecyclerview {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkForExistingRecyclerview() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.editTextTextPersonName), withText("null"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("tomato"))

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.editTextTextPersonName), withText("tomato"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.generateButton), withText("Generate Recipes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_activity_main),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerView),
                withParent(withParent(withId(R.id.lin_layout))),
                isDisplayed()
            )
        )
        recyclerView.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
