package Engine;
public class Point
{
	public double X;
	public double Y;
	public Point()
	{
		this(0, 0);
	}
	public Point(double _x, double _y)
	{
		X = _x;
		Y = _y;
	}
	public Point(Point base)
	{
		this(base.X, base.Y);
	}
	public Point add(Point _point)
	{
		return new Point(X + _point.X, Y + _point.Y);
	}
	public Point subtract(Point _point)
	{
		return new Point(X - _point.X, Y - _point.Y);
	}
	public Point multiply(Point _point)
	{
		return new Point(X * _point.X, Y * _point.Y);
	}
	public Point divide(Point _point)
	{
		return new Point(X / _point.X, Y / _point.Y);
	}
	public boolean equals(Point _point)
	{
		if ((X == _point.X) && (Y == _point.Y))
		{
			return true;
		}
		return false;
	}
	public int distance(Point _point)
	{
		int x = (int)(Math.pow(X - _point.X, 2));
		int y = (int)(Math.pow(Y - _point.Y, 2));
		return (int)(Math.sqrt(x + y));
	}
	public String toString()
	{
		return "(" + X + ", " + Y + ")";
	}
	public void MoveHorizontal(double x)
	{
		X += x;
	}
	public void MoveVertical(double y)
	{
		Y += y;
	}
}
