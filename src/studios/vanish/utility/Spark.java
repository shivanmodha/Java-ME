package Spark;
public class Spark
{
	public static Object InvokeMethod(Object _object, String _method, Object... _parameters)
	{
		Object _return = false;
		try
		{			
			_return = _object.getClass().getMethod(_method, Spark.GetClassType(_parameters)).invoke(_object, _parameters);
			if (_return == null)
			{
				_return = "success: return type null";
			}
		}
		catch (Exception e)
		{
			if (e instanceof java.lang.NoSuchMethodException)
			{
				_return = "\"" + e.getMessage() + "\" does not exist";
			}
			else
			{
				_return = e;
			}
		}
		return _return;
	}
	@SuppressWarnings("rawtypes")
	public static Class[] GetClassType(Object... _parameters)
	{
		Class[] _type = new Class[_parameters.length];
		for (int i = 0; i < _parameters.length; i++)
		{
			_type[i] = _parameters[i].getClass();
			if (_parameters[i] instanceof Integer)
			{
				_type[i] = int.class;
			}
			else if (_parameters[i] instanceof Double)
			{
				_type[i] = double.class;
			}
			else if (_parameters[i] instanceof Boolean)
			{
				_type[i] = boolean.class;
			}
			else if (_parameters[i] instanceof Float)
			{
				_type[i] = float.class;
			}
			else if (_parameters[i] instanceof Character)
			{
				_type[i] = char.class;
			}
		}
		return _type;
	}
	public static int Random(int start, int end)
	{
		return (int)(Math.random() * ((end - start) + 1)) + start;
	}
}
