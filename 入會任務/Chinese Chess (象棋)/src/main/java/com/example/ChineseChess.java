package com.example;

import java.util.Map;

public class ChineseChess {

    private Map<String, String> boardState;
    private boolean gameOver = false;

    public ChineseChess() {
        this.boardState = new java.util.HashMap<>();
    }

    public void setBoard(Map<String, String> boardState) {
        this.boardState = new java.util.HashMap<>(boardState);
        this.gameOver = false;
    }

    public boolean isLegalMove(String piece, int fromRow, int fromCol, int toRow, int toCol) {
        boolean isLegal = false;
        switch (piece) {
            case "Red General":
                isLegal = isLegalGeneralMove(fromRow, fromCol, toRow, toCol);
                break;
            case "Red Guard":
                isLegal = isLegalGuardMove(fromRow, fromCol, toRow, toCol);
                break;
            case "Red Rook":
                isLegal = isLegalRookMove(fromRow, fromCol, toRow, toCol);
                break;
            case "Red Horse":
                isLegal = isLegalHorseMove(fromRow, fromCol, toRow, toCol);
                break;
            case "Red Cannon":
                isLegal = isLegalCannonMove(fromRow, fromCol, toRow, toCol);
                break;
            case "Red Elephant":
                isLegal = isLegalElephantMove(fromRow, fromCol, toRow, toCol);
                break;
            case "Red Soldier":
                isLegal = isLegalSoldierMove(fromRow, fromCol, toRow, toCol);
                break;
        }

        if (isLegal) {
            String targetPosition = String.format("(%d, %d)", toRow, toCol);
            if (boardState.containsValue(targetPosition)) {
                String capturedPiece = "";
                for (Map.Entry<String, String> entry : boardState.entrySet()) {
                    if (entry.getValue().equals(targetPosition)) {
                        capturedPiece = entry.getKey();
                        break;
                    }
                }
                if ("Black General".equals(capturedPiece)) {
                    gameOver = true;
                }
                boardState.remove(capturedPiece);
            }
        }

        return isLegal;
    }

    private boolean isLegalSoldierMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Before crossing the river (rows 1-5 for Red)
        if (fromRow <= 5) {
            return toRow == fromRow + 1 && toCol == fromCol;
        } else { // After crossing the river
            return (toRow == fromRow + 1 && toCol == fromCol) || (Math.abs(toCol - fromCol) == 1 && toRow == fromRow);
        }
    }

    private boolean isLegalElephantMove(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        if (rowDiff != 2 || colDiff != 2) {
            return false;
        }

        // Cannot cross the river
        if (toRow > 5) {
            return false;
        }

        // Check for blocking piece at the midpoint
        if (isPieceAt(fromRow + (toRow - fromRow) / 2, fromCol + (toCol - fromCol) / 2)) {
            return false;
        }

        return true;
    }

    private boolean isLegalCannonMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }

        // Check for pieces in the path
        int piecesInPath = 0;
        if (fromRow == toRow) { // Horizontal move
            int start = Math.min(fromCol, toCol) + 1;
            int end = Math.max(fromCol, toCol);
            for (int c = start; c < end; c++) {
                if (isPieceAt(fromRow, c)) {
                    piecesInPath++;
                }
            }
        } else { // Vertical move
            int start = Math.min(fromRow, toRow) + 1;
            int end = Math.max(fromRow, toRow);
            for (int r = start; r < end; r++) {
                if (isPieceAt(r, fromCol)) {
                    piecesInPath++;
                }
            }
        }

        if (isPieceAt(toRow, toCol)) { // Capture move
            return piecesInPath == 1;
        } else { // Non-capture move
            return piecesInPath == 0;
        }
    }

    private boolean isLegalHorseMove(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        if (!((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2))) {
            return false;
        }

        // Check for blocking piece
        if (rowDiff == 2) { // Moving 2 rows
            if (isPieceAt(fromRow + (toRow - fromRow) / 2, fromCol)) {
                return false;
            }
        } else { // Moving 2 cols
            if (isPieceAt(fromRow, fromCol + (toCol - fromCol) / 2)) {
                return false;
            }
        }

        return true;
    }

    private boolean isLegalRookMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }

        // Check for pieces in the path
        if (fromRow == toRow) { // Horizontal move
            int start = Math.min(fromCol, toCol) + 1;
            int end = Math.max(fromCol, toCol);
            for (int c = start; c < end; c++) {
                if (isPieceAt(fromRow, c)) {
                    return false;
                }
            }
        } else { // Vertical move
            int start = Math.min(fromRow, toRow) + 1;
            int end = Math.max(fromRow, toRow);
            for (int r = start; r < end; r++) {
                if (isPieceAt(r, fromCol)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isPieceAt(int row, int col) {
        if (boardState == null) {
            return false;
        }
        String position = String.format("(%d, %d)", row, col);
        return boardState.containsValue(position);
    }

    private boolean isLegalGuardMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isInsideRedPalace(toRow, toCol)) {
            return false;
        }

        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        return rowDiff == 1 && colDiff == 1;
    }

    private boolean isLegalGeneralMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isInsideRedPalace(toRow, toCol)) {
            return false;
        }

        // Generals facing each other rule
        if (boardState != null && boardState.containsKey("Black General")) {
            int[] blackGeneralPosition = parsePosition(boardState.get("Black General"));
            int blackGeneralCol = blackGeneralPosition[1];
            if (toCol == blackGeneralCol) {
                return false; // Illegal move if they are on the same file
            }
        }

        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
    }

    private int[] parsePosition(String position) {
        String[] parts = position.replaceAll("[()]", "").split(",");
        return new int[]{Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())};
    }

    private boolean isInsideRedPalace(int row, int col) {
        // Palace for Red is rows 1-3, cols 4-6
        return row >= 1 && row <= 3 && col >= 4 && col <= 6;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}