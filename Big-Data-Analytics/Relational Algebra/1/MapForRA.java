import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapForRA extends Mapper<LongWritable,Text,Text,Text>
{
    
    public void map(LongWritable key , Text value ,Context c)
    throws IOException , InterruptedException   
    {
        String line = value.toString();
        String[] rel = line.split(",");
        if(rel[0].equals("B1"))
        {
            c.write(new Text(rel[0]),new Text(rel[1]));
        }
    } 
}