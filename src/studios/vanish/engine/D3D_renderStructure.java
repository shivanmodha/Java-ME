package studios.vanish.engine;
import java.util.ArrayList;
public class D3D_renderStructure
{
	public ArrayList<Vertex[]> facesTransformed = new ArrayList<Vertex[]>();
	public ArrayList<Vertex[]> facesUntransformed = new ArrayList<Vertex[]>();
	public ArrayList<Color> color = new ArrayList<Color>();
	public ArrayList<Vertex> Location = new ArrayList<Vertex>();
	public ArrayList<Vertex> Rotation = new ArrayList<Vertex>();
	public ArrayList<Vertex> Scale = new ArrayList<Vertex>();
	public ArrayList<Vertex> Revolution = new ArrayList<Vertex>();
	public ArrayList<Vertex> RevolutionRadius = new ArrayList<Vertex>();
	public D3D_renderStructure()
	{
		
	}
	public D3D_renderStructure(ArrayList<Vertex[]> fT, ArrayList<Vertex[]> fUt, ArrayList<Color> fC, ArrayList<Vertex> l, ArrayList<Vertex> r, ArrayList<Vertex> s, ArrayList<Vertex> rev, ArrayList<Vertex> revRad)
	{
		facesTransformed = fT;
		facesUntransformed = fUt;
		color = fC;
		Location = l;
		Rotation = r;
		Scale = s;
		Revolution = rev;
		RevolutionRadius = revRad;
	}
}