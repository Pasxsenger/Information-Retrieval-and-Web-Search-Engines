import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

public class BigramIndex {
    public static class InvertedIndexMapper extends Mapper<Object, Text, Text, Text> {
        private Text word = new Text();
        private Text document_ID = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            HashSet<String> bigrams = new HashSet<>();
            bigrams.add("computer science");
            bigrams.add("information retrieval");
            bigrams.add("power politics");
            bigrams.add("los angeles");
            bigrams.add("bruce willis");

            String[] doc_text_arr = value.toString().split("\t",2);
            document_ID.set(doc_text_arr[0]);

            String word_text = doc_text_arr[1].toLowerCase();
            word_text = word_text.replaceAll("[^a-zA-Z]+", " ");

            StringTokenizer itr = new StringTokenizer(word_text);
            String first_word = itr.nextToken();
            while (itr.hasMoreTokens()) {
                String second_word = itr.nextToken();
                String bigram = first_word + " " + second_word;
                if(bigrams.contains(bigram)){
                    word.set(bigram);
                    context.write(word, document_ID);
                }
                first_word = second_word;
            }
        }
    }

    public static class InvertedIndexReducer extends Reducer<Text,Text,Text,Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            HashMap<String, Integer> docID_count = new HashMap<>();
            for(Text doc_ID: values){
                String id = doc_ID.toString();
                docID_count.put(id, docID_count.getOrDefault(id, 0) + 1);
            }

            StringBuilder result = new StringBuilder();
            for(Map.Entry<String, Integer> entry : docID_count.entrySet()) {
                if(result.length() > 0) {
                    result.append(" ");
                }
                String add_result = entry.getKey() + ":" + entry.getValue();
                result.append(add_result);
            }
            context.write(key, new Text(String.valueOf(result)));
        }
    }
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");

        job.setJarByClass(UnigramIndex.class);
        job.setMapperClass(InvertedIndexMapper.class);
        job.setReducerClass(InvertedIndexReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
