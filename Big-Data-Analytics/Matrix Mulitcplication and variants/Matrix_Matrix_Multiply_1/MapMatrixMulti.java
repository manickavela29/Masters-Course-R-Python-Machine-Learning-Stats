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

public class MapMatrixMulti extends Mapper<LongWritable,Text,Text,Text>
{       
        static Boolean flag = false;
        //Done under the assumption that the matrix is properly stored in the M.txt without any error
        //With this method , multplication will only happen if it is possible to do so
        protected void setup(Context C) throws IOException,InterruptedException
        {
            Path pt=new Path("hdfs:/MMmulti_1/M.txt");  //Location of file in HDFS
            FileSystem fs = FileSystem.get(new Configuration());

            BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
            String line;
            line=br.readLine();

            Integer i = 0 , imax = 0 , j = 0 , jmax = 0;;
            while (line != null)
            {
                if(line.split(",")[0].equals("A")) //Integer.parseInt(line.split(",")[1])
                {
                    i = Integer.parseInt(line.split(",")[2]);
                    if(imax < i)
                    {
                        imax = i;
                    }
                }
                else if(line.split(",")[0].equals("B")) //Integer.parseInt(line.split(",")[1])
                {
                    j = Integer.parseInt(line.split(",")[1]);
                    if(jmax < j)
                    {
                        jmax = j;
                    }
                }
                line=br.readLine();
            }
            if(imax != jmax)
            {
                flag = false;
            }
            else
            {
                flag = true;
            }

        }
        public void map(LongWritable key,Text value,Context c) throws IOException , InterruptedException
        {
            if(flag)
            {
                String[] element = value.toString().split(",");
                if(element[0].equals("A"))
                {
                    c.write(new Text(element[2]),new Text(element[0] + "," + element[1] + "," + element[3]));
                }
                else if(element[0].equals("B"))
                {
                    c.write(new Text(element[1]),new Text(element[0] + "," + element[2] + "," + element[3]));
                }
            }
            else
            {
                c.write(new Text("Error"),new Text(" : Dimension Mismatch"));
            }
        }
}