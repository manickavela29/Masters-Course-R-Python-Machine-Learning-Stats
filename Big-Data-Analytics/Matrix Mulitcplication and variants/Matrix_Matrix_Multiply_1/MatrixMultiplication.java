import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MatrixMultiplication 
{
    public static void main(String[] args) throws Exception
    {
        Job j = Job.getInstance();
        j.setJarByClass(MatrixMultiplication.class);
        j.setJobName("Matrix Multiplication");
        j.setMapperClass(MapMatrixMulti.class);
        j.setReducerClass(ReduceMatrixMulti.class);
        j.setOutputKeyClass(Text.class);
        j.setOutputValueClass(Text.class);
        //j.setNumReduceTasks(0);
        FileInputFormat.setInputPaths(j,new Path(args[0]));
        FileOutputFormat.setOutputPath(j,new Path(args[1]));
        j.waitForCompletion(true);   

        Job j2 = Job.getInstance();
        j2.setJarByClass(MatrixMultiplication.class);
        j2.setJobName("Relational Algebra 2");
        j2.setMapperClass(MapMatrixMulti1.class);
        j2.setMapOutputKeyClass(Text.class);
        j2.setMapOutputValueClass(Text.class);
        
        j2.setReducerClass(ReduceMatrixMulti1.class);
        j2.setOutputKeyClass(Text.class);
        j2.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(j2,new Path(args[1]));
        FileOutputFormat.setOutputPath(j2,new Path(args[2]));
        j2.waitForCompletion(true);  
    }
}