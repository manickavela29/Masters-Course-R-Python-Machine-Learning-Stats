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
        static Integer Vcount = new Integer(0) , max = new Integer(0);

        public void setup(Context C) throws IOException,InterruptedException
        {
            V = new HashMap();

            Path pt=new Path("hdfs:/tmp/mm/V.txt");      //Location of file in HDFS
            FileSystem fs = FileSystem.get(new Configuration());

            BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
            String line;
            line=br.readLine();
            while (line != null)
            {
                String[] row = line.split(",");
                V.put(row[0],row[1]);
                System.out.println("In setup : "+V);
                line=br.readLine();
                Vcount++;
            }
        }
        
        public void map(LongWritable key,Text value,Context c) throws IOException , InterruptedException
        {
            String[] element = value.toString().split(",");

            Integer i = Integer.parseInt(element[0]) - 1;
            Integer j = Integer.parseInt(element[1]);

            /*if( j > max)
            {
                max = j;
            }*/
                                                               // M(m,n) x V(n,1)
            if(V.get(j.toString()) == null )//|| Vcount < max) //where n(of M) could more than n(of V)
            {
                System.out.println("Got null condition trying to  index : "+j);
                c.write(new Text("Error"),new IntWritable(0));   
            }
            else
            {
                Integer num = Integer.parseInt(V.get(j.toString()).toString());
                Integer mult = Integer.parseInt(element[2])*num;  
                c.write(new Text(i.toString()+","+Vcount.toString()),new IntWritable(mult));
            }
            
        }
}
