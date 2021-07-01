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

public class Map2 extends Mapper<LongWritable,Text,Text,Text>
{
    
    public void map(LongWritable key , Text value ,Context c)
    throws IOException , InterruptedException   
    {
        String line = value.toString();
        String[] rel = line.split("\t");

        //Float val = Float.parseFloat(rel[1]);
        
        c.write(new Text("c"),new Text(rel[0]+","+rel[1]));

    } 
}