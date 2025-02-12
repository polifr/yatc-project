package mascot_test

import (
	"testing"

	"poli.it/go-test-prj/mascot"
)

func TestMascot(t *testing.T) {
	if mascot.BestMascot() != "Go Gopher" {
		t.Fatal("Wrong mascot!")
	}
}
