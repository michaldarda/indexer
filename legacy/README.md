Indexer
=======

Authors
-------
+ Michał Darda <michaldarda1@gmail.com>
+ Radek Starczynowski <radek.starczynowski@gmail.com>

About
-----
Program builds index of files, using later this index to quickly find and jump over word occurrences in that documents.

Usage
-----

> java -jar Indexer.jar [files] [-b command.txt] [-o output]

`-b` - file with command
`-o` - program output

the `-b` and `-o` commands are optional

Program can work in two basic modes:

+ interactive - you just get an interactive shell which you can put commands in real time

+ script files - you put a file with command (each command at separate line) and specify an output file

Available commands
------------------------------
These commands work both for interactive and script mode

+ `word` - you just specify a word and it will find its first occurrence
+ `word x:xxx` - you specify a word and place where to start searching in format `doc_num:word_num`
+ `> x:xxx` - search last word, from `doc_num:word_num` forward
+ `< x:xxx` - search last word, from `doc_num:word_num` backward
+ `!` - quit program

Each commands output is in format:

`word x:xxx` - if word has been found or just `word` if word hasn't been found