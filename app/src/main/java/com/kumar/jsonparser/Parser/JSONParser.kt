package com.kumar.jsonparser.Parser

import com.kumar.jsonparser.Lexer.Token
import com.kumar.jsonparser.Lexer.TokenType
import om.kumar.jsonparser.Lexer.JsonLexer

class JsonParser(private val lexer: JsonLexer) {
   private var currentToken: Token = lexer.nextToken()

   private fun consume(expectedType: TokenType) {
      if (currentToken.type == expectedType) {
         currentToken = lexer.nextToken()
      } else {
         throw IllegalArgumentException("Expected $expectedType but found ${currentToken.type}")
      }
   }

   fun parse(): Any? {
      return when (currentToken.type) {
         TokenType.LEFT_BRACE -> parseObject()
         TokenType.LEFT_BRACKET -> parseArray()
         TokenType.STRING -> currentToken.value.also { consume(TokenType.STRING) }
         TokenType.NUMBER -> currentToken.value.toInt().also { consume(TokenType.NUMBER) }
         TokenType.BOOLEAN -> currentToken.value.toBoolean().also { consume(TokenType.BOOLEAN) }
         TokenType.NULL -> null.also { consume(TokenType.NULL) }
         else -> throw IllegalArgumentException("Unexpected token: ${currentToken.type}")
      }
   }

   private fun parseObject(): Map<String, Any?> {
      val map = mutableMapOf<String, Any?>()
      consume(TokenType.LEFT_BRACE)
      while (currentToken.type != TokenType.RIGHT_BRACE) {
         val key = currentToken.value
         consume(TokenType.STRING)
         consume(TokenType.COLON)
         val value = parse()
         map[key] = value
         if (currentToken.type != TokenType.RIGHT_BRACE) consume(TokenType.COMMA)
      }
      consume(TokenType.RIGHT_BRACE)
      return map
   }

   private fun parseArray(): List<Any?> {
      val list = mutableListOf<Any?>()
      consume(TokenType.LEFT_BRACKET)
      while (currentToken.type != TokenType.RIGHT_BRACKET) {
         list.add(parse())
         if (currentToken.type != TokenType.RIGHT_BRACKET) consume(TokenType.COMMA)
      }
      consume(TokenType.RIGHT_BRACKET)
      return list
   }
}
