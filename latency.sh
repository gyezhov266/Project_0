#!/bin/bash

# Set the input file
input_file="staticLogs.log"

# Set the output file
output_file="latency.txt"

# Initialize the latency sum and count to 0
latency_sum=0
latency_count=0

# Read each line of the input file
while read -r line; do
  # Check if the line starts with "Response: [200 OK]"
  if [[ $line =~ ^Response:.*200.* ]]; then
    # Extract the latency value (in ms) from the line
    latency=$(echo $line | sed -E 's/.* execution took ([0-9]*\.[0-9]*) ms.*/\1/')
    # Add the latency value to the sum
    latency_sum=$(echo "$latency_sum + $latency" | bc -l)
    # Increment the latency count
    ((latency_count++))
  fi
done < "$input_file"

# Calculate the average latency
average_latency=$(echo "$latency_sum / $latency_count" | bc -l)

# Round the average latency to 2 decimal places
average_latency=$(printf "%.2f" $average_latency)

# Write the average latency to the output file
echo $average_latency > $output_file

# Print the average latency
echo "Average latency: $average_latency ms"
