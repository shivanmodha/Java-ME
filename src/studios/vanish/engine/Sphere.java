package Engine;
public class Sphere extends Object3D
{
	public Sphere()
	{
		super();
	}
	public Sphere(int x, int y, boolean Cylinder, Color color)
	{
		Initialize(x, y, Cylinder, color);
	}
	public void Initialize(int x, int y, boolean Cylinder, Color color)
	{
		double xAngle = 360 / x;
		double yAngle = 360 / y;
		double factor = 1;
		Vertices = new Vertex[x * y];
		Vertex Transform = new Vertex(0, 0, 0);
		int vertexIndex = 0;
		for (int yIndex = 0; yIndex < y; yIndex++)
		{
			double yAng = Math.sin(toRad(yAngle * yIndex)) * factor;
			Transform.Y = yAng;
			double xAngY = Math.cos(toRad(yAngle * yIndex)) * factor;
			if (Cylinder == true)
			{
				xAngY = 1;
			}
			for (int xIndex = 0; xIndex < x; xIndex++)
			{
				double xAng = Math.sin(toRad(xAngle * xIndex)) * factor;
				double zAng = Math.cos(toRad(xAngle * xIndex)) * factor;
				Transform.X = xAng * xAngY;
				Transform.Z = zAng * xAngY;
				Vertices[vertexIndex] = new Vertex(Transform);
				vertexIndex++;
			}
		}
		Indices = new Index[(x * y) - y - 1];
		Colors = new Color[(x * y) - y - 1];
		int indexValue = 0;
		for (int index = 0; index < (x * y) - y - 1; index++)
		{
			if (indexValue + y + 1 < vertexIndex)
			{
				Indices[index] = new Index(indexValue, indexValue + 1, indexValue + 1 + y, indexValue + y);
				Colors[index] = color;
				indexValue++;
			}
		}
	}
	public void ColorGradient(Color start, Color end)
	{
		int R = start.R;
		int G = start.G;
		int B = start.B;
		for (int index = 0; index < Colors.length; index++)
		{
			if (R < end.R) R++;
			if (R > end.R) R--;
			if (G < end.G) G++;
			if (G > end.G) G--;
			if (B < end.B) B++;
			if (B > end.B) B--;
			Colors[index] = new Color(R, G, B, 255);
		}
	}
	private double toRad(double ang)
	{
		return ang * Math.PI / 180;
	}
}
