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

public class SunCurvyLines003 extends AbstractSun 
{
    private int[] _colors = null;
    // private int _maxRayCount = 512;
    private int _maxRayCount = 128;
    private float _minRayLength = 0;
    private WeightedCollection<Float> _maxRayLengthWeightedCollection = new WeightedCollection<Float>();
    private float _minRayWidth = 1;
    private WeightedCollection<Float> _maxRayWidthWeightedCollection = new WeightedCollection<Float>();

    private Ray[] _rays = new Ray[_maxRayCount];

    private float _scale = 1f;

    public static float NoiseGeneratorInputMultiplier;
    public static long NoiseGeneratorSeed;
    public static OpenSimplexNoiseGenerator NoiseGenerator;

    static 
    {
        NoiseGeneratorInputMultiplier = 0.0025f;
        NoiseGeneratorSeed = new Date().getTime();
        NoiseGenerator = new OpenSimplexNoiseGenerator(NoiseGeneratorSeed);
        NoiseGenerator.ClampValues(true);
        NoiseGenerator.AllowNegativeValues(true);
        NoiseGenerator.InputMultiplier(NoiseGeneratorInputMultiplier);
    }
    
    public SunCurvyLines003(PGraphics graphics) 
    {
        this(graphics.width / 2, graphics.height / 2, graphics.width, graphics.height, graphics);
    }

    public SunCurvyLines003(int x, int y, int width, int height, PGraphics graphics) 
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
        public float MaxAngleDeviation = (float)(Math.PI * 0.55);
        public float Length;
        public float TotalLength;
        public float Width;
        public float MaxWidth;
        public int Color;

        public float NormalizedLength;
        public float EaseOutLength;
        public float Speed = 8;

        public int Alpha() 
        {
            if (Length >= TotalLength) 
            {
                return 0;
            } 
            if (Length >= TotalLength - EaseOutLength) 
            {
                return (int) (Easing.OutQuadratic(Length, 0, TotalLength) * 255);
            }

            return 255;
        }

        public boolean IsAlive() 
        {
            return Length < TotalLength;
        }

        @Override
        public void Setup() 
        {
            Length = 0;

            EaseOutLength = RandomGenerator.Value(TotalLength * 0.75f, TotalLength);

            Angle = RandomGenerator.Value((float) Math.PI * 2.0f);

            TotalLength = RandomGenerator.Value(_minRayLength, _maxRayLengthWeightedCollection.Next());

            MaxWidth = RandomGenerator.Value(_minRayWidth, _maxRayWidthWeightedCollection.Next());

            Color = _colors[(int) RandomGenerator.Value(0, _colors.length)];
        }

        @Override
        public void Update(float elapsed) 
        {
            Length += Speed * elapsed;
            NormalizedLength = Easing.NormalizedTime(Length, 0, TotalLength);
            Width = _minRayWidth + Easing.OutQuadratic(NormalizedLength) * MaxWidth;

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

            float step = 0.1f;
            float l = 0;
            float px = 0;
            float py = 0;

            while(l < Length)
            {
                float v = (float) NoiseGenerator.Value(px, py);
            
                float angle = Angle + v * MaxAngleDeviation;

                // System.out.println(Utility.Math.ToDegrees(angle));

                float x = (float)(px + (8 * Math.cos(angle)));
                float y = (float)(py + (8 * Math.sin(angle)));
    
                Graphics.pushMatrix();
    
                Graphics.rotate(Angle);
    
                Graphics.noFill();
                Graphics.strokeWeight(4);
                Graphics.stroke(Graphics.color(Color, Alpha()));
                Graphics.line(px, py, x, y);

                Graphics.popMatrix();
                    
                l += step;
                px = x;
                py = y;
            }
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
                // .Add(8f, 360f * _scale)
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
