package com.company;

public class DummyTriNode extends TriNode {

  public DummyTriNode(boolean b) {
    super(b);
  }

  @Override
  public TriNode putIfAbsent(byte b, boolean isLastNode) {
    throw new NullPointerException("Wrong Invokation");
  }

  @Override
  public TriNode get(Byte b, boolean is) {
    throw new NullPointerException("Wrong Invokation");
  }
}
