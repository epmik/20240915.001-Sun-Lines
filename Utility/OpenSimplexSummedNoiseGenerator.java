package Utility;

import java.util.Date;

public class OpenSimplexSummedNoiseGenerator extends SummedNoiseGenerator<OpenSimplexNoiseGenerator> 
{
    public OpenSimplexSummedNoiseGenerator()
    {
        this(new Date().getTime());
    }
    
	public OpenSimplexSummedNoiseGenerator(long seed)
    {
        super(new OpenSimplexNoiseGenerator(seed));
    }
}