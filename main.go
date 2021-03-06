package main

import (
	"flag"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"os"
	"strings"
	"unicode"
)

func readFilesAndBuildIndex(fileNames []string) InvertedIndex {
	index := InvertedIndex{}
	for i, fileName := range fileNames {
		documentNumber := i + 1
		file, err := os.Open(fileName)
		if err != nil {
			log.Fatal(err)
		}
		defer file.Close()

		fileContent, err := ioutil.ReadAll(file)
		if err != nil {
			log.Fatal(err)
		}
		words := strings.FieldsFunc(string(fileContent), func(r rune) bool {
			return !unicode.IsLetter(r)
		})
		for i, word := range words {
			wordNumber := i + 1
			index.Add(word, documentNumber, wordNumber)
		}
	}
	return index
}

func main() {
	commandFileName := flag.String("c", "", "file with command script")
	outputFileName := flag.String("o", "", "output file")
	debug := flag.Bool("debug", false, "debug mode")
	flag.Parse()

	var in io.Reader
	var out io.Writer

	if *commandFileName != "" {
		f, err := os.Open(*commandFileName)
		if err != nil {
			log.Fatal(err)
		}
		defer f.Close()
		in = f
	} else {
		in = os.Stdin
	}

	if *outputFileName != "" {
		f, err := os.OpenFile(*outputFileName, os.O_RDWR|os.O_APPEND|os.O_CREATE, 0666)
		if err != nil {
			log.Fatal(err)
		}
		defer f.Close()
		in = f
	} else {
		out = os.Stdout
	}

	if len(flag.Args()) < 1 {
		fmt.Fprintf(os.Stderr, "usage: %s [files]\n", os.Args[0])
		os.Exit(2)
	}

	index := readFilesAndBuildIndex(flag.Args())
	if *debug {
		index.PrettyPrint()
	}

	startCommandLine(index, in, out)
}
