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
        String list = "";
        ArrayList al = new ArrayList();

        for(Text val:values)
        {
            list = list +val.toString()+"/";
            al.add(val.toString());
            c.write(word,new Text("1,"+val.toString()));
        }
        list = list.substring(0,list.length() - 1);     //For removing the last extra ' , '
        for(int i = 0; i < al.size();i++)
        {
            c.write(new Text(al.get(i).toString()),new Text(word+"/"+list));
        }
    }
}   