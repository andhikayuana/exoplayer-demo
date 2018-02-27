package id.yuana.exoplayer

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            player?.release()
            player = null
        }
    }

    private fun hideSystemUi() {
        epvVideo.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private var player: SimpleExoPlayer? = null

    private fun initPlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(this),
                DefaultTrackSelector(),
                DefaultLoadControl())

        epvVideo.player = player

        player?.playWhenReady = true
        player?.seekTo(0, 0)

        val uri = Uri.parse("https://r5---sn-ogueln7r.googlevideo.com/videoplayback?id=o-AN19VoK5Sf6cpH7tMVjLdVDhV8PhSGkG8zN6QOtGYIEv&c=WEB&mime=video%2Fmp4&lmt=1474129528804693&ip=23.239.195.119&pl=24&itag=22&dur=276.038&source=youtube&fvip=5&ei=lgSVWoXJCIOsuwWr_YzIAQ&requiressl=yes&sparams=dur,ei,expire,id,initcwndbps,ip,ipbits,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&expire=1519737078&ipbits=0&key=cms1&ratebypass=yes&signature=62208A9C04AD9E458A326ECD72416D216B2517CE.706648BE6868DC05922E142CE90F5648EE271497&video_id=XJgh7iaN164&title=Muse+-+Undisclosed+Desires+%5BLive+At+Royal+Albert+Hall%5D&rm=sn-mv-qxoe76&req_id=f7bfb4162444a3ee&redirect_counter=2&cm2rm=sn-q4feed76&cms_redirect=yes&mip=117.20.54.3&mm=34&mn=sn-ogueln7r&ms=ltu&mt=1519714810&mv=u")
        val mediaSource = buildMediaSource(uri)

        player?.prepare(mediaSource, true, false)
    }

    private fun buildMediaSource(uri: Uri?): MediaSource? {
        return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(BuildConfig.APPLICATION_ID))
                .createMediaSource(uri)
    }

}
