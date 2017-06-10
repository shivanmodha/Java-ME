package Engine;
public class Size
{
	public int Width;
	public int Height;
	public Size()
	{
		this(0, 0);
	}
	public Size(int _width, int _height)
	{
		Width = _width;
		Height = _height;
	}
	public Size add(Size _size)
	{
		return new Size(Width + _size.Width, Height + _size.Height);
	}
	public Size subtract(Size _size)
	{
		return new Size(Width - _size.Width, Height - _size.Height);
	}
	public Size multiply(Size _size)
	{
		return new Size(Width * _size.Width, Height * _size.Height);
	}
	public Size divide(Size _size)
	{
		return new Size(Width / _size.Width, Height / _size.Height);
	}
	public Size half()
	{
		return new Size(Width / 2, Height / 2);
	}
	public boolean equals(Size _size)
	{
		if ((Width == _size.Width) && (Height == _size.Height))
		{
			return true;
		}
		return false;
	}
	public String toString()
	{
		return Width + " by " + Height;
	}
	public Point toPoint()
	{
		return new Point(Width, Height);
	}
}
