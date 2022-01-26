#!/bin/bash
#!bin/bash

# iteration EXAMPLE guide - think about:
# (1) what you're trying to use for input 
# (2) how you could leverage what you know to get that input
# What you're needing to do is basically just string ops
# So think about:
# can you just remove x characters? Or do you need to split things up using non-length based parameters? 
# e.x. REGEX - google then text me if you have not used regular expression patterns before, good luck!
# NOTE - this is just an example where I have added files with names corresponding to a selection of the same names of your files, so you can see what grabbing different parts using length rules would look like!!

Path="/Users/gturman/Desktop/tarB_Examp/ATGGwhatever/"
FILES="/Users/gturman/Desktop/tarB_Examp/ATGGwhatever/*.txt"

for f in $FILES; do

	echo "Full file name with path is: $f"
	
	justFilename=$(basename $f)
	echo "Just the filename is: $justFilename"
	echo ""
	echo ""


	justLast=${justFilename: (-21)}
	echo "Just the last 21 chars is: $justLast"
	

	justWhatWant=${justLast: 1:20}.txt
	echo "Just what's wanted is: $justWhatWant"

	# Notice that Path is set above with FILES
	echo "Path is: $Path"

	newFullName=$Path$justWhatWant
	echo "New filename with full path is: $newFullName"

	echo ""

done