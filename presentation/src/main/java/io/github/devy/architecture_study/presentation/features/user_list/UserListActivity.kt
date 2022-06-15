package io.github.devy.architecture_study.presentation.features.user_list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import io.github.devy.architecture_study.R
import io.github.devy.architecture_study.databinding.ActivityUserListBinding
import io.github.devy.architecture_study.presentation.features.user_detail.UserDetailActivity
import io.github.devy.architecture_study.showToast

@AndroidEntryPoint
class UserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding

    private val viewModel: UserListViewModel by viewModels()

    private val listAdapter by lazy {
        UserListAdapter({ pos, model ->
            //클릭시
            UserDetailActivity.start(this, model.id)
        }) { pos, model ->
            //좋아요 클릭시
            viewModel.toggleLike(model)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list)
        binding.rvUsers.adapter = listAdapter

        binding.rvUsers.addItemDecoration(DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL))

        binding.srl.setOnRefreshListener {
            viewModel.loadUserList()
        }

        binding.tvOnlyLike.setOnClickListener { viewModel.toggleOnlyLike() }

        viewModel.uiState.observe(this) {
            listAdapter.updateList(it.userList, it.header)
            binding.srl.isRefreshing = it.loading
            binding.tvOnlyLike.text =
                getString(if (it.onlyLike) R.string.str_see_all else R.string.str_only_like)
        }

        viewModel.uiEvent.observe(this) {
            when (it) {
                is UserListViewModel.UiEvent.LikeStateChanged -> {
                    listAdapter.notifyItemChangedByUserId(it.userId)
                }
                is UserListViewModel.UiEvent.Toast -> {
                    showToast(it.msg)
                }
            }
        }
    }
}