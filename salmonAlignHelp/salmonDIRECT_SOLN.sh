#!/bin/bash
# salmon script to process through all desired fastqs for RNAseq
# direct solution
 
#SBATCH -p general
 
#SBATCH --mem=12g
 
#SBATCH -n 4
 
#SBATCH -N 1
 
#SBATCH -t 2:00:00
 
 
salmon quant -i /proj/seq/data/salmon_RNAseq_genomes/mm10_decoy/salmon_sa_index/default -l A -1 $1 -2 $2 -p 4 --validateMappings -o $3   
 
# Example above uses Human transcriptome index with full decoys
# You will need to adjust index path if you are planning to use just transcriptome