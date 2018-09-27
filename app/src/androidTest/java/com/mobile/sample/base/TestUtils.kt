package com.mobile.sample.base

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.intercepting.SingleActivityFactory
import com.mobile.sample.main.MainActivity

object TestUtils {

    inline fun <reified Type : Activity> launchActivity(rule: ActivityTestRule<Type>) {
        val intent = Intent(InstrumentationRegistry.getTargetContext(), Type::class.java)
        rule.launchActivity(intent)
    }

    inline fun <reified Type : Activity> createActivityFactory(viewModel: ViewModel): SingleActivityFactory<Type> {
        return object : SingleActivityFactory<Type>(Type::class.java) {
            override fun create(intent: Intent): Type {
                val instrumentation = InstrumentationRegistry.getInstrumentation()
                val app = instrumentation.targetContext.applicationContext as TestApplication

                val testComponent = DaggerTestComponent
                        .builder()
                        .application(app)
                        .viewModel(viewModel)
                        .build()
                testComponent.inject(app)

                val activity = MainActivity()
                activity.viewModelFactory = testComponent.getViewModelFactory()
                return activity as Type
            }
        }
    }
}