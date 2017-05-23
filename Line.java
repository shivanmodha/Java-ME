package Engine;
public class Line
{
	public Vertex Start = new Vertex();
	public Vertex End = new Vertex();
	public Line()
	{
		this(new Vertex(), new Vertex());
	}
	public Line(Vertex _start, Vertex _end)
	{
		Start = new Vertex(_start);
		End = new Vertex(_end);
	}
	public Line(Vertex _start, Vertex direction, int time)
	{
		Start = new Vertex(_start);
		End = new Vertex(direction.X * time + Start.X, direction.Y * time + Start.Y, direction.Z * time + Start.Z);
	}
	public Vertex Vector()
	{
		return new Vertex(End.X - Start.X, End.Y - Start.Y, End.Z - Start.Z);
	}
	public double X(double time)
	{
		return Start.X + (End.X - Start.X) * time;
	}
	public double Y(double time)
	{
		return Start.Y + (End.Y - Start.Y) * time;
	}
	public double Z(double time)
	{
		return Start.Z + (End.Z - Start.Z) * time;
	}
	public Vertex Vertex(double time)
	{
		return new Vertex(X(time), Y(time), Z(time));
	}
	public Vertex Intersection(Line ln)
	{
		double[] a = new double[] {Vector().X, ln.Vector().X};
		double[] b = new double[] {Vector().Y, ln.Vector().Y};
		double[] x1 = new double[] {Start.X, ln.Start.X};
		double[] y1 = new double[] {Start.Y, ln.Start.Y};
		double[] matrix = new double[]{
				(x1[1] - x1[0]),
				(y1[1] - y1[0])
		};
		double factor = 1 / ((-a[0] * b[1]) + (a[1] * b[0]));
		double[][] inverse = new double[][]{
				{-b[1] * factor, a[1] * factor},
				{-b[0] * factor, a[0] * factor}
		};
		double t1 = (inverse[0][0] * matrix[0]) + (inverse[0][1] * matrix[1]);
		double t2 = (inverse[1][0] * matrix[0]) + (inverse[1][1] * matrix[1]);
		if (Vertex(t1).equals(ln.Vertex(t2)))
		{
			return Vertex(t1);
		}
		else
		{
			return null;
		}
	}
	public Vertex Intersection(Vertex[] Plane)
	{
		Vertex direction1 = Plane[1].subtract(Plane[0]);
		Vertex direction2 = Plane[3].subtract(Plane[0]);
		Vertex vector = direction1.cross(direction2);
		double d = (vector.X * Plane[0].X) + (vector.Y * Plane[0].Y) + (vector.Z * Plane[0].Z);
		double timeTop = d - (vector.X * Start.X) - (vector.Y * Start.Y) - (vector.Z * Start.Z);
		double timeBottom = (vector.X * Vector().X) + (vector.Y * Vector().Y) + (vector.Z * Vector().Z);
		double t = timeTop / timeBottom;
		return Vertex(t);
	}
	public boolean OnSegment(Vertex vert)
	{
		if (vert != null)
		{
			double loX = Start.X;
			double hiX = End.X;
			if (End.X < Start.X)
			{
				loX = End.X;
				hiX = Start.X;
			}
			double loY = Start.Y;
			double hiY = End.Y;
			if (End.Y < Start.Y)
			{
				loY = End.Y;
				hiY = Start.Y;
			}
			double loZ = Start.Z;
			double hiZ = End.Z;
			if (End.Z < Start.Z)
			{
				loZ = End.Z;
				hiZ = Start.Z;
			}
			if ((vert.X <= hiX) && (vert.X >= loX) && (vert.Y <=hiY) && (vert.Y >= loY) && (vert.Z <= hiZ) && (vert.Z >= loZ))
			{
				return true;
			}
		}
		return false;
	}
}