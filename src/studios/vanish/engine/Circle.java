package Engine;
public class Circle extends Shape
{
	public Circle(Color Color, Point Center, int Radius)
	{
		Initialize(Color, Center, Radius);
	}
	public boolean Intersects(Shape shape)
	{
		if (shape instanceof Circle)
		{
			if (Center.distance(shape.Center) < Radius + shape.Radius)
			{
				return true;
			}
		}
		else if (shape instanceof Rectangle)
		{
			if ((Center.X >= shape.Location.X) && (Center.X <= shape.Location.X + shape.Size.Width) && (Center.Y >= shape.Location.Y) && (Center.Y <= shape.Location.Y + shape.Size.Height))
			{
				return true;
			}
			else
			{
				Point tl = new Point(shape.Location.X, shape.Location.Y);
				Point tr = new Point(shape.Location.X + shape.Size.Width, shape.Location.Y);
				Point bl = new Point(shape.Location.X, shape.Location.Y + shape.Size.Height);
				Point br = new Point(shape.Location.X + shape.Size.Width, shape.Location.Y + shape.Size.Height);
				if ((Center.distance(tl) < Radius) || (Center.distance(tr) < Radius) || (Center.distance(bl) < Radius) || (Center.distance(br) < Radius))
				{
					return true;
				}
				Point pt = new Point(0, 0);
				boolean runToggle = false;
				if ((Center.X > shape.Location.X) && (Center.X < shape.Location.X + shape.Size.Width))
				{
					runToggle = true;
					pt.X = Center.X;
					if (Center.Y < shape.Location.Y)
					{
						pt.Y = shape.Location.Y;
					}
					else
					{
						pt.Y = shape.Location.Y + shape.Size.Height;
					}
				}
				else if ((Center.Y > shape.Location.Y) && (Center.Y < shape.Location.Y + shape.Size.Height))
				{
					runToggle = true;
					pt.Y = Center.Y;
					if (Center.X < shape.Location.X)
					{
						pt.X = shape.Location.X;
					}
					else
					{
						pt.X = shape.Location.X + shape.Size.Width;
					}
				}
				if ((runToggle == true) && (Center.distance(pt) < Radius))
				{
					return true;
				}
			}
		}
		else
		{
			if ((Center.X + Radius > shape.Location.X) && (Center.X - Radius < shape.Location.X + shape.Size.Width) && (Center.Y + Radius > shape.Location.Y) && (Center.Y - Radius < shape.Location.Y + shape.Size.Height))
			{
				return true;
			}
		}
		return false;
	}
	public void Render(GraphicsUnit Graphics)
	{
		Graphics.FillEllipse(Color, new Point(Center.X - Radius, Center.Y - Radius), new Size(Radius * 2, Radius * 2));
	}
}
