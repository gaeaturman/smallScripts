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

temp=$(awk '$3=="gene"' $GFFfile)
echo "$temp" > "$GFFfile"

##GFF Refseq to conventional chromosome naming conversions
## sed to quick find and replace

sed -i '' 's/NC_001133\.9/1/g' $GFFfile
sed -i '' 's/NC_001134\.8/2/g' $GFFfile
sed -i '' 's/NC_001135\.5/3/g' $GFFfile
sed -i '' 's/NC_001136\.10/4/g' $GFFfile
sed -i '' 's/NC_001137\.3/5/g' $GFFfile
sed -i '' 's/NC_001138\.5/6/g' $GFFfile
sed -i '' 's/NC_001139\.9/7/g' $GFFfile
sed -i '' 's/NC_001140\.6/8/g' $GFFfile
sed -i '' 's/NC_001141\.2/9/g' $GFFfile
sed -i '' 's/NC_001142\.9/10/g' $GFFfile
sed -i '' 's/NC_001143\.9/11/g' $GFFfile
sed -i '' 's/NC_001144\.5/12/g' $GFFfile
sed -i '' 's/NC_001145\.3/13/g' $GFFfile
sed -i '' 's/NC_001146\.8/14/g' $GFFfile
sed -i '' 's/NC_001147\.6/15/g' $GFFfile
sed -i '' 's/NC_001148\.4/16/g' $GFFfile


## could make a list, for i in list, sed replace, doesn't matter
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


##clear output file//ensure empty
empty=""
echo "$empty" > "$outFile"


cat $GFFfile | while read line || [[ -n $line ]];
do

    lineInfo=$line;

    lineChrom=$(awk -v var="$line" 'BEGIN {print var}' | awk '{print $1}')

    if [[ "$lineChrom" == "$chrNum" ]]
    then
   
          geneStart=$(awk -v var="$line" 'BEGIN {print var}' | awk '{print $4}')
          geneEnd=$(awk -v var="$line" 'BEGIN {print var}' | awk '{print $5}')

          if [[ ("$geneStart" -ge "$peakStart" && "$geneStart" -le "$peakEnd") || ("$geneEnd" -ge "$peakStart" && "$geneEnd" -le "$peakEnd") ]]
          then

                echo "$lineInfo" >> "$outFile"
          fi
    fi
done

printf "done-zo\n"
exit
