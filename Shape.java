package Engine;
public abstract class Shape
{
	public Point Location;
	public Point Center;
	public Size Size;
	public int Radius;
	public Color Color;
	public void Initialize(Color Color, Point Location, Size Size)
	{
		this.Color = Color;
		this.Location = Location;
		this.Size = Size;
		this.Center = new Point((Location.X + Size.Width) / 2, (Location.Y + Size.Height) / 2);
		this.Radius = (int)(Math.sqrt(Math.pow(Size.Width / 2, 2) + Math.pow(Size.Height / 2, 2)));
	}
	public void Initialize(Color Color, Point Center, int Radius)
	{
		this.Color = Color;
		this.Center = Center;
		this.Radius = Radius;
		int side = (int)(Math.sqrt(Math.pow(Radius, 2) / 2));
		this.Size = new Size(side, side);
		this.Location = new Point(this.Center.X - (side / 2), this.Center.Y - (side / 2));
	}
	public abstract boolean Intersects(Shape shape);
	public abstract void Render(GraphicsUnit Graphics);
}
