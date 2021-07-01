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

public class MapMatrixMulti extends Mapper<LongWritable,Text,Text,IntWritable>
{
        static List<Integer> V = new ArrayList<>();

        protected void setup(Context C) throws IOException,InterruptedException
        {
            Path pt=new Path("hdfs:/tmp/V.txt");//Location of file in HDFS
            FileSystem fs = FileSystem.get(new Configuration());

            BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
            String line;
            line=br.readLine();
            while (line != null)
            {
                System.out.println(V);
                V.add(Integer.parseInt(line.split(",")[1]));
                line=br.readLine();
            }

        }
        
        public void map(LongWritable key,Text value,Context c) throws IOException , InterruptedException
        {
            String[] element = value.toString().split(",");
            Integer i = Integer.parseInt(element[1]) - 1;
            Integer mult = Integer.parseInt(element[2])*V.get(i);

            c.write(new Text(element[0]),new IntWritable(mult));
        }
}