# Java Matrix Engine (Java-ME)
## About
Java-ME is a lightweight 3D graphics engine for Java based applications. It allows for improved rendering, providing a managed framework to build stable applications.

Java-ME provides the following capabilities
- 2D Graphics
- 3D Graphics (Built from the ground up)
- Multi-Threaded Rendering
- Dynamic Event Handling
- Lights
- Basic GUI
- Color Library
- User Input (Keyboard and Mouse)
- Audio (Beta)

A basic rotating cube sample can be found below.
## Included Libraries
- studios.vanish.engine
- studios.vanish.utility
## Improved Event Handling
This API utilizes a Utility library (found in `studios.vanish.utility`) that allows for improved event handling. This library was developed along side Java-ME. The `Method` class takes in a string that represents a function name, and an object where the function can be found. You can later invoke this function with as many parameters as you like through the `Invoke(Object... obj)` method. `Invoke` forwards the parameters to the specified method, and returns the same value as the method invoked.

The `EventHandler` class holds multiple `Method`s, and allows one to invoke all of them at the same time through the `InvokeAll(Object... obj)` method. Throughout the Matrix Graphics Engine, `EventHandler`s are created so that the end user can specify what to do when a specific event occurs. An example of such is the `OnPaint` method. When it is time to draw objects to the screen, every method in `OnPaint` are invoked, allowing the user to render their own objects.
## GraphicsUnit
All of Java-ME's rendering capabilities are handled in the `GraphicsUnit` class. A `GraphicsUnit` object is automatically created when using the `Window` class, and is sent for use through the OnPaint event. However, you can always create a `GraphicsUnit` object, in case you want to use the 3D capabilities without initializing a `Window`.
```
GraphicsUnit Graphics = new GraphicsUnit(Graphic, FieldOfView, Size, Resolution, FillMode, Camera, CalculateIntersections, RenderLights);
```
- `Graphic`: The underlining graphics used, a `Graphics2D` object in Java Swing.
- `FieldOfView`: A value that specifies how much of the 3D space is seen. 1024 is a good start.
- `Size`: The size of the `JFrame`, or `Window`
- `Resolution`: The resolution used to draw the 3D components
- `FillMode`: Either Solid, Wireframe, or Vertices. This specifies how to draw the 3D objects.
- `Camera`: A `Camera` object that specifies the user viewport.
- `CalculateIntersections`: Automatically calculate whether objects intersect, and render accordingly. (Unfortunately, this process is very resource heavy and slow if it does work at all).
- `RenderLights`: Specify whether to enable light based rendering, or simply render based on object color.

Many of these parameters impact the way the 3D components are drawn. `GraphicsUnit` has the capability to perform many 3D calculations that are useful to rendering, but are sometimes useful when updating a scene. Therefore, a useful `GetNullGraphics` method can be found in the `Window` class, which creates a `GraphicsUnit` object without the rendering capabilities.
```
GraphicsUnit Graphics = window.GetNullGraphicsUnit();
```
## Getting Started
Instead of manually creating a `JFrame` window, Java-ME handles it through the `Window` class. Thus, you can use Java-ME as the underlining framework for your application, as everything needed to render to the screen is already handled.
```
Window window = new Window("Java Matrix Engine", new Size(800, 600), true); //specify the name, size, and border style
```
The next lines initialize the graphical components that the window class uses by specifying the framerate, and shows the window. `Initialize3D` only needs to be called if you are going to render 3D components.
```
window.Initialize(60); //specify the framerate
window.Initialize3D(new Size(800, 600), 1024, FillMode.Solid, false, false); //specify resolution, field of view, fill mode, calculate intersections (=false), automatically render all 3D objects
window.Show();
```
```
window.Initialize3D();
```
To draw to the screen, you need to handle the OnPaint event.
```
window.OnPaint.Add(this, "Render"); //specify the object, and the function's name
```
Don't forget to create the coinciding method. Remember that the method name must match the name you added to the `OnPaint` event handler. The method must also be public so that it can be accessed externally, and the parameters must correctly match the ones that the OnPaint method sends. In this case, `OnPaint` only sends one parameter.
```
public void Render(GraphicsUnit Graphics) //The Render method that will be called
```
## Sample Cube Application
Here is a sample rotating cube application.
```
public class Program
{
    Object3D cube = new Object3D();
    Window window = new Window("Java Matrix Engine", new Size(800, 600), true);
    public static void main(String[] args)
    {
        new Program();
    }
    public Program()
    {
        Initialize(60);
        while (true)
        {
            Update();
            window.Wait(2);
        }
    }
    public void Initialize(int FPS)
    {
        window.OnPaint.Add(this, "Render");
        window.Initialize(FPS);
        window.Initialize3D(new Size(800, 600), 1024, FillMode.Wireframe, false, true);
        window.Show();
        cube.Vertices = new Vertex[]{
                new Vertex(-1, +1, -1), new Vertex(+1, +1, -1), new Vertex(+1, -1, -1), new Vertex(-1, -1, -1),
                new Vertex(-1, +1, +1), new Vertex(+1, +1, +1), new Vertex(+1, -1, +1), new Vertex(-1, -1, +1)};
        cube.Indices = new Index[]{
                new Index(0, 1, 2, 3), new Index(4, 5, 6, 7),
                new Index(0, 1, 5, 4), new Index(2, 3, 7, 6),
                new Index(1, 2, 6, 5), new Index(0, 3, 7, 4)};
        cube.Colors = new Color[]{
                Color.Orange, Color.Green,
                Color.Blue, Color.Yellow,
                Color.Red, Color.Pink};
        cube.Location = new Vertex(0, 0, 5);
    }
    public void Update()
    {
        cube.Rotation.X += 0.1;
        cube.Rotation.Y += 0.1;
    }
    public void Render(GraphicsUnit Graphics)
    {
        Graphics.FillRectangle(Color.Black, new Point(0, 0), window.Size);
        cube.Render(Graphics);
    }
}
```
## Further Documentation
Unfortunately, I never planned on releasing this when I first embarked on making a 3D enabled engine for Java. Therefore, I need some time to make proper documentation to make this API easy to use. As of right now, there is a preliminary guide below, which will be able to get you started.
# Color Library
Java-ME comes with a list of predefined colors. All of these colors were taken from [Rapid Tables](http://www.rapidtables.com/web/color/RGB_Color.htm).
## Color Object
You can create your own colors
```
Color color = new Color(255, 255, 255); //R, G, B
Color color = new Color(255, 255, 255, 255); //R, G, B, A
```
## ColorLinearGradient
Instead of a basic solid color, this color type represents a linear gradient. You can send `ColorLinearGradient` colors to any render method, and it will be draw as a gradient. To create a `ColorLinearGradient`, you must specify a couple of things.
```
ColorLinearGradient col = new ColorLinearGradient(2); //How many times are you going to change the color?
col.Set(0, new Point(0, 0), Color.Black); //Where is the first color, and what is that color?
col.Set(1, new Point(800, 500), Color.Blue); //Where is the second color, and what is that color?
```
# Rendering Structures
## Point
A 2D point, with an X and Y.
## Size
Similar to a 2D point, except with Width, and Height.
## Vertex
A 3D Point, with X, Y, and Z.
# 2D Rendering
`GraphicsUnit` has a couple of methods that can be used to draw things. Note that Java-ME also comes with shapes that can also be used to draw. Further examples of this can be found below.
## Identifiers: `Fill` vs `Draw`
The identifier before the basic rendering methods specifies whether or not to render the shape as a solid object or as a bordered object. `Fill` renders it as a solid, while `Draw` render it as an outline.
## `DrawString(String Text, Color Color, Point Location, Font Font)`
This draws `Text` to the screen, in `Color` at `Location` in the `Font` specified. Note that `Font` is not in Java-ME, but is a default Java class (found at `java.awt.Font`).
## `DrawLine(Color Color, Point[] Points)`
This draws a line between two points in the color specified. Note that you can send more than two points, but only the first two points will be used when drawing the line.
## Identifier + `Rectangle(Color Color, Point Location, Size Size)`
This draws a rectangle at `Location.X` and `Location.Y`, with size `Size.Width` and `Size.Height`, in `Color`.
## Identifier + `Ellipse(Color Color, Point Location, Size Size)`
This draws an ellipse at `Location.X` and `Location.Y`, with size `Size.Width` and `Size.Height`, in `Color`. If you want to draw a circle, set `Size.Width = Size.Height`.
## Identifier + `Polygon(Color Color, Point[] Points)`
This draws any polygon. Specify the points that make up the polygon, and give it a `Color`.
## Shapes
Java-ME comes with an abstract `Shape` class, that specifies an `Intersects` and `Render` method. The two basic objects that implement this are
- Rectangle
- Circle

You can render a rectangle by using the rectangle class.
```
Rectangle rectangle;
rectangle.Initialize(Color.Black, new Point(0, 0), new Size(500, 500));
rectangle.Render(Graphics);
```
You can do hit-detection by calling the `Intersects` method.
```
if (shape1.Intersects(shape2))
{
    //do something special, because the objects have collided
}
else
{
    //don't do something special, because the objects aren't colliding yet
}
```
# 3D Rendering
All of the 3D methods in `GraphicsUnit` have the `D3D_` identifier before them. This differentiates the 3D capabilities of the graphics library from the 3D. Note that the 3D engine is still heavily based upon the 2D framework.
## Rendering through `Object3D`
To render a 3D object effectively, use the `Object3D` class. This will ensure that any object rendered  exactly as specified and that they will be rendered according to their Z axis, as this will automatically implement a Z Buffer. Transformations will also be self-contained, easy to manage, and fast. To create an `Object3D`, you need to specify the object's faces, and colors.

When creating an object, you need to specify every vertex that will be used. Identify these vertices within the vertices array object.
```
object.Vertices = new Vertex[]
{
    //Include every single vertex here
};
```
Next, you define a face through the Indices array object. An `Index` object takes as many integer values as it takes to define a face. It takes at minimum 4 vertices to define a face. Each integer value in `Index` corresponds to the index value of the vertex.
```
object.Indices = new Index[]
{
    //Include every face here
};
``` 
Finally, you need to define the color for each face. Note that the following condition must be met: `object.Colors.length = object.Indices.length`.
```
object.Colors = new Color[]
{
    //Include the colors of each face here
};
```
The following code will generate a 3D Cube.
```
Object3D object = new Object3D();
object.Vertices = new Vertex[]
{
    new Vertex(-1, +1, -1), new Vertex(+1, +1, -1), new Vertex(+1, -1, -1), new Vertex(-1, -1, -1),
    new Vertex(-1, +1, +1), new Vertex(+1, +1, +1), new Vertex(+1, -1, +1), new Vertex(-1, -1, +1)
};
object.Indices = new Index[]
{
    new Index(0, 1, 2, 3), new Index(4, 5, 6, 7),
    new Index(0, 1, 5, 4), new Index(2, 3, 7, 6),
    new Index(1, 2, 6, 5), new Index(0, 3, 7, 4)
};
obj1.Colors = new Color[]
{
    new Color(Color.Orange, 100), new Color(Color.Green, 100),
    new Color(Color.Blue, 100), new Color(Color.Yellow, 100),
    new Color(Color.Red, 100), new Color(Color.Pink, 100)
};
```
Finally, render the object by using the render method.
```
object.Render(Graphics); //where Graphics is a GraphicsUnit object
```
If in `Initialize3D`, you disable AutomaticRender, you need to manually render the 3D objects.
```
Graphics.Render(); //where Graphics is a GraphicsUnit object
```
Each `Object3D` object comes with variables that specify transformations.
```
object.Location = new Vertex(0, 0, 10); //translates the object
object.Rotation = new Vertex(10, 10, 10); //rotates the object
object.Scale = new Vertex(1, 1, 1); //scales the object. Values between 0 and 1 make it smaller, while values greater than 1 make it larger
object.Revolution = new Vertex(10, 10, 10); //revolves the object around its location. Note that this only works when RevolutionRadius is specified
object.RevolutionRadius = new Vertex(0, 4, 0); //specifies how to revolve the object
```
Java-ME comes with a special `Object3D` class called `Sphere`, which automatically generates the vertices and indices for a sphere or cylinder. 
```
Object3D sphere = new Sphere(10, 10, false, Color.White); //generate a sphere with 10 by 10 definition
Object3D cylinder = new Sphere(10, 10, true, Color.White); //generate a cylinder with 10 by 10 definition
```
## `Vertex D3D_ToProjection(Vertex vertex)`
This converts any 3D point, or `Vertex`, into a 2D point so that it can be drawn onto the screen.  It essentially projects any 3D point onto the screen. Note that this returns a `Vertex`, instead of a simple `Point`. The Z value remains the same.
## `Vertex D3D_FromProjection(Point point, Size resolution, Size windowSize, double FieldOfView, double CameraZ)`
This goes the other way. It converts a 2D point into 3D, given the screens resolution, the size of the window, the output's field of view, and the Z value (this is normally the camera's Z location)
## `Vertex D3D_Transformation_Translation(Vertex vertices, Vertex Location)`
This transfers any `Vertex` by `Location`
## `Vertex D3D_Transformation_Rotation_X(Vertex vertices, Vertex Rotation)`
This rotates any `Vertex` by `Rotation`. Note that this only uses the X value.
## `Vertex D3D_Transformation_Rotation_Y(Vertex vertices, Vertex Rotation)`
This rotates any `Vertex` by `Rotation`. Note that this only uses the Z value.
## `Vertex D3D_Transformation_Rotation_Z(Vertex vertices, Vertex Rotation)`
This rotates any `Vertex` by `Rotation`. Note that this only uses the Y value.