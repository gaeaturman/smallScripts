#!/bin/bash
# iteration script to process through all desired fastqs
# direct solution

for f1 in $(ls /proj/barc/data/sequencer_comparison_CMP_SEQ/180112_UNC17-D00216_0501_BCC1G4ANXX/*_R1_001.fastq.gz)
 
do
        f2=${f1/_R1/_R2}
 
        justLastPartName=${f1: -31}
 
         justWantedName=${justLastPartName: 0:6}"_out"
 
         f3="/pine/scr/t/a/userTAB/KelkarRNAseq/HTSF_RNAseq/UNC_17_salmon/"$justWantedName”.quant.sh”
 
        sbatch UNC22_salmon.sh $f1 $f2 $f3
done