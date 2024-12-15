package builder.the.barry.bnbwc

import android.util.Log
import android.widget.Toast
import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.carousel

class MyEpoxyController(val onItemClick: (String) -> Unit) : AsyncEpoxyController() {

    var firstClass: List<UiModel> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    var secondClass: List<UiModel> = emptyList()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
//        getUiModel().forEachIndexed { pos, model ->
//            content{
//                id(pos)
//                title(model.content.name)
//            }
//        }
//        secondClass.forEach(){
        val models = secondClass.map {
            ContentBindingModel_()
                .id(it.content.id)
                .title(it.content.name)
                .onClickContent { _ ->
                    this@MyEpoxyController.onItemClick(it.content.name)
                }
        }
//        models.forEach(){
//            Log.i("barry-dev", "ID: ${it.id()}, Name: ${it.title()}")
//        }
//        Log.i("barry-dev",models.toString())
        carousel {
            id("second")
//                padding(Carousel.Padding.dp(0, 4, 0, 16, 8))
            models(models)
        }

        secondClass.forEachIndexed(){ pos, data ->
            content {
                id(pos)
                title(data.content.name)
                onClickContent { _ ->
                    this@MyEpoxyController.onItemClick(data.content.name)
                }
            }
        }

        carousel {
            id("second")
                padding(Carousel.Padding.dp(0, 4, 0, 16, 8))
            models(models)
        }


    }
}
