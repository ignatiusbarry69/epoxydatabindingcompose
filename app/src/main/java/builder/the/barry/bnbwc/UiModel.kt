package builder.the.barry.bnbwc

data class UiModel (
    val header: String,
    val content: User
)
data class User(
    val id: Int,
    val name: String
) {
}

fun getUiModel(): MutableList<UiModel>{
    return mutableListOf(
        UiModel("First Class",User(1,"barry-a")),
        UiModel("First Class",User(2,"barry-b")),
        UiModel("First Class",User(3,"barry-c")),
        UiModel("Second Class",User(4,"barry-d")),
        UiModel("Second Class",User(5,"barry-e")),
        UiModel("Second Class",User(6,"barry-f")),
        UiModel("Second Class",User(7,"barry-g")),
        UiModel("Second Class",User(8,"barry-h")),
        UiModel("Second Class",User(9,"barry-i")),
        UiModel("Second Class",User(10,"barry-j")),
    )
}