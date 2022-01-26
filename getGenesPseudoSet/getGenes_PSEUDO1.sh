#!/bin/bash

##takes chromosome number and genomic interval and returns list of gene features within given interval

##arguments = 1) peak chromosome number (seqname) 2) peak start coordinate  3) peak end coordinate  4) GFF file

##in GFF, if attribute has start or end coordinate within peak start and end coordinates AND attribute is a gene, print line



##check for args to prevent script screaming (inaudibly)
if [ $# -ne 5 ]
  then
    echo "Usage: getGenes.sh Chr# peakStartCoord peakEndCoord GFFfile OutFile"
    exit
fi

chrNum=$1
peakStart=$2
peakEnd=$3
GFFfile=$4
outFile=$5

##convert chromosomes from refseq style to human interpretable format
##set refseq chroms to chrom variables

Chr1='NC_001133.9'
Chr2='NC_001134.8'
Chr3='NC_001135.5'
Chr4='NC_001136.10'
Chr5='NC_001137.3'
Chr6='NC_001138.5'
Chr7='NC_001139.9'
Chr8='NC_001140.6'
Chr9='NC_001141.2'
Chr10='NC_001142.9'
Chr11='NC_001143.9'
Chr12='NC_001144.5'
Chr13='NC_001145.3'
Chr14='NC_001146.8'
Chr15='NC_001147.6'
Chr16='NC_001148.4'

##this is the pseudocode 1 version of the getGenes.sh script. To "run", remove the "_PSEUDO1" from the .sh file name and...
##call from the command line accordingly. This is the "high difficulty" version of the script pseudocode, which really...
##just provides a more detailed framework for you and prompts you to try and build your own logic structure on a more...
##detailed level. Which is to say, take this:
##
##      "If Chr in GFF file = input Chr
##      AND the feature start coordinate or end coordinate are within the input coordinate interval,
##      AND the feature is a gene, print line to file."
##
## ...and try to make it more like actual pseudocode, e.g.:
## variableCurrentLineInfo = infoFromCurrentLineInFile
## variableChrom = variableCurrentLineInfo[index of chrom item]
## if [ variableChrom == userDesignatedChrom]
## then do
## _______________
## else do
## _______________
## fi *(closes "if")
##
## (Note, this is not fully realized//correct syntax above, but it's more realistic pseudocode...
## ...writing out stuff like this will help you garner a greater understanding of coding logic, and help...
## ...you in trying to determine, for example, what kind of variables you'll need to store. Notice that...
## ...above I have a loose variable declaration in my pseudocode. Writing out this kind of thing will elucidate...
## ...what kinds of questions you need to ask to make progress on your script, e.g. "how do I get the first item in...
## ...a tab seperated string?", and preceeding that "how do I set the current line in a file to a variable?"
##
##notice that I put "run" in quotes above because executing this scipt as is will simply yell at you if you fail to provide...
## ...the correct number of arguments. Below is a basic logic framework for writing up more detailed pseudocode.


##Also just note that there's a lot of possible options for ways to write up a script like this, like when you're dealing with...
## ...the refseq format chrom designations for example. You can do:
##either in file overall, OR for each line in file, find and replace refseq reference with chrom number
##think about if you know of any tools or functions that can do a find and replace in a file, and then think about how you might utilize that
## e.g. do you want to do an overall edit on your file and change all refseq references to normal chromosome numbers before dealing with your...
## ...other "by-line" checks? Or do you want to just check as you go? (there's not a true "right" answer here, just more a prompt to help you think about...
## ...how you want to write up your script.

##BASIC SCRIPT FRAMEWORK:
##convert all refseq chrom strings to "chr_" style, either in original file or in a new file from the original
##once converted, start doing checks. What kinds of scenarios might you run into that would break your script?
##for example, if you're looking at a comment at the top of your gff and you're trying to get the i-th tab-delimited item, is your script going to break?
##think about those kinds of things, although not entirely necessary for a single use script of this ease//nature, it's a good exercise to consider...
## ...limitations in what we code. Lots of options exist for handling top of file comments, you can simply clip them from your file, skip the lines if they...
## ...start with a comment, etc.
## assuming you're now only dealing with true//expected gff file lines, what checks do you need to do?
## - check if you're looking at the correct chromosome
## - check if you're looking at a gene feature
## - check if the gene start OR end is within the user specified range
## what order should you do these checks in? Again, there's not really a wrong answer, however it would make more sense to start with checking for...
## ...the desired chromosome or gene feature before doing the start/end range checks
## nested "if" conditional statements to find the lines you want, once you have those lines append to outFile
