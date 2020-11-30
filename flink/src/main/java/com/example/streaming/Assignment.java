package com.example.streaming;


import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.tuple.Tuple8;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;


import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Assignment {

    public static void main(String args){
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> data = env.readTextFile("/home/dev/flinkexamples/flink/src/main/resources/cabflink");

        //cab id, cab number plate, cab type, cab driver name, ongoing trip/not, pickup location, destination,passenger count
        // tuple  [June,Category5,Bat,12,1]
        DataStream<Tuple8<String, String, String, String, String, String, String, Integer>> mapped = data.map(new Splitter());


    }

    public static class Splitter implements MapFunction<String, Tuple8<String, String, String, Integer, Integer>>
    {
        public Tuple8<String, String, String, String, String, String, String, Integer> map(String value)         // 01-06-2018,June,Category5,Bat,12
        {
            String[] words = value.split(",");                             // words = [{01-06-2018},{June},{Category5},{Bat}.{12}
            // ignore timestamp, we don't need it for any calculations
            return new Tuple8<String, String, String, String, String, String, String, Integer>(words[1], words[2],	words[3], Integer.parseInt(words[4]), 1);
        }                                                            //    June    Category5      Bat                      12
    }
}
