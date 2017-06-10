package studios.vanish.engine;
import studios.vanish.utility.EventHandler;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Window
{
	private JFrame window = new JFrame();
	private JPanel container = new JPanel()
	{
		private static final long serialVersionUID = 1L;
		public void paintComponent(Graphics g)
		{
			GraphicsUnit graphics = new GraphicsUnit((Graphics2D)g, FieldOfView, Size, Resolution, FillMode, Camera, CalculateIntersections, RenderLights);
			OnPaint.InvokeAll(graphics);
			if (AutomaticRender == true)
			{
				graphics.D3D_Queue_Render();
			}
		}
	};
	public String Name = "New Window";
	public Size Size = new Size(800, 600);
	public Point Location = new Point(0, 0);
	public Point MouseLocation = new Point(0, 0);
	public boolean[] Keys = new boolean[526];
	public boolean[] Mouse = new boolean[4];	
	public EventHandler OnPaint = new EventHandler();
	public EventHandler OnResize = new EventHandler();
	public EventHandler OnMove = new EventHandler();
	public EventHandler OnKeyDown = new EventHandler();
	public EventHandler OnKeyUp = new EventHandler();
	public EventHandler OnMouseMove = new EventHandler();
	public EventHandler OnMouseDown = new EventHandler();
	public EventHandler OnMouseUp = new EventHandler();
	public EventHandler OnMouseEnter = new EventHandler();
	public EventHandler OnMouseLeave = new EventHandler();
	public EventHandler OnMouseClick = new EventHandler();
	public EventHandler OnMouseDrag = new EventHandler();
	public EventHandler OnShow = new EventHandler();
	public EventHandler OnClose = new EventHandler();
	public EventHandler OnFocus = new EventHandler();
	public EventHandler OnWindowStateChanged = new EventHandler();
	private Timer renderTimer;
	private Timer secondTimer;
	private int frames;
	private int refreshSpeed;
	public int FPS;
	private int fpsCap;
	private boolean oscillation = false;
	private int osc1 = 0;
	private int osc2 = 0;
	private int osc3 = 0;
	public int FieldOfView = 256;	
	public FillMode FillMode;
	public Camera Camera = new Camera();
	public boolean CalculateIntersections = true;
	public boolean AutomaticRender = true;
	private Robot rob;
	public Size Resolution = new Size(800, 600);
	public boolean Border = true;
	public boolean FullScreen = false;
	public boolean RenderLights = false;
	public Window()
	{
		initializeFrame();
	}
	public Window(String _name, Size _size, boolean _border)
	{
		Name = _name;
		Size = new Size(_size.Width, _size.Height);
		Border = _border;
		initializeFrame();
	}	
	public void Initialize3D(Size Res, int _FOV, FillMode fillMode, boolean calcIntersection, boolean autoRender)
	{
		FieldOfView = _FOV;
		FillMode = fillMode;
		CalculateIntersections = calcIntersection;
		AutomaticRender = autoRender;
		Resolution = new Size(Res.Width, Res.Height);
	}	
	private void initializeFrame()
	{
		try
		{
			rob = new Robot();
		}
		catch (Exception e) 
		{
			
		}
		container.setSize(Size.Width, Size.Height);
		container.setLocation(0, 0);
		window.setUndecorated(!Border);
		window.add(container);
		window.addComponentListener(new ComponentAdapter() 
		{  
	        public void componentResized(ComponentEvent evt)
	        {
	            Size newSize = new Size(container.getSize().width, container.getSize().height);
	            Size oldSize = new Size(Size.Width, Size.Height);
	        	Size = new Size(newSize.Width, newSize.Height);
	        	OnResize.InvokeAll(oldSize, newSize);
	        }
	        public void componentMoved(ComponentEvent e)
	        {
	        	Point newLocation = new Point(window.getLocation().x, window.getLocation().y);
	        	Point oldLocation = new Point(Location.X, Location.Y);
	        	Location = new Point(newLocation.X, newLocation.Y);
	        	OnMove.InvokeAll(oldLocation, newLocation);
	        }
		});
		window.addWindowListener(new WindowListener()
		{
			public void windowActivated(WindowEvent arg0)
			{
				OnFocus.InvokeAll(true);
			}
			public void windowClosed(WindowEvent arg0)
			{				
			}
			public void windowClosing(WindowEvent arg0)
			{				
				OnClose.InvokeAll();
			}
			public void windowDeactivated(WindowEvent arg0)
			{
				OnFocus.InvokeAll(false);
			}
			public void windowDeiconified(WindowEvent arg0)
			{				
			}
			public void windowIconified(WindowEvent arg0)
			{				
			}
			public void windowOpened(WindowEvent arg0)
			{
				OnShow.InvokeAll();
				renderTimer.start();
				secondTimer.start();
			}
		});
		window.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e)
			{
				Keys[e.getKeyCode()] = true;
				OnKeyDown.InvokeAll(e.getKeyCode(), e.getKeyChar());
			}
		    public void keyReleased(KeyEvent e)
		    {
				Keys[e.getKeyCode()] = false;
				OnKeyDown.InvokeAll(e.getKeyCode(), e.getKeyChar());
		    }
		    public void keyTyped(KeyEvent e)
		    {
		    }
		});
		window.addWindowStateListener(new WindowStateListener()
		{
			public void windowStateChanged(WindowEvent arg0)
			{
				if (arg0.getNewState() == 0)
				{
					FullScreen = false;
		            Size newSize = new Size(container.getSize().width, container.getSize().height);
		            Size oldSize = new Size(Size.Width, Size.Height);
		        	Size = new Size(newSize.Width, newSize.Height);
		        	OnResize.InvokeAll(oldSize, newSize);
				}
				else if (arg0.getNewState() == 6)
				{
					FullScreen = true;
		            Size newSize = new Size(container.getSize().width, container.getSize().height);
		            Size oldSize = new Size(Size.Width, Size.Height);
		        	Size = new Size(newSize.Width, newSize.Height);
		        	OnResize.InvokeAll(oldSize, newSize);
				}
				OnWindowStateChanged.InvokeAll(arg0.getNewState());
			}
		});
		container.addMouseListener(new MouseListener()
		{
			public void mouseClicked(MouseEvent e)
			{
				OnMouseClick.InvokeAll(new Point(e.getX(), e.getY()), e.getButton());
			}
			public void mouseEntered(MouseEvent e)
			{
				OnMouseEnter.InvokeAll(new Point(e.getX(), e.getY()), e.getButton());				
			}
			public void mouseExited(MouseEvent e)
			{
				OnMouseLeave.InvokeAll(new Point(e.getX(), e.getY()), e.getButton());				
			}
			public void mousePressed(MouseEvent e)
			{
				Mouse[e.getButton()] = true;
				OnMouseDown.InvokeAll(new Point(e.getX(), e.getY()), e.getButton());
			}
			public void mouseReleased(MouseEvent e)
			{
				Mouse[e.getButton()] = false;
				OnMouseUp.InvokeAll(new Point(e.getX(), e.getY()), e.getButton());				
			}			
		});
		container.addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e)
			{
				MouseLocation = new Point(e.getX(), e.getY());
				OnMouseDrag.InvokeAll(new Point(e.getX(), e.getY()), e.getButton());	
			}
			public void mouseMoved(MouseEvent e)
			{
				MouseLocation = new Point(e.getX(), e.getY());
				OnMouseMove.InvokeAll(new Point(e.getX(), e.getY()), e.getButton());
			}
		});		
		window.setTitle(Name);
		window.setSize(Size.Width, Size.Height);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void Render()
	{
		window.repaint();
	}
	public void Show()
	{
		window.setVisible(true);
	}
	public void Close()
	{
		window.setVisible(false);
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}
	public void Initialize(int fps)
	{
		SetFPS(fps);
		renderTimer = new Timer(refreshSpeed, new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Render();
				frames++;
			}
		});
		secondTimer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{				
				FPS = frames;
				frames = 0;
				if (oscillation == false)
				{
					if (FPS > fpsCap)
					{
						osc1 = FPS;
						refreshSpeed++;
						if (refreshSpeed > -1)
						{
							renderTimer.setDelay(refreshSpeed);
						}
					}
					else if (FPS < fpsCap)
					{
						osc3 = FPS;
						refreshSpeed--;
						if (refreshSpeed > -1)
						{
							renderTimer.setDelay(refreshSpeed);
						}
					}
					if ((osc1 != 0) && (osc1 == osc2))
					{
						oscillation = true;
						int d1 = Math.abs(fpsCap - osc1);
						int d2 = Math.abs(fpsCap - osc3);
						if (d2 > d1)
						{
							refreshSpeed++;
						}
					}
					osc2 = FPS;
				}
			}
		});
	}
	public Point GetMousePosition()
	{
		return new Point(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
	}
	public void SetFPS(int fps)
	{
		fpsCap = fps;
		refreshSpeed = GetRefreshSpeed(fps);
	}
	public int GetRefreshSpeed(int frames)
	{
		return 960 / frames;
	}
	public void Wait(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException e)
		{
		}
	}
	public void SetMousePosition(Point location)
	{
		rob.mouseMove((int)location.X, (int)location.Y);
	}
	public boolean LockMouseInWindow()
	{
		if ((GetMousePosition().X <= Location.X) || (GetMousePosition().X <= 0))
		{
			SetMousePosition(new Point(Location.X + Size.Width, GetMousePosition().Y));
			return true;
		}
		else if ((GetMousePosition().X >= Location.X + Size.Width))
		{
			SetMousePosition(new Point(Location.X + 1, GetMousePosition().Y));
			return true;
		}
		else if ((GetMousePosition().Y <= Location.Y) || (GetMousePosition().Y <= 0))
		{
			SetMousePosition(new Point(GetMousePosition().X, Location.Y + Size.Height));
			return true;
		}
		else if ((GetMousePosition().Y >= Location.Y + Size.Height))
		{
			SetMousePosition(new Point(GetMousePosition().X, Location.Y + 1));
			return true;
		}
		return false;
	}
	public void ToFullScreen()
	{
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		FullScreen = true;
	}
	public void FromFullScreen()
	{
		window.setExtendedState(JFrame.NORMAL);
		FullScreen = false;
	}
	public void Cursor_Blank()
	{
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new java.awt.Point(0, 0), "blank");
		window.getContentPane().setCursor(blankCursor);
	}
	public void Cursor_Set(String fileName)
	{
		try
		{
			BufferedImage img = ImageIO.read(new File(fileName));
			Cursor curs = Toolkit.getDefaultToolkit().createCustomCursor(img, new java.awt.Point(0, 0), "curs");
			window.getContentPane().setCursor(curs);
		}
		catch (Exception e)
		{
			Cursor_Blank();
		}
	}
	public void Cursor_Set(int id)
	{
		window.getContentPane().setCursor(Cursor.getPredefinedCursor(id));
	}
	public void Cursor_Reset()
	{
		window.getContentPane().setCursor(Cursor.getDefaultCursor());
	}	
	public GraphicsUnit GetNullGraphicsUnit()
	{
		return new GraphicsUnit(null, FieldOfView, Size, Resolution, FillMode, Camera, CalculateIntersections, RenderLights);
	}
}