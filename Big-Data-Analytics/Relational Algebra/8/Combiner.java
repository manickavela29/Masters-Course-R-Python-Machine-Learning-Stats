import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class Combiner extends Reducer<Text,IntWritable,Text,IntWritable> 
{
    public void reduce(Text word , Iterable<IntWritable> values ,Context c) 
    throws IOException, InterruptedException   
    {
        int sum = 0;
        for(IntWritable val:values)
        {
            sum++;
        }
        c.write(word,new IntWritable(sum));
    }
}  