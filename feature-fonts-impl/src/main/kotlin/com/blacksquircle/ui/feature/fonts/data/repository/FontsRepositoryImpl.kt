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

package com.blacksquircle.ui.feature.fonts.data.repository

import com.blacksquircle.ui.core.data.storage.database.AppDatabase
import com.blacksquircle.ui.core.data.storage.keyvalue.SettingsManager
import com.blacksquircle.ui.core.domain.coroutine.DispatcherProvider
import com.blacksquircle.ui.feature.fonts.data.converter.FontConverter
import com.blacksquircle.ui.feature.fonts.domain.model.FontModel
import com.blacksquircle.ui.feature.fonts.domain.repository.FontsRepository
import kotlinx.coroutines.withContext

class FontsRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val settingsManager: SettingsManager,
    private val appDatabase: AppDatabase,
) : FontsRepository {

    override suspend fun fetchFonts(query: String): List<FontModel> {
        return withContext(dispatcherProvider.io()) {
            val defaultFonts = internalFonts()
                .filter { it.fontName.contains(query, ignoreCase = true) }
            val userFonts = appDatabase.fontDao().loadAll(query)
                .map(FontConverter::toModel)
            userFonts + defaultFonts
        }
    }

    override suspend fun createFont(fontModel: FontModel) {
        withContext(dispatcherProvider.io()) {
            appDatabase.fontDao().insert(FontConverter.toEntity(fontModel))
        }
    }

    override suspend fun removeFont(fontModel: FontModel) {
        withContext(dispatcherProvider.io()) {
            appDatabase.fontDao().delete(FontConverter.toEntity(fontModel))
            if (settingsManager.fontType == fontModel.fontPath) {
                settingsManager.remove(SettingsManager.KEY_FONT_TYPE)
            }
        }
    }

    override suspend fun selectFont(fontModel: FontModel) {
        withContext(dispatcherProvider.io()) {
            settingsManager.fontType = fontModel.fontPath
        }
    }

    private fun internalFonts(): List<FontModel> {
        return listOf(
            FontModel(
                fontName = "Droid Sans Mono",
                fontPath = "file:///android_asset/fonts/droid_sans_mono.ttf",
                supportLigatures = false,
                isExternal = false,
            ),
            FontModel(
                fontName = "JetBrains Mono",
                fontPath = "file:///android_asset/fonts/jetbrains_mono.ttf",
                supportLigatures = true,
                isExternal = false,
            ),
            FontModel(
                fontName = "Fira Code",
                fontPath = "file:///android_asset/fonts/fira_code.ttf",
                supportLigatures = true,
                isExternal = false,
            ),
            FontModel(
                fontName = "Source Code Pro",
                fontPath = "file:///android_asset/fonts/source_code_pro.ttf",
                supportLigatures = false,
                isExternal = false,
            ),
            FontModel(
                fontName = "Anonymous Pro",
                fontPath = "file:///android_asset/fonts/anonymous_pro.ttf",
                supportLigatures = false,
                isExternal = false,
            ),
            FontModel(
                fontName = "DejaVu Sans Mono",
                fontPath = "file:///android_asset/fonts/dejavu_sans_mono.ttf",
                supportLigatures = false,
                isExternal = false,
            ),
        )
    }
}