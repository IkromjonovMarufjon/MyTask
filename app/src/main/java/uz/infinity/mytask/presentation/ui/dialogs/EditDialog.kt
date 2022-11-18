package uz.infinity.mytask.presentation.ui.dialogs

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.infinity.mytask.R
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity
import uz.infinity.mytask.databinding.DialogEditBinding
import uz.infinity.mytask.presentation.vm.EditViewModel
import uz.infinity.mytask.presentation.vm.impl.EditViewModelImpl
import uz.infinity.mytask.utils.scope
import uz.infinity.mytask.utils.text

@AndroidEntryPoint
class EditDialog : DialogFragment(R.layout.dialog_edit) {
    private val viewModel: EditViewModel by viewModels<EditViewModelImpl>()
    private val binding: DialogEditBinding by viewBinding(DialogEditBinding::bind)
    private var editDataListener: ((Unit) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        val oldData = requireArguments().getSerializable("DATA") as UserEntity
        editEmail.setText(oldData.email)
        editPassword.setText(oldData.password)

        combine(
            editEmail.textChanges().map {
                editEmail.error = null
                it.length > 10 && it.endsWith("@gmail.com")
            },
            editPassword.textChanges().map { it.length > 4 },
            transform = { email, password -> email && password }
        ).onEach {
            viewModel.changeButtonStatus(it)
        }.launchIn(lifecycleScope)

        buttonEdit.setOnClickListener {
            viewModel.updateUserInfo(UserEntity(oldData.id, editEmail.text(), editPassword.text()))
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorObserver)
        viewModel.changeButtonStatusLiveData.observe(viewLifecycleOwner, changeButtonStatusObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successObserver)
    }

    override fun onResume() {
        super.onResume()
        val params = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params
    }

    private val errorObserver = Observer<Unit> { binding.editEmail.error = "This email is busy" }
    private val changeButtonStatusObserver = Observer<Boolean> { binding.buttonEdit.isEnabled = it }
    private val successObserver = Observer<Unit> {
        dismiss()
        editDataListener?.invoke(Unit)
    }

    fun setEditDataListener(block: (Unit) -> Unit) {
        editDataListener = block
    }
}
