package Geometry;

public class Vector2 
{
    public float X;
    public float Y;

    public final static Vector2 Zero = new Vector2(0, 0);
    public final static Vector2 UnitX = new Vector2(1, 0);
    public final static Vector2 UnitY = new Vector2(0, 1);

    public Vector2() 
    {
        this(0, 0);
    }

    public Vector2(float x, float y) 
    {
        X = x;
        Y = y;
    }

    public Vector2(double x, double y) 
    {
        X = (float)x;
        Y = (float)y;
    }

    public Vector2(Vector3 v) 
    {
        X = v.X;
        Y = v.Y;
    }

    public Vector2 Add(Vector2 a) 
    {
        return new Vector2(X + a.X, Y + a.Y);
    }

    public Vector2 Subtract(Vector2 a) 
    {
        return new Vector2(X - a.X, Y - a.Y);
    }

    public Vector2 Multiply(Vector2 a) 
    {
        return new Vector2(X * a.X, Y * a.Y);
    }

    public Vector2 Multiply(float a) 
    {
        return new Vector2(X * a, Y * a);
    }

    public Vector2 Multiply(Double a) 
    {
        return new Vector2(X * a, Y * a);
    }

    public Vector2 Divide(Vector2 a) 
    {
        return new Vector2(X / a.X, Y / a.Y);
    }

    public Vector2 Divide(float a) 
    {
        return new Vector2(X / a, Y / a);
    }

    public Vector2 Divide(Double a) 
    {
        return new Vector2(X / a, Y / a);
    }

    public double LengthSquared() 
    {
        return (X * X) + (Y * Y);
    }
    
    public double Length() 
    {
        return Utility.Math.Sqrt(LengthSquared());
    }
    
    public double Dot(Vector2 a) 
    {
        return Dot(this, a);
    }

    public static Vector2 Subtract(Vector2 a, Vector2 b) 
    {
        return new Vector2(b.X - a.X, b.Y - a.Y);
    }
    
    public static double Distance(Vector2 a, Vector2 b) 
    {
        return Subtract(a, b).Length();
    }
    
    public static double Dot(Vector2 a, Vector2 b) 
    {
        return (a.X * b.X) + (a.Y * b.Y);
    }
    
    public Vector2 Normalize() 
    {
        return Vector2.Normalize(this);
    }
    
    public static Vector2 Normalize(Vector2 a) 
    {
        var length = a.Length();

        if (length > 0)
        {
            return a.Divide(length);
        }
        
        return new Vector2();
    }
}