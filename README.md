# ppProject

Description: We'll build an application that takes the output files of three Copy Number Value(CNV) caller tools - readDepth, SVdetect and CNVnator(this one can be optional), and, based on some well defined rules, we'll combine those files in order to have a better prediction of the CNVs. Also if the user has a file which contains a reference genome, the app can also merge the output with the reference genome file.

NOTE: The folders "Input Files" and "Output Files" does not exist on this repository because the files required exceeds 100MB; also the output files may exceed 100MB. We'll transfer these files through wetransfer. 

Team Members: - Remus Barbulescu
              - Filip Voicu
              
Members tasks: - Remus Barbulescu - Write the java/scala code required for the application to work;
	       - Filip Voicu - Write the java/scala code that will combine the output file of MetaCNV with the Reference Genome GRCh38.84;
	       - Remus & Filip - code the interface

Draft on the deliverables: Week 1: Most of the java code (without the interface)
			   Week 2: Interface coding + scala
