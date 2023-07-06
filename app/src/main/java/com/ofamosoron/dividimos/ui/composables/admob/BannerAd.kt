package com.ofamosoron.dividimos.ui.composables.admob

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.ofamosoron.dividimos.BuildConfig
import com.ofamosoron.dividimos.R

@Composable
fun BannerAd() {
    val isDebug = BuildConfig.BUILD_TYPE.equals("debug")

    val unitId =
        if (isDebug) {
            stringResource(id = R.string.ad_mob_test_banner_id)
        } else {
            stringResource(id = R.string.ad_mob_banner_id)
        }

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = unitId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}