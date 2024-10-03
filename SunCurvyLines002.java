import java.util.Date;

import Geometry.Vector2;
import Utility.Easing;
import Utility.OpenSimplexNoiseGenerator;
import Utility.RandomGenerator;
import Utility.WeightedCollection;
import Utility.Interfaces.IRandomGenerator;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class SunCurvyLines002 extends AbstractSun 
{
    private int[] _colors = null;
    // private int _maxRayCount = 512;
    private int _maxRayCount = 24;
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

    public static float NoiseGeneratorInputMultiplier = 0.001f;
    public static long NoiseGeneratorSeed;
    public static OpenSimplexNoiseGenerator NoiseGenerator;

    static 
    {
        NoiseGeneratorInputMultiplier = 0.001f;
        NoiseGeneratorSeed = new Date().getTime();
        NoiseGenerator = new OpenSimplexNoiseGenerator(NoiseGeneratorSeed);
        NoiseGenerator.ClampValues(true);
        NoiseGenerator.AllowNegativeValues(true);
        NoiseGenerator.InputMultiplier(NoiseGeneratorInputMultiplier);
    }
    
    public SunCurvyLines002(PGraphics graphics) 
    {
        this(graphics.width / 2, graphics.height / 2, graphics.width, graphics.height, graphics);
    }

    public SunCurvyLines002(int x, int y, int width, int height, PGraphics graphics) 
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
        public float MaxAngleDeviation = (float)(Math.PI * 0.25);
        public float Length;
        public float MaxLength;
        public float Width;
        public float MaxWidth;
        public int Color;
        public Vector2 PreviousPosition = new Vector2(0, 0);

        public float Time;
        public float NormalizedTime;
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
            Time += elapsed;
            NormalizedTime = Easing.NormalizedTime(Time, 0, TotalTimeToLive);
            Length = Easing.OutQuadratic(NormalizedTime) * MaxLength;
            Width = _minRayWidth + Easing.OutQuadratic(NormalizedTime) * MaxWidth;

            if (!IsAlive()) 
            {
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

            // float xx = (float)(Length * Math.cos(Angle));
            // float yy = (float)(Length * Math.sin(Angle));

            float v = (float) NoiseGenerator.Value(PreviousPosition.X, PreviousPosition.Y);
            
            float angle = Angle + v * MaxAngleDeviation;

            float x = (float)(Length * Math.cos(angle));
            float y = (float)(Length * Math.sin(angle));

            Graphics.pushMatrix();

            Graphics.rotate(Angle);

            Graphics.noFill();
            Graphics.stroke(Graphics.color(Color, Alpha()));
            Graphics.line(PreviousPosition.X, PreviousPosition.Y, x, y);

            Graphics.popMatrix();

            PreviousPosition.X = x;
            PreviousPosition.Y = y;
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
}
