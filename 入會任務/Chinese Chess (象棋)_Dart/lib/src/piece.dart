enum PieceType { General, Guard, Elephant, Horse, Rook, Cannon, Soldier }
enum PlayerColor { Red, Black }

class Piece {
  final PieceType type;
  final PlayerColor color;

  Piece(this.type, this.color);
}