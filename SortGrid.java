public class SortGrid {
    private static int compares = 0;
    private static int[][] grid;

    // PUBLIC METHODS
    public static int sort(int[][] thisGrid) {
        compares = 0;
        //TO BE IMPLEMENTED
        int row = thisGrid.length;
        int col = thisGrid[0].length;
        int size = row * col;
        int beg = 0;
        int end = size - 1;
        grid = thisGrid;
        sortGrid(beg, end, col);
        return compares;
    }


    static void sortGrid(int beg, int end, int col) {
        if (beg < end) {
            int mid = (beg + end) / 2;
            sortGrid(beg, mid, col);
            sortGrid(mid + 1, end, col);
            merge(beg, end, col);
        }
    }

    static void merge(int beg, int end, int col) {
        if (beg < end) {
            int mid = (beg + end) / 2;
            int beg2 = mid + 1;
            //Loop until pointer for both arr hits the end
            while (beg <= mid && beg2 <= end) {
                if (lessThan(beg / col, beg % col, beg2 / col, beg2 % col)) {
                    beg++;
                    //If element 1 is not in right place
                } else {
                    int val = grid[beg2 / col][beg2 % col];
                    int i = beg2;
                    //Shift element 1 and 2 to right
                    while (i > beg) {
                        grid[i / col][i % col] = grid[(i - 1) / col][(i - 1) % col];
                        i--;
                    }
                    grid[beg / col][beg % col] = val;
                    //Increment
                    beg++;
                    mid++;
                    beg2++;
                }
            }
        }
    }


    //  HELPER METHODS
    // returns true if value at (r1, c1) is less
    // than value at (r2, c2) and false otherwise;
    // counts as 1 compare
    private static boolean lessThan(int r1, int c1, int r2, int c2) {
        compares++;

        if (grid[r1][c1] < grid[r2][c2])
            return true;

        return false;
    }

    // returns true if value at (r1, c1) is greater than
    // value at (r2, c2) and false otherwise;
    // counts as 1 compare
    private static boolean greaterThan(int r1, int c1, int r2, int c2) {
        compares++;

        if (grid[r1][c1] > grid[r2][c2])
            return true;

        return false;
    }

    // swaps values in the grid
    // at (r1, c1) and (r2, c2);
    // assumes that the parameters
    // are within the bounds
    private static void swap(int r1, int c1, int r2, int c2) {
        int temp = grid[r1][c1];
        grid[r1][c1] = grid[r2][c2];
        grid[r2][c2] = temp;
    }
}

