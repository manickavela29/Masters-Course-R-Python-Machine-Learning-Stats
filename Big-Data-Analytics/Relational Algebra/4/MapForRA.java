import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class MapForRA extends Mapper<LongWritable,Text,Text,Text>
{
    
    public void map(LongWritable key , Text value ,Context c)
    throws IOException , InterruptedException   
    {
        String line = value.toString();
        String[] rel = line.split(",");
        char ch = rel[1].charAt(0);
        if(ch == 'A')
        {
            //c.write(new Text("c"),new Text(rel[0]+",A"));
            System.out.println("Passing : "+rel[0]);
            c.write(new Text(rel[0]),new Text(rel[1]));
        }
        if(ch == 'C')
        {
            System.out.println("Passing : "+rel[0]);
            c.write(new Text(rel[0]),new Text(rel[1]));
        }
    } 
}

