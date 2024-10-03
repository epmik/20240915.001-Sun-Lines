package Geometry;

public class Vector3 
{
    public float X;
    public float Y;
    public float Z;

    public final static Vector3 Zero = new Vector3(0, 0, 0);
    public final static Vector3 UnitX = new Vector3(1, 0, 0);
    public final static Vector3 UnitY = new Vector3(0, 1, 0);
    public final static Vector3 UnitZ = new Vector3(0, 0, 1);

    public Vector3() 
    {
        this(0, 0, 0);
    }

    public Vector3(float x, float y, float z) 
    {
        X = x;
        Y = y;
        Z = z;
    }

    public Vector3(double x, double y, double z) 
    {
        X = (float)x;
        Y = (float)y;
        Z = (float)z;
    }

    public Vector3(Vector2 v) 
    {
        X = v.X;
        Y = v.Y;
    }

    public Vector3(Vector3 v) 
    {
        X = v.X;
        Y = v.Y;
        Z = v.Z;
    }

    public Vector3 Add(Vector3 a) 
    {
        return new Vector3(X + a.X, Y + a.Y, Z + a.Z);
    }

    public Vector3 Subtract(Vector3 a) 
    {
        return new Vector3(X - a.X, Y - a.Y, Z - a.Z);
    }

    public Vector3 Multiply(Vector3 a) 
    {
        return new Vector3(X * a.X, Y * a.Y, Z * a.Z);
    }

    public Vector3 Multiply(float a) 
    {
        return new Vector3(X * a, Y * a, Z * a);
    }

    public Vector3 Multiply(Double a) 
    {
        return new Vector3(X * a, Y * a, Z * a);
    }

    public Vector3 Divide(Vector3 a) 
    {
        return new Vector3(X / a.X, Y / a.Y, Z / a.Z);
    }

    public Vector3 Divide(float a) 
    {
        return new Vector3(X / a, Y / a, Z / a);
    }

    public Vector3 Divide(Double a) 
    {
        return new Vector3(X / a, Y / a, Z / a);
    }

    public double LengthSquared() 
    {
        return (X * X) + (Y * Y) + (Z * Z);
    }
    
    public double Length() 
    {
        return Utility.Math.Sqrt(LengthSquared());
    }
    
    public double Dot(Vector3 a) 
    {
        return Dot(this, a);
    }
    
    public static double Dot(Vector3 a, Vector3 b) 
    {
        return (a.X * b.X) + (a.Y * b.Y) + (a.Z * b.Z);
    }
    
    public Vector3 Normalize() 
    {
        return Vector3.Normalize(this);
    }
    
    public static Vector3 Normalize(Vector3 a) 
    {
        var length = a.Length();

        if (length > 0)
        {
            return a.Divide(length);
        }
        
        return new Vector3();
    }
}