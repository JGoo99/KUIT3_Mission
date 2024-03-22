package utils;

import static constant.Direction.NONE_DIRECTION;
import static exception.ErrorCode.DISALLOWED_CONTINUOUS_LINE;
import static exception.ErrorCode.LINE_ALREADY_EXISTS;
import static exception.ErrorCode.OUT_OF_BOUNDS_COL_POSITION;

import exception.LadderException;

public class Row {

  private final Node[] nodes;

  private Row(int numberOfPerson) {
    this.nodes = new Node[numberOfPerson + 1];

    for (int i = 1; i < nodes.length; i++) {
      nodes[i] = Node.of(NONE_DIRECTION);
    }
  }

  public static Row of(LadderNumber numberOfPerson) {
    return new Row(numberOfPerson.getIntValue());
  }

  public Position getNextPosition(Position colP) {
    return nodes[colP.getIntValue()].move(colP);
  }

  public void drawLine(Position colP) {
    validateColPosition(colP);
    nodes[colP.getIntValue()].drawRightLine();
    nodes[colP.next().getIntValue()].drawLeftLine();
  }

  private void validateColPosition(Position colP) {
    if (colP.getIntValue() >= nodes.length - 1) {
      throw new LadderException(OUT_OF_BOUNDS_COL_POSITION);
    }
    if (existLeftLine(colP) || existRightLine(colP.next())) {
      throw new LadderException(DISALLOWED_CONTINUOUS_LINE);
    }
    if (existRightLine(colP)) {
      throw new LadderException(LINE_ALREADY_EXISTS);
    }
  }

  private boolean existLeftLine(Position position) {
    return nodes[position.getIntValue()].isLeft();
  }

  private boolean existRightLine(Position position) {
    return nodes[position.getIntValue()].isRight();
  }

  public Node[] getNodes() {
    return this.nodes;
  }
}
