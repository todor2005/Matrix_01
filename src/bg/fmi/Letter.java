package bg.fmi;

import java.awt.Color;

/**
 *
 * @author Todor-PC
 */
public class Letter {

    public char symbol;
    public Color color;
    public int x = 0;
    public int y = 0;

    public boolean is_howing = false;

    private int counter = 0;
    private float alpha = 1f;

    public static interface OnNextLetterListener {

        public char getNextLetter();
    }

    private OnNextLetterListener letterListener = null;

    public void setLetterListener(OnNextLetterListener letterListener) {
        this.letterListener = letterListener;
    }

    public void update() {

        if (is_howing) {

            if (counter == 0) {
                if (letterListener != null) {
                    this.symbol = letterListener.getNextLetter();
                }
            }

            if (counter < 5) {
                //щс се седи бяло

                switch (counter) {
                    case 0: {
                        color = new Color(255, 255, 255);
                        break;
                    }
                    case 1: {
                        color = new Color(153, 255, 153);
                        break;
                    }
                    case 2: {
                        color = new Color(102, 255, 102);
                        break;
                    }
                    case 3: {
                        color = new Color(51, 255, 51);
                        break;
                    }
                    case 4: {
                        color = new Color(0, 255, 0);
                        break;
                    }
                }
            } else if (counter < 15) {
                color = new Color(0f, 1f, 0f, alpha);
                alpha -= 0.1;

            } else {
                color = Color.black;
            }
        }

        counter++;

        if (counter > 25) {
            counter = 0;
            alpha = 1;
        }

    }
}
