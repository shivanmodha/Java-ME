package studios.vanish.engine;

import studios.vanish.utility.EventHandler;

import java.awt.Font;
import java.awt.event.MouseEvent;
public class Button
{
	public Point Location = new Point(0, 0);
	public Size Size = new Size(130, 25);
	public String Text = "button";
	public Font TextFont = new Font("Segoe UI", Font.PLAIN, 11);
	public Color[] BackColor = {Color.Black, Color.Red, Color.Green};
	public Color[] ForeColor = {Color.White, Color.Green, Color.Red};
	public EventHandler OnClick = new EventHandler();
	public boolean Enabled = true;
	private int state = 0;
	private Rectangle rect;
	private boolean down = false;
	Window wnd;
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
		rect = new Rectangle(BackColor[state], Location, Size);
	}
	public void Check(Point MouseLocation, int Button)
	{
		if (Enabled == true)
		{
			rect = new Rectangle(BackColor[state], Location, Size);
			if (rect.Intersects(MouseLocation))
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
		Point text = new Point((Location.X + (Size.Width) / 2) - (center.Width / 2), (Location.Y + (Size.Height) / 2) - (center.Height / 2));
		Graphics.FillRectangle(BackColor[state], Location, Size);
		Graphics.DrawString(Text, ForeColor[state], text, TextFont);
	}
}