package uz.infinity.mytask.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.infinity.mytask.MainActivity
import uz.infinity.mytask.R
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity
import uz.infinity.mytask.databinding.ScreenHomeBinding
import uz.infinity.mytask.presentation.ui.adapters.UserAdapter
import uz.infinity.mytask.presentation.ui.dialogs.EditDialog
import uz.infinity.mytask.presentation.ui.dialogs.EventDialog
import uz.infinity.mytask.presentation.vm.HomeViewModel
import uz.infinity.mytask.presentation.vm.impl.HomeViewModelImpl
import uz.infinity.mytask.utils.scope
import uz.infinity.mytask.utils.showToast

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.screen_home) {
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val binding: ScreenHomeBinding by viewBinding(ScreenHomeBinding::bind)
    private val adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.clickHomeButtonLiveData.observe(this, clickHomeButtonObserver)
        viewModel.showEventDialogLiveData.observe(this, showEventDialogObserver)
        viewModel.showEditDialogLiveData.observe(this, showEditDialogObserver)
        viewModel.logOutLiveData.observe(this, logOutObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(requireContext())
        adapter.setClickMoreButtonListener { viewModel.showEventDialog(it) }
        buttonLogOut.setOnClickListener { viewModel.logOutUser() }
        buttonMenu.setOnClickListener { viewModel.clickHomeButton() }

        viewModel.allUserLiveData.observe(viewLifecycleOwner, allUserObserver)
    }

    private val allUserObserver = Observer<List<UserEntity>> { adapter.submitList(it) }
    private val clickHomeButtonObserver = Observer<Unit> { showToast("Click") }
    private val logOutObserver = Observer<Unit> {
        (requireActivity() as MainActivity).clearSteak(arrayListOf(R.id.homeScreen), R.id.loginScreen)
    }
    private val showEventDialogObserver = Observer<UserEntity> { data ->
        val eventDialog = EventDialog()
        eventDialog.setDeleteUserListener {
            viewModel.deleteUser(data)
            Snackbar.make(binding.root, "Delete user", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    viewModel.undoTask()
                }.show()
        }
        eventDialog.setEditUserListener { viewModel.showEditDialog(data) }
        eventDialog.show(childFragmentManager, "EventDialog")
    }
    private val showEditDialogObserver = Observer<UserEntity> {
        val editDialog = EditDialog()
        val bundle = Bundle()
        bundle.putSerializable("DATA", it)
        editDialog.arguments = bundle
        editDialog.setEditDataListener {
            viewModel.loadUsers()
        }
        editDialog.show(childFragmentManager, "EDIT_USER")
    }
}
