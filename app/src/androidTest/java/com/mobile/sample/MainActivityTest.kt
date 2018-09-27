package com.mobile.sample

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.agoda.kakao.KRecyclerItem
import com.agoda.kakao.KRecyclerView
import com.agoda.kakao.KTextView
import com.agoda.kakao.Screen
import com.mobile.sample.base.TestUtils.createActivityFactory
import com.mobile.sample.base.TestUtils.launchActivity
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.local.UserLocalModel
import com.mobile.sample.main.MainActivity
import com.mobile.sample.main.UsersViewModel
import com.mobile.sample.utils.LiveEvent
import com.mobile.sample.utils.Result
import com.mobile.sample.utils.Success
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val screen = MainScreen()
    private val user = UserLocalModel(0, "Jon", "Jon Snow")
    private val usersViewModel: UsersViewModel = mock(UsersViewModel::class.java)

    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity>

    init {
        rule = ActivityTestRule(createActivityFactory(usersViewModel), false, false)
    }

    inner class MainScreen : Screen<MainScreen>() {
        val recycler: KRecyclerView = KRecyclerView({
            withId(R.id.userRecyclerView)
        }, itemTypeBuilder = {
            itemType(::Item)
        })

        inner class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
            val name: KTextView = KTextView(parent) { withId(R.id.name) }
            val username: KTextView = KTextView(parent) { withId(R.id.username) }
        }
    }

    @Test
    fun requestUserData_hasData_displayUsers() {
        val userData = MutableLiveData<Result<List<User>>>()
        userData.postValue(Success(listOf(user)))

        `when`(usersViewModel.getUsers()).thenReturn(userData)
        `when`(usersViewModel.getUserClickedEvent()).thenReturn(LiveEvent())

        launchActivity(rule)

        screen {
            recycler {
                isVisible()
                hasSize(1)
                firstChild<MainScreen.Item> {
                    name.hasText("Jon")
                    username.hasText("Jon Snow")
                }
            }
        }
    }
}