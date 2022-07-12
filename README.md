# PathTracer
A path tracer than can render simple objects in a scene. It can render simple objects in a lit scene.

Below is a sample scene with 100 samples per pixel and 10 light bounces per sample.
![Sample render](https://user-images.githubusercontent.com/20031686/163906732-a57b59aa-85de-4150-a232-89f13cbb4f49.png)

To reduce noise, I would like to implement bi-directional lighting. To further accelerate rendering, I would like to implement bounding volume hierarchies.

This code was previously written in Java but rewritten in Kotlin because Kotlins operators reduced code clutter.
