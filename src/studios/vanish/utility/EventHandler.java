package studios.vanish.utility;
import java.util.ArrayList;
public class EventHandler
{
    private ArrayList<Method> events = new ArrayList<Method>();
    public void Add(Object _object, String _name)
    {
        events.add(new Method(_object, _name));
    }
    public Object Invoke(int _method, Object... obj)
    {
        return events.get(_method).Invoke(obj);
    }
    public Object[] InvokeAll(Object... obj)
    {
        Object[] _return = new Object[events.size()];
        for (int i = 0; i < events.size(); i++)
        {
            _return[i] = Invoke(i, obj);
        }
        return _return;
    }
}