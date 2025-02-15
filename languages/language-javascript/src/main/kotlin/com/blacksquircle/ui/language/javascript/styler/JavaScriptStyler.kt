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

package com.blacksquircle.ui.language.javascript.styler

import android.util.Log
import com.blacksquircle.ui.language.base.model.ColorScheme
import com.blacksquircle.ui.language.base.span.StyleSpan
import com.blacksquircle.ui.language.base.span.SyntaxHighlightSpan
import com.blacksquircle.ui.language.base.styler.LanguageStyler
import com.blacksquircle.ui.language.javascript.lexer.JavaScriptLexer
import com.blacksquircle.ui.language.javascript.lexer.JavaScriptToken
import java.io.IOException
import java.io.StringReader
import java.util.regex.Pattern

class JavaScriptStyler private constructor() : LanguageStyler {

    companion object {

        private const val TAG = "JavaScriptStyler"

        private val METHOD = Pattern.compile("(?<=(function)) (\\w+)")

        private var javaScriptStyler: JavaScriptStyler? = null

        fun getInstance(): JavaScriptStyler {
            return javaScriptStyler ?: JavaScriptStyler().also {
                javaScriptStyler = it
            }
        }
    }

    override fun execute(source: String, scheme: ColorScheme): List<SyntaxHighlightSpan> {
        val syntaxHighlightSpans = mutableListOf<SyntaxHighlightSpan>()
        val sourceReader = StringReader(source)
        val lexer = JavaScriptLexer(sourceReader)

        // FIXME flex doesn't support positive lookbehind
        val matcher = METHOD.matcher(source)
        matcher.region(0, source.length)
        while (matcher.find()) {
            val styleSpan = StyleSpan(scheme.methodColor)
            val syntaxHighlightSpan = SyntaxHighlightSpan(styleSpan, matcher.start(), matcher.end())
            syntaxHighlightSpans.add(syntaxHighlightSpan)
        }

        while (true) {
            try {
                when (lexer.advance()) {
                    JavaScriptToken.LONG_LITERAL,
                    JavaScriptToken.INTEGER_LITERAL,
                    JavaScriptToken.FLOAT_LITERAL,
                    JavaScriptToken.DOUBLE_LITERAL -> {
                        val styleSpan = StyleSpan(scheme.numberColor)
                        val syntaxHighlightSpan = SyntaxHighlightSpan(styleSpan, lexer.tokenStart, lexer.tokenEnd)
                        syntaxHighlightSpans.add(syntaxHighlightSpan)
                    }
                    JavaScriptToken.EQEQ,
                    JavaScriptToken.NOTEQ,
                    JavaScriptToken.OROR,
                    JavaScriptToken.PLUSPLUS,
                    JavaScriptToken.MINUSMINUS,
                    JavaScriptToken.LT,
                    JavaScriptToken.LTLT,
                    JavaScriptToken.LTEQ,
                    JavaScriptToken.LTLTEQ,
                    JavaScriptToken.GT,
                    JavaScriptToken.GTGT,
                    JavaScriptToken.GTGTGT,
                    JavaScriptToken.GTEQ,
                    JavaScriptToken.GTGTEQ,
                    JavaScriptToken.GTGTGTEQ,
                    JavaScriptToken.AND,
                    JavaScriptToken.ANDAND,
                    JavaScriptToken.PLUSEQ,
                    JavaScriptToken.MINUSEQ,
                    JavaScriptToken.MULTEQ,
                    JavaScriptToken.DIVEQ,
                    JavaScriptToken.ANDEQ,
                    JavaScriptToken.OREQ,
                    JavaScriptToken.XOREQ,
                    JavaScriptToken.MODEQ,
                    JavaScriptToken.LPAREN,
                    JavaScriptToken.RPAREN,
                    JavaScriptToken.LBRACE,
                    JavaScriptToken.RBRACE,
                    JavaScriptToken.LBRACK,
                    JavaScriptToken.RBRACK,
                    JavaScriptToken.EQ,
                    JavaScriptToken.NOT,
                    JavaScriptToken.TILDE,
                    JavaScriptToken.QUEST,
                    JavaScriptToken.COLON,
                    JavaScriptToken.PLUS,
                    JavaScriptToken.MINUS,
                    JavaScriptToken.MULT,
                    JavaScriptToken.DIV,
                    JavaScriptToken.OR,
                    JavaScriptToken.XOR,
                    JavaScriptToken.MOD,
                    JavaScriptToken.ELLIPSIS,
                    JavaScriptToken.ARROW -> {
                        val styleSpan = StyleSpan(scheme.operatorColor)
                        val syntaxHighlightSpan = SyntaxHighlightSpan(styleSpan, lexer.tokenStart, lexer.tokenEnd)
                        syntaxHighlightSpans.add(syntaxHighlightSpan)
                    }
                    JavaScriptToken.SEMICOLON,
                    JavaScriptToken.COMMA,
                    JavaScriptToken.DOT -> {
                        continue // skip
                    }
                    JavaScriptToken.FUNCTION,
                    JavaScriptToken.PROTOTYPE,
                    JavaScriptToken.DEBUGGER,
                    JavaScriptToken.SUPER,
                    JavaScriptToken.THIS,
                    JavaScriptToken.ASYNC,
                    JavaScriptToken.AWAIT,
                    JavaScriptToken.EXPORT,
                    JavaScriptToken.FROM,
                    JavaScriptToken.EXTENDS,
                    JavaScriptToken.FINAL,
                    JavaScriptToken.IMPLEMENTS,
                    JavaScriptToken.NATIVE,
                    JavaScriptToken.PRIVATE,
                    JavaScriptToken.PROTECTED,
                    JavaScriptToken.PUBLIC,
                    JavaScriptToken.STATIC,
                    JavaScriptToken.SYNCHRONIZED,
                    JavaScriptToken.THROWS,
                    JavaScriptToken.TRANSIENT,
                    JavaScriptToken.VOLATILE,
                    JavaScriptToken.YIELD,
                    JavaScriptToken.DELETE,
                    JavaScriptToken.NEW,
                    JavaScriptToken.IN,
                    JavaScriptToken.INSTANCEOF,
                    JavaScriptToken.TYPEOF,
                    JavaScriptToken.OF,
                    JavaScriptToken.WITH,
                    JavaScriptToken.BREAK,
                    JavaScriptToken.CASE,
                    JavaScriptToken.CATCH,
                    JavaScriptToken.CONTINUE,
                    JavaScriptToken.DEFAULT,
                    JavaScriptToken.DO,
                    JavaScriptToken.ELSE,
                    JavaScriptToken.FINALLY,
                    JavaScriptToken.FOR,
                    JavaScriptToken.GOTO,
                    JavaScriptToken.IF,
                    JavaScriptToken.IMPORT,
                    JavaScriptToken.PACKAGE,
                    JavaScriptToken.RETURN,
                    JavaScriptToken.SWITCH,
                    JavaScriptToken.THROW,
                    JavaScriptToken.TRY,
                    JavaScriptToken.WHILE,
                    JavaScriptToken.CONST,
                    JavaScriptToken.VAR,
                    JavaScriptToken.LET -> {
                        val styleSpan = StyleSpan(scheme.keywordColor)
                        val syntaxHighlightSpan = SyntaxHighlightSpan(styleSpan, lexer.tokenStart, lexer.tokenEnd)
                        syntaxHighlightSpans.add(syntaxHighlightSpan)
                    }
                    JavaScriptToken.CLASS,
                    JavaScriptToken.INTERFACE,
                    JavaScriptToken.ENUM,
                    JavaScriptToken.BOOLEAN,
                    JavaScriptToken.BYTE,
                    JavaScriptToken.CHAR,
                    JavaScriptToken.DOUBLE,
                    JavaScriptToken.FLOAT,
                    JavaScriptToken.INT,
                    JavaScriptToken.LONG,
                    JavaScriptToken.SHORT,
                    JavaScriptToken.VOID -> {
                        val styleSpan = StyleSpan(scheme.typeColor)
                        val syntaxHighlightSpan = SyntaxHighlightSpan(styleSpan, lexer.tokenStart, lexer.tokenEnd)
                        syntaxHighlightSpans.add(syntaxHighlightSpan)
                    }
                    JavaScriptToken.TRUE,
                    JavaScriptToken.FALSE,
                    JavaScriptToken.NULL,
                    JavaScriptToken.NAN,
                    JavaScriptToken.UNDEFINED -> {
                        val styleSpan = StyleSpan(scheme.langConstColor)
                        val syntaxHighlightSpan = SyntaxHighlightSpan(styleSpan, lexer.tokenStart, lexer.tokenEnd)
                        syntaxHighlightSpans.add(syntaxHighlightSpan)
                    }
                    JavaScriptToken.DOUBLE_QUOTED_STRING,
                    JavaScriptToken.SINGLE_QUOTED_STRING,
                    JavaScriptToken.SINGLE_BACKTICK_STRING -> {
                        val styleSpan = StyleSpan(scheme.stringColor)
                        val syntaxHighlightSpan = SyntaxHighlightSpan(styleSpan, lexer.tokenStart, lexer.tokenEnd)
                        syntaxHighlightSpans.add(syntaxHighlightSpan)
                    }
                    JavaScriptToken.LINE_COMMENT,
                    JavaScriptToken.BLOCK_COMMENT -> {
                        val styleSpan = StyleSpan(scheme.commentColor)
                        val syntaxHighlightSpan = SyntaxHighlightSpan(styleSpan, lexer.tokenStart, lexer.tokenEnd)
                        syntaxHighlightSpans.add(syntaxHighlightSpan)
                    }
                    JavaScriptToken.IDENTIFIER,
                    JavaScriptToken.WHITESPACE,
                    JavaScriptToken.BAD_CHARACTER -> {
                        continue
                    }
                    JavaScriptToken.EOF -> {
                        break
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, e.message, e)
                break
            }
        }
        return syntaxHighlightSpans
    }
}