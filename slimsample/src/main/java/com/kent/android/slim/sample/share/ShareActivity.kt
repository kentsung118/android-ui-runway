package com.kent.android.slim.sample.share

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.kent.android.slim.sample.R
import kotlinx.android.synthetic.main.activity_share.*


/**
 * Created by Kent Sung on 2022/8/4.
 */
class ShareActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        init()
    }

    var uri1: Uri? = null
    var uri2: Uri? = null

    @RequiresApi(Build.VERSION_CODES.R)
    private fun init() {
        initReaderBtn()
        initIGStory()
        initIGFeed()
        initFBStory()
        initURLScheme()
    }

    private fun initURLScheme(){
        btn_url_scheme.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("media17://v2/live/15014570"))
            startActivity(intent)
        }
    }

    private fun initFBStory() {
        btn_fb_story.setOnClickListener {
            // Define photo or video asset URI
            val backgroundAssetUri = Uri.parse("your-image-asset-uri-goes-here")
            val appId = "160057864349016" // This is your application's FB ID
            // Instantiate implicit intent with ADD_TO_STORY action
            val intent = Intent("com.facebook.stories.ADD_TO_STORY")
            intent.setDataAndType(uri1, "image/jpeg")
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.putExtra("com.facebook.platform.extra.APPLICATION_ID", appId)
            // Instantiate activity and verify it will resolve implicit intent
            val activity: Activity = this
            if (activity.packageManager.resolveActivity(intent, 0) != null) {
                activity.startActivityForResult(intent, 0)
            }
        }
    }

    private fun initIGFeed() {
        btn_ig_feed.setOnClickListener {
            // Create the new Intent using the 'Send' action.
            val share = Intent(Intent.ACTION_SEND)
            // Set the MIME type
            share.type = "image/*"
            // Create the URI from the media
    //        val media = File(mediaPath)
    //        val uri = Uri.fromFile(media)
            // Add the URI to the Intent.
            share.putExtra(Intent.EXTRA_STREAM, uri1)
            // Broadcast the Intent.
            startActivity(Intent.createChooser(share, "Share to"))
        }
    }

    private fun initIGStory() {
        btn_ig_story_bg.setOnClickListener {
            // Define image asset URI
    //            val backgroundAssetUri: Uri = Uri.parse(uriString)
            val sourceApplication = "com.my.app"
            // Instantiate implicit intent with ADD_TO_STORY action and background asset
            val intent = Intent("com.instagram.share.ADD_TO_STORY")
            intent.setDataAndType(uri1, "image/*")
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            // Instantiate activity and verify it will resolve implicit intent
            val activity: Activity = this
            if (activity.packageManager.resolveActivity(intent, 0) != null) {
                activity.startActivityForResult(intent, 0)
            }
        }

        btn_ig_story_sticker.setOnClickListener {
            // Define image asset URI
            val stickerAssetUri = Uri.parse("your-image-asset-uri-goes-here")
            val sourceApplication = "com.my.app"
            // Instantiate implicit intent with ADD_TO_STORY action,
            // sticker asset, and background colors
            val intent = Intent("com.instagram.share.ADD_TO_STORY")
            intent.putExtra("source_application", sourceApplication)

            intent.type = "image/*"
            intent.putExtra("interactive_asset_uri", uri1)
            intent.putExtra("top_background_color", "#33FF33")
            intent.putExtra("bottom_background_color", "#FF00FF")
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            // Instantiate activity and verify it will resolve implicit intent
            val activity: Activity = this
            activity.grantUriPermission(
                "com.instagram.android", uri1, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            if (activity.packageManager.resolveActivity(intent, 0) != null) {
                activity.startActivityForResult(intent, 0)
            }
        }

        btn_ig_story_bg_sticker.setOnClickListener {
            // Define background and sticker asset URIs
            val backgroundAssetUri = Uri.parse("your-background-image-asset-uri-goes-here")
            val stickerAssetUri = Uri.parse("your-sticker-image-asset-uri-goes-here")
            val sourceApplication = "com.my.app"
            // Instantiate implicit intent with ADD_TO_STORY action,
            // background asset, and sticker asset
            val intent = Intent("com.instagram.share.ADD_TO_STORY")
            intent.putExtra("source_application", sourceApplication)

            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.setDataAndType(uri1, "image/*")
            intent.putExtra("interactive_asset_uri", uri2)
            // Instantiate activity and verify it will resolve implicit intent
            val activity: Activity = this
            activity.grantUriPermission(
                "com.instagram.android", uri2, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            if (activity.packageManager.resolveActivity(intent, 0) != null) {
                activity.startActivityForResult(intent, 0)
            }
        }
    }

    private fun initReaderBtn() {
        btn_reader_pic1.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            pickedActivityResultLauncher1.launch(intent)
        }

        btn_reader_pic2.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            pickedActivityResultLauncher2.launch(intent)
        }
    }

    private val pickedActivityResultLauncher1 = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            this.uri1 = uri
            text_uri.text = uri.toString()
            img_1.setImageURI(uri)
        }
    }

    private val pickedActivityResultLauncher2 = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            this.uri2 = uri
            img_2.setImageURI(uri)
        }
    }

}