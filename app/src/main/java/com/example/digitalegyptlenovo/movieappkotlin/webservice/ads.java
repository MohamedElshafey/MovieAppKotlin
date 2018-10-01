package com.example.digitalegyptlenovo.movieappkotlin.webservice;

public class ads {

    public int solution(int[] A) {

        for (int i = 0; i < 1_000_000; i++) {
            if (!contains(i, A)) {
                return i;
            }
        }
        return 1;
    }

    private boolean contains(int i, int[] A) {
        for (int number : A) {
            if (number == i)
                return true;
        }
        return false;
    }

}
