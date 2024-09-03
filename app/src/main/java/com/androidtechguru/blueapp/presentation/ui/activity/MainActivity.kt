package com.androidtechguru.blueapp.presentation.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.androidtechguru.blueapp.R
import com.androidtechguru.blueapp.databinding.ActivityMainBinding
import com.androidtechguru.blueapp.presentation.ui.adapter.CarouselPagerAdapter
import com.androidtechguru.blueapp.presentation.ui.adapter.ItemsAdapter
import com.androidtechguru.blueapp.presentation.ui.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.toolbarLayout.title = title
        setupRecyclerView()
        setupCarouselViewPager()
        setupSearchView()

        binding.fab.setOnClickListener {
            viewModel.onFabClicked()
            lifecycleScope.launch {
                showBottomSheet()
            }
        }
    }

    private fun setupSearchView() {
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchQueryChanged(query.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupCarouselViewPager() {
        val adapter = CarouselPagerAdapter(viewModel.images.value)
        binding.carouselViewPager.adapter = adapter

        val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                showDotIndicator(viewModel.images.value.size,
                    "${position + 1}/${viewModel.images.value.size}")
                viewModel.onPageChanged(position + 1)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

            }
        }
        binding.carouselViewPager.registerOnPageChangeCallback(pageChangeCallback)

        TabLayoutMediator(binding.pagerDotIndicator, binding.carouselViewPager)
        { tab, position -> }.attach()
    }

    private fun setupRecyclerView() {
        binding.itemsRecyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity)
        binding.itemsRecyclerView.adapter = ItemsAdapter(viewModel.itemList.value)
    }

    val maxDotIndicator = 10
    private fun setupObserver() {
        viewModel.itemList.onEach { itemList ->
            (binding.itemsRecyclerView.adapter as ItemsAdapter)
                .updateData(itemList)
        }.launchIn(lifecycleScope)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.images.collect { images ->
                    showDotIndicator(images.size)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.error.collectLatest { errorMessage ->
                showToast(errorMessage)
            }
        }

        lifecycleScope.launch {
            viewModel.filteredItems.collectLatest { itemList ->
                (binding.itemsRecyclerView.adapter as ItemsAdapter)
                    .updateData(itemList)
            }
        }
    }

    private fun showDotIndicator(size: Int, indicatorCount: String = "") {
        if (size in 2..maxDotIndicator) {
            binding.pagerDotIndicator.visibility = View.VISIBLE
            binding.pagerCounterIndicator.visibility = View.GONE
        } else {
            binding.pagerDotIndicator.visibility = View.GONE
            binding.pagerCounterIndicator.visibility = View.VISIBLE
            binding.pagerCounterIndicator.text = indicatorCount
        }
    }

    private suspend fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_content, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()

        val closeBtn = view.findViewById<TextView>(R.id.close_btn)
        closeBtn.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        val totalItemsTv = view.findViewById<TextView>(R.id.total_items_tv)
        val statsTextView = view.findViewById<TextView>(R.id.stats_data_tv)
        viewModel.statistics.collect { stats ->
            totalItemsTv.text = buildString {
                append(getString(R.string.total_items))
                append(stats.totalItems.toString())
            }
            statsTextView.text = stats.topCharacters.toMap().entries
                .sortedByDescending { it.value }
                .joinToString("\n\n")
                { "${it.key} = ${it.value}" }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}