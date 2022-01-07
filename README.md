# Virtual Piano
Create any kind of piano from the customizable PianoKey view.

## Code for single Piano Key

> White key
```xml
<com.dreamtech.virtual_piano.PianoKeyView
    android:layout_width="50dp"
    android:layout_height="300dp"
    app:default_style="white"
    app:label="D" />
```

![](https://github.com/gangadharashettypj/virtual_piano/blob/master/images/white_key.png?raw=true)

&nbsp;
> Black key
```xml
<com.dreamtech.virtual_piano.PianoKeyView
    android:layout_width="50dp"
    android:layout_height="300dp"
    app:default_style="black"
    app:label="D" />
```

![](https://github.com/gangadharashettypj/virtual_piano/blob/master/images/black_key.png?raw=true)

&nbsp;
> Kotlin code for key press and release events
```kotlin
    var key = findViewById<PianoKeyView>(R.id.piano_key)
    key.setOnTapDownListener { 
        //TODO play your mp3 file
    }
    key.setOnTapReleaseListener {
        //TODO pause your mp3
    }
```

&nbsp;
> Sample layout for full 1 octave

[Check code for images shown below](https://github.com/gangadharashettypj/virtual_piano/blob/master/app/src/main/res/layout/activity_main.xml)

![](https://github.com/gangadharashettypj/virtual_piano/blob/master/images/full_octave.png?raw=true)

![](https://github.com/gangadharashettypj/virtual_piano/blob/master/images/full_octave_white_selected.png?raw=true)

![](https://github.com/gangadharashettypj/virtual_piano/blob/master/images/full_octave_black_selected.png?raw=true)


## Available properties

| Key Words | Descriprion |
| -------------- | -------------- |
| label | key label text |
| label_color | key label text color |
| label_size | key label text size |
| outer_border_width | border width around the key |
| stroke_color | border color for key border |
| shadow_color | bottom pressing portion color of the key |
| top_left_radius | top left radius |
| top_right_radius | top right radius |
| bottom_left_radius | bottom left radius |
| bottom_right_radius | bottom right radius |
| animation_duration | key press/release aimation duration |
| pressed_height | bottom key pressing height |
| key_color | key background color |
| pressed_color | key color on key pressed |
| default_style | white or black => white key or black key with default values set for all the above properties |

