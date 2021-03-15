package com.basim.bitcoinstats.uitests.tests

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.basim.bitcoinstats.MainActivity
import com.basim.bitcoinstats.uitests.pages.HomePage
import com.basim.bitcoinstats.utils.CountingIdlingResource
import com.basim.bitcoinstats.utils.Utils
import org.junit.*
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


/**
 * UI Automation test cases to verify major UI flows
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4::class)
class UITests {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var currentTestName = TestName()

    //POM object for home page
    private val homePage = HomePage()

    val chartList = Utils.getCharts()

    /**
     * Register the IdlingResource for tests to wait for API calls
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(CountingIdlingResource.countingIdlingResource)
    }

    /**
     * This test case will verify if home page displayed properly
     */
    @Test
    fun verifyHomeScreenLaunched() {
        homePage.verifyHomePageItems()
    }

    /**
     * This test case will verify chart recyler view shown properly
     */
    @Test
    fun verifyChartListShown() {
        homePage.verifyChartRecyclerView(chartList)
    }

    /**
     * This test case will verify chart recycler view shown properly
     */
    @Test
    fun verifyChartListClick() {
        homePage.verifyChartRecyclerViewClick(chartList)
    }

    /**
     * This test case will verify if error message is showing properly in offline scenario
     */
    @Test
    fun verifyOfflineScenario(){
        //Turn off the WIFI
        InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi disable")
        Thread.sleep(1000)
        homePage.tapRecyclerViewItem(1)
        homePage.verifyErrorSnackBar()
    }

    /**
     * Unregister the IdlingResource and cleanup the tests
     */
    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(CountingIdlingResource.countingIdlingResource)
        //Turn on WIFI back after offline scenario test
        if(currentTestName.methodName == "verifyOfflineScenario") {
            InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("svc wifi enable")
            //Will wait for some time for WIFI reconnection so that other tests wont get affected
            Thread.sleep(3000)
        }
    }

}