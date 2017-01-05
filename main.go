package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strings"
)

func readFilesAndBuildIndex(fileNames []string) InvertedIndex {
	index := InvertedIndex{}
	for fileNumber, fileName := range fileNames {
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
		for wordNumber, word := range tokenizedFileContents {
			index.Add(word, fileNumber, wordNumber)
		}
	}
	return index
}

func main() {
	argsCount := len(os.Args)
	if argsCount < 2 {
		fmt.Fprintf(os.Stderr, "usage: %s [files]\n", os.Args[0])
		os.Exit(2)
	}

	index := readFilesAndBuildIndex(os.Args[1:len(os.Args)])
	index.PrettyPrint()
}
