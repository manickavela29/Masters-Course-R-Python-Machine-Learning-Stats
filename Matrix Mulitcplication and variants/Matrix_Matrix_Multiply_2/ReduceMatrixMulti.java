import java.io.IOException;
import java.util.Iterator;
import java.util.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



public class ReduceMatrixMulti extends  Reducer<Text,Text,Text,Text> 
{
    public void reduce(Text index , Iterable<Text> values ,Context c) 
    throws IOException , InterruptedException
    {
        if(index.toString().equals("Error"))
        {
            c.write(new Text("Error"),new Text(" : Dimension Mismatch"));
        }
        else
        {
            Map A = new HashMap();
            Map B = new HashMap();


            for(Text val:values)
            {
                String line = val.toString();
                String[] rel = line.split(",");
                if(rel[0].equals("A"))
                {
                    A.put(rel[1],rel[2]);
                }
                if(rel[0].equals("B"))
                {
                    B.put(rel[1],rel[2]);
                }
            }

            Integer sum = 0;
            for(Integer i = 1 ; i <= A.size() ; i++)
            {
                String a = A.get(i.toString()).toString();
                String b = B.get(i.toString()).toString();
                sum = sum + Integer.parseInt(a)*Integer.parseInt(b);  



            }

            c.write(index , new Text(sum.toString()));
        }
        
        
    }
}   