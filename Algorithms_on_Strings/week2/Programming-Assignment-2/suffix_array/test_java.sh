#!/bin/bash
# for f in *.java
# do
#     CODE=$f
# done
# PROGRAM=${CODE%.*}
PROGRAM=SuffixArray
SAMPLE=$1
echo "Compile $PROGRAM.java:"
javac -encoding UTF-8 $PROGRAM.java
echo "Done!"
echo
echo "Run $PROGRAM on $SAMPLE:"
java -Xmx1024m $PROGRAM < $SAMPLE
echo
