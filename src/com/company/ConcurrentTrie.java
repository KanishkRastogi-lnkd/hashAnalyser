package com.company;

public class ConcurrentTrie {

  TriNode _rootNode;
  public ConcurrentTrie() {
    _rootNode = new TriNode(false);
  }
  public boolean isPresent(long l) {
    long tmp = l;
    TriNode curNode = _rootNode;
    for (int i = 0; i < 4; i++) {
      byte b = (byte) (l & 0xFF);
      curNode = curNode.get(b, i == 3);
      if (curNode == null) return false;
      l = l>>8;
    }
//    System.out.println("Present " + Long.toHexString(tmp) );
    return true;
  }

  public void insert(long l) {
    TriNode curNode = _rootNode;
    long tmp = l;
    for (int i = 0; i < 4; i++) {
      byte b = (byte) (l & 0xFF);
      curNode = curNode.putIfAbsent(b, i == 3);
      l = l >> 8;
    }
//    System.out.println("Inserted " + Long.toHexString(tmp) );
  }
}
