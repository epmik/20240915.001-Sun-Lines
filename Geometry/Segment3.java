package Geometry;

// David Eberly, Geometric Tools, Redmond WA 98052
// Copyright (c) 1998-2021
// Distributed under the Boost Software License, Version 1.0.
// https://www.boost.org/LICENSE_1_0.txt
// https://www.geometrictools.com/License/Boost/LICENSE_1_0.txt
// Version: 4.0.2019.08.13

// The segment is represented by (1-t)*P0 + t*P1, where P0 and P1 are the
// endpoints of the segment and 0 <= t <= 1.  Some algorithms prefer a
// centered representation that is similar to how oriented bounding boxes are
// defined.  This representation is C + s*D, where C = (P0 + P1)/2 is the
// center of the segment, D = (P1 - P0)/|P1 - P0| is a unit-length direction
// vector for the segment, and |t| <= e.  The value e = |P1 - P0|/2 is the
// extent (or radius or half-length) of the segment.

    public class Segment3
    {
        public Vector3[] P = new Vector3[] { new Vector3(-1, 0, 0), new Vector3(1, 0, 0) };

    // public:
        // Construction and destruction.  The default constructor sets p0 to
        // (-1,0,...,0) and p1 to (1,0,...,0).  NOTE:  If you set p0 and p1;
        // compute C, D, and e; and then recompute q0 = C-e*D and q1 = C+e*D,
        // numerical round-off errors can lead to q0 not exactly equal to p0
        // and q1 not exactly equal to p1.
        public Segment3()
        {
        }

        public Segment3(Vector3 p0, Vector3 p1)
        {
            P[0] = p0;
            P[1] = p1;
        }

        public Segment3(Vector3[] p)
        {
            P[0] = p[0];
            P[1] = p[1];
        }

        public Segment3(Vector3 center, Vector3 direction, double extent)
        {
            FromCenteredSegment(center, direction, extent);
        }

        // Manipulation via the centered form.
        public static Segment3 FromCenteredSegment(Vector3 center, Vector3 direction, double extent)
        {
            var result = new Segment3();

            result.P[0] = center.Subtract(direction.Multiply(extent));
            result.P[1] = center.Add(direction.Multiply(extent));

            return result;
        }

        public CenteredSegment ToCenteredSegment()
        {
            var result = new CenteredSegment();

            result.Center = P[0].Add(P[1]).Multiply(0.5);
            result.Direction = P[1].Subtract(P[0]);
            var length = result.Direction.Length();
            result.Direction = Vector3.Normalize(result.Direction);
            result.Extent = 0.5 * length;

            return result;
        }

        public class CenteredSegment
        {
            public Vector3 Center;    
            public Vector3 Direction;    
            public double Extent;    
        }

        //// Public member access.
        //public std::array<Vector<N, Real>, 2> p;

    // public:
    //     // Comparisons to support sorted containers.
    //     bool operator==(Segment const& segment) const
    //     {
    //         return p == segment.p;
    //     }

    //     bool operator!=(Segment const& segment) const
    //     {
    //         return p != segment.p;
    //     }

    //     bool operator< (Segment const& segment) const
    //     {
    //         return p < segment.p;
    //     }

    //     bool operator<=(Segment const& segment) const
    //     {
    //         return p <= segment.p;
    //     }

    //     bool operator> (Segment const& segment) const
    //     {
    //         return p > segment.p;
    //     }

    //     bool operator>=(Segment const& segment) const
    //     {
    //         return p >= segment.p;
    //     }
    };

    // // Template aliases for convenience.
    // template <typename Real>
    // using Segment2 = Segment<2, Real>;

    // template <typename Real>
    // using Segment3 = Segment<3, Real>;
