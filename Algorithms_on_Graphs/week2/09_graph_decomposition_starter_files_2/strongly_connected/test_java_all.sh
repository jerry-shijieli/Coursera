#!/bin/bash
# for f in *.java
# do
#     CODE=$f
# done
# PROGRAM=${CODE%.*}
PROGRAM=StronglyConnected
echo ">>> Compile $PROGRAM.java:"
javac -encoding UTF-8 $PROGRAM.java
echo 

for f in sample*
do
    echo ">>> Run $PROGRAM on $f:"
    java -Xmx1024m $PROGRAM < $f
done
