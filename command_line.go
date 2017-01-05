package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strings"
)

func startCommandLine(index InvertedIndex, device io.Reader) {
	var currentWord string
	var currentOccurrence WordOccurrence = WordOccurrence{documentNumber: -1, wordNumber: -1}

	reader := bufio.NewReader(device)
	for {
		fmt.Print("> ")
		text, _ := reader.ReadString('\n')
		if text == "!\n" {
			os.Exit(0)
		}
		if (currentWord == "") && (text == ">\n" || text == "<\n") {
			fmt.Println("You must provide word to search")
			continue
		}

		if text == ">\n" {
			result := index.SearchForward(currentWord, currentOccurrence)

			fmt.Println(currentWord, result)
			currentOccurrence = result
		} else if text == "<\n" {
			result := index.SearchBackward(currentWord, currentOccurrence)

			fmt.Println(currentWord, result)
			currentOccurrence = result
		} else {
			currentWord = strings.TrimRight(text, "\n")
			currentOccurrence = index.SearchForward(currentWord, currentOccurrence)

			fmt.Println(currentWord, currentOccurrence)
		}
	}
}
