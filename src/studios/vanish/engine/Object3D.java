package Engine;
import java.util.ArrayList;
public class Object3D
{
	public Vertex[] Vertices;
	public Index[] Indices;
	public Color[] Colors;
	public Vertex Location = new Vertex();
	public Vertex Rotation = new Vertex();
	public Vertex Scale = new Vertex(1, 1, 1);
	public Vertex Revolution = new Vertex();
	public Vertex RevolutionRadius = new Vertex();
	public Object3D()
	{
		
	}
	public Object3D(Vertex[] vert, Index[] index, Color[] color)
	{
		Vertices = vert;
		Indices = index;
		Colors = color;
	}
	public static double GetZIndex(ArrayList<Double> z)
	{
		double _return = 0;
		double smallest = z.get(0);
		double largest = z.get(0);
		for (int i = 0; i < z.size(); i++)
		{
			_return += z.get(i);
			if (z.get(i) < smallest)
			{
				smallest = z.get(i);
			}
			if (z.get(i) > largest)
			{
				largest = z.get(i);
			}
		}
		return ((_return / z.size()) + ((smallest))) / 2;
	}
	public void Render(GraphicsUnit Graphics)
	{
		Vertex[] verticesTransformed = Graphics.D3D_Transformation(Vertices, Location, Rotation, Scale, Revolution, RevolutionRadius);
		ArrayList<Double> vertexZ = new ArrayList<Double>();
		ArrayList<Color> vertexColor = new ArrayList<Color>();
		ArrayList<Double> vertexZTemp = new ArrayList<Double>();
		ArrayList<Vertex[]> faces = new ArrayList<Vertex[]>();
		ArrayList<Vertex[]> facesTrans = new ArrayList<Vertex[]>();
		for (int index = 0; index < Indices.length; index++)
		{
			Vertex[] face = new Vertex[Indices[index].Index.length];
			Vertex[] faceTrans = new Vertex[Indices[index].Index.length];
			vertexZTemp.clear();
			for (int vertex = 0; vertex < Indices[index].Index.length; vertex++)
			{
				face[vertex] = verticesTransformed[Indices[index].Index[vertex]];
				faceTrans[vertex] = Vertices[Indices[index].Index[vertex]];
				vertexZTemp.add(face[vertex].Z);
			}
			boolean clip = true;
			int AllZNegative = 0;
			for (int i = 0; i < face.length; i++)
			{
				Vertex frame = Graphics.D3D_ToProjection(face[i]);
				if ((frame.X > -10) && (frame.X < Graphics.WindowSize.Width + 10) && (frame.Y > -10) && (frame.Y < Graphics.WindowSize.Height + 10))
				{
					clip = false;
				}
				if (face[i].Z < 0)
				{
					AllZNegative++;
				}
			}
			if (AllZNegative == face.length)
			{
				clip = true;
			}
			if (!clip)
			{
				int idx = 0;
				double addin = GetZIndex(vertexZTemp);
				while ((idx < vertexZ.size()) && (addin <= vertexZ.get(idx)))
				{
					idx++;
				}
				vertexColor.add(idx, Colors[index]);
				vertexZ.add(idx, addin);
				faces.add(idx, face);
				facesTrans.add(idx, faceTrans);
			}
		}
		D3D_renderStructure parse = new D3D_renderStructure(faces, facesTrans, vertexColor, new ArrayList<Vertex>(), new ArrayList<Vertex>(), new ArrayList<Vertex>(), new ArrayList<Vertex>(), new ArrayList<Vertex>());
		for (int i = 0; i < faces.size(); i++)
		{
			parse.Location.add(Location);
			parse.Rotation.add(Rotation);
			parse.Scale.add(Scale);
			parse.Revolution.add(Revolution);
			parse.RevolutionRadius.add(RevolutionRadius);
		}
		if (parse != null)
		{
			Graphics.D3D_Queue_Add(parse);
		}
	}
	public Vertex GetTransformedLocation(Window wnd)
	{
		GraphicsUnit g = wnd.GetNullGraphicsUnit();
		return g.D3D_Transformation_Rotation_Z(g.D3D_Transformation_Rotation_Y(g.D3D_Transformation_Rotation_X(g.D3D_Transformation_Translation(g.D3D_Transformation_Rotation_Z(g.D3D_Transformation_Rotation_Y(g.D3D_Transformation_Rotation_X(g.D3D_Transformation_Scale(Location, Scale), Rotation), Rotation), Rotation), RevolutionRadius), Revolution), Revolution), Revolution);
	}
}