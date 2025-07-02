import 'package:gherkin/gherkin.dart';
import 'package:chinese_chess/src/chinese_chess.dart';
import 'package:chinese_chess/src/piece.dart';
import './shared_state.dart' as shared_state;

// A generic step for setting up a board with a single piece
class GivenTheBoardIsEmptyExceptForAPieceAt extends Given3<String, String, String> {
  @override
  RegExp get pattern => RegExp(r"the board is empty except for a Red (\w+) at \((\d+), (\d+)\)");

  @override
  Future<void> executeStep(String pieceType, String row, String col) async {
    final r = int.parse(row);
    final c = int.parse(col);
    final type = PieceType.values.firstWhere((e) => e.toString().split('.')[1].toLowerCase() == pieceType.toLowerCase());
    
    shared_state.game = ChineseChess();
    shared_state.game!.board.addPiece(Piece(type, PlayerColor.Red), r, c);
  }
}

// A generic step for moving a piece
class WhenRedMovesPieceFromTo extends When5<String, String, String, String, String> {
  @override
  RegExp get pattern => RegExp(r"Red moves the (\w+) from \((\d+), (\d+)\) to \((\d+), (\d+)\)");

  @override
  Future<void> executeStep(String pieceType, String fromRow, String fromCol, String toRow, String toCol) async {
    final fr = int.parse(fromRow);
    final fc = int.parse(fromCol);
    final tr = int.parse(toRow);
    final tc = int.parse(toCol);
    shared_state.lastMoveLegal = shared_state.game!.isLegalMove(fr, fc, tr, tc);
    if (shared_state.lastMoveLegal!) {
      shared_state.game!.move(fr, fc, tr, tc);
    }
  }
}

class ThenTheMoveIsLegal extends Then {
  @override
  RegExp get pattern => RegExp(r"the move is legal");

  @override
  Future<void> executeStep() async {
    this.expect(shared_state.lastMoveLegal, true);
  }
}

class ThenTheMoveIsIllegal extends Then {
  @override
  RegExp get pattern => RegExp(r"the move is illegal");

  @override
  Future<void> executeStep() async {
    this.expect(shared_state.lastMoveLegal, false);
  }
}

class GivenTheBoardHas extends Given1<GherkinTable> {
  @override
  RegExp get pattern => RegExp(r"the board has:");

  @override
  Future<void> executeStep(GherkinTable data) async {
    shared_state.game = ChineseChess();
    for (final row in data.asMap()) {
      final pieceName = row['Piece']!;
      final position = row['Position']!;
      
      final piece = _parsePiece(pieceName);
      final pos = _parsePosition(position);

      shared_state.game!.board.addPiece(piece, pos[0], pos[1]);
    }
  }

  Piece _parsePiece(String pieceName) {
    final parts = pieceName.split(' ');
    final colorName = parts[0].toLowerCase();
    final typeName = parts.length > 1 ? parts[1].toLowerCase() : '';
    
    final color = PlayerColor.values.firstWhere((e) => e.toString().split('.')[1].toLowerCase() == colorName);
    final type = PieceType.values.firstWhere((e) => e.toString().split('.')[1].toLowerCase() == typeName);
    return Piece(type, color);
  }

  List<int> _parsePosition(String position) {
    final cleaned = position.replaceAll(RegExp(r'[\(\)]'), '');
    final parts = cleaned.split(',');
    return [int.parse(parts[0]), int.parse(parts[1])];
  }
}

class ThenRedWinsImmediately extends Then {
  @override
  RegExp get pattern => RegExp(r"Red wins immediately");

  @override
  Future<void> executeStep() async {
    this.expect(shared_state.game!.gameStatus, GameStatus.red_wins);
  }
}

class ThenTheGameIsNotOver extends Then {
  @override
  RegExp get pattern => RegExp(r"the game is not over just from that capture");

  @override
  Future<void> executeStep() async {
    this.expect(shared_state.game!.gameStatus, GameStatus.ongoing);
  }
}