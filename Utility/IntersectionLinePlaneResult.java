package Utility;

import Geometry.Vector3;

public class IntersectionLinePlaneResult 
{
  IntersectionStatus Status;

  // The number of intersections is 0 (no intersection), 1 (linear
  // component and plane intersect in a point), or
  // std::numeric_limits<int>::max() (linear component is on the
  // plane).  If the linear component is on the plane, 'point'
  // component's origin and 'parameter' is zero.
  int NumberOfIntersections;
  double Parameter;
  Vector3 Point;
}