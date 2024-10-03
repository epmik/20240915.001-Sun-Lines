import java.util.Date;

import Geometry.Vector2;
import Geometry.Vector3;
import Utility.Easing;
import Utility.RandomGenerator;
import Utility.WeightedCollection;
import Utility.Interfaces.IRandomGenerator;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlP5;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class SunStraightLines002 extends AbstractSun
{
    private int[] _colors = null;
    private int _foreColor;
    private int _backColor;
    private int _accentColor;
    private int _maxRayCount = 512;
    private float _minRayTimeToLiveMillis = 2000;
    private float _maxRayTimeToLiveMillis = 2000;
    private float _minRayTimeToEaseInMillis = 1000;
    private float _maxRayTimeToEaseInMillis = 2500;
    private float _minRayTimeToEaseOutMillis = 2500;
    private float _maxRayTimeToEaseOutMillis = 8000;
    private float _minRayLengthScale = 1f;
    private float _minRayLength = 100 * 8;
    
    private float _maxRayLengthScale = 1f;
    private WeightedCollection<Float> _maxRayLengthWeightedCollection = new WeightedCollection<Float>();

    private float _minRayWidth = 1;
    private WeightedCollection<Float> _maxRayWidthWeightedCollection = new WeightedCollection<Float>()
        .Add(8f, 8f * 8).Add(4f, 8f * 8).Add(100f, 4f * 8);

    private Ray[] _rays = new Ray[_maxRayCount];

    private Vector2 _screenCenter = null;

    // public ISunBackground Background = null;
   
    private float _scale = 1f;

    public enum RayColorMode 
    {
        Random,
        VerticalGradiant,
        RadialGradientForeToBack,
        RadialGradientBackToFore;

        private static final RayColorMode[] _values = values();

        public RayColorMode Next() 
        {
            return _values[(this.ordinal() + 1) % _values.length];
        }
    }
    
    private RayColorMode _RayColorMode = RayColorMode.Random;

    private class Ray implements ISunRay
    {
        // public boolean Enabled = true;
        public float Angle;
        public float Length;
        public float MaxLength;
        public float MaxWidth;
        public int Color;

        public float Time;    
        public float TotalTimeToLive;
        public float EaseInTime;    
        public float EaseOutTime;

        private float[] _dots = new float[2024];
        private float _lastDotsX = 0;
        private float _lastDotsY = 0;
        private int _dotsIndex = 0;

        public float NormalizedTime()
        {
            return Easing.NormalizedTime(Time, 0, TotalTimeToLive);
        }

        public void InsertDot(float x, float y, float time)
        {
            var insert = true;

            if(_dotsIndex > 0)
            {
                if(Math.abs(_lastDotsX - x) < 0.10f && Math.abs(_lastDotsY - y) > 0.10f)
                {
                    insert = false;
                }
            }

            if(insert)
            {
                _dots[_dotsIndex++] = time;
                _lastDotsX = x;
                _lastDotsY = y;
            }
        }
        
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
    
            _dotsIndex = 0;    
        }

        @Override
        public void Update(float elapsed) 
        {
            var t = Easing.NormalizedTime(Time, 0, TotalTimeToLive);

            Time += elapsed;
            Length = Easing.OutQuadratic(t) * MaxLength * _scale;

            float x = (float) (Length * Math.cos(Angle));
            float y = (float) (Length * Math.sin(Angle));

            InsertDot(x, y, Time);            

            if (!IsAlive()) 
            {
                Setup();
            }
        }

        @Override
        public void Draw() 
        {
        }
    }

    public SunStraightLines002(int x, int y, int width, int height, PGraphics graphics) 
    {
        super(x, y, width, height, graphics);

        _scale = width / 800f;

        _rays = new Ray[_maxRayCount];

        for(var i = 0; i < _rays.length; i++)
        {
            _rays[i] = new Ray();
        }
    }
 
    public void Setup(ISketch sketch)
    {
        super.Setup(sketch);

        _colors = new int[] {
                Graphics.color(255, 210, 0),
                Graphics.color(255, 162, 0),
                Graphics.color(255, 138, 0),
                Graphics.color(255, 216, 0)
        };

        _foreColor = _colors[0];
        _backColor = _colors[2];

        _accentColor = Graphics.color(254, 0, 132);

        _screenCenter = new Vector2(Graphics.width / 2, Graphics.height / 2);

        SetupMinMaxRayLength();

        for (var i = 0; i < _rays.length; i++) 
        {
            _rays[i] = new Ray();
            _rays[i].Setup();
        }

        // if(Background == null)
        // {
        //     Background = new SunBackgroundVerticalGradiant(Graphics);
        // }

        _sketch.Gui().addTab("extra");
        
        var _minRayLengthScaleSlider = _sketch.Gui()
                .addSlider("_minRayLengthScale")
                .setLabel("MIN Ray Length Scale")
                .setRange(0.5f, 2f)
                .setNumberOfTickMarks(100)
                .setValue(_minRayLengthScale)
                .moveTo("extra");

        _minRayLengthScaleSlider.addCallback(new CallbackListener() {
            public void controlEvent(CallbackEvent event) {
                if (event.getAction() == ControlP5.ACTION_CLICK) {
                    var c = event.getController();

                    _minRayLengthScale = c.getValue();

                    SetupMinMaxRayLength();
                }
            }
        });

        var _maxRayLengthScaleSlider = _sketch.Gui()
                .addSlider("_maxRayLengthScale")
                .setLabel("MAX Ray Length Scale")
                .setRange(0.5f, 2f)
                .setNumberOfTickMarks(100)
                .setValue(_maxRayLengthScale)
                .moveTo("extra");

        _maxRayLengthScaleSlider.addCallback(new CallbackListener() {
            public void controlEvent(CallbackEvent event) {
                if (event.getAction() == ControlP5.ACTION_CLICK) {
                    var c = event.getController();

                    _maxRayLengthScale = c.getValue();

                    SetupMinMaxRayLength();
                }
            }
        });
    }
   
    private void SetupMinMaxRayLength()
    {
        _minRayLength = 100f * _minRayLengthScale * _scale;

        _maxRayLengthWeightedCollection.Clear();
        
        _maxRayLengthWeightedCollection
            .Add(24f, 360f * _maxRayLengthScale * _scale)
            .Add(100f, 120f * _maxRayLengthScale * _scale)
            .Add(100f, 180f * _maxRayLengthScale * _scale);
    }

    private void UpdateRays(float elapsed)
    {
        for (var i = 0; i < _rays.length; i++) 
        {
            _rays[i].Update(elapsed);
        }
    }

    @Override
    public void Update(float elapsed) 
    {
        // RandomGenerator.ReSeed(RandomGenerator.Seed());

        UpdateRays(elapsed);
    }
     
    @Override
    public void Draw()
    {
        // Background.Draw();
                 
        DrawRays();
    }
    
    public void DrawRays()
    {
        Graphics.beginDraw();

        Graphics.pushMatrix();

        Graphics.translate(Graphics.width / 2, Graphics.height / 2);

        for (int i = 0; i < _rays.length; i++) 
        {
            var ray = _rays[i];

            if (!ray.IsAlive()) 
            {
                continue;
            }

            DrawRayDots(ray);
        }

        Graphics.popMatrix();

        Graphics.endDraw();
    }
    
    private void DrawRayDots(Ray ray)
    {
        for (int i = 0; i < ray._dotsIndex; i++) 
        {
            var time = ray._dots[i];

            var t = Easing.NormalizedTime(time, 0, ray.TotalTimeToLive);

            var width = _minRayWidth + Easing.OutQuadratic(t) * ray.MaxWidth;

            var length = Easing.OutQuadratic(t) * ray.MaxLength;

            // float x = (float) (length * Math.cos(ray.Angle));
            // float y = (float) (length * Math.sin(ray.Angle));
            float x = (float) (length);
            float y = 0f;

            Graphics.pushMatrix();

            Graphics.rotate(ray.Angle);

            Graphics.noStroke();
            Graphics.fill(RayColor(ray, x + Graphics.width / 2, y + Graphics.height / 2));
            Graphics.circle(x, y, (float) width);

            Graphics.popMatrix();
        }
    }
    
    int RayColor(Ray ray, float x, float y)
    {
        switch (_RayColorMode) 
        {
            case RadialGradientForeToBack:
            {
                float distance = (float) Vector2.Distance(_screenCenter, new Vector2(x, y));
                float factor = distance / Graphics.height;
                return Graphics.color(InterpolateColors(_foreColor, _accentColor, factor), ray.Alpha());
            }
            case RadialGradientBackToFore:
            {
                float distance = (float) Vector2.Distance(_screenCenter, new Vector2(x, y));
                float factor = distance / (Graphics.height / 2);
                return Graphics.color(InterpolateColors(_backColor, _foreColor, factor), ray.Alpha());
            }
            case VerticalGradiant:
            {
                float factor = y / (float)(Graphics.height - 100 * 8);
                return Graphics.color(InterpolateColors(_backColor, _foreColor, factor), ray.Alpha());
            }
            default:
                return Graphics.color(ray.Color, ray.Alpha());
        }
    }

    int InterpolateColors(int foreColor, int backColor, float factor)
    {
        return Graphics.lerpColor(foreColor, backColor, factor);
    } 

    public void KeyReleased(KeyEvent event)
    {
        if (event.getKey() == PConstants.CODED) 
        {
          switch (event.getKeyCode()) 
          {
            case PConstants.UP:
              break;
            case PConstants.DOWN:
              break;
            case PConstants.LEFT:
             break;
            case PConstants.RIGHT:
              break;
            default:
              break;
          }
        }
        else 
        {
          switch (event.getKey()) 
          {
            case 'c':
            case 'C':
                _RayColorMode = _RayColorMode.Next();
              break;
            default:
              break;
          }
        }
        }
}
