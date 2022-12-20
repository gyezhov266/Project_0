#!/bin/bash

# initialize total to 0
total=0
# initialize count to 0
count=0

# extract the lines containing the string "execution took" from the log file
latency_lines=$(grep "execution took" rollingFile.log)

# iterate through each extracted line
for line in $latency_lines; do
  # extract the latency value from the line using cut
  latency=$(echo $line | cut -d' ' -f5 | cut -d'.' -f1)
  # add the latency value to the total
  total=$((total + latency))
  # increment the count
  count=$((count + 1))
done

# calculate the average latency
average=$((total / count))

# print the average latency
echo "Average latency: $average ms"
