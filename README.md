#Masters : Data Science and Computing
#Contains all the relevant course works and assignments together
## Big-Data-Analytics
This repository contains all the Assignments and Lab work that were assigned by V Bhaskaran(https://www.sssihl.edu.in/faculty/v-bhaskaran/).
Each directory has its own Text file(.txt) decribing the problem in details along with the necessary files.

Problems where solved using the Hadoop MapReduce Framework in Java , necessary data associated is stored in hdfs.

Questions Overview
------------------
1) Matrix Matrix Mulitplication with different conditions and formats
2) Word count of document in different forms
3) Relational Algebra 
    A Database of F(User,Friend) that indicates the friendship.
    A record P1,P2 indicates that P2 is a friend of P1, but it need not mean P1 is a friend of P2.
    Associated Questions 
    
    Write individual MapReduce programs to find the following and store it in n.txt in hdfs, where n is q.no:
    1. List the friends of B1
    2. List the friends of B*
    3. List persons who have C* as their friend.
    4. List persons who have both C* and A* as their friend.
    5. List persons who have either A* or B* as their friend.
    6. List all P1,P2 such that P2 is friend of P1 but P1 is not a friend of P2.
    7. List all P1,P2 such that P2 is friend of P1 and P1 is also a friend of P2.
    8. Find the person having maximum number of friends.
    9. Find the person who is friend to maximum persons.
    10. Find popularity rank of each person. Popularity rank(p) = No. of (Friends(Friends(p))
    11. Find the most popular person based on their popularity rank.

LAB TEST 1 & 2 Question
-------------------
A tool for testing internet bandwidth outputs the following into a text file for each hour:
Hour, Bandwidth in MB/s
0,100.34
1,123.89
.
.
.
22,96.77
23,49.28
The numbers 0-23 indicate the hour of the day in 24-hour format.

Write MapReduce code to find the following:
1. Best Quarter of the day. Q1: 0-5, Q2: 6-11, Q3: 12-17, Q4: 18-23. Best Quarter is the one that had the highest average bandwidth. [10M]
2. The hour of the day that had the best increase and the worst fall compared to the previous hour. [15M]
For any of the above, if there are multiple answers, output them all.

Constraint: Should NOT use setup() method and should NOT write code to read any file in Map or Reduce.
Provide two separate folders for parts 1 and 2.
Zip the two folders into <regno>.zip and submit the zip file.




    
