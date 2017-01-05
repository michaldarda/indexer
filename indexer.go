package main

import "fmt"

type WordOccurrence struct {
	documentNumber int
	wordNumber     int
}

func (w1 WordOccurrence) Compare(w2 WordOccurrence) int {
	if w1.documentNumber > w2.documentNumber {
		return 1
	} else if w1.documentNumber == w2.documentNumber && w1.wordNumber > w2.wordNumber {
		return 1
	} else if w1.documentNumber == w2.documentNumber && w1.wordNumber == w2.wordNumber {
		return 0
	} else {
		return -1
	}
}

type InvertedIndex map[string][]WordOccurrence

func (index InvertedIndex) Add(word string, documentNumber int, wordNumber int) {
	if index[word] != nil {
		index[word] = append(index[word], WordOccurrence{documentNumber: documentNumber, wordNumber: wordNumber})
		return
	}
	index[word] = []WordOccurrence{WordOccurrence{documentNumber: documentNumber, wordNumber: wordNumber}}
}

func (index InvertedIndex) SearchForward(word string, startingPoint WordOccurrence) *WordOccurrence {
	occurrences := index[word]
	if occurrences == nil {
		return nil
	}

	_, last := BinarySearch(occurrences, startingPoint, func(w1 WordOccurrence, w2 WordOccurrence) bool {
		return (w1.Compare(w2) == 1)
	})

	if last+1 == len(occurrences) {
		return nil
	}

	return &occurrences[last+1]
}

func (index InvertedIndex) SearchBackward(word string, startingPoint WordOccurrence) *WordOccurrence {
	occurrences := index[word]
	if occurrences == nil {
		return nil
	}

	first, _ := BinarySearch(occurrences, startingPoint, func(w1 WordOccurrence, w2 WordOccurrence) bool {
		return (w1.Compare(w2) == 1 || w1.Compare(w2) == 0)
	})

	if first-1 < 0 {
		return nil
	}

	return &occurrences[first-1]
}

func BinarySearch(
	occurrences []WordOccurrence,
	startingPoint WordOccurrence,
	partitionFunction func(WordOccurrence, WordOccurrence) bool) (int, int) {

	first := 0
	last := len(occurrences) - 1
	for first <= last {
		mid := (first + last) / 2
		if partitionFunction(occurrences[mid], startingPoint) {
			last = mid - 1
		} else {
			first = mid + 1
		}
	}

	return first, last
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
