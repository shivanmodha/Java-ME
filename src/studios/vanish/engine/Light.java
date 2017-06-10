package Engine;
public class Light
{
	public Vertex Location;
	public Point Attenuation;
	public Color Diffuse;
	public Light()
	{
		Location = new Vertex(0, 0, 0);
		Attenuation = new Point(1, 1);
		Diffuse = new Color(255, 255, 255, 255);
	}
	public Light(Vertex _location, Point _attenuation, Color _color)
	{
		Location = new Vertex(_location);
		Attenuation = new Point(_attenuation);
		Diffuse = new Color(_color);
	}
	public void Render(GraphicsUnit Graphics)
	{
		Graphics.D3D_Lights_Add(this);
	}
	public static Color Ambient = new Color(10, 10, 10);
}
