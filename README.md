# Huffman-Coding-Project
by Christopher Barnett

A program that encodes each character in a text file to a shorter code based on how many times the character appears in the file. This is done using Huffman's algorithm.
The newly encoded characters and the generated huffman tree are written to a smaller binary file. This file can be decoded to produce the original input file.

Encode.class

Encodes a text file using Huffman's algorithm.
 * Produces a binary file representation of the input file.
 * Optionally, output a dot graph representation of the huffman tree or canonical tree using the -h and/or -c arguments
 * Usage: java huffman.Encode [-h HUFFMAN_TREE_FILE] [-c CANONICAL_TREE_FILE] SOURCEFILE TARGETFILE
 
Decode.class
 
Decodes a text file using Huffman's algorithm.
 * Produces a text file representation of the input file.
 * Optionally, output a dot graph representation of the canonical tree using the -c argument
 * Usage: java huffman.Encode [-c CANONICAL_TREE_FILE] SOURCEFILE TARGETFILE