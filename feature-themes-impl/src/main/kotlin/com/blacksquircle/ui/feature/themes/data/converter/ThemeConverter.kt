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

package com.blacksquircle.ui.feature.themes.data.converter

import androidx.core.graphics.toColorInt
import com.blacksquircle.ui.core.data.storage.database.entity.theme.ThemeEntity
import com.blacksquircle.ui.core.ui.extensions.toHexString
import com.blacksquircle.ui.feature.themes.data.model.ExternalScheme
import com.blacksquircle.ui.feature.themes.data.model.ExternalTheme
import com.blacksquircle.ui.feature.themes.domain.model.ThemeModel
import com.blacksquircle.ui.language.base.model.ColorScheme
import java.util.*

object ThemeConverter {

    fun toModel(themeEntity: ThemeEntity): ThemeModel {
        return ThemeModel(
            uuid = themeEntity.uuid,
            name = themeEntity.name,
            author = themeEntity.author,
            description = themeEntity.description,
            isExternal = true,
            colorScheme = ColorScheme(
                textColor = themeEntity.textColor.toColorInt(),
                backgroundColor = themeEntity.backgroundColor.toColorInt(),
                gutterColor = themeEntity.gutterColor.toColorInt(),
                gutterDividerColor = themeEntity.gutterDividerColor.toColorInt(),
                gutterCurrentLineNumberColor = themeEntity.gutterCurrentLineNumberColor.toColorInt(),
                gutterTextColor = themeEntity.gutterTextColor.toColorInt(),
                selectedLineColor = themeEntity.selectedLineColor.toColorInt(),
                selectionColor = themeEntity.selectionColor.toColorInt(),
                suggestionQueryColor = themeEntity.suggestionQueryColor.toColorInt(),
                findResultBackgroundColor = themeEntity.findResultBackgroundColor.toColorInt(),
                delimiterBackgroundColor = themeEntity.delimiterBackgroundColor.toColorInt(),
                numberColor = themeEntity.numberColor.toColorInt(),
                operatorColor = themeEntity.operatorColor.toColorInt(),
                keywordColor = themeEntity.keywordColor.toColorInt(),
                typeColor = themeEntity.typeColor.toColorInt(),
                langConstColor = themeEntity.langConstColor.toColorInt(),
                preprocessorColor = themeEntity.preprocessorColor.toColorInt(),
                variableColor = themeEntity.variableColor.toColorInt(),
                methodColor = themeEntity.methodColor.toColorInt(),
                stringColor = themeEntity.stringColor.toColorInt(),
                commentColor = themeEntity.commentColor.toColorInt(),
                tagColor = themeEntity.tagColor.toColorInt(),
                tagNameColor = themeEntity.tagNameColor.toColorInt(),
                attrNameColor = themeEntity.attrNameColor.toColorInt(),
                attrValueColor = themeEntity.attrValueColor.toColorInt(),
                entityRefColor = themeEntity.entityRefColor.toColorInt(),
            ),
        )
    }

    fun toEntity(themeModel: ThemeModel): ThemeEntity {
        return ThemeEntity(
            uuid = themeModel.uuid,
            name = themeModel.name,
            author = themeModel.author,
            description = themeModel.description,
            textColor = themeModel.colorScheme.textColor.toHexString(),
            backgroundColor = themeModel.colorScheme.backgroundColor.toHexString(),
            gutterColor = themeModel.colorScheme.gutterColor.toHexString(),
            gutterDividerColor = themeModel.colorScheme.gutterDividerColor.toHexString(),
            gutterCurrentLineNumberColor = themeModel.colorScheme.gutterCurrentLineNumberColor.toHexString(),
            gutterTextColor = themeModel.colorScheme.gutterTextColor.toHexString(),
            selectedLineColor = themeModel.colorScheme.selectedLineColor.toHexString(),
            selectionColor = themeModel.colorScheme.selectionColor.toHexString(),
            suggestionQueryColor = themeModel.colorScheme.suggestionQueryColor.toHexString(),
            findResultBackgroundColor = themeModel.colorScheme.findResultBackgroundColor.toHexString(),
            delimiterBackgroundColor = themeModel.colorScheme.delimiterBackgroundColor.toHexString(),
            numberColor = themeModel.colorScheme.numberColor.toHexString(),
            operatorColor = themeModel.colorScheme.operatorColor.toHexString(),
            keywordColor = themeModel.colorScheme.keywordColor.toHexString(),
            typeColor = themeModel.colorScheme.typeColor.toHexString(),
            langConstColor = themeModel.colorScheme.langConstColor.toHexString(),
            preprocessorColor = themeModel.colorScheme.preprocessorColor.toHexString(),
            variableColor = themeModel.colorScheme.variableColor.toHexString(),
            methodColor = themeModel.colorScheme.methodColor.toHexString(),
            stringColor = themeModel.colorScheme.stringColor.toHexString(),
            commentColor = themeModel.colorScheme.commentColor.toHexString(),
            tagColor = themeModel.colorScheme.tagColor.toHexString(),
            tagNameColor = themeModel.colorScheme.tagNameColor.toHexString(),
            attrNameColor = themeModel.colorScheme.attrNameColor.toHexString(),
            attrValueColor = themeModel.colorScheme.attrValueColor.toHexString(),
            entityRefColor = themeModel.colorScheme.entityRefColor.toHexString(),
        )
    }

    fun toExternalTheme(themeModel: ThemeModel): ExternalTheme {
        return ExternalTheme(
            uuid = themeModel.uuid,
            name = themeModel.name,
            author = themeModel.author,
            description = themeModel.description,
            externalScheme = ExternalScheme(
                textColor = themeModel.colorScheme.textColor.toHexString(),
                backgroundColor = themeModel.colorScheme.backgroundColor.toHexString(),
                gutterColor = themeModel.colorScheme.gutterColor.toHexString(),
                gutterDividerColor = themeModel.colorScheme.gutterDividerColor.toHexString(),
                gutterCurrentLineNumberColor = themeModel.colorScheme.gutterCurrentLineNumberColor.toHexString(),
                gutterTextColor = themeModel.colorScheme.gutterTextColor.toHexString(),
                selectedLineColor = themeModel.colorScheme.selectedLineColor.toHexString(),
                selectionColor = themeModel.colorScheme.selectionColor.toHexString(),
                suggestionQueryColor = themeModel.colorScheme.suggestionQueryColor.toHexString(),
                findResultBackgroundColor = themeModel.colorScheme.findResultBackgroundColor.toHexString(),
                delimiterBackgroundColor = themeModel.colorScheme.delimiterBackgroundColor.toHexString(),
                numberColor = themeModel.colorScheme.numberColor.toHexString(),
                operatorColor = themeModel.colorScheme.operatorColor.toHexString(),
                keywordColor = themeModel.colorScheme.keywordColor.toHexString(),
                typeColor = themeModel.colorScheme.typeColor.toHexString(),
                langConstColor = themeModel.colorScheme.langConstColor.toHexString(),
                preprocessorColor = themeModel.colorScheme.preprocessorColor.toHexString(),
                variableColor = themeModel.colorScheme.variableColor.toHexString(),
                methodColor = themeModel.colorScheme.methodColor.toHexString(),
                stringColor = themeModel.colorScheme.stringColor.toHexString(),
                commentColor = themeModel.colorScheme.commentColor.toHexString(),
                tagColor = themeModel.colorScheme.tagColor.toHexString(),
                tagNameColor = themeModel.colorScheme.tagNameColor.toHexString(),
                attrNameColor = themeModel.colorScheme.attrNameColor.toHexString(),
                attrValueColor = themeModel.colorScheme.attrValueColor.toHexString(),
                entityRefColor = themeModel.colorScheme.entityRefColor.toHexString(),
            ),
        )
    }

    fun toModel(externalTheme: ExternalTheme?): ThemeModel {
        return ThemeModel(
            uuid = externalTheme?.uuid ?: UUID.randomUUID().toString(),
            name = externalTheme?.name ?: "",
            author = externalTheme?.author ?: "",
            description = externalTheme?.description ?: "",
            isExternal = true,
            colorScheme = ColorScheme(
                textColor = (externalTheme?.externalScheme?.textColor ?: "#000000").toColorInt(),
                backgroundColor = (externalTheme?.externalScheme?.backgroundColor ?: "#000000").toColorInt(),
                gutterColor = (externalTheme?.externalScheme?.gutterColor ?: "#000000").toColorInt(),
                gutterDividerColor = (externalTheme?.externalScheme?.gutterDividerColor ?: "#000000").toColorInt(),
                gutterCurrentLineNumberColor = (externalTheme?.externalScheme?.gutterCurrentLineNumberColor ?: "#000000").toColorInt(),
                gutterTextColor = (externalTheme?.externalScheme?.gutterTextColor ?: "#000000").toColorInt(),
                selectedLineColor = (externalTheme?.externalScheme?.selectedLineColor ?: "#000000").toColorInt(),
                selectionColor = (externalTheme?.externalScheme?.selectionColor ?: "#000000").toColorInt(),
                suggestionQueryColor = (externalTheme?.externalScheme?.suggestionQueryColor ?: "#000000").toColorInt(),
                findResultBackgroundColor = (externalTheme?.externalScheme?.findResultBackgroundColor ?: "#000000").toColorInt(),
                delimiterBackgroundColor = (externalTheme?.externalScheme?.delimiterBackgroundColor ?: "#000000").toColorInt(),
                numberColor = (externalTheme?.externalScheme?.numberColor ?: "#000000").toColorInt(),
                operatorColor = (externalTheme?.externalScheme?.operatorColor ?: "#000000").toColorInt(),
                keywordColor = (externalTheme?.externalScheme?.keywordColor ?: "#000000").toColorInt(),
                typeColor = (externalTheme?.externalScheme?.typeColor ?: "#000000").toColorInt(),
                langConstColor = (externalTheme?.externalScheme?.langConstColor ?: "#000000").toColorInt(),
                preprocessorColor = (externalTheme?.externalScheme?.preprocessorColor ?: "#000000").toColorInt(),
                variableColor = (externalTheme?.externalScheme?.variableColor ?: "#000000").toColorInt(),
                methodColor = (externalTheme?.externalScheme?.methodColor ?: "#000000").toColorInt(),
                stringColor = (externalTheme?.externalScheme?.stringColor ?: "#000000").toColorInt(),
                commentColor = (externalTheme?.externalScheme?.commentColor ?: "#000000").toColorInt(),
                tagColor = (externalTheme?.externalScheme?.tagColor ?: "#000000").toColorInt(),
                tagNameColor = (externalTheme?.externalScheme?.tagNameColor ?: "#000000").toColorInt(),
                attrNameColor = (externalTheme?.externalScheme?.attrNameColor ?: "#000000").toColorInt(),
                attrValueColor = (externalTheme?.externalScheme?.attrValueColor ?: "#000000").toColorInt(),
                entityRefColor = (externalTheme?.externalScheme?.entityRefColor ?: "#000000").toColorInt(),
            ),
        )
    }
}