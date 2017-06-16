package studios.vanish.engine;

import studios.vanish.utility.EventHandler;


import java.awt.Font;
import java.awt.event.MouseEvent;
public class Button
{
	public enum ButtonType
	{
		Rectangle, Circle
	}
	public Point Location = new Point(0, 0);
	public Size Size = new Size(130, 25);
	public String Text = "button";
	public Font TextFont = new Font("Segoe UI", Font.PLAIN, 11);
	public Color[] BackColor = {Color.Black, Color.Red, Color.Green};
	public Color[] ForeColor = {Color.White, Color.Green, Color.Red};
	public EventHandler OnClick = new EventHandler();
	public ButtonType Type = ButtonType.Rectangle;
	public boolean Enabled = true;
	private int state = 0;
	private Shape bounds;
	private boolean down = false;
	public FillMode FillMode = studios.vanish.engine.FillMode.Solid;
	Window wnd;
	public int GetState()
	{
		return state;
	}
	public Button(Window wnd, String text)
	{
		wnd.OnMouseMove.Add(this, "Check");
		wnd.OnMouseDown.Add(this, "Check");
		wnd.OnMouseUp.Add(this, "Check");
		wnd.OnShow.Add(this, "Initialize");
		Text = text;
		this.wnd = wnd;
	}
	public Button(Window wnd, String text, Point location, Size size)
	{
		wnd.OnMouseMove.Add(this, "Check");
		wnd.OnMouseDown.Add(this, "Check");
		wnd.OnMouseUp.Add(this, "Check");
		wnd.OnShow.Add(this, "Initialize");
		Text = text;
		Location = location;
		Size = size;
		this.wnd = wnd;
	}
	public void SetBackColor(Color normal, Color hover, Color press)
	{
		BackColor = new Color[]{normal, hover, press};
	}
	public void SetForeColor(Color normal, Color hover, Color press)
	{
		ForeColor = new Color[]{normal, hover, press};
	}
	public void Initialize()
	{
		int rad = Size.Width;
		if (Size.Height > Size.Width)
		{
			rad = Size.Height;
		}
		if (Type == ButtonType.Rectangle)
		{
			bounds = new Rectangle(BackColor[state], Location, Size);
		}
		else if (Type == ButtonType.Circle)
		{
			bounds = new Circle(BackColor[state], Location, rad);
		}
		bounds.FillMode = FillMode;
	}
	public void Check(Point MouseLocation, int Button)
	{
		if (Enabled == true)
		{
			Initialize();
			if (bounds.Intersects(MouseLocation))
			{
				if (wnd.Mouse[MouseEvent.BUTTON1])
				{
					state = 2;
					down = true;
				}
				else
				{
					state = 1;
					if (down == true)
					{
						OnClick.InvokeAll(MouseLocation, Button);
						down = false;
					}
				}
			}
			else
			{
				down = false;
				state = 0;
			}
		}
	}
	public void Render(GraphicsUnit Graphics)
	{
		Size center = Graphics.GetTextSize(Text, TextFont);
		Initialize();
		Point text = new Point(bounds.GetShapeCenter().X - (center.Width / 2), bounds.GetShapeCenter().Y - (center.Height / 2));
		bounds.Render(Graphics);
		Graphics.DrawString(Text, ForeColor[state], text, TextFont);
	}
}