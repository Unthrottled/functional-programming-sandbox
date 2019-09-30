fun main() {
    println(IntAddition(IntAddition.zero, 1))
    println(IntMultiplication(IntMultiplication.zero, 1))
    println(BooleanOr(BooleanOr.zero, false))
    println(BooleanOr(BooleanOr.zero, true))
    println(BooleanAnd(BooleanAnd.zero, false))
    println(BooleanAnd(BooleanAnd.zero, true))
}

interface Monoid<T> {
    val zero: T

    operator fun invoke(left: T, right: T): T
}

object IntAddition : Monoid<Int> {
    override val zero: Int
        get() = 0

    override fun invoke(left: Int, right: Int): Int =
        left + right
}

object IntMultiplication: Monoid<Int> {
    override val zero: Int
        get() = 1

    override fun invoke(left: Int, right: Int): Int =
        left * right

}

object BooleanOr: Monoid<Boolean> {
    override val zero: Boolean
        get() = false

    override fun invoke(left: Boolean, right: Boolean): Boolean =
        left || right

}
object BooleanAnd: Monoid<Boolean> {
    override val zero: Boolean
        get() = true

    override fun invoke(left: Boolean, right: Boolean): Boolean =
        left && right
}

//object EndoFunction<T>: Monoid<(T) -> T> {
//    override val zero: (T) -> T
//        get() = { it }
//
//    override fun invoke(left: (T) -> T, right: (T) -> T): (T) -> T {
//        return { it }
//    }
//
//}