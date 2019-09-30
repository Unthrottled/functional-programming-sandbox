fun main() {
    println(IntAddition(IntAddition.zero, 1))
    println(IntMultiplication(IntMultiplication.zero, 1))
    println(BooleanOr(BooleanOr.zero, false))
    println(BooleanOr(BooleanOr.zero, true))
    println(BooleanAnd(BooleanAnd.zero, false))
    println(BooleanAnd(BooleanAnd.zero, true))
    val endoFundie: EndoFunction<Int> = EndoFunction()
    val adder: (Int) -> Int = { IntAddition(it, it) }
    println(endoFundie(adder, adder)(1))

    val stringo = listOf("Finna", "bust", "a", "nut")
    println(stringo.reduceRight(StringConcat.operation))
    println(stringo.reduceLeft { it }(StringConcat.operation))

    val sentenceBoi = " lorem ipsum dolor sit amet, "
    println(WordCount(WordCount.zero, sentenceBoi.toStub()))
    println(WordCount(WordCount.zero, sentenceBoi.trim().toStub()))
    println(WordCount(WordCount.zero, "famravioli".toStub()))
    println(WordCount("famravioli".toStub(), WordCount.zero))

}

// Yo dawg, you like Indian food?
fun <T, R> List<T>.reduceLeft(mapper: (T) -> R): ((R, R) -> R) -> R {
    return { reducer -> this.asReversed().map(mapper).reduceRight(reducer) }
}

interface Monoid<T> {
    val zero: T
    operator fun invoke(left: T, right: T): T

    val operation: (T, T) -> T
        get() = { l, r -> invoke(l, r) }
}

interface WC
data class Stub(val characters: String) : WC
data class Part(
    val leftStub: String,
    val rightStub: String,
    val wordCount: Int
) : WC

fun String.toStub(): Stub = Stub(this)

object WordCount : Monoid<WC> {
    override val zero: WC
        get() = Stub("")

    override fun invoke(left: WC, right: WC): WC {
        val leftPart = if (left is Stub) toPart(left) else left as Part
        val rightPart = if (right is Stub) toPart(right) else right as Part
        val splitWord = leftPart.rightStub + rightPart.leftStub
        val wordCount = leftPart.wordCount + rightPart.wordCount + if (splitWord.isNotBlank()) 1 else 0
        return Part(leftPart.leftStub, rightPart.rightStub, wordCount)
    }

    private fun toPart(stub: Stub): Part {
        val words = stub.characters.split(" ")
        return Part(
            words.getOrElse(0) { "" },
            if(words.size == 1) "" else words.getOrElse(words.size - 1) { "" },
            if (words.size < 3) 0 else words.size - 2
        )
    }

}

object StringConcat : Monoid<String> {

    override fun invoke(left: String, right: String): String = "$left$right"

    override val zero: String
        get() = ""

}

object IntAddition : Monoid<Int> {
    override val zero: Int
        get() = 0

    override fun invoke(left: Int, right: Int): Int =
        left + right
}

object IntMultiplication : Monoid<Int> {
    override val zero: Int
        get() = 1

    override fun invoke(left: Int, right: Int): Int =
        left * right

}

object BooleanOr : Monoid<Boolean> {
    override val zero: Boolean
        get() = false

    override fun invoke(left: Boolean, right: Boolean): Boolean =
        left || right

}

object BooleanAnd : Monoid<Boolean> {
    override val zero: Boolean
        get() = true

    override fun invoke(left: Boolean, right: Boolean): Boolean =
        left && right
}

class EndoFunction<T> : Monoid<(T) -> T> {
    override val zero: (T) -> T
        get() = { it }

    override fun invoke(left: (T) -> T, right: (T) -> T): (T) -> T {
        return { leftT -> right(leftT) }
    }
}