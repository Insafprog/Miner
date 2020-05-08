enum class Box {
    ZERO, NUM1, NUM2, NUM3, NUM4, NUM5, NUM6, NUM7, NUM8, BOMB, OPENED, CLOSED, FLAGED, BOMBED, NOBOMB, INFORM;

    val nextNumberBox: Box
           get() = values()[ordinal + 1]
    lateinit var image: Any
}