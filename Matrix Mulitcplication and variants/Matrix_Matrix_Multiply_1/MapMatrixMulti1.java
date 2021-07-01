import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapMatrixMulti1 extends Mapper<LongWritable,Text,Text,Text>
{        
        public void map(LongWritable key,Text value,Context c) throws IOException , InterruptedException
        {
            String line = value.toString();
            String[] words = line.split("\t");

            if(words[0].equals("Error"))
            {
                c.write(new Text("Error"),new Text(" : Dimension Mismatch"));
            }
            else
            {
                c.write(new Text(words[0]),new Text(words[1]));  
            }
               
        }
}