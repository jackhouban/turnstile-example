package com.example.turnstile

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.turnstile.ui.theme.TurnstileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TurnstileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column() {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        AndroidView(
            factory = { context ->
                WebView(context)
            },
            update = { webView ->
                webView.settings.javaScriptEnabled = true
                webView.settings.domStorageEnabled = true
                webView.loadDataWithBaseURL("https://domainThatYouEnableOnTurnstileDash", turnstileHtml, "text/html", "UTF-8", null)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TurnstileTheme {
        Greeting("Android")
    }
}

val turnstileHtml = """
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <script src="https://challenges.cloudflare.com/turnstile/v0/api.js?render=explicit&onload=_turnstileCb" async defer></script>
        <style>
            body {
                text-align: center;
            }
            .container {
                width: 80%; /* Or a fixed width */
                margin: 0 auto; /* Centers the block-level element */
            }
        </style>
    </head>
    <body>
        <div class="cf-turnstile" id="cf-turnstile"></div>
        <script>
            function _turnstileCb() {
                console.log('_turnstileCb called');         

                turnstile.render('#cf-turnstile', {
                    sitekey: '0xreplacewithyoursitekey',
                    theme: 'light',
                    callback: function (token) {
                        console.log('Challenge Success ' + token);
                        Android.onChallenge(token)
                    },
                });
            }
        </script>
    </body>
    </html>
""".trimIndent()