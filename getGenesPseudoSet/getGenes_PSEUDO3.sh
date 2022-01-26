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

##this is the pseudocode 3 version of the getGenes.sh script. To "run", remove the "_PSEUDO3" from the .sh file name and...
##call from the command line accordingly. This is the "easy-ish difficulty" version of the script pseudocode, which gives...
## ...you a fully realized psuedocode to work with for writing your script and correct syntax in a lot of places plus function...
## ...recommendations to help you find something you could use at each step in your script.
##similar to pseudocodes 1 and 2, this won't actually run properly unless you fix the syntax issues and put in actual commands where...
## ...there's currently just pseudocode. However it's very close to being the completed script!

##BASIC SCRIPT FRAMEWORK:

##STEP(1) GFF Refseq to conventional chromosome naming conversions, do a "find and replace" on the given input file...
## ...to change to desired chromosome designations

##GFF Refseq to conventional chromosome naming conversions
##possible option - use "sed" to quick find and replace
##sed -i changes the original file, so for example:
 sed -i '' 's/toast/bread/' $aFile

##would change toast to bread in $aFile and save that change in the original file
##look up how to change every instance of something in a file, as the syntax provided doesn't do this
##basic structure for your purposes though:
##e.x. sed -i '' 's/refseqRef/ChromNum/g' $GFFfile
##ALSO! note that following the sed options there's a blank set of single brackets - this is a MacOSx thing,
##it's a syntax specification that I don't believe linux requires for sed. Basically Mac makes you provide a file...
##extention for the backup sed file. If you run into a syntax error that you don't think is your fault here, try deleting those
##I could double check if this actually reads as a syntax error on Windows/Linux, but it'll be better for you learning-wise
##to potentially have to do some investigation of sed syntax regardless

sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile
sed -i '' 's///' $GFFfile

##before moving onto step 2, I personally did a wipe of the outfile (so that each new inquiry would...
##overwrite the file if it had old results in it since I chose to append results into the outfile)
##this is the kind of thing you think about when building your own logic. E.x. if I want to append my results...
## ...for each line into my file, waht things do I need to consider? Well, if there's already something in my file...
## ...because I'm just appending I might end up with stuff I don't want in there if the file already exists, so clear it

##clear output file//ensure empty
empty=""
echo "$empty" > "$outFile"



##STEP(2) Read gff line by line to do checks for correct chrom, feature type, and gene boundaries
##first do a check to see if line is a comment

##one option for reading a file in bash is to walk through it line by line until you hit the end of the file.
##the basic syntax for this is:

cat $GFFfile | while read line || [[ -n $line ]];
do

    lineInfo=$line;

    ##check to see if line is a comment
    startswith=${lineInfo:_:_} ##fill in the blanks here, and google if you're unsure how this gets you the first character in the line Str!

    if [[ "$startswith" != "#" ]]
    then

        ##use awk -v to get the information you want for lineChrom, and featureType,geneStart,geneEnd later in code
        ##look up how to use awk -v, you're piping into a second awk to actually get the thing you want into your variable
        ##there are other ways to do this in the even this feels too complex, but it'd be good to try and figure out how to...
        ## ...make this syntax work (it's good for your coding brain to have to struggle and consult the internet srry bud)
        lineChrom=$(awk -__ ________ 'BEGIN {print var}' | awk '{print $_}')

        ##check if correct chromosome
        if [[ "$lineChrom" == "$chrNum" ]]
        then

            featureType=$(awk -__ ________ 'BEGIN {print var}' | awk '{print $_}')

            ##check if correct feature type
            if [ "$featureType" == "gene" ]
            then

                geneStart=$(awk -__ ________ 'BEGIN {print var}' | awk '{print $_}')
                geneEnd=$(awk -__ ________ 'BEGIN {print var}' | awk '{print $_}')

                ##check if gene start or end within bounds
                ##I left this bit still pseudo-codey because even if you can syntactiacally figure out how to write this,
                ##you should try to logically work through the check you'd do for withinbounds. Hint: your if statement is actually...
                ## ...a nested if, if ( (geneStart is ______ && geneStart is ______ ) OR (geneEnd is ______ && geneEnd is ______) )
                if [[ ("$geneStart" withinBounds ) || ("$geneEnd" withinBounds ) ]]
                then

                    ##then it's the info we want, append to file
                    echo "$lineInfo" >> "$outFile"
                fi
            fi
        fi
    fi

    ##close ifs
done
##close do

##I always like to end my programs with some kind of exit message and give them a manual kill (e.g. exit)
printf "done-zo\n"
exit

##notice that in this pseudocode version the syntax structure is nearly run-able.
##this is sort of a mad-libs format so you can theoretically look up the correct syntax, usage, "how's this work"...
## ...for what's given and get yourself some usable code. Let me know if you want my solution code. If you think somethnig...
## ...can be more easily accomplished using a different function or method I highly encourage you explore those options.
##Good luck!
