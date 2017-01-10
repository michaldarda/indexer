package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strconv"
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
			// move up
			command := strings.TrimRight(text, "\n")
			tokenizedCommand := strings.FieldsFunc(command, func(r rune) bool {
				return r == ' ' || r == ':'
			})

			if len(tokenizedCommand) != 1 && len(tokenizedCommand) != 3 {
				fmt.Println("Wrong command", len(tokenizedCommand))
				continue
			}

			currentWord = tokenizedCommand[0]
			if len(tokenizedCommand) == 3 {
				documentNumber, err := strconv.Atoi(tokenizedCommand[1])
				if err != nil {
					fmt.Println("Supply command in format word doc_num:word_num")
				}
				wordNumber, err := strconv.Atoi(tokenizedCommand[2])
				if err != nil {
					fmt.Println("Supply command in format word doc_num:word_num")
				}

				currentOccurrence = WordOccurrence{documentNumber: documentNumber, wordNumber: wordNumber}
			} else {
				currentOccurrence = WordOccurrence{documentNumber: -1, wordNumber: -1}
			}
			fmt.Println("searching for command greater than", currentOccurrence)
			currentOccurrence = index.SearchForward(currentWord, currentOccurrence)

			fmt.Println(currentWord, currentOccurrence)
		}
	}
}
