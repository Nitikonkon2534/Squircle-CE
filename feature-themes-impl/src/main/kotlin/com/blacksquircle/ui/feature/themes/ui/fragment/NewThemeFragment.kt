/*
 * Copyright 2023 Squircle CE contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blacksquircle.ui.feature.themes.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.graphics.toColorInt
import androidx.core.view.MenuProvider
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.ColorPalette
import com.afollestad.materialdialogs.color.colorChooser
import com.blacksquircle.ui.core.ui.adapter.OnItemClickListener
import com.blacksquircle.ui.core.ui.contract.ContractResult
import com.blacksquircle.ui.core.ui.contract.OpenFileContract
import com.blacksquircle.ui.core.ui.delegate.viewBinding
import com.blacksquircle.ui.core.ui.extensions.*
import com.blacksquircle.ui.core.ui.viewstate.ViewEvent
import com.blacksquircle.ui.feature.themes.R
import com.blacksquircle.ui.feature.themes.databinding.FragmentNewThemeBinding
import com.blacksquircle.ui.feature.themes.domain.model.Meta
import com.blacksquircle.ui.feature.themes.domain.model.PropertyItem
import com.blacksquircle.ui.feature.themes.ui.adapter.PropertyAdapter
import com.blacksquircle.ui.feature.themes.ui.viewmodel.ThemesViewModel
import com.blacksquircle.ui.feature.themes.ui.viewstate.NewThemeViewState
import com.blacksquircle.ui.filesystem.base.utils.isValidFileName
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

@AndroidEntryPoint
class NewThemeFragment : Fragment(R.layout.fragment_new_theme) {

    private val viewModel by hiltNavGraphViewModels<ThemesViewModel>(R.id.themes_graph)
    private val binding by viewBinding(FragmentNewThemeBinding::bind)
    private val navController by lazy { findNavController() }
    private val navArgs by navArgs<NewThemeFragmentArgs>()
    private val openFileContract = OpenFileContract(this) { result ->
        when (result) {
            is ContractResult.Success -> viewModel.importTheme(result.uri)
            is ContractResult.Canceled -> Unit
        }
    }

    private lateinit var adapter: PropertyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.fetchProperties(navArgs.uuid)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFadeTransition(binding.container, R.id.toolbar)
        postponeEnterTransition(view)
        observeViewModel()

        view.applySystemWindowInsets(true) { _, top, _, bottom ->
            binding.toolbar.updatePadding(top = top)
            binding.root.updatePadding(bottom = bottom)
        }

        binding.textInputThemeName.doAfterTextChanged {
            viewModel.onThemeNameChanged(it.toString())
        }
        binding.textInputThemeAuthor.doAfterTextChanged {
            viewModel.onThemeAuthorChanged(it.toString())
        }
        binding.textInputThemeDescription.doAfterTextChanged {
            viewModel.onThemeDescriptionChanged(it.toString())
        }

        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.adapter = PropertyAdapter(object : OnItemClickListener<PropertyItem> {
            @SuppressLint("CheckResult")
            override fun onClick(item: PropertyItem) {
                MaterialDialog(requireContext()).show {
                    title(R.string.dialog_title_color_picker)
                    colorChooser(
                        colors = ColorPalette.Primary,
                        subColors = ColorPalette.PrimarySub,
                        initialSelection = item.propertyValue.toColorInt(),
                        allowCustomArgb = true,
                        showAlphaSelector = false,
                    ) { _, color ->
                        viewModel.onThemePropertyChanged(item.propertyKey, color.toHexString())
                    }
                    positiveButton(R.string.action_select)
                    negativeButton(android.R.string.cancel)
                }
            }
        }).also {
            adapter = it
        }

        binding.actionSave.setOnClickListener {
            val meta = Meta(
                uuid = navArgs.uuid ?: UUID.randomUUID().toString(),
                name = binding.textInputThemeName.text.toString(),
                author = binding.textInputThemeAuthor.text.toString(),
                description = binding.textInputThemeDescription.text.toString(),
            )
            viewModel.createTheme(meta, adapter.currentList)
        }

        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
        binding.toolbar.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_new_theme, menu)
                }
                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.action_import -> openFileContract.launch(OpenFileContract.JSON)
                    }
                    return true
                }
            },
            viewLifecycleOwner,
        )
    }

    private fun observeViewModel() {
        viewModel.newThemeState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                when (state) {
                    is NewThemeViewState.MetaData -> {
                        val name = binding.textInputThemeName.text.toString()
                        val author = binding.textInputThemeAuthor.text.toString()
                        val description = binding.textInputThemeDescription.text.toString()

                        if (name != state.meta.name) {
                            binding.textInputThemeName.setText(state.meta.name)
                        }
                        if (author != state.meta.author) {
                            binding.textInputThemeAuthor.setText(state.meta.author)
                        }
                        if (description != state.meta.description) {
                            binding.textInputThemeDescription.setText(state.meta.description)
                        }

                        val isNameValid = state.meta.name.trim().isValidFileName()
                        val isAuthorValid = state.meta.author.trim().isNotBlank()
                        val isDescriptionValid = state.meta.description.trim().isNotBlank()

                        binding.actionSave.isEnabled =
                            isNameValid && isAuthorValid && isDescriptionValid

                        adapter.submitList(state.properties)
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.viewEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { event ->
                when (event) {
                    is ViewEvent.Toast -> context?.showToast(text = event.message)
                    is ViewEvent.PopBackStack -> navController.popBackStack()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}