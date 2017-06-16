#!/bin/bash
PROGRAM=is_bst
echo "Compile $PROGRAM.java:"
javac -encoding UTF-8 $PROGRAM.java

for f in sample*
do
	echo "Run $PROGRAM on $f:"
    java -Xmx1024m $PROGRAM < $f
done
