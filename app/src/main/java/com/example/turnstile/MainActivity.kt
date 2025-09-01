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
import androidx.compose.ui.res.stringResource
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
        val recaptchaKey = stringResource(R.string.recaptcha_key)
        AndroidView(
            factory = { context ->
                WebView(context)
            },
            update = { webView ->
                webView.settings.javaScriptEnabled = true
                webView.settings.domStorageEnabled = true
                webView.loadDataWithBaseURL("https://dev.homely.com.au", html.replace("<RECAPTCHA_KEY>", recaptchaKey), "text/html", "UTF-8", null)
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

val html = """
    <html>
        <head>
            <script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=<RECAPTCHA_KEY>" async defer></script>
        </head>
        <body>
            <script type="text/javascript">
                var onloadCallback = function() {
                    console.log('Captcha is ready');
                   
                    grecaptcha.ready(function() {
                        grecaptcha.execute('<RECAPTCHA_KEY>', {action: 'submit'}).then(function(token) {
                            // Add your logic to submit to your backend server here.
                            console.log('The token: ' + token)
                        });
                    });
                };
            </script>
        </body>
    </html>
""".trimIndent()