import java.io.IOException;
import java.util.Iterator;
import java.util.*;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class Reduce extends Reducer<Text,Text,Text,Text> 
{
    public void reduce(Text hour , Iterable<Text> values ,Context c) 
    throws IOException, InterruptedException   
    {
        /*Integer count = 0;
        Float avg = 0.0f;
        for(FloatWritable val:values)
        {
            avg = avg + val.get();
            count++;
            //c.write(word,val);
        }
        avg = avg/count;
        c.write(quarter,new Text(avg.toString()));*/
        Float dif = 0.0f;
        //Float[] num = [0.0f,0.0f];
        Integer i = 0;

        float[] num = new float[2];
        Arrays.fill(num,0.0f);

        for(Text val:values)
        {
            String line = val.toString();
            String[] rel = line.split(",");

            num[i] = Float.parseFloat(rel[1]);
            i++;
        }
        dif = num[1] - num[0];

        c.write(hour,new Text(dif.toString()));

    }
}   