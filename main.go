package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strings"
)

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

func main() {
	argsCount := len(os.Args)
	if argsCount < 2 {
		fmt.Fprintf(os.Stderr, "usage: %s [files]\n", os.Args[0])
		os.Exit(2)
	}

	index := InvertedIndex{}
	for argNumber, fileName := range os.Args {
		if argNumber == 0 { // skip the program name
			continue
		}

		file, err := os.Open(fileName)
		if err != nil {
			log.Fatal(err)
		}
		defer file.Close()

		b, err := ioutil.ReadAll(file)
		if err != nil {
			log.Fatal(err)
		}
		tokenizedFileContents := strings.Fields(strings.ToLower(string(b)))
		fileNumber := argNumber - 1
		for wordNumber, word := range tokenizedFileContents {
			index.Add(word, fileNumber, wordNumber)
		}
	}

	index.PrettyPrint()
}
