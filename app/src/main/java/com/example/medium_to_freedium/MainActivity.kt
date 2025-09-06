package com.example.medium_to_freedium

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.medium_to_freedium.ui.theme.Medium_To_FreediumTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // No UI rendered (no setContent{})

        // Handle the intent
        if (intent?.action == Intent.ACTION_SEND) {
            // Process shared Medium link
            handleShareIntent(intent)
        } else {
            // Show error if app is opened directly
            showError("This app does't support UI.Bye Bye🙂")
            finish()
        }
    }

    private fun handleShareIntent(intent: Intent) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null && isMediumUrl(sharedText)) {

            redirectToFreedium(sharedText)
        } else {
            showError("Not a Medium link.")
        }
        finish() // Close the app
    }

    private fun isMediumUrl(text: String): Boolean {
        return text.contains("medium.com/")
    }

    private fun redirectToFreedium(sharedText: String) {
        val startingIndex=sharedText.indexOf("https://")
        val mediumUrl=sharedText.subSequence(startingIndex,sharedText.length)
        val freediumUrl = "https://freedium.cfd/${mediumUrl.trim()}"
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(freediumUrl)))
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        // Optional: Log the error for debugging
        Log.e("Freedium", message)
    }
}

