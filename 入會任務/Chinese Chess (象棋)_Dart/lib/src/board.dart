import 'piece.dart';

class Board {
  final Map<String, Piece> _pieces = {};
  String? _redGeneralPos;
  String? _blackGeneralPos;

  void addPiece(Piece piece, int row, int col) {
    final pos = '$row,$col';
    _pieces[pos] = piece;
    if (piece.type == PieceType.General) {
      if (piece.color == PlayerColor.Red) {
        _redGeneralPos = pos;
      } else {
        _blackGeneralPos = pos;
      }
    }
  }

  Piece? getPiece(int row, int col) {
    return _pieces['$row,$col'];
  }

  void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
    final piece = getPiece(fromRow, fromCol);
    if (piece != null) {
      removePiece(fromRow, fromCol);
      addPiece(piece, toRow, toCol);
    }
  }

  void removePiece(int row, int col) {
    final pos = '$row,$col';
    final piece = _pieces[pos];
    if (piece != null && piece.type == PieceType.General) {
      if (piece.color == PlayerColor.Red) {
        _redGeneralPos = null;
      } else {
        _blackGeneralPos = null;
      }
    }
    _pieces.remove(pos);
  }

  List<int>? get redGeneralPos {
    if (_redGeneralPos == null) return null;
    final parts = _redGeneralPos!.split(',');
    return [int.parse(parts[0]), int.parse(parts[1])];
  }

  List<int>? get blackGeneralPos {
    if (_blackGeneralPos == null) return null;
    final parts = _blackGeneralPos!.split(',');
    return [int.parse(parts[0]), int.parse(parts[1])];
  }
}