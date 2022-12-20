#!/bin/bash
# extract the lines containing the string "execution took" from the log file
latency_lines=$(grep "execution took" rollingFile.log | cut -d',' -f2 | cut -d' ' -f4 | cut -d'.' -f1)

# initialize total to 0
total=0

# initialize count to 0
count=0

# iterate through each extracted line
for line in $latency_lines
do
  # extract the latency value from the line using cut
  latency=$(echo $line)
  # add the latency value to the total
  total=$((total + latency))
  # increment the count
  count=$((count + 1))
done

# calculate the average latency
average=$(echo "scale=2; $total / $count" | bc)

# print the average latency
echo "Average latency: $average ms"
