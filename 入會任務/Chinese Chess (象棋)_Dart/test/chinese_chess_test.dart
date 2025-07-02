import 'package:gherkin/gherkin.dart';
import 'package:glob/glob.dart';
import 'steps/general_steps.dart';

Future<void> main() async {
  final config = TestConfiguration(
    features: [Glob(r"features/**.feature")],
    reporters: [
      StdoutReporter(MessageLevel.error),
      ProgressReporter(),
      TestRunSummaryReporter(),
      JsonReporter(path: './reports/cucumber.json'),
    ],
    tagExpression: "not @ignore",
    stepDefinitions: [
      GivenTheBoardIsEmptyExceptForAPieceAt(),
      WhenRedMovesPieceFromTo(),
      ThenTheMoveIsLegal(),
      ThenTheMoveIsIllegal(),
      GivenTheBoardHas(),
      ThenRedWinsImmediately(),
      ThenTheGameIsNotOver(),
    ],
  );

  return GherkinRunner().execute(config);
}