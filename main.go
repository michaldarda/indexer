package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strings"
)

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
