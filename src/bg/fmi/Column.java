/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.fmi;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author Todor-PC
 */
public class Column {
    //some comment
    //aa

    public Column(int y) {

        for (int i = 0; i < 30; i++) {
            Letter l = new Letter();
            l.color = Color.white;
            // l.symbol = (char) (rnd.nextInt(57) + 65);
            l.x = y;
            l.y = i + 1;

            l.setLetterListener(() -> nextLetter());

            letters.add(l);
        }
    }

    private Queue<Character> tweet = new LinkedList<>();

    private char nextLetter() {

        if (tweet.isEmpty()) {
            Random rnd = new Random();
            return (char) (rnd.nextInt(57) + 65);
        } else {
            char c = tweet.remove();
            
            return c;
        }

    }

    private final ArrayList<Letter> letters = new ArrayList<>();
    public boolean is_falling = false;

    public ArrayList<Letter> getLetters() {
        return this.letters;
    }

    public static interface OnNextTweetListener {

        public String getNextTweet();
    }

    private OnNextTweetListener nextTweetListener = null;

    public void setLetterListener(OnNextTweetListener nextTweetListener) {
        this.nextTweetListener = nextTweetListener;
    }

    private void getNextTweet() {
        if (nextTweetListener != null) {
            String tweet_str = nextTweetListener.getNextTweet();
            for (int i = 0; i < tweet_str.length(); i++) {
                tweet.add(tweet_str.charAt(i));
            }
        }
    }

    public void update() {

        if (is_falling == false) {
            return;
        }

        if (tweet.isEmpty()) {
            getNextTweet();
        }

        for (Letter l : letters) {
            if (l.is_howing) {
                l.update();
            }
        }

        for (Letter l : letters) {

            if (l.is_howing == false) {
                l.is_howing = true;
                l.update();
                break;
            }
        }

    }

}
