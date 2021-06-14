package com.lasige.roteiroentremares.util;

import android.content.Context;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class PuzzleFactory {

    public static final int COLUMNS = 3;
    public static final int DIMENSIONS = COLUMNS * COLUMNS;
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    private static Context context;
    private static OnSolvedListener onSolvedListener;

    private static String[] tileList;
    private static int[] puzzleImages;

    private static GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    public PuzzleFactory(Context context, GestureDetectGridView gestureDetectGridView, OnSolvedListener onSolvedListener, int[] puzzleImages) {
        this.context = context;
        this.onSolvedListener = onSolvedListener;
        this.puzzleImages = puzzleImages;

        init(gestureDetectGridView);
        scramble();
        setDimensions();
    }

    private void init(GestureDetectGridView gestureDetectGridView) {
        // gridView = findViewById(R.id.grid_view)
        gridView = gestureDetectGridView;
        gridView.setNumColumns(COLUMNS);

        tileList = new String[DIMENSIONS];

        for (int i = 0; i < DIMENSIONS; i++) {
            tileList[i] = String.valueOf(i);
        }
    }

    private void scramble() {
        int index;
        String temp;
        Random random = new Random();

        for (int i = tileList.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private static void display() {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for (int i = 0; i < tileList.length; i++) {
            button = new Button(context);

            if (tileList[i].equals("0")) {
                button.setBackgroundResource(puzzleImages[0]);
            } else if (tileList[i].equals("1")) {
                button.setBackgroundResource(puzzleImages[1]);
            } else if (tileList[i].equals("2")) {
                button.setBackgroundResource(puzzleImages[2]);
            } else if (tileList[i].equals("3")) {
                button.setBackgroundResource(puzzleImages[3]);
            } else if (tileList[i].equals("4")) {
                button.setBackgroundResource(puzzleImages[4]);
            } else if (tileList[i].equals("5")) {
                button.setBackgroundResource(puzzleImages[5]);
            } else if (tileList[i].equals("6")) {
                button.setBackgroundResource(puzzleImages[6]);
            } else if (tileList[i].equals("7")) {
                button.setBackgroundResource(puzzleImages[7]);
            } else {
                button.setBackgroundResource(puzzleImages[8]);
            }

            buttons.add(button);
        }

        gridView.setAdapter(new PuzzleAdapter(buttons, columnWidth, columnHeight));
    }

    private void setDimensions() {
        ViewTreeObserver viewTreeObserver = gridView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = gridView.getMeasuredWidth();
                int displayHeight = gridView.getMeasuredHeight();

                int statusBarHeight = getStatusBarHeight();
                int requiredHeight = displayHeight - statusBarHeight;

                columnWidth = displayWidth / COLUMNS;
                columnHeight = requiredHeight / COLUMNS;

                display();
            }
        });
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (result > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private static void swap(int position, int swap) {
        String newPosition = tileList[position + swap];
        tileList[position + swap] = tileList[position];
        tileList[position] = newPosition;

        display();

        if (isSolved()) {
            onSolvedListener.onSolvedCallback();
        }
    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++) {
            if (tileList[i].equals(String.valueOf(i))) {
                // Solved
                solved = true;
            } else {
                // Not solved
                solved = false;
                break;
            }
        }

        return solved;
    }

    public static void moveTiles(String direction, int position) {

        if (position == 0) {
            // Upper left corner tile -> position 0, can only DOWN and RIGHT

            if (direction.equals(RIGHT)) {
                swap(position, 1);
            } else if (direction.equals(DOWN)) {
                swap(position, COLUMNS);
            } else {
                Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            }
        } else if (position > 0 && position < COLUMNS - 1) {
            // Upper center tiles

            if (direction.equals(LEFT)) {
                swap(position, -1);
            } else if (direction.equals(DOWN)) {
                swap(position, COLUMNS);
            } else if (direction.equals(RIGHT)) {
                swap(position, 1);
            } else {
                Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            }
        } else if (position == COLUMNS - 1) {
            if (direction.equals(LEFT)) swap(position, -1);
            else if (direction.equals(DOWN)) swap(position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(UP)) swap(position, -COLUMNS);
            else if (direction.equals(RIGHT)) swap(position, 1);
            else if (direction.equals(DOWN)) swap(position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(UP)) swap(position, -COLUMNS);
            else if (direction.equals(LEFT)) swap(position, -1);
            else if (direction.equals(DOWN)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1) swap(position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {
            if (direction.equals(UP)) swap(position, -COLUMNS);
            else if (direction.equals(RIGHT)) swap(position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
            if (direction.equals(UP)) swap(position, -COLUMNS);
            else if (direction.equals(LEFT)) swap(position, -1);
            else if (direction.equals(RIGHT)) swap(position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(UP)) swap(position, -COLUMNS);
            else if (direction.equals(LEFT)) swap(position, -1);
            else if (direction.equals(RIGHT)) swap(position, 1);
            else swap(position, COLUMNS);
        }
    }

    public interface OnSolvedListener {
        void onSolvedCallback();
    }
}
