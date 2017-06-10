package Engine;
public class Rectangle extends Shape
{
	public Rectangle(Color Color, Point Location, Size Size)
	{
		Initialize(Color, Location, Size);
	}
	public boolean Intersects(Shape shape)
	{
		if ((Location.X + Size.Width > shape.Location.X) && (Location.X < shape.Location.X + shape.Size.Width) && (Location.Y + Size.Height > shape.Location.Y) && (Location.Y < shape.Location.Y + shape.Size.Height))
		{
			return true;
		}
		return false;
	}
	public boolean Intersects(Point point)
	{
		if ((point.X > Location.X) && (point.X < Location.X + Size.Width) && (point.Y > Location.Y) && (point.Y < Location.Y + Size.Height))
		{
			return true;
		}
		return false;
	}
	public void Render(GraphicsUnit Graphics)
	{
		Graphics.FillRectangle(Color, Location, Size);
	}
}