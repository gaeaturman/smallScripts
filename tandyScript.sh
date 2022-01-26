#!/bin/bash
##takes a genotyped vcf file and outputs a per chromosome file with base position, reference allele count, and alternate allele counts in a tab delimited text file


##output file contains  ##position    ##reference allele count    ##alternate allele count

##check for args to prevent script screaming (inaudibly)
if [ $# -ne 2 ]
  then
    echo "Usage: tandyScript inputFile outputDirectory"
    exit
fi

inFile=$1
outDir=$2


##change +56 to desired start line number (inclusive)
tail -n +56 $inFile | awk '{ print $1, $2, $10}' | awk '{ split($3, a, ":"); split(a[2], aa, ","); print $1, $2, aa[1], aa[2]}' > "$outDir/highpool_allChroms.txt"

currentChrom=1
lastLineInfo=$(tail -n 1 highpool_allChroms.txt | awk '{print $1, $2, $3, $4}')
output=""

cat highpool_allChroms.txt | while read line || [[ -n $line ]];
do
   lineInfo=$(awk -v var="$line" 'BEGIN {print var}' | awk '{print $1, $2, $3, $4}')
   wantedInfo=$(awk -v var="$line" 'BEGIN {print var}' | awk '{print $2, $3, $4}')
   lineChrom=$(awk -v var="$line" 'BEGIN {print var}' | awk '{print $1}')

   if [ $currentChrom -ne $lineChrom ]
     then

       outFile="highpool_Chrom_${currentChrom}.txt"
       echo "$output" > "$outDir/$outFile"

       cleanedOutput=$(tail -n +2 "$outDir/$outFile") #strip leading newline char

       echo "$cleanedOutput" > "$outDir/$outFile"

       output=""
       currentChrom=$((currentChrom+1))

     else
       if [ "$lineInfo" == "$lastLineInfo" ]
         then

           outFile="highpool_Chrom_${currentChrom}.txt"
           echo "$output" > "$outDir/$outFile"
           
           cleanedOutput=$(tail -n +2 "$outDir/$outFile") #strip leading newline char
           echo "$cleanedOutput" > "$outDir/$outFile"
         else
           output=$output$'\n'$wantedInfo
           #echo $output
       fi
   fi
done

printf "done-zo\n"
exit
