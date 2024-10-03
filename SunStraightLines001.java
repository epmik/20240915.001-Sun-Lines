import java.util.Date;

import Utility.Easing;
import Utility.RandomGenerator;
import Utility.WeightedCollection;
import Utility.Interfaces.IRandomGenerator;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class SunStraightLines001 extends AbstractSun 
{
    private int[] _colors = null;
    private int _maxRayCount = 512;
    private float _minRayTimeToLiveMillis = 2000;
    private float _maxRayTimeToLiveMillis = 2000;
    private float _minRayTimeToEaseInMillis = 1000;
    private float _maxRayTimeToEaseInMillis = 2500;
    private float _minRayTimeToEaseOutMillis = 1000;
    private float _maxRayTimeToEaseOutMillis = 5000;
    private float _minRayLength = 100;
    private WeightedCollection<Float> _maxRayLengthWeightedCollection = new WeightedCollection<Float>();
    private float _minRayWidth = 1;
    private WeightedCollection<Float> _maxRayWidthWeightedCollection = new WeightedCollection<Float>();

    private Ray[] _rays = new Ray[_maxRayCount];

    private float _scale = 1f;
    
    public SunStraightLines001(PGraphics graphics) 
    {
        this(graphics.width / 2, graphics.height / 2, graphics.width, graphics.height, graphics);
    }

    public SunStraightLines001(int x, int y, int width, int height, PGraphics graphics) 
    {
        super(x, y, width, height, graphics);

        _scale = width / 800f;

        SetupMinMaxRayLength();

        _rays = new Ray[_maxRayCount];

        for (var i = 0; i < _rays.length; i++) 
        {
            _rays[i] = new Ray();
        }
    }

    private class Ray implements ISunRay 
    {
        public float Angle;
        public float Length;
        public float MaxLength;
        public float Width;
        public float MaxWidth;
        public int Color;

        public float Time;
        public float TotalTimeToLive;
        public float EaseInTime;
        public float EaseOutTime;

        public int Alpha() 
        {
            if (Time < EaseInTime) 
            {
                return (int) (Easing.InQuadratic(Time, 0, EaseInTime) * 255);
            } 
            else if (Time >= TotalTimeToLive) 
            {
                return 0;
            } 
            else if (Time >= TotalTimeToLive - EaseOutTime) 
            {
                return (int) (Easing.OutQuadratic(TotalTimeToLive - Time, 0, EaseOutTime) * 255);
            }

            return 255;
        }

        public boolean IsAlive() 
        {
            return Time < TotalTimeToLive;
        }

        @Override
        public void Setup() 
        {
            Time = 0;
            EaseInTime = RandomGenerator.Value(_minRayTimeToEaseInMillis, _maxRayTimeToEaseInMillis);
            EaseOutTime = RandomGenerator.Value(_minRayTimeToEaseOutMillis, _maxRayTimeToEaseOutMillis);
            TotalTimeToLive = EaseInTime + EaseOutTime
                    + RandomGenerator.Value(_minRayTimeToLiveMillis, _maxRayTimeToLiveMillis);

            Angle = RandomGenerator.Value((float) Math.PI * 2.0f);

            MaxLength = RandomGenerator.Value(_minRayLength, _maxRayLengthWeightedCollection.Next());

            MaxWidth = RandomGenerator.Value(_minRayWidth, _maxRayWidthWeightedCollection.Next());

            Color = _colors[(int) RandomGenerator.Value(0, _colors.length)];
        }

        @Override
        public void Update(float elapsed) 
        {
            var t = Easing.NormalizedTime(Time, 0, TotalTimeToLive);

            Time += elapsed;
            Length = Easing.OutQuadratic(t) * MaxLength;
            Width = _minRayWidth + Easing.OutQuadratic(t) * MaxWidth;

            if (!IsAlive()) {
                Setup();
            }
        }

        @Override
        public void Draw() 
        {
            if(!IsAlive())
            {
                return;
            }

            // float xx = (float) (ray.Length * Math.cos(ray.Angle));
            // float yy = (float) (ray.Length * Math.sin(ray.Angle));

            float x = (float) (Length);
            float y = 0;

            // var m = Graphics.getMatrix();

            // m.mult(null, null);

            Graphics.pushMatrix();

            Graphics.rotate(Angle);

            // float xxx = Graphics.screenX(x, y);
            // float yyy = Graphics.screenY(x, y);

            Graphics.stroke(Graphics.color(Color, Alpha()));
            Graphics.strokeWeight(Width);
            Graphics.line(0f, 0f, x, y);

            Graphics.popMatrix();
        }
    }
    
    public void Setup(ISketch sketch)
    {
        super.Setup(sketch);

        _colors = new int[] 
        {
            Graphics.color(255, 210, 0),
            Graphics.color(255, 162, 0),
            Graphics.color(255, 138, 0),
            Graphics.color(255, 216, 0)
        };

        for(var i = 0; i < _rays.length; i++)
        {
            _rays[i] = new Ray();
            _rays[i].Setup();
        }
    }
  
    private void SetupMinMaxRayLength()
    {
        _minRayLength = 100f * _scale;

        _maxRayLengthWeightedCollection.Clear();

        _maxRayLengthWeightedCollection
                .Add(8f, 360f * _scale)
                .Add(100f, 180f * _scale);

        _minRayWidth = 1 * _scale;

        _maxRayWidthWeightedCollection.Clear();

        _maxRayWidthWeightedCollection
                .Add(8f, 24f * _scale)
                .Add(100f, 4f * _scale);
    }
    
    private void UpdateRays(float elapsed)
    {
        for(var i = 0; i < _rays.length; i++)
        {
            _rays[i].Update(elapsed);
        }
    }

    public void Update(float elapsed) 
    {
        // RandomGenerator.ReSeed(RandomGenerator.Seed());

        UpdateRays(elapsed);
    }   
    
    public void Draw()
    {                 
        DrawRays();
    }
    
    public void DrawRays()
    {
        Graphics.beginDraw();

        Graphics.pushMatrix();

        Graphics.translate(Position.X, Position.Y);

        for (int i = 0; i < _rays.length; i++) 
        {
            _rays[i].Draw();
        }

        Graphics.popMatrix();

        Graphics.endDraw();
    }
 
    // public void MousePressed(MouseEvent event)
    // {
    //     Background.MousePressed(event);
    // }

    // public void MouseReleased(MouseEvent event)
    // {
    //     Background.MouseReleased(event);
    // }

    // public void KeyPressed(KeyEvent event)
    // {
    //     Background.KeyPressed(event);
    // }

    // public void KeyReleased(KeyEvent event)
    // {
    //     Background.KeyReleased(event);
    // }
}
