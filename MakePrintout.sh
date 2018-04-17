#!/bin/bash

function markdownEscapeForCode(){
	echo "";
	echo "\`\`\`$1"
	# echo "";
}

function printFileInfo() {
	echo "";
	echo "### $1";
	markdownEscapeForCode "$2"
	cat "$filename";
	markdownEscapeForCode
}

JAVA_FILES=$(find src/ \( -name "*.java" \) )
TRUMPSCRIPT_FILES=$(find src/ -name "*.trump*")
JAR_PATH="out/artifacts/TrumpScriptPlusPlus_jar/TrumpScriptPlusPlus.jar"

# see https://stackoverflow.com/questions/13515893/set-margin-size-when-converting-from-markdown-to-pdf-with-pandoc
cat << "EOF"
---
title: TrumpScript++ Parser 
author: JJ Brown (113223831)
date: \today
geometry: margin=2cm
output: pdf_document
---
EOF

echo "My submission is as follows:"
echo "";

echo "## Output"; # includes .trump script input file
markdownEscapeForCode
java "-Dfile.encoding=UTF-8" -jar "$JAR_PATH" --sample;
markdownEscapeForCode

echo " \\pagebreak ";
echo "";
echo "## Source Code";
echo "";
for filename in $JAVA_FILES ; do
	printFileInfo "$filename" "java";
	echo " \\vspace{3cm} ";
done
