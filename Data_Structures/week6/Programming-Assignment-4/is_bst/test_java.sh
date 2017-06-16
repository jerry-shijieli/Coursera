#!/bin/bash
PROGRAM=is_bst
SAMPLE=$1
echo "Compile $PROGRAM.java:"
javac -encoding UTF-8 $PROGRAM.java
echo "Done!"
echo
echo "Run $PROGRAM on $SAMPLE:"
java -Xmx1024m $PROGRAM < $SAMPLE
echo
