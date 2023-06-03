#!/bin/bash
HADOOP_CLASSPATH=`/home/manickavela/Softwares/hadoop-3.3.0/bin/hadoop classpath`;
javac -classpath ".:$HADOOP_CLASSPATH" -Xdiags:verbose $1;
