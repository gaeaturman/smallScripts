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

##this is the pseudocode 2 version of the getGenes.sh script. To "run", remove the "_PSEUDO2" from the .sh file name and...
##call from the command line accordingly. This is the "medium difficulty" version of the script pseudocode, which gives...
## ...you a fully realized psuedocode to work with for writing your script. It takes the logic construction out and makes...
## ...it so you're trying to focus on correct syntax and finding an appropriate tool to execute each command.
##similar to pseudocode 1, this won't actually run properly unless you fix the syntax issues and put in actual commands where...
## ...there's currently just pseudocode.

##BASIC SCRIPT FRAMEWORK:

##STEP(1) GFF Refseq to conventional chromosome naming conversions, do a "find and replace" on the given input file...
## ...to change to desired chromosome designations

some_func_FINDnREPLACEallInstances "NC_001133.9" with "1" in $GFFfile #how can I find and replace a string in a file in bash?
some_func_FINDnREPLACEallInstances "NC_001134.8" with "2" in $GFFfile

##and so on,
##could alternatively make a list, for i in list, do replace, but it doesn't matter and I thought the sequential individual...
# ...replace calls would be more clear for you logic-wise



##STEP(2) Read gff line by line to do checks for correct chrom, feature type, and gene boundaries
##first do a check to see if line is a comment (or clip)

While GFFfile has lines ##how do I read a file line-by-line in bash?
get line and do:
    lineInfo=$line ##how do I set the value of a line to a variable in bash?

    startsWith=firstLetterofLineInfo ##how do I get the first letter of a string using bash?

    if [[ "$startsWith" doesNotEqual "#" ]] ##how do I check if two strings are equal in bash?
    then

        ##then the line is NOT a comment, and we can continue our checks

        lineChrom=getItemAtIndex1ofLineInfo ##how do I get the first item in a tab seperated string in bash?

        ##check to see if correct chromosome

        if [[ "$lineChrom" equals "$chrNum" ]]
        then

            ##then the chromosome IS the one we want it to be and we move on to checking the feature type

            featureType=getItemAtIndexofFeatureType

            if [ "$featureType" equals "gene" ]
            then

                ##then the feature IS a gene and we move on to checking for gene start or end within bounds of search
                ##think about this from a logic standpoint, on a very simple level what are you trying to determine?
                ##By this I mean, think about what it means for something to be within a set of bounds. Coding involves...
                ## ...a lot of breaking things down into really simple pieces, so ideally you WOULDN'T google
                ## "how to determine if number is between bounds in bash". Just think about it for a bit
                geneStart=getItemAtIndexofGeneStart
                geneEnd=getItemAtIndexofGeneEnd

                if [[ ("$geneStart" withinBounds ) || ("$geneEnd" withinBounds ) ]]
                then

                    ##then this is info we want and we append it to the outfile
                    echo "$lineInfo" >> "$outFile"
                fi
            fi
        fi
    fi
done

printf "done-zo\n"
exit

##notice that in this pseudocode version the syntax structure is a lot closer to the real deal. Typically this is what we...
## ...aim for when writing pseudocode, to have variables, loops, and functions loosely established in a way that takes into...
## ...account syntax to a reasonable degree. Ideally you would also have more functions in here but...
## ...I'm leaving things purposely ambiguous for learning reasons.
