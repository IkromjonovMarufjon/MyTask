package uz.infinity.mytask.data.resources.local.pref

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.infinity.mytask.data.model.ScreenEnum
import uz.infinity.mytask.utils.screenEnum
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val pref = context.getSharedPreferences("Task", Context.MODE_PRIVATE)

    var currentScreen: ScreenEnum
        get() = pref.getString("CURRENT_SCREEN", ScreenEnum.LOGIN._name)!!.screenEnum()
        set(value) {
            pref.edit().putString("CURRENT_SCREEN", value._name).apply()
        }


}

