#!/bin/bash

[ -f memory.csv ] && rm memory.csv
[ -f time.csv ] && rm time.csv
[ -d out ] && rm -f out/*

algos=("merge" "heap" "bubble" "insertion" "selection" "quick" "library" "timsort" "cocktail" "comb" "tournament" "intro")
sizes=("1k" "10k" "100k" "1m")

for size in "${sizes[@]}"; do
  for algo in "${algos[@]}"; do
    echo "Running: java Test $algo $size"
    java Test "$algo" "$size"
  done
done