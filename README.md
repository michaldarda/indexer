# Indexer

Builds an inverted index of given files, that can later be user to quickly navigate over word occurences in documents. **Work in progress**

Ideas:

1. This is rewrite of my original undergraduate project (which can be found in legacy/ dir, here be dragons).
2. This project should be faster than the original one written in Java
(should be fair simple since original one had serious performance problems due to lack of binary search in word occurrences).
3. Interface compatible with original version.
4. Learn and take advantage in go concurrency mechanisms during index build.

Usage
-----

    $ indexer [files] [-b command.txt] [-o output]

`-b` - file with command

`-o` - program output

Program can work in two modes:

+ interactive - after run you boot into interactive shell.
+ script files - you supply a file with command (each command at separate line) and specify an output file to save results.

Available commands
------------------------------
These commands work both for interactive and script mode

+ `word` - you just specify a word and it will find its first occurrence
+ `word x:xxx` - you specify a word and place where to start searching in format `doc_num:word_num`
+ `> x:xxx` - search last word, from `doc_num:word_num` forward
+ `< x:xxx` - search last word, from `doc_num:word_num` backward
+ `!` - quit program

Each commands responds is in format:
`word x:xxx` - if word has been found or just `word` if word hasn't been found
