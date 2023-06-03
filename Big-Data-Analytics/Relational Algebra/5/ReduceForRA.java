import java.io.IOException;
import java.util.*;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class ReduceForRA extends Reducer<Text,Text,Text,Text> 
{
    public void reduce(Text word , Iterable<Text> values ,Context c) 
    throws IOException, InterruptedException   
    {
        for(Text val:values)
        {
            c.write(word,val);
        }
    }
}   