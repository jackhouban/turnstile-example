Replace the domain with the domainThatYouEnableOnTurnstileDash enable in the turnstile widget dash. This is in line 50 of MainActivity.kt

```
webView.loadDataWithBaseURL("https://domainThatYouEnableOnTurnstileDash", turnstileHtml, "text/html", "UTF-8", null)
```

Replace site key found in turnstile dash. This is in line 86 of MainActivity.kt

```
sitekey: '0xreplacewithyoursitekey',
```
