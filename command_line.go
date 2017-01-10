package main

import (
	"bufio"
	"fmt"
	"io"
	"strconv"
	"strings"
)

func startCommandLine(index InvertedIndex, in io.Reader, out io.Writer) {
	reader := bufio.NewReader(in)
	var currentWord string
	var currentOccurrence WordOccurrence = WordOccurrence{documentNumber: -1, wordNumber: -1}
	for {
		fmt.Fprintf(out, "indexer_ $ ")
		command, _ := reader.ReadString('\n')
		command = strings.TrimRight(command, "\n")
		tokenizedCommand := strings.FieldsFunc(command, func(r rune) bool {
			return r == ' ' || r == ':'
		})
		if len(tokenizedCommand) != 1 && len(tokenizedCommand) != 3 {
			fmt.Fprintf(out, "Wrong command supplied")
			continue
		}
		if tokenizedCommand[0] == "!" {
			break
		}
		if (currentWord == "") && (tokenizedCommand[0] == ">" || tokenizedCommand[0] == "<") {
			fmt.Fprintln(out, "You must provide word to search")
			continue
		}
		if command == ">" {
			result := index.SearchForward(currentWord, currentOccurrence)
			fmt.Fprintln(out, currentWord, result)
			currentOccurrence = result
		} else if command == "<" {
			result := index.SearchBackward(currentWord, currentOccurrence)
			fmt.Fprintln(out, currentWord, result)
			currentOccurrence = result
		} else {
			currentWord = tokenizedCommand[0]
			if len(tokenizedCommand) == 3 {
				documentNumber, err := strconv.Atoi(tokenizedCommand[1])
				wordNumber, err := strconv.Atoi(tokenizedCommand[2])
				if err != nil {
					fmt.Fprintln(out, "Supply command in format word doc_num:word_num")
				}
				currentOccurrence = WordOccurrence{documentNumber: documentNumber, wordNumber: wordNumber}
			} else {
				currentOccurrence = WordOccurrence{documentNumber: -1, wordNumber: -1}
			}
			currentOccurrence = index.SearchForward(currentWord, currentOccurrence)
			fmt.Fprintln(out, currentWord, currentOccurrence)
		}
	}
}
