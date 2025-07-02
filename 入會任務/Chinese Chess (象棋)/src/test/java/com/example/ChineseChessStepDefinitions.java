package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChineseChessStepDefinitions {

    private ChineseChess game;
    private boolean isLegalMove;

    @Given("the board is empty except for a Red General at \\({int}, {int})")
    public void the_board_is_empty_except_for_a_red_general_at(Integer row, Integer col) {
        game = new ChineseChess();
    }
    @When("Red moves the General from \\({int}, {int}) to \\({int}, {int})")
    public void red_moves_the_general_from_to(Integer fromRow, Integer fromCol, Integer toRow, Integer toCol) {
        isLegalMove = game.isLegalMove("Red General", fromRow, fromCol, toRow, toCol);
    }
    @Then("the move is legal")
    public void the_move_is_legal() {
        assertTrue(isLegalMove);
    }

    @Then("the move is illegal")
    public void the_move_is_illegal() {
        assertFalse(isLegalMove);
    }

    @Given("the board has:")
    public void the_board_has(io.cucumber.datatable.DataTable dataTable) {
        game = new ChineseChess();
        game.setBoard(dataTable.asMap(String.class, String.class));
    }

    @Given("the board is empty except for a Red Guard at \\({int}, {int})")
    public void the_board_is_empty_except_for_a_red_guard_at(Integer row, Integer col) {
        game = new ChineseChess();
    }

    @When("Red moves the Guard from \\({int}, {int}) to \\({int}, {int})")
    public void red_moves_the_guard_from_to(Integer fromRow, Integer fromCol, Integer toRow, Integer toCol) {
        isLegalMove = game.isLegalMove("Red Guard", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Rook at \\({int}, {int})")
    public void the_board_is_empty_except_for_a_red_rook_at(Integer row, Integer col) {
        game = new ChineseChess();
    }

    @When("Red moves the Rook from \\({int}, {int}) to \\({int}, {int})")
    public void red_moves_the_rook_from_to(Integer fromRow, Integer fromCol, Integer toRow, Integer toCol) {
        isLegalMove = game.isLegalMove("Red Rook", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Horse at \\({int}, {int})")
    public void the_board_is_empty_except_for_a_red_horse_at(Integer row, Integer col) {
        game = new ChineseChess();
    }

    @When("Red moves the Horse from \\({int}, {int}) to \\({int}, {int})")
    public void red_moves_the_horse_from_to(Integer fromRow, Integer fromCol, Integer toRow, Integer toCol) {
        isLegalMove = game.isLegalMove("Red Horse", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Cannon at \\({int}, {int})")
    public void the_board_is_empty_except_for_a_red_cannon_at(Integer row, Integer col) {
        game = new ChineseChess();
    }

    @When("Red moves the Cannon from \\({int}, {int}) to \\({int}, {int})")
    public void red_moves_the_cannon_from_to(Integer fromRow, Integer fromCol, Integer toRow, Integer toCol) {
        isLegalMove = game.isLegalMove("Red Cannon", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Elephant at \\({int}, {int})")
    public void the_board_is_empty_except_for_a_red_elephant_at(Integer row, Integer col) {
        game = new ChineseChess();
    }

    @When("Red moves the Elephant from \\({int}, {int}) to \\({int}, {int})")
    public void red_moves_the_elephant_from_to(Integer fromRow, Integer fromCol, Integer toRow, Integer toCol) {
        isLegalMove = game.isLegalMove("Red Elephant", fromRow, fromCol, toRow, toCol);
    }

    @Given("the board is empty except for a Red Soldier at \\({int}, {int})")
    public void the_board_is_empty_except_for_a_red_soldier_at(Integer row, Integer col) {
        game = new ChineseChess();
    }

    @When("Red moves the Soldier from \\({int}, {int}) to \\({int}, {int})")
    public void red_moves_the_soldier_from_to(Integer fromRow, Integer fromCol, Integer toRow, Integer toCol) {
        isLegalMove = game.isLegalMove("Red Soldier", fromRow, fromCol, toRow, toCol);
    }

    @Then("Red wins immediately")
    public void red_wins_immediately() {
        assertTrue(game.isGameOver());
    }

    @Then("the game is not over just from that capture")
    public void the_game_is_not_over_just_from_that_capture() {
        assertFalse(game.isGameOver());
    }
}