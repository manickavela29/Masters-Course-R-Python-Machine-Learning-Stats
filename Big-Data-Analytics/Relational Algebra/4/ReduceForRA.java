import java.io.IOException;
import java.util.*;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class ReduceForRA extends Reducer<Text,Text,Text,Text> 
{
    public void reduce(Text word , Iterable<Text> values ,Context c) 
    throws IOException, InterruptedException   
    {

        Boolean flagA = false , flagB = false;
        for(Text val:values)
        {
            char ch = val.toString().charAt(0);
            if(ch == 'A')
                flagA = true;
            if(ch == 'B')
                flagB = true;
        }
        if(flagA && flagB)
        {
            c.write(word, new Text(""));
        }
        
    }
}   


















//Better method is available 
        //Map -> get people who are friends with A or C
        //Reduce check whether the list is having Both A and C