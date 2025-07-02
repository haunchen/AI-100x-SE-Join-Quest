import 'dart:math';
import 'board.dart';
import 'piece.dart';

class ChineseChess {
  final Board board = Board();
  GameStatus gameStatus = GameStatus.ongoing;

  bool isLegalMove(int fromRow, int fromCol, int toRow, int toCol) {
    final piece = board.getPiece(fromRow, fromCol);
    if (piece == null) {
      return false;
    }

    if (!_isPieceMovementLegal(piece, fromRow, fromCol, toRow, toCol)) {
      return false;
    }

    return _isMoveLegalUnderGeneralRules(fromRow, fromCol, toRow, toCol);
  }

  void move(int fromRow, int fromCol, int toRow, int toCol) {
    final targetPiece = board.getPiece(toRow, toCol);
    if (targetPiece?.type == PieceType.General) {
      gameStatus = GameStatus.red_wins; // Assuming Red is the one capturing
    }
    board.movePiece(fromRow, fromCol, toRow, toCol);
  }

  bool _isPieceMovementLegal(Piece piece, int fromRow, int fromCol, int toRow, int toCol) {
    switch (piece.type) {
      case PieceType.General:
        return _isLegalGeneralMove(fromRow, fromCol, toRow, toCol, piece.color);
      case PieceType.Guard:
        return _isLegalGuardMove(fromRow, fromCol, toRow, toCol, piece.color);
      case PieceType.Elephant:
        return _isLegalElephantMove(fromRow, fromCol, toRow, toCol, piece.color);
      case PieceType.Horse:
        return _isLegalHorseMove(fromRow, fromCol, toRow, toCol);
      case PieceType.Rook:
        return _isLegalRookMove(fromRow, fromCol, toRow, toCol);
      case PieceType.Cannon:
        return _isLegalCannonMove(fromRow, fromCol, toRow, toCol);
      case PieceType.Soldier:
        return _isLegalSoldierMove(fromRow, fromCol, toRow, toCol, piece.color);
      default:
        return false;
    }
  }

  bool _isMoveLegalUnderGeneralRules(int fromRow, int fromCol, int toRow, int toCol) {
    final pieceToMove = board.getPiece(fromRow, fromCol)!;
    final pieceAtTarget = board.getPiece(toRow, toCol);

    board.movePiece(fromRow, fromCol, toRow, toCol);

    bool isIllegal = _isGeneralsFacingEachOther();

    board.movePiece(toRow, toCol, fromRow, fromCol);
    if (pieceAtTarget != null) {
      board.addPiece(pieceAtTarget, toRow, toCol);
    } else {
      board.removePiece(fromRow, fromCol);
      board.addPiece(pieceToMove, fromRow, fromCol);
    }

    return !isIllegal;
  }

  bool _isLegalGeneralMove(int fromRow, int fromCol, int toRow, int toCol, PlayerColor color) {
    if (!_isInPalace(toRow, toCol, color)) {
      return false;
    }
    final rowDiff = (toRow - fromRow).abs();
    final colDiff = (toCol - fromCol).abs();
    return rowDiff + colDiff == 1;
  }

  bool _isLegalGuardMove(int fromRow, int fromCol, int toRow, int toCol, PlayerColor color) {
    if (!_isInPalace(toRow, toCol, color)) {
      return false;
    }
    final rowDiff = (toRow - fromRow).abs();
    final colDiff = (toCol - fromCol).abs();
    return rowDiff == 1 && colDiff == 1;
  }

  bool _isLegalElephantMove(int fromRow, int fromCol, int toRow, int toCol, PlayerColor color) {
    if (color == PlayerColor.Red && toRow > 5) return false;
    if (color == PlayerColor.Black && toRow < 6) return false;

    final rowDiff = (toRow - fromRow).abs();
    final colDiff = (toCol - fromCol).abs();
    if (rowDiff != 2 || colDiff != 2) return false;

    final midRow = (fromRow + toRow) ~/ 2;
    final midCol = (fromCol + toCol) ~/ 2;
    return board.getPiece(midRow, midCol) == null;
  }

  bool _isLegalHorseMove(int fromRow, int fromCol, int toRow, int toCol) {
    final rowDiff = (toRow - fromRow).abs();
    final colDiff = (toCol - fromCol).abs();
    if (!((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2))) {
      return false;
    }

    if (rowDiff == 2) {
      if (board.getPiece(fromRow + (toRow - fromRow) ~/ 2, fromCol) != null) {
        return false;
      }
    } else { // colDiff == 2
      if (board.getPiece(fromRow, fromCol + (toCol - fromCol) ~/ 2) != null) {
        return false;
      }
    }
    return true;
  }

  bool _isLegalRookMove(int fromRow, int fromCol, int toRow, int toCol) {
    return _countPiecesBetween(fromRow, fromCol, toRow, toCol) == 0;
  }

  bool _isLegalCannonMove(int fromRow, int fromCol, int toRow, int toCol) {
    final piecesBetween = _countPiecesBetween(fromRow, fromCol, toRow, toCol);
    final targetPiece = board.getPiece(toRow, toCol);

    if (targetPiece == null) { // Move
      return piecesBetween == 0;
    } else { // Capture
      return piecesBetween == 1;
    }
  }

  bool _isLegalSoldierMove(int fromRow, int fromCol, int toRow, int toCol, PlayerColor color) {
    final rowDiff = toRow - fromRow;
    final colDiff = (toCol - fromCol).abs();

    if (color == PlayerColor.Red) {
      if (fromRow <= 5) { // Before crossing river
        return rowDiff == 1 && colDiff == 0;
      } else { // After crossing river
        return (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
      }
    } else { // Black
      if (fromRow >= 6) { // Before crossing river
        return rowDiff == -1 && colDiff == 0;
      } else { // After crossing river
        return (rowDiff == -1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1);
      }
    }
  }

  bool _isInPalace(int row, int col, PlayerColor color) {
    if (color == PlayerColor.Red) {
      return row >= 1 && row <= 3 && col >= 4 && col <= 6;
    } else {
      return row >= 8 && row <= 10 && col >= 4 && col <= 6;
    }
  }

  bool _isGeneralsFacingEachOther() {
    final redPos = board.redGeneralPos;
    final blackPos = board.blackGeneralPos;
    if (redPos == null || blackPos == null) return false;
    if (redPos[1] != blackPos[1]) return false;
    return _countPiecesBetween(redPos[0], redPos[1], blackPos[0], blackPos[1]) == 0;
  }

  int _countPiecesBetween(int r1, int c1, int r2, int c2) {
    if (r1 != r2 && c1 != c2) return -1; // Not a straight line
    int count = 0;
    if (r1 == r2) {
      for (int c = min(c1, c2) + 1; c < max(c1, c2); c++) {
        if (board.getPiece(r1, c) != null) count++;
      }
    } else {
      for (int r = min(r1, r2) + 1; r < max(r1, r2); r++) {
        if (board.getPiece(r, c1) != null) count++;
      }
    }
    return count;
  }
}

enum GameStatus { ongoing, red_wins, black_wins }