package main

import (
	"fmt"
	"reflect"
	"testing"
)

const word = "word"

func TestIndexerAdd(t *testing.T) {
	index := InvertedIndex{}
	index.Add(word, 0, 0)
	index.Add(word, 1, 1)
	expected := []WordOccurrence{
		WordOccurrence{documentNumber: 0, wordNumber: 0},
		WordOccurrence{documentNumber: 1, wordNumber: 1},
	}
	actual := index[word]
	if !reflect.DeepEqual(actual, expected) {
		t.Error("Expected:", expected, "actual:", actual)
	}
}

func TestSearchForward(t *testing.T) {
	index := InvertedIndex{}
	index.Add(word, 0, 0)
	index.Add(word, 1, 1)
	index.Add(word, 1, 2)
	index.Add(word, 2, 0)
	expected := WordOccurrence{documentNumber: 1, wordNumber: 1}
	actual := index.SearchForward(word, WordOccurrence{documentNumber: 0, wordNumber: 0})
	if !reflect.DeepEqual(actual, expected) {
		t.Error("Expected:", expected, "actual:", actual)
	}
}

func TestSearchForward2(t *testing.T) {
	index := InvertedIndex{}
	index.Add(word, 0, 0)
	actual := index.SearchForward(word, WordOccurrence{documentNumber: -1, wordNumber: -1})
	if actual != (WordOccurrence{}) {
		t.Error("Expected nil", "actual:", actual)
	}
}

func TestSearchForward3(t *testing.T) {
	index := InvertedIndex{}
	index.Add(word, 0, 2)
	result2 := index.SearchForward(word, WordOccurrence{documentNumber: 0, wordNumber: 2})
	fmt.Println(result2)

}

func TestSearchBackward(t *testing.T) {
	index := InvertedIndex{}
	index.Add(word, 0, 0)
	index.Add(word, 1, 1)
	index.Add(word, 1, 2)
	index.Add(word, 2, 0)
	expected := WordOccurrence{documentNumber: 0, wordNumber: 0}
	actual := index.SearchBackward(word, WordOccurrence{documentNumber: 1, wordNumber: 1})
	if !reflect.DeepEqual(actual, expected) {
		t.Error("Expected:", expected, "actual:", actual)
	}
}
