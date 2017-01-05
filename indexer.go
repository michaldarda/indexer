package main

import "fmt"

type WordOccurrence struct {
	documentNumber int
	wordNumber     int
}
type InvertedIndex map[string][]WordOccurrence

func (index InvertedIndex) Add(word string, documentNumber int, wordNumber int) {
	if index[word] != nil {
		index[word] = append(index[word], WordOccurrence{documentNumber: documentNumber, wordNumber: wordNumber})
		return
	}
	index[word] = []WordOccurrence{WordOccurrence{documentNumber: documentNumber, wordNumber: wordNumber}}
}

func (index InvertedIndex) PrettyPrint() {
	for word, occurrences := range index {
		fmt.Print(word)
		fmt.Print(" {")

		for _, occurence := range occurrences {
			fmt.Printf("(%d, %d),", occurence.documentNumber, occurence.wordNumber)
		}
		fmt.Print("}")

		fmt.Println("")
	}
}
