package om.kumar.jsonparser.Lexer

import com.kumar.jsonparser.Lexer.Token
import com.kumar.jsonparser.Lexer.TokenType

class JsonLexer(private val input: String) {
   private var pos = 0

   fun nextToken(): Token {
      while (pos < input.length) {
         when (val char = input[pos]) {
            '{' -> return consume(TokenType.LEFT_BRACE, "{")
            '}' -> return consume(TokenType.RIGHT_BRACE, "}")
            '[' -> return consume(TokenType.LEFT_BRACKET, "[")
            ']' -> return consume(TokenType.RIGHT_BRACKET, "]")
            ':' -> return consume(TokenType.COLON, ":")
            ',' -> return consume(TokenType.COMMA, ",")
            '"' -> return Token(TokenType.STRING, consumeString())
            in '0'..'9' -> return Token(TokenType.NUMBER, consumeNumber())
            't', 'f' -> return Token(TokenType.BOOLEAN, consumeBoolean())
            'n' -> return Token(TokenType.NULL, consumeNull())
            ' ', '\n', '\t' -> pos++ // Ignoring whitespace
            else -> throw IllegalArgumentException("Unexpected character: $char")
         }
      }
      return Token(TokenType.EOF, "")
   }

   private fun consume(type: TokenType, value: String): Token {
      pos++
      return Token(type, value)
   }

   private fun consumeString(): String {
      pos++
      val start = pos
      while (pos < input.length && input[pos] != '"') pos++
      val result = input.substring(start, pos)
      pos++
      return result
   }

   private fun consumeNumber(): String {
      val start = pos
      while (pos < input.length && input[pos].isDigit()) pos++
      return input.substring(start, pos)
   }

   private fun consumeBoolean(): String {
      val start = pos
      if (input.startsWith("true", start)) {
         pos += 4
         return "true"
      } else if (input.startsWith("false", start)) {
         pos += 5
         return "false"
      }
      throw IllegalArgumentException("Invalid boolean value")
   }

   private fun consumeNull(): String {
      if (input.startsWith("null", pos)) {
         pos += 4
         return "null"
      }
      throw IllegalArgumentException("Invalid null value")
   }
}
