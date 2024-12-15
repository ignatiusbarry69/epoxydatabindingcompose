package builder.the.barry.bnbwc

import android.app.Application
import com.airbnb.epoxy.Carousel

class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        Carousel.setDefaultGlobalSnapHelperFactory(null)
    }
}