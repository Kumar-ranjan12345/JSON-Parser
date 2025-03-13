package com.kumar.jsonparser.Lexer

enum class TokenType {
   LEFT_BRACE, RIGHT_BRACE, LEFT_BRACKET, RIGHT_BRACKET,
   COLON, COMMA, STRING, NUMBER, BOOLEAN, NULL, EOF
}

data class Token(val type: TokenType, val value: String)
