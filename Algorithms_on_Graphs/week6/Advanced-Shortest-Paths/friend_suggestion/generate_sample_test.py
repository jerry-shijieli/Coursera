#!/usr/bin/
import sys
import os

filename = raw_input("Please enter the filename of *.graph file:\n")
print "You entered", filename

if (os.path.exists(filename)):
    fin = open(filename, 'r')
    fout = open("sample_test.txt", 'w')
    nodeIndex = 0
    for line in fin.readlines():
        if (nodeIndex==0):
            param = line.strip().split()
            fout.write(param[0]+" "+str(int(param[1])*2)+'\n')
            nodeIndex += 1
            continue
        if (len(line)!=0):
            for neighbor in line.strip().split():
                if neighbor.isdigit():
                    fout.write(str(nodeIndex)+" "+neighbor+" 1\n")
        nodeIndex += 1
    fout.close()
    fin.close()
    print "Done!"
else:
    print "File not exist!"