import java.io.File;
import java.text.DateFormat;
import processing.core.*;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import controlP5.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

import Utility.RandomGenerator;
import Utility.Interfaces.IRandomGenerator;

public class Sketch004 extends PApplet implements ISketch
{
  public int WindowWidth = 800;
  public int WindowHeight = 800;
  public int SketchWidth = WindowWidth * 4;
  public int SketchHeight = WindowHeight * 4;

  public IRandomGenerator RandomGenerator;
  protected long RandomGeneratorSeed;

  private Gui Gui;

  private boolean _drawNewLine = true;
  private float _drawNewLineElapsedTime = Float.MAX_VALUE;
 
  // ArrayList<ISunSurface> _sunSurfaces = null;
  // int _sunSurfaceIndex = 0;

  SunBackgroundVerticalGradiant _background;
  
  public PGraphics Graphics;
  
  protected String Renderer = PConstants.P2D;

  long _sketchStartNanoTime = 0, _sketchRunningNanoTime = 0, _frameStartNanoTime = 0, _previousFrameNanoTime = 0;
  float _ellapsedFrameMillis = 0.0f;
  Trajectory001 _trajectory001;
  Trajectory002 _trajectory002;
  Trajectory003 _trajectory003;
  TrajectoryComponent _trajectory001Component, _trajectory002Component, _trajectory003Component;

  public Sketch004() 
  {
    super();

    RandomGeneratorSeed = new Date().getTime();

    RandomGenerator = new RandomGenerator(RandomGeneratorSeed);
  }

  public Gui Gui()
  {
    return Gui;
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

    Graphics = createGraphics(SketchWidth, SketchHeight, Renderer);
    Graphics.smooth(16);
    Graphics.colorMode(PConstants.RGB, 255, 255, 255, 255);

    Gui = new Gui(this);

    _trajectory001 = new Trajectory001();
    _trajectory001.Radius = 1000;

    _trajectory002 = new Trajectory002();
    _trajectory002.Radius = 1000;

    _trajectory003 = new Trajectory003();
    _trajectory003.Radius = 1000;

    _trajectory001Component = new TrajectoryComponent(_trajectory001);
    _trajectory002Component = new TrajectoryComponent(_trajectory002);
    _trajectory003Component = new TrajectoryComponent(_trajectory003);

    _background = new SunBackgroundVerticalGradiant(Graphics);

    Gui.AddSlider((v) -> { _trajectory002.Radius = _trajectory001.Radius = v; }, _trajectory001.Radius, "Radius", 10, 2000, 200);

    Gui.AddSlider((v) -> { _trajectory002.angleDeviation = _trajectory001.MaxDeviation = v; }, _trajectory001.MaxDeviation, "Deviation", 0, 800, 200);

    Gui.AddToggle((v) -> { _trajectory001.EaseIn = v; }, _trajectory001.EaseIn, "Ease in");

    System.out.println("setup done");

    _previousFrameNanoTime = _frameStartNanoTime = _sketchStartNanoTime = System.nanoTime();
  }  
  
  void update()
  {
    _previousFrameNanoTime = _frameStartNanoTime;
    _frameStartNanoTime = System.nanoTime();
    _sketchRunningNanoTime = _frameStartNanoTime - _sketchStartNanoTime;

    _ellapsedFrameMillis = (_frameStartNanoTime - _previousFrameNanoTime) * 0.000001f;

    if (_drawNewLineElapsedTime > 1000)
    {
      _drawNewLineElapsedTime = 0;
      _drawNewLine = true;
    }

    _drawNewLineElapsedTime += _ellapsedFrameMillis;

    _trajectory002.RandomGenerator.ReSeed(_trajectory002.RandomGenerator.Seed());
    _trajectory002.NoiseGenerator.ReSeed(_trajectory002.NoiseGenerator.Seed());

    _trajectory003.RandomGenerator.ReSeed(_trajectory003.RandomGenerator.Seed());
    _trajectory003.NoiseGenerator.ReSeed(_trajectory003.NoiseGenerator.Seed());

    _trajectory001.Update(_ellapsedFrameMillis);

    _trajectory002.Update(_ellapsedFrameMillis);

    _trajectory003.Update(_ellapsedFrameMillis);

    _background.Update(_ellapsedFrameMillis);
  }
      
	@Override
  public void draw() 
  {
    update();

    _background.Draw();

    // Graphics.beginDraw();

    // Graphics.translate(Graphics.width / 2, Graphics.height / 2);

    // _trajectory002.Draw(Graphics);

    // Graphics.endDraw();

    _trajectory001Component.Draw(Graphics);
    _trajectory002Component.Draw(Graphics);
    _trajectory003Component.Draw(Graphics);

    g.image(Graphics, 0, 0, g.width, g.height);
  }
    
  float clampDegrees(float degrees)
  {
    return degrees < 360 ? degrees : degrees - 360f;
  }
 
  public void mousePressed(MouseEvent event) 
  {
    // _sunSurfaces.get(_sunSurfaceIndex).MousePressed(event);
    _trajectory001.RandomGenerator.ReSeed();
    _trajectory001.NoiseGenerator.ReSeed();

    _trajectory002.RandomGenerator.ReSeed();
    _trajectory002.NoiseGenerator.ReSeed();

    _trajectory003.RandomGenerator.ReSeed();
    _trajectory003.NoiseGenerator.ReSeed();
  }    
 
  public void mouseReleased(MouseEvent event) 
  {
    // _sunSurfaces.get(_sunSurfaceIndex).MouseReleased(event);
  }    
 
  public void keyPressed(KeyEvent event) 
  {
    // _sunSurfaces.get(_sunSurfaceIndex).KeyPressed(event);
  }    
 
  public void keyReleased(KeyEvent event) 
  {
    // _sunSurfaces.get(_sunSurfaceIndex).KeyReleased(event);

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
          // _sunSurfaceIndex--;
          // if (_sunSurfaceIndex < 0) 
          // {
          //   _sunSurfaceIndex = _sunSurfaces.size() - 1;
          // }
          break;
        case 's':
        case 'S':
          SaveFrame(Graphics);
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