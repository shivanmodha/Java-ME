package Engine;
public class Vertex
{
	public double X;
	public double Y;
	public double Z;
	public Vertex()
	{
		this(0, 0, 0);
	}
	public Vertex(double _in)
	{
		this(_in, _in, _in);
	}
	public Vertex(double _x, double _y, double _z)
	{
		X = _x;
		Y = _y;
		Z = _z;
	}
	public Vertex(Vertex base)
	{
		this(base.X, base.Y, base.Z);
	}
	public Vertex add(Vertex _point)
	{
		return new Vertex(X + _point.X, Y + _point.Y, Z + _point.Z);
	}
	public Vertex subtract(Vertex _point)
	{
		return new Vertex(X - _point.X, Y - _point.Y, Z - _point.Z);
	}
	public Vertex multiply(Vertex _point)
	{
		return new Vertex(X * _point.X, Y * _point.Y, Z * _point.Z);
	}
	public Vertex divide(Vertex _point)
	{
		return new Vertex(X / _point.X, Y / _point.Y, Z / _point.Z);
	}
	public boolean equals(Vertex _point)
	{
		if ((X == _point.X) && (Y == _point.Y) && (Z == _point.Z))
		{
			return true;
		}
		return false;
	}
	public double dot(Vertex vert)
	{
		return (X * vert.X) + (Y * vert.Y) + (Z * vert.Z);
	}
	public Vertex cross(Vertex vert)
	{
		return new Vertex((Y * vert.Z) - (Z * vert.Y), (X * vert.Z) - (Z * vert.X), (X * vert.Y) - (Y * vert.X));
	}
	public double magnitude()
	{
		return Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2) + Math.pow(Z, 2));
	}
	public String toString()
	{
		return "(" + X + ", " + Y + ", " + Z + ")";
	}
	public Point toPoint()
	{
		return new Point(X, Y);
	}
}