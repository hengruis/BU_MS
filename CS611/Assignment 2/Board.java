import java.util.Arrays;


public abstract class Board {
    protected int size;
    protected String[][] board;

    public Board(int size) {
        this.size = size;
    }

    public void initBoard() {
        // initialize
        this.board = new String[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.board[i][j] = " ";
            }
        }
    }

    public String repeatString(String str, int n, String seg) {
        // repeat *str* for *n* times with delimiter *seg*
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(str).append(seg);
        }
        return sb.substring(0, sb.length() - seg.length());
    }

    public void displayBoard() {
        for (int i = 0; i < this.size; i++) {
            System.out.println("+" + repeatString("---", this.size, "+") + "+");
            for (int j = 0; j < this.size; j++) {
                System.out.printf("| %s ", this.board[i][j]);
                if (j == this.size - 1) System.out.print("|\n");
            }
        }
        System.out.println("+" + repeatString("---", this.size, "+") + "+");
    }

    public boolean isFilled() {
        // check the board if it is filled up
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j].equals(" ")) return false;
            }
        }
        return true;
    }

    public String[] getDiag(String type) {
        // return a diagonal
        // type: "\" or "/"
        String[] diag = new String[this.size];
        switch (type) {
            case "\\" -> {
                for (int m = 0; m < this.size; m++) {
                    // m: row/column
                    diag[m] = this.board[m][m];
                }
            }
            case "/" -> {
                for (int m = 0; m < this.size; m++) {
                    // m: row/column
                    diag[m] = this.board[m][this.size-m-1];
                }
            }
        }
        return diag;
    }

    public boolean isInLine(String[] line, String side, int num) {
        // check if *num* elements in a line are the same
        String[] target = new String[num];
        Arrays.fill(target, side);
        return Arrays.equals(line, target);
    }

    public abstract boolean isWinner(String side);
}


class TTTBoard extends Board {

    public TTTBoard(int size) {
        super(size);
    }

    @Override
    public boolean isWinner(String side) {
        // horizontally
        for (int i = 0; i < this.size; i++) {
            if (isInLine(this.board[i], side, this.size)) return true;
        }

        // vertically
        for (int colIdx = 0; colIdx < this.size; colIdx++) {
            String[] col = new String[this.size];
            for (int rowIdx = 0; rowIdx < this.size; rowIdx++) {
                col[rowIdx] = this.board[rowIdx][colIdx];
            }
            if (isInLine(col, side, this.size)) return true;
        }

        // diagonally
        String[] diag1 = getDiag("\\");
        String[] diag2 = getDiag("/");
        return isInLine(diag1, side, this.size) || isInLine(diag2, side, this.size);
    }
}


class OACBoard extends Board {

    public OACBoard() {
        super(6);
    }

    public boolean isInFive(String[] line, String order) {
        // check if any continuous 5 elements in a line are the same
        // expected size of string[] line: 6
        return isInLine(Arrays.copyOfRange(line, 0, 5), order, 5) ||
                isInLine(Arrays.copyOfRange(line, 1, 6), order, 5);
    }

    @Override
    public boolean isWinner(String side) {
        // horizontally
        for (int i = 0; i <this.size; i++) {
            if (isInFive(this.board[i], side)) return true;
        }

        // vertically
        for (int colIdx = 0; colIdx < this.size; colIdx++) {
            String[] col = new String[this.size];
            for (int rowIdx = 0; rowIdx < this.size; rowIdx++) {
                col[rowIdx] = this.board[rowIdx][colIdx];
            }
            if (isInFive(col, side)) return true;
        }

        //diagonally
        String[] diag1 = getDiag("\\");
        String[] diag2 = getDiag("/");
        String[] diag1_down = new String[5];
        String[] diag1_right = new String[5];
        String[] diag2_down = new String[5];
        String[] diag2_left = new String[5];
        for (int r = 1; r < this.size;r++) {
            diag1_down[r-1] = this.board[r][r-1];
            diag2_down[r-1] = this.board[r][this.size-r];
        }
        for (int c = 1; c < this.size;c++) {
            diag1_right[c-1] = this.board[c-1][c];
            diag2_left[c-1] = this.board[c-1][this.size-c-1];
        }
        return isInFive(diag1, side) || isInFive(diag2, side) || isInFive(diag1_right, side) ||
                isInFive(diag1_down, side) || isInFive(diag2_left, side) ||
                isInFive(diag2_down, side);
    }
}
