import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce2ForRA extends Reducer<Text,Text,Text,Text>
{
    public void reduce(Text key, Iterable<Text> values , Context c) throws IOException , InterruptedException
    {
        //Set<String> Myset = new HashSet<String>();
        
        ArrayList<String> MyFriends = new ArrayList<String>();
        ArrayList<String> Temp = new ArrayList<String>();
        
        for(Text val:values)
        {   
            Temp.add(val.toString());
            if(val.toString().substring(0,1).equals("1"))
            {
                //Second Layer friends
                String[] rel = val.toString().split(",");
                MyFriends.add(rel[1]);
            }
        }
        for(int i = 0; i < Temp.size() ; i++)
        {
            if(Temp.get(i).toString().substring(0,1).equals("1"))
            {
                continue;
            }
            else
            {
                //Primary Friends
                String t = Temp.get(i).toString();
                String[] rel = t.split("/");
                String str = rel[0];
                String store = "";
                
                for(int j = 1;j <= rel.length -1 ; j++)
                {
                    store = store +"," +rel[j];
                }
                c.write(new Text(rel[0]),new Text(String.join(",",MyFriends)+store));
            }
        }
        
    }
}