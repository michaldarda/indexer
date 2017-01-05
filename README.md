# Indexer
**Work in progress**

Builds an inverted index of given files, that can later be user to quickly navigate over word occurences in documents.

To run:

    $ ./build fixtures/big.txt

Ideas:

1. This is rewrite of my original undergraduate student project (which can be found in legacy/ dir, here be dragons).
2. This project should be faster than the original one written in Java.
(should be fair simple since original one had serious performance problems due to lack of binary search in word occurrences).
3. Interface compatible with original version.
4. Learn and take advantage in go concurrency mechanisms during index build.
5. Create a gui frontend later which can use index to search highlight words in big documents.
6. Replace golang map with self-made hash map impl, see if there are differences in perf.

Todo:

1. Implement missing commands, word x:xxx etc.
2. Reading command file.
3. Saving output to a file.
4. Benchmarks (searching time, index building time), acceptance tests, regression tests comparing the legacy version and a new one.

Links:
https://nullwords.wordpress.com/2013/04/18/inverted-indexes-inside-how-search-engines-work/

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

+ `word` - will find first occurrence of `word`
+ `word x:xxx` - will find occcurrence of `word` starting searching right after `doc_num:word_num`
+ `> x:xxx` - search last word, from `doc_num:word_num` forward
+ `< x:xxx` - search last word, from `doc_num:word_num` backward
+ `!` - quit program

Each commands output is in format:
`word x:xxx` - if word has been found or just `word` if word hasn't been found
