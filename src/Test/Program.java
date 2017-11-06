package Test;

import studios.vanish.engine.Color;
import studios.vanish.engine.FillMode;
import studios.vanish.engine.GraphicsUnit;
import studios.vanish.engine.Object3D;
import studios.vanish.engine.Point;
import studios.vanish.engine.Size;
import studios.vanish.engine.Index;
import studios.vanish.engine.Vertex;
import studios.vanish.engine.Window;

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
		Initialize(2000);
		while (true)
		{
			Update();
			window.Wait(2);
		}
	}
	public void Initialize(int FPS)
	{
		window.OnPaint.Add(this, "Render");
		window.OnMouseWheel.Add(this, "Wheel");
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
	public void Wheel(int ScrollAmount)
	{
		window.Camera.Location.Z -= ScrollAmount;
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
		window.SetTitle(window.FPS + "");
	}
}