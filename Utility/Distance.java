package Utility;

import Geometry.Plane3;
import Geometry.Vector2;
import Geometry.Vector3;

public class Distance
{
  // private static final double Epsilon = 0.0001;

  // public enum Status {
  //   None, Intersect, Overlap,
  // }
 
  public static DistancePointPlaneResult Compute(Vector3 point, Plane3 plane) 
  {
    var result = new DistancePointPlaneResult();
      
      result.SignedDistance = Vector3.Dot(plane.Normal, point) - plane.Constant;
      result.Distance = Math.Abs(result.SignedDistance);
      result.PlaneClosestPoint = point.Subtract(plane.Normal.Multiply(result.SignedDistance));
      
      return result;
  }
 
  public static double Compute(Vector3 a, Vector3 b) 
  {
    return b.Subtract(a).Length();
  }

  public static double Compute(Vector2 a, Vector2 b) 
  {
    return b.Subtract(a).Length();
  }
}