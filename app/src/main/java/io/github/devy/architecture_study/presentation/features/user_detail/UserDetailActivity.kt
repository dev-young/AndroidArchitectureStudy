package io.github.devy.architecture_study.presentation.features.user_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.github.devy.architecture_study.R
import io.github.devy.architecture_study.databinding.ActivityUserDetailBinding
import io.github.devy.architecture_study.showToast

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context, userId: Int) {
            val starter = Intent(context, UserDetailActivity::class.java)
                .putExtra("userId", userId)
            context.startActivity(starter)
        }
    }

    private val viewModel: UserDetailViewModel by viewModels()

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        intent.getIntExtra("userId", -1).let {
            if (it != -1) {
                viewModel.loadUser(it)
            }
        }

        binding.ivLike.setOnClickListener {
            viewModel.toggleLike()
        }

        viewModel.uiState.observe(this) {
            val user = it.user
            binding.tvName.text = user.name
            binding.tvDescription.text = user.description
            binding.ivLike.imageTintList = ContextCompat.getColorStateList(this,
                if (user.like) R.color.like else R.color.un_like)

            Glide.with(this)
                .load(user.photo)
                .into(binding.ivThumb)
        }

        viewModel.uiEvent.observe(this) {
            when (it) {
                is UserDetailViewModel.UiEvent.Toast -> {
                    showToast(it.msg)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }
}