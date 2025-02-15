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

package com.blacksquircle.ui.language.visualbasic.parser

import com.blacksquircle.ui.language.base.exception.ParseException
import com.blacksquircle.ui.language.base.model.ParseResult
import com.blacksquircle.ui.language.base.parser.LanguageParser

class VisualBasicParser private constructor() : LanguageParser {

    companion object {

        private var visualBasicParser: VisualBasicParser? = null

        fun getInstance(): VisualBasicParser {
            return visualBasicParser ?: VisualBasicParser().also {
                visualBasicParser = it
            }
        }
    }

    override fun execute(name: String, source: String): ParseResult {
        // TODO Implement parser
        val parseException = ParseException("Unable to parse unsupported language", 0, 0)
        return ParseResult(parseException)
    }
}