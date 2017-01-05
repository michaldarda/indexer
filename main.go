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
	for documentNumber, fileName := range fileNames {
		file, err := os.Open(fileName)
		if err != nil {
			log.Fatal(err)
		}
		defer file.Close()

		fileContent, err := ioutil.ReadAll(file)
		if err != nil {
			log.Fatal(err)
		}
		words := strings.Fields(strings.ToLower(string(fileContent)))
		for wordNumber, word := range words {
			index.Add(word, documentNumber, wordNumber)
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

	index := readFilesAndBuildIndex(os.Args[1:argsCount])
	index.PrettyPrint()
}
