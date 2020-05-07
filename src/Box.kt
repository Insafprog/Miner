enum class Box {
    ZERO, NUM1, NUM2, NUM3, NUM4, NUM5, NUM6, NUM7, NUM8, BOMB, OPENED, CLOSED, FLAGED, BOMBED, NOBOMB;

    val number: Int
        get() = this.ordinal
    val nextNumberBox: Box
        get() = values()[this.ordinal + 1]
    lateinit var image: Any
}