import java.io.IOException;
import java.util.*;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReduceForRA extends Reducer<Text,Text,Text,Text> 
{
    List<String> MyFriends = new ArrayList<>();
    List<String> FriendTo = new ArrayList<>();

    public void reduce(Text word , Iterable<Text> values ,Context c) 
    throws IOException, InterruptedException   
    {
        MyFriends = new ArrayList<>();
        FriendTo = new ArrayList<>();
        
        //The loop divides the incoming data into two distinct lists as
        //To whom "word"(user name) is friend -> MyFriends
        //Who consider "word" as thier friend
        for(Text val:values)
        {
            String line = val.toString();
            String[] rel = line.split(",");
            if(rel[0].equals("1"))
            {
                MyFriends.add(rel[1]);
            }
            if(rel[0].equals("2"))
            {
                FriendTo.add(rel[1]);
            }
        }


        //P1,P2     P2 is a friend of P1 but P1 is not friend of P2
        //For a pair to be selected it must exist in MyFriends list but it must not exist in FriendsTo list
        for(int i = 0; i < MyFriends.size(); i++)
        {   
            Boolean flag = false;
            String line = MyFriends.get(i) , buddy = "";
            for(int j = 0; j < FriendTo.size() ; j++)
            {
                buddy = FriendTo.get(j);
                if(line.equals(buddy))
                {
                    flag = true;
                    break;
                }
            }

            if(flag == false)
            {
                c.write(word,new Text(MyFriends.get(i)));
            }
        }
    }
}       