import java.io.IOException;
import java.util.Iterator;
import java.util.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



public class ReduceMatrixMulti1 extends  Reducer<Text,Text,Text,Text> 
{
    public void reduce(Text index , Iterable<Text> values ,Context c) 
    throws IOException , InterruptedException
    {

        if(index.toString().equals("Error"))
        {
                c.write(new Text("Error"),new Text(" : Dimension Mismatch"));
        }
        else
        {                                         
            Integer sum = 0,count = 0 ;              //Assumed that n is given before hand
            for(Text val:values)
            {

                sum = sum + Integer.parseInt(val.toString());
                count++;
            }
            c.write(index , new Text(sum.toString()));
        }
    }
}   