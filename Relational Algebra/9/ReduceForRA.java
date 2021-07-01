import java.io.IOException;
import java.util.*;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class ReduceForRA extends Reducer<Text,IntWritable,Text,IntWritable> 
{
    public void reduce(Text word , Iterable<IntWritable> values ,Context c) 
    throws IOException, InterruptedException   
    {
        int sum = 0;
        for(IntWritable val:values)
        {
            sum += val.get();
        }
        c.write(word,new IntWritable(sum));
    }
}   