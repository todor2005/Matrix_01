package bg.fmi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 *
 * @author Todor-PC
 */
public class TextPanel extends javax.swing.JPanel {

    public TextPanel() {
        initComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private int size = 50;

    private ArrayList<Column> columns = new ArrayList<>();

    public void init() {

        getTweets();

        Timer timer = new Timer(100, new MyActionListener());
        timer.start();

        for (int i = 0; i < size; i++) {

            Column column = new Column(i);
            column.setLetterListener(new Column.OnNextTweetListener() {
                @Override
                public String getNextTweet() {
                    return getTweet();
                }
            });

            columns.add(column);
        }

    }

    private boolean reading_tweets = false;

    private void getTweets() {

        Thread t = new Thread(() -> {
            reading_tweets = true;

            Random rnd = new Random();
            char c = (char) (rnd.nextInt(57) + 65);
            
            Query query = new Query( String.valueOf(c) );
            query.setCount(100);
            QueryResult result = null;
            try {
                
                System.out.println("aaaaaaaaaaa");
                
                result = Globals.twitter.search(query);
            } catch (TwitterException ex) {
            }
            if (result != null) {
                
                System.out.println("result.getTweets().size() = " + result.getTweets().size());

                for (Status status : result.getTweets()) {

                    tweets.add("@" + status.getUser().getScreenName() + ":" + status.getText());
                }
            }

            reading_tweets = false;
        });

        t.start();

    }

    private String getTweet() {

        if (tweets.isEmpty()) {

            if (reading_tweets == false) {
                reading_tweets = true;
                getTweets();
            }

            return "";

        } else {
            return tweets.remove();
        }
    }

    private Queue<String> tweets = new LinkedList<>();

    public class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            ArrayList<Column> not_falling = new ArrayList<>();

            for (Column column : columns) {

                if (column.is_falling == false) {

                    not_falling.add(column);

//                    Random rnd = new Random();
//                    
//                    if (rnd.nextInt(100) % 37 == 0) {
//                        column.is_falling = true;
//                    }
                }
            }

            if (not_falling.size() > 0 && tweets.isEmpty() == false) {
                Random rnd = new Random();
                int index = rnd.nextInt(not_falling.size());

                Column column = not_falling.get(index);
                column.is_falling = true;
            }

            for (Column column : columns) {
                column.update();
            }

            repaint();
        }

    }

    private int distanse = 15;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        setBackground(Color.BLACK);

        for (Column column : columns) {

            for (Letter l : column.getLetters()) {
                if (l.is_howing) {
                    g.setColor(l.color);
                    g.drawString(String.valueOf(l.symbol), l.x * distanse, l.y * distanse);
                }
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
