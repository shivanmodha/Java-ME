package Engine;
public class ColorLinearGradient extends Color
{
	public class ColoredPoint
	{
		Point Location;
		Color Color;
		public ColoredPoint(Point _point, Color _color)
		{
			Location = new Point(_point);
			Color = new Color(_color);
		}
	}
	public ColoredPoint[] Point;
	public ColorLinearGradient(int numPoints)
	{
		Point = new ColoredPoint[numPoints];
		if (numPoints < 2)
		{
			Point = new ColoredPoint[2];
		}
	}
	public void Set(int index, Point _point, Color _color)
	{
		if (index < Point.length)
		{
			Point[index] = new ColoredPoint(_point, _color);
		}
	}
}