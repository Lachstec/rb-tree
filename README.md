# Red Black Tree
Exercise where a [red-black tree](https://de.wikipedia.org/wiki/Rot-Schwarz-Baum) is implemented and insertions are visualized utilizing the
[graphviz](https://graphviz.org/) software.

## Rules for RB-Trees
1. Every Node is *either* red or black
2. The root of the tree is *black*
3. All null-leafs are black
4. A red node must not have red children
5. All paths from a node to a leaf contain the same amount of black nodes

## Goal of this program
This program inserts 15 randomly generated values into a red black tree and generates
a graphviz dotfile for each insertion, saving it into the `output` directory. The dotfiles
can then be visualized by converting them to a svg using a command like:
```bash
dot -Tsvg "output/rbtree-0.dot" > "svgs/rbtree-0.svg"
```
The resulting SVGs can then be turned into a PDF, thus visualizing every step of insertion into the
red black tree.

## Getting started
Make sure that you have [graphviz](https://graphviz.org/) and the `rsvg-convert` command available.


To run the program with maven, run
```bash
mvn clean compile exec:java
```
inside the project directory. You can then use the script `genPDF.sh` to generate SVGs and put them
into a PDF file.
