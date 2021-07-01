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
            List<String> A = new ArrayList<>();
            List<String> B = new ArrayList<>();

            for(Text val:values)
            {
                String line = val.toString();
                String[] rel = line.split(",");
                if(rel[0].equals("A"))
                {
                    A.add(val.toString());
                }
                if(rel[0].equals("B"))
                {
                    B.add(val.toString());
                }
            }
            Integer multi = 0;

            for(int i = 0 ; i < A.size() ; i++)
            {
                String[] ElementA = A.get(i).split(",");

                for(int j = 0; j < B.size() ; j++)
                {
                    String[] ElementB = B.get(j).split(",");
                    multi = Integer.parseInt(ElementA[2])*Integer.parseInt(ElementB[2]);  
                    c.write(new Text(ElementA[1]+","+ElementB[1]) , new Text(multi.toString()));
                }
            }
        }
        
        
    }
}   