package uz.infinity.mytask.presentation.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.infinity.mytask.databinding.DialogEventBinding
import uz.infinity.mytask.utils.scope

class EventDialog : BottomSheetDialogFragment() {
    private var _binding: DialogEventBinding? = null
    private val binding get() = _binding!!
    private var deleteUserListener: (() -> Unit)? = null
    private var editUserListener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)

        buttonDelete.setOnClickListener {
            deleteUserListener?.invoke()
            dismiss()
        }
        buttonEdit.setOnClickListener {
            editUserListener?.invoke()
            dismiss()
        }
    }

    fun setDeleteUserListener(block: () -> Unit) {
        deleteUserListener = block
    }

    fun setEditUserListener(block: () -> Unit) {
        editUserListener = block
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}