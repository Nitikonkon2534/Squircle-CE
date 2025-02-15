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

package com.blacksquircle.ui.feature.settings.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blacksquircle.ui.core.data.storage.keyvalue.SettingsManager
import com.blacksquircle.ui.core.domain.resources.StringProvider
import com.blacksquircle.ui.core.ui.viewstate.ViewEvent
import com.blacksquircle.ui.feature.settings.R
import com.blacksquircle.ui.feature.settings.data.converter.ReleaseConverter
import com.blacksquircle.ui.feature.settings.domain.SettingsRepository
import com.blacksquircle.ui.feature.settings.ui.adapter.item.PreferenceItem
import com.blacksquircle.ui.feature.settings.ui.adapter.item.ReleaseModel
import com.blacksquircle.ui.feature.settings.ui.navigation.SettingsScreen
import com.blacksquircle.ui.filesystem.base.model.ServerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val settingsRepository: SettingsRepository,
    private val settingsManager: SettingsManager,
) : ViewModel() {

    private val _headersState = MutableStateFlow(
        listOf(
            PreferenceItem(
                R.string.pref_header_application_title,
                R.string.pref_header_application_summary,
                SettingsScreen.Application,
            ),
            PreferenceItem(
                R.string.pref_header_editor_title,
                R.string.pref_header_editor_summary,
                SettingsScreen.Editor,
            ),
            PreferenceItem(
                R.string.pref_header_codeStyle_title,
                R.string.pref_header_codeStyle_summary,
                SettingsScreen.CodeStyle,
            ),
            PreferenceItem(
                R.string.pref_header_files_title,
                R.string.pref_header_files_summary,
                SettingsScreen.Files,
            ),
            PreferenceItem(
                R.string.pref_header_cloud_title,
                R.string.pref_header_cloud_summary,
                SettingsScreen.Cloud,
            ),
            PreferenceItem(
                R.string.pref_header_about_title,
                R.string.pref_header_about_summary,
                SettingsScreen.About,
            ),
        ),
    )
    val headersState: StateFlow<List<PreferenceItem>> = _headersState.asStateFlow()

    private val _serverState = MutableStateFlow<List<ServerModel>>(emptyList())
    val serverState: StateFlow<List<ServerModel>> = _serverState.asStateFlow()

    private val _changelogState = MutableStateFlow<List<ReleaseModel>>(emptyList())
    val changelogState: StateFlow<List<ReleaseModel>> = _changelogState.asStateFlow()

    private val _viewEvent = Channel<ViewEvent>(Channel.BUFFERED)
    val viewEvent: Flow<ViewEvent> = _viewEvent.receiveAsFlow()

    var fullscreenMode: Boolean
        get() = settingsManager.fullScreenMode
        set(value) { settingsManager.fullScreenMode = value }
    var keyboardPreset: String
        get() = settingsManager.keyboardPreset
        set(value) { settingsManager.keyboardPreset = value }

    fun fetchServers() {
        viewModelScope.launch {
            _serverState.value = settingsRepository.fetchServers()
        }
    }

    fun upsertServer(serverModel: ServerModel) {
        viewModelScope.launch {
            try {
                if (serverModel.name.isBlank() || serverModel.address.isBlank()) {
                    _viewEvent.send(
                        ViewEvent.Toast(
                            stringProvider.getString(R.string.message_server_missing_fields),
                        ),
                    )
                    return@launch
                }
                settingsRepository.upsertServer(serverModel)
                fetchServers()
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
                _viewEvent.send(ViewEvent.Toast(e.message.orEmpty()))
            }
        }
    }

    fun deleteServer(serverModel: ServerModel) {
        viewModelScope.launch {
            try {
                settingsRepository.deleteServer(serverModel)
                fetchServers()
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
                _viewEvent.send(ViewEvent.Toast(e.message.orEmpty()))
            }
        }
    }

    fun resetKeyboardPreset() {
        viewModelScope.launch {
            try {
                settingsRepository.resetKeyboardPreset()
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
                _viewEvent.send(ViewEvent.Toast(e.message.orEmpty()))
            }
        }
    }

    fun fetchChangeLog(changelog: String) {
        _changelogState.value = ReleaseConverter.toReleaseModels(changelog)
    }

    companion object {
        private const val TAG = "SettingsViewModel"
    }
}