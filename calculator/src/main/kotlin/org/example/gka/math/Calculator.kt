package org.example.gka.math

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.math.BigDecimal
import kotlin.collections.ArrayDeque

fun calc(math: String) : BigDecimal {
    val lexer = MathLexer(CharStreams.fromString(math))
    val tokens = CommonTokenStream(lexer)
    val parser = MathParser(tokens)
    val context: ParseTree = parser.math()
    val walker = ParseTreeWalker()
    val listener = CalculatorListener()
    walker.walk(listener, context)
    return (listener.stack.firstOrNull()?.apply() as? NumberMonad)?.value ?: syntaxError()
}

private fun syntaxError() : Nothing = error("Syntax error")

@FunctionalInterface
private interface Monad {
    fun apply() : Monad
}

private class NumberMonad(val value: BigDecimal) : Monad {
    override fun apply() = this
}

private class CalculatorListener : MathBaseListener() {
    val stack = ArrayDeque<Monad>()

    fun popTwo() : Pair<BigDecimal, BigDecimal> {
        val rightArg = stack.removeLastOrNull() ?: syntaxError()
        val leftArg = stack.removeLastOrNull() ?: syntaxError()
        val leftNum = leftArg.apply() as? NumberMonad ?: syntaxError()
        val rightNum = rightArg.apply() as? NumberMonad ?: syntaxError()
        return Pair(leftNum.value, rightNum.value)
    }

    override fun exitScalar(ctx: MathParser.ScalarContext) {
        val (leftNum, rightNum) = popTwo()
        when {
            ctx.scaleop()?.DIVIDE() != null -> stack.addLast(NumberMonad(leftNum.divide(rightNum)))
            ctx.scaleop()?.MULTIPLY() != null -> stack.addLast(NumberMonad(leftNum.multiply(rightNum)))
            else -> syntaxError()
        }
    }

    override fun exitLinear(ctx: MathParser.LinearContext) {
        val (leftNum, rightNum) = popTwo()
        when {
            ctx.sumop()?.MINUS() != null -> stack.addLast (NumberMonad(leftNum.subtract(rightNum)))
            ctx.sumop()?.PLUS() != null ->stack.addLast (NumberMonad(leftNum.add(rightNum)))
            else -> syntaxError()
        }
    }

    override fun exitNumber(ctx: MathParser.NumberContext) =
        stack.addLast(NumberMonad(BigDecimal(ctx.text)))
}

