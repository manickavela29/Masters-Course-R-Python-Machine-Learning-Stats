import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class ReduceForWordCount extends MapReduceBase implements Reducer<Text,IntWritable,Text,IntWritable> 
{
    public void reduce(Text word , Iterator<IntWritable> values , OutputCollector<Text , IntWritable> out, Reporter r) 
    throws IOException
    {
        int sum = 0;
        while(values.hasNext())
        {
            sum += values.next().get();
        }
        out.collect(word,new IntWritable(sum));
    }
}   