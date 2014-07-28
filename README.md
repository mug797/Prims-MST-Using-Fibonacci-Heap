Prims-MST-Using-Fibonacci-Heap
==============================

Input can be 
1. File containing an adjacency list graph 
2. Random generated graph


Steps to Compile:
  javac -d . *.java

1. Random mode:  

Steps to run: 
  java project_final_s.MST –r n d

Runs in a random connected graph with n vertices and d% of density.
Produces a random graph that is connected with the input density and number of nodes. It first runs Prim's algorithm without fibonacci heap using direct edge comparisons. It then runs Prim's using Fibnacci heap. Output is a time comparison of both the schemes.

2. File mode: 

Steps to run: 
  java project_final_s.MST -s file-name : read the input from a file ‘file-name’ for simple scheme 
  java project_final_s.MST -f file-name : read the input from a file ‘file-name’ for fibonacci scheme

Sample file is attached. The format of the file is thus: n m // The number of vertices and edges respectively in the first line v1 v2 c1 // the edge (v1, v2) with cost c1 v2 v3 c2 // the edge (v2, v3) with cost c2 
