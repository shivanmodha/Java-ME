package studios.vanish.engine;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
public class Camera
{
    public Vertex Location = new Vertex();
    public Vertex Rotation = new Vertex();
    private Point MousePreviousPosition;
    private Robot rob;
    public boolean FreeFloating = true;
    public Camera()
    {
        try
        {
            rob = new Robot();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public Camera(Vertex _location, Vertex _rotation)
    {
        try
        {
            rob = new Robot();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Location = new Vertex(_location);
        Rotation = new Vertex(_rotation);
    }
    public void Look(double MouseSpeed, Point WindowLocation, Size WindowSize)
    {
        Size Monitor = new Size((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        if (MousePreviousPosition != null)
        {
            Point moved = GetMousePosition().subtract(MousePreviousPosition);
            Rotation.Z -= moved.X * MouseSpeed;
            Rotation.X -= moved.Y * MouseSpeed;
            if (Rotation.Z >= 360)
            {
                Rotation.Z -= 360;
            }
            if (Rotation.Z <= -360)
            {
                Rotation.Z += 360;
            }
            if (Rotation.X >= 360)
            {
                Rotation.X -= 360;
            }
            if (Rotation.X <= -360)
            {
                Rotation.X += 360;
            }
        }
        int offset = 2;
        int gotoOffset = 50;
        if ((GetMousePosition().X <= WindowLocation.X + offset) || (GetMousePosition().X <= 0))
        {
            SetMousePosition(new Point(WindowLocation.X + WindowSize.Width - gotoOffset, GetMousePosition().Y));
        }
        else if ((GetMousePosition().X >= WindowLocation.X + WindowSize.Width - offset) || GetMousePosition().X >= Monitor.Width)
        {
            SetMousePosition(new Point(WindowLocation.X + 1 + gotoOffset, GetMousePosition().Y));
        }
        else if ((GetMousePosition().Y <= WindowLocation.Y + offset) || (GetMousePosition().Y <= 0))
        {
            SetMousePosition(new Point(GetMousePosition().X, WindowLocation.Y + WindowSize.Height - gotoOffset));
        }
        else if ((GetMousePosition().Y >= WindowLocation.Y + WindowSize.Height - offset) || GetMousePosition().Y >= Monitor.Height - 1)
        {
            SetMousePosition(new Point(GetMousePosition().X, WindowLocation.Y + 1 + gotoOffset));
        }
        MousePreviousPosition = GetMousePosition();
    }
    private void SetMousePosition(Point location)
    {
        rob.mouseMove((int)location.X, (int)location.Y);
    }
    private Point GetMousePosition()
    {
        return new Point(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
    }
    public void SetMousePreviousPosition(Point MouseLocation)
    {
        MousePreviousPosition = new Point(MouseLocation);
    }
    public void Forward(double factor)
    {
        double radian = Rotation.Z * Math.PI / 180;
        double x = Math.sin(radian) * factor;
        double z = Math.cos(radian) * factor;
        Location.X -= x;
        Location.Z += z;
        radian = Rotation.X * Math.PI / 180;
        if (FreeFloating == true)
        {
            double y = Math.sin(radian) * factor;
            Location.Y += y;
        }
    }
    public void Right(double factor)
    {
        double radian = (Rotation.Z - 90) * Math.PI / 180;
        double x = Math.sin(radian) * factor;
        double z = Math.cos(radian) * factor;
        Location.X -= x;
        Location.Z += z;
        radian = (Rotation.X - 90) * Math.PI / 180;
    }
    public Vertex GetForwardPoint(double factor)
    {
        Vertex _return = new Vertex();
        double radian = Rotation.Z * Math.PI / 180;
        double x = Math.sin(radian) * factor;
        double z = Math.cos(radian) * factor;
        _return.X = Location.X - x;
        _return.Z = Location.Z + z;
        radian = Rotation.X * Math.PI / 180;
        double y = Math.sin(radian) * factor;
        _return.Y = Location.Y + y;
        return _return;
    }
}