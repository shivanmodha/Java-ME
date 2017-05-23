package Engine;
public class Grid extends Object3D
{
	public Grid()
	{
		super();
	}
	public Grid(int x, int y, Color color)
	{
		Initialize(x, y, color);
	}
	public void Initialize(int x, int y, Color color)
	{
		x++;
		y++;
		Vertices = new Vertex[x * y];
		int vertexIndex = 0;
		for (int j = 0; j < y; j++)
		{
			for (int i = 0; i < x; i++)
			{
				Vertices[vertexIndex] = new Vertex(i - (x / 2), 0, j - (y / 2));
				vertexIndex++;
			}
		}
		Indices = new Index[(x - 1) * (y - 1)];
		Colors = new Color[(x - 1) * (y - 1)];
		vertexIndex = 0;
		for (int j = 0; j < y - 1; j++)
		{
			for (int i = 0; i < x - 1; i++)
			{
				int heightOffset = x * j;
				Indices[vertexIndex] = new Index(heightOffset + i, heightOffset + i + 1, heightOffset + x + i + 1, heightOffset + x + i);
				Colors[vertexIndex] = color;
				vertexIndex++;
			}
		}
	}
}