package Utility;

import Geometry.Plane3;
import Geometry.Segment3;
import Geometry.Vector3;

public class Intersection
{
  private static final double Epsilon = 0.0001;
 
  public static IntersectionLinePlaneResult Compute(Segment3 segment, Plane3 plane) 
  {
    return Compute(segment, plane, Epsilon);
  }
 
  public static IntersectionLinePlaneResult Compute(Segment3 segment, Plane3 plane, double epsilon) 
  {
    var centeredSegment = segment.ToCenteredSegment();

    var result = ComputeSegmentPlane(centeredSegment.Center, centeredSegment.Direction, centeredSegment.Extent, plane);

    if (result.Status == IntersectionStatus.Intersect) 
    {
      result.Point = centeredSegment.Center.Add(centeredSegment.Direction.Multiply(result.Parameter));
    }

    return result;
  }
  
  private static IntersectionLinePlaneResult ComputeLinePlane(Vector3 lineOrigin, Vector3 lineDirection, Plane3 plane)
  {
    var result = new IntersectionLinePlaneResult();

    double DdN = Vector3.Dot(lineDirection, plane.Normal);
    
    var vpResult = Distance.Compute(lineOrigin, plane);

    if (DdN != 0)
    {
        // The line is not parallel to the plane, so they must
        // intersect.
        result.Status = IntersectionStatus.Intersect;
        result.NumberOfIntersections = 1;
        result.Parameter = -vpResult.SignedDistance / DdN;
    }
    else
    {
      // The line and plane are parallel.  Determine whether the
      // line is on the plane.
      if (vpResult.Distance == 0) {
        // The line is coincident with the plane, so choose t = 0
        // for the parameter.
        result.Status = IntersectionStatus.Overlap;
        result.NumberOfIntersections = Integer.MAX_VALUE;
        result.Parameter = 0;
      } else {
        // The line is not on the plane.
        result.Status = IntersectionStatus.None;
        result.NumberOfIntersections = 0;
      }
    }
    return result;
  }

  private static IntersectionLinePlaneResult ComputeSegmentPlane(Vector3 segOrigin, Vector3 segDirection, double segExtent, Plane3 plane)
  {
    var result = ComputeLinePlane(segOrigin, segDirection, plane);

    if (result.Status == IntersectionStatus.Intersect)
    {
        // The line intersects the plane in a point that might not be
        // on the segment.
        if (Math.Abs(result.Parameter) > segExtent)
        {
            result.Status = IntersectionStatus.None;
            result.NumberOfIntersections = 0;
        }
    }

    return result;
  }  
}