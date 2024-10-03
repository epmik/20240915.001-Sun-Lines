import java.io.File;
import java.text.DateFormat;
import processing.core.*;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import controlP5.*;
import java.util.ArrayList;

public class Sketch extends PApplet implements ISketch
{
  public int WindowWidth = 800;
  public int WindowHeight = 800;
  public int SketchWidth = WindowWidth * 4;
  public int SketchHeight = WindowHeight * 4;

  private ControlP5 _gui;
  ArrayList<ISunSurface> _sunSurfaces = null;
  int _sunSurfaceIndex = 0;
  
  public PGraphics Graphics;
  
  protected String Renderer = PConstants.P2D;

  long _sketchStartNanoTime = 0, _sketchRunningNanoTime = 0, _frameStartNanoTime = 0, _previousFrameNanoTime = 0;
  float _ellapsedFrameMillis = 0.0f;

  public ControlP5 Gui()
  {
    return _gui;
  } 

  public int Width()
  {
    return Graphics().width;
  } 

  public int Height()
  {
    return Graphics().height;
  } 

  public PGraphics Graphics()
  {
    return Graphics != null ? Graphics : this.g;
  } 

  public void Graphics(PGraphics graphics)
  {
    Graphics = graphics;
  } 
   
  public void settings() 
  {
      super.settings();

      size(WindowWidth, WindowHeight, Renderer);

      smooth(16);
  }
   
	@Override
  public void setup() 
  {
    super.setup();

    frameRate(60);

    // Graphics = createGraphics(SketchWidth, SketchHeight, Renderer);
    // Graphics.smooth(16);
    // Graphics.colorMode(PConstants.RGB, 255, 255, 255, 255);

    _gui = new ControlP5(this);

    // _sunSurface = new SunSurface(SketchWidth, SketchHeight);
    
    // _sunSurface.Setup(this);

    _sunSurfaces = new ArrayList<ISunSurface>();

    var sunStraightLines001Surface = new SunSurface(SketchWidth, SketchHeight);
    _sunSurfaces.add(sunStraightLines001Surface);
    sunStraightLines001Surface.Setup(this);
    sunStraightLines001Surface.Add(new SunStraightLines001(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, sunStraightLines001Surface.Graphics)).Setup(this);

    var sunStraightLines002Surface = new SunSurface(SketchWidth, SketchHeight);
    _sunSurfaces.add(sunStraightLines002Surface);
    sunStraightLines002Surface.Setup(this);
    sunStraightLines002Surface.Add(new SunStraightLines002(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, sunStraightLines002Surface.Graphics)).Setup(this);

    var sunStraightLines003Surface = new SunSurface(SketchWidth, SketchHeight);
    _sunSurfaces.add(sunStraightLines003Surface);
    sunStraightLines003Surface.Setup(this);
    sunStraightLines003Surface.Add(new SunStraightLines001(SketchWidth / 4, SketchHeight / 4, SketchWidth, SketchHeight, sunStraightLines003Surface.Graphics)).Setup(this);
    sunStraightLines003Surface.Add(new SunStraightLines002(SketchWidth / 2, SketchHeight / 2, SketchWidth/4, SketchHeight/4, sunStraightLines003Surface.Graphics)).Setup(this);

    var sunCurvyLines001Surface = new SunSurface(SketchWidth, SketchHeight);
    _sunSurfaces.add(sunCurvyLines001Surface);
    sunCurvyLines001Surface.Setup(this);
    sunCurvyLines001Surface.Add(new SunCurvyLines001(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, sunCurvyLines001Surface.Graphics)).Setup(this);

    var sunCurvyLines002Surface = new SunSurface(SketchWidth, SketchHeight);
    _sunSurfaces.add(sunCurvyLines002Surface);
    sunCurvyLines002Surface.Setup(this);
    sunCurvyLines002Surface.Add(new SunCurvyLines002(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, sunCurvyLines002Surface.Graphics)).Setup(this);

    var sunCurvyLines003Surface = new SunSurface(SketchWidth, SketchHeight);
    _sunSurfaces.add(sunCurvyLines003Surface);
    sunCurvyLines003Surface.Setup(this);
    sunCurvyLines003Surface.Add(new SunCurvyLines003(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, sunCurvyLines003Surface.Graphics)).Setup(this);

    var mixedWeightedSunSurface = new MixedWeightedSunSurface(SketchWidth, SketchHeight);
    _sunSurfaces.add(mixedWeightedSunSurface);
    mixedWeightedSunSurface.Setup(this);
    mixedWeightedSunSurface.Add(new SunStraightLines001(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, mixedWeightedSunSurface.Graphics)).Setup(this);
    mixedWeightedSunSurface.Add(new SunStraightLines002(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, mixedWeightedSunSurface.Graphics)).Setup(this);
    mixedWeightedSunSurface.Add(new SunCurvyLines001(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, mixedWeightedSunSurface.Graphics)).Setup(this);
    mixedWeightedSunSurface.Add(new SunCurvyLines002(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, mixedWeightedSunSurface.Graphics)).Setup(this);
    mixedWeightedSunSurface.Add(new SunCurvyLines003(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, mixedWeightedSunSurface.Graphics)).Setup(this);

    _sunSurfaceIndex = _sunSurfaces.size() - 1;
  
    System.out.println("setup done");

    _previousFrameNanoTime = _frameStartNanoTime = _sketchStartNanoTime = System.nanoTime();
  }
  
  void update()
  {
    _previousFrameNanoTime = _frameStartNanoTime;
    _frameStartNanoTime = System.nanoTime();
    _sketchRunningNanoTime = _frameStartNanoTime - _sketchStartNanoTime;

    _ellapsedFrameMillis = (_frameStartNanoTime - _previousFrameNanoTime) * 0.000001f;

    _sunSurfaces.get(_sunSurfaceIndex).Update(_ellapsedFrameMillis);
  }
      
	@Override
  public void draw() 
  {
    update();

    _sunSurfaces.get(_sunSurfaceIndex).Draw();

    g.image(_sunSurfaces.get(_sunSurfaceIndex).Graphics(), 0, 0, g.width, g.height);
  }
    
  float clampDegrees(float degrees)
  {
    return degrees < 360 ? degrees : degrees - 360f;
  }
 
  public void mousePressed(MouseEvent event) 
  {
    _sunSurfaces.get(_sunSurfaceIndex).MousePressed(event);
  }    
 
  public void mouseReleased(MouseEvent event) 
  {
    _sunSurfaces.get(_sunSurfaceIndex).MouseReleased(event);
  }    
 
  public void keyPressed(KeyEvent event) 
  {
    _sunSurfaces.get(_sunSurfaceIndex).KeyPressed(event);
  }    
 
  public void keyReleased(KeyEvent event) 
  {
    _sunSurfaces.get(_sunSurfaceIndex).KeyReleased(event);

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
        case PConstants.ENTER:
          // NoiseGenerator.ReSeed();
          break;
        case PConstants.TAB:
        _sunSurfaceIndex--;
          if (_sunSurfaceIndex < 0) 
          {
            _sunSurfaceIndex = _sunSurfaces.size() - 1;
          }
          break;
        case 's':
        case 'S':
          SaveFrame(_sunSurfaces.get(_sunSurfaceIndex).Graphics());
          break;
        default:
          break;
      }
    }
  }

  protected String ClassName() 
  {
    return this.getClass().getSimpleName();
  }

  protected String InputDirectory() 
  {
    return "data" + File.separator + "in";
  }

  protected String OutputDirectory() 
  {
    return "data" + File.separator + "out";
  }

  public static String PathCombine(String... paths) 
  {
    File file = new File(paths[0]);

    for (int i = 1; i < paths.length; i++) 
    {
      file = new File(file, paths[i]);
    }

    return file.getPath();
  }

  public static boolean FileExists(String path) 
  {
    var file = new File(path);

    return file.exists() && !file.isDirectory();
  }

  protected void SaveFrame(PImage canvas) 
  {
    DateFormat dateFormat = new java.text.SimpleDateFormat("yyyyMMdd.HHmmss.SSS");

    SaveFrame(canvas,
      PathCombine(
        OutputDirectory(),
            dateFormat.format(java.util.Calendar.getInstance().getTime()) + "." + ClassName() + ".png")
        );
  }

  protected void SaveFrame(PImage canvas, String path) 
  {
    canvas.save(path);
  }
}