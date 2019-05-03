# Spotflock Studio SDK (Java)

[![](https://studio.spotflock.com/static/img/logo-high.png)](https://studio.spotflock.com)
Spotflock Studio renders a comprehensive spectrum of solutions that can be accessed by users on-demand from our pool of transformational technologies.

### Installation
Spotflock Studio SDK requires Java 1.8 + . Go to https://studio.spotflock.com and create an app. On creation of an app, you will get an API Key.

```sh
import com.spotflock.StudioClient;

StudioClient c = new StudioClient("xxx");
String response = c.sentimentAnalysis("I am feeling sick.");
System.out.print(response);
```

For more details, visit https://studio.spotflock.com