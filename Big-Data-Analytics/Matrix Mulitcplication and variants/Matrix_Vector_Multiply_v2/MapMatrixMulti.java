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
        static Map V = new HashMap();
        public Boolean FlagV = new Boolean(false);

        public Map readV(int split) throws IOException,InterruptedException
        {
            V = new HashMap();
            String fl = "";

            if(split == 1) 
            {
                fl = "v1.txt";
            }
            else if(split == 2)
            {
                fl = "v2.txt";    
            }

            Path pt=new Path("hdfs:/tmp/mm/"+fl);      //Location of file in HDFS
            FileSystem fs = FileSystem.get(new Configuration());

            BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
            String line;
            line=br.readLine();
            while (line != null)
            {
                //V.add(line.split(","));
                String[] row = line.split(",");
                V.put(row[0],row[1]);
                line=br.readLine();
                //System.out.println(V.get(row[0]));
            }

            FlagV = true;

            return V;
        }
        
        public void map(LongWritable key,Text value,Context c) throws IOException , InterruptedException
        {
            String[] element = value.toString().split(",");

            if(FlagV == false)
            {
                readV(Integer.parseInt(element[0]));
            }

            Integer i = Integer.parseInt(element[1]) - 1;
            Integer j = Integer.parseInt(element[2]);
            System.out.println(V.get(j.toString()));
            Integer num = Integer.parseInt(V.get(j.toString()).toString());
            //V.get returns an object -> toString()

            Integer mult = Integer.parseInt(element[3])*num;  

            c.write(new Text(i.toString()),new IntWritable(mult));
        }
}
