import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class Reduce extends Reducer<Text,FloatWritable,Text,Text> 
{
    public void reduce(Text quarter , Iterable<FloatWritable> values ,Context c) 
    throws IOException, InterruptedException   
    {
        Integer count = 0;
        Float avg = 0.0f;
        for(FloatWritable val:values)
        {
            avg = avg + val.get();
            count++;
            //c.write(word,val);
        }
        avg = avg/count;
        c.write(quarter,new Text(avg.toString()));
    }
}   