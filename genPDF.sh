#!/bin/bash
for i in {0..14}
do
  dot -Tsvg "output/rbtree-$i.dot" > "svgs/rbtree-$i.svg"
done
rsvg-convert -f pdf -o rbtree.pdf svgs/rbtree-?.svg svgs/rbtree-??.svg