import java.io.File;
import java.text.DateFormat;
import processing.core.*;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import controlP5.*;
import java.util.ArrayList;
import java.util.Date;

import Utility.RandomGenerator;
import Utility.Interfaces.IRandomGenerator;

public class Sketch003 extends PApplet implements ISketch
{
  public int WindowWidth = 800;
  public int WindowHeight = 800;
  public int SketchWidth = WindowWidth * 4;
  public int SketchHeight = WindowHeight * 4;

  public IRandomGenerator RandomGenerator;
  protected long RandomGeneratorSeed;

  private ControlP5 _gui;

  private boolean _drawNewLine = true;
  private float _drawNewLineElapsedTime = Float.MAX_VALUE;
 
  // ArrayList<ISunSurface> _sunSurfaces = null;
  // int _sunSurfaceIndex = 0;

  SunBackgroundVerticalGradiant _background;
  
  public PGraphics Graphics;
  
  protected String Renderer = PConstants.P2D;

  long _sketchStartNanoTime = 0, _sketchRunningNanoTime = 0, _frameStartNanoTime = 0, _previousFrameNanoTime = 0;
  float _ellapsedFrameMillis = 0.0f;

  public Sketch003() 
  {
    super();

    RandomGeneratorSeed = new Date().getTime();

    RandomGenerator = new RandomGenerator(RandomGeneratorSeed);
  }

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

    Graphics = createGraphics(SketchWidth, SketchHeight, Renderer);
    Graphics.smooth(16);
    Graphics.colorMode(PConstants.RGB, 255, 255, 255, 255);

    _gui = new ControlP5(this);

    // _sunSurface = new SunSurface(SketchWidth, SketchHeight);
    
    // _sunSurface.Setup(this);

    // _sunSurfaces = new ArrayList<ISunSurface>();

    // var sunStraightLines001Surface = new SunSurface(SketchWidth, SketchHeight);
    // _sunSurfaces.add(sunStraightLines001Surface);
    // // sunStraightLines001Surface.Background(new SunBackgroundVerticalGradiant());
    // sunStraightLines001Surface.Setup(this);
    // sunStraightLines001Surface.Add(new SunStraightLines001(SketchWidth / 2, SketchHeight / 2, SketchWidth, SketchHeight, sunStraightLines001Surface.Graphics)).Setup(this);

    // _sunSurfaceIndex = _sunSurfaces.size() - 1;

    _background = new SunBackgroundVerticalGradiant(Graphics);
  
    System.out.println("setup done");

    _previousFrameNanoTime = _frameStartNanoTime = _sketchStartNanoTime = System.nanoTime();

    _background.Draw();

    _background.Alpha = 1;
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

    _background.Update(_ellapsedFrameMillis);
  }
      
	@Override
  public void draw() 
  {
    update();

    if (_drawNewLine)
    {
      Graphics.beginDraw();

      Graphics.noFill();
      Graphics.strokeWeight(20);
      Graphics.stroke(255, 0, 0);
      Graphics.line(RandomGenerator.Int(0, Graphics.width / 2), RandomGenerator.Int(0, Graphics.height), RandomGenerator.Int(Graphics.width / 2, Graphics.width), RandomGenerator.Int(0, Graphics.height));
      Graphics.endDraw();

      _drawNewLine = false;
    }

    _background.Draw();

    g.image(Graphics, 0, 0, g.width, g.height);
  }
    
  float clampDegrees(float degrees)
  {
    return degrees < 360 ? degrees : degrees - 360f;
  }
 
  public void mousePressed(MouseEvent event) 
  {
    // _sunSurfaces.get(_sunSurfaceIndex).MousePressed(event);
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