import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import Geometry.Vector2;
import Utility.Easing;
import Utility.OpenSimplexSummedNoiseGenerator;
import Utility.RandomGenerator;
import Utility.SummedNoiseGenerator;
import Utility.Interfaces.INoiseGenerator;
import Utility.Interfaces.IRandomGenerator;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Trajectory002 implements ITrajectory
{
    public IRandomGenerator RandomGenerator;
    public INoiseGenerator NoiseGenerator;
    public float step = 0.01f;
    public float noiseMultiplier = 0.0025f;
    public float Radius = 200;
    public float rotation;
    public float angleDeviation = 90f;

    public Trajectory002() 
    {
        super();

        RandomGenerator = new RandomGenerator();
        NoiseGenerator = new OpenSimplexSummedNoiseGenerator();
    }
 
    public void Setup()
    {
    }

    public void Update(float elapsed) 
    {
    }
  
    public Vector2 PointAt(float normalizedTime)
    {
        var a = Anchors(normalizedTime);

        return a.Point;
    }

    public Anchors Anchors(float normalizedTime)
    {
        float x = 0, y = 0, s = 0, t = 0, v = 0, degrees = 0, radians = 0, xx = 0, yy = 0;
        Anchors anchors = new Anchors();
        float u = 0;

        s = Radius * Math.min(step, 1f);

        while(t <= normalizedTime)
        {
            u = normalizedTime - t;

            t += step;

            x = xx;
            y = yy;

            v = (float) NoiseGenerator.Value((x) * noiseMultiplier, (y) * noiseMultiplier);

            degrees = -(angleDeviation * 0.5f) + (v * angleDeviation);

            radians = (float) Utility.Math.ToRadians(degrees);

            xx = x + (s * (float)Math.cos(radians));
            yy = y + (s * (float)Math.sin(radians));
        }

        anchors.First.X = x;
        anchors.First.Y = y;
        anchors.Last.X = xx;
        anchors.Last.Y = yy;
        anchors.FirstToLastFraction = u / step;
        anchors.Point = anchors.First.Add(Vector2.Subtract(anchors.Last, anchors.First).Multiply(anchors.FirstToLastFraction));
        
        return anchors;
    }
}
