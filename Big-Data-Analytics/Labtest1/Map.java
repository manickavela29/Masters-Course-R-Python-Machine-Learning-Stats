import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map extends Mapper<LongWritable,Text,Text,FloatWritable>
{
    
    public void map(LongWritable key , Text value ,Context c)
    throws IOException , InterruptedException   
    {
        String line = value.toString();
        String[] rel = line.split(",");
        Integer num = Integer.parseInt(rel[0]);
        Float val = Float.parseFloat(rel[1]);
        

        if(num < 6)
        {
            c.write(new Text("Q1"),new FloatWritable(val));
        }
        else if(num < 12)
        {
            c.write(new Text("Q2"),new FloatWritable(val));
        }
        else if(num < 18)
        {
            c.write(new Text("Q3"),new FloatWritable(val));
        }
        else if(num < 24)
        {
            c.write(new Text("Q4"),new FloatWritable(val));
        }
    } 
}