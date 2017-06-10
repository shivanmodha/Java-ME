package Spark;
public class Method
{
	private String Name;
	private Object Handle;
	public Method(Object _object, String _name)
	{
		Handle = _object;
		Name = _name;
	}
	public Object Invoke(Object... obj)
	{
		return Spark.InvokeMethod(Handle, Name, obj);
	}
}