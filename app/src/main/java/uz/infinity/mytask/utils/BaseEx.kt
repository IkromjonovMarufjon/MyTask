package uz.infinity.mytask.utils

import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import timber.log.Timber
import uz.infinity.mytask.data.model.ScreenEnum

fun String.screenEnum(): ScreenEnum {
    return when (this) {
        "LOGIN" -> ScreenEnum.LOGIN
        else -> ScreenEnum.HOME
    }
}

fun <T> T.scope(block: T.() -> Unit) {
    block(this)
}

fun myLog(message: String, tag: String = "TTT") {
    Timber.tag(tag).d(message)
}

fun AppCompatEditText.text(): String = this.text.toString()

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun AppCompatEditText.addMyTextChangeListener(block: (String) -> Unit) {
    this.addTextChangedListener {
        it?.let {
            block.invoke(it.toString())
        }
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}
