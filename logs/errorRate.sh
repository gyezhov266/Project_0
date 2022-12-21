#!/bin/bash
# extract the lines containing the string "execution took" from the log file
responses=$(grep "execution took" rollingFile.log | cut -d',' -f1 | cut -d'[' -f2 | cut -d' ' -f1)

httpRequestTotal=0
httpFailures=0

# iterate through each extracted line
for code in $responses
do
    ((httpRequestTotal++))
    if [ $code -eq 500 ]
    then
        ((httpFailures++))
    fi
done

httpSuccess=$(($httpRequestTotal - $httpFailures))

result=$(echo "scale=2; 100 - $httpSuccess / $httpRequestTotal" | bc)

echo "HTTP success rate: $result%"
