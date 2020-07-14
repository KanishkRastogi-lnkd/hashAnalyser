package com.company;

import java.util.concurrent.ConcurrentHashMap;


public class ConcurrentTrie {
  class TriNode {
    ConcurrentHashMap <Byte, TriNode> _map = new ConcurrentHashMap<>(256);
    TriNode(boolean isLastNode) {
      if (!isLastNode) {
        _map = new ConcurrentHashMap<>();
        _map.clear();
      } else {
        _map = null;
      }
    }

    public TriNode putIfAbsent(byte b, boolean isLastNode) {
      TriNode t = new TriNode(isLastNode);
      _map.putIfAbsent(b, t);
      return _map.get(b);
    }

    public TriNode get(Byte b) {
      return _map.get(b);
    }
  }

  TriNode _rootNode;
  public ConcurrentTrie() {
    _rootNode = new TriNode(false);
  }
  public boolean isPresent(long l) {
    long tmp = l;
    TriNode curNode = _rootNode;
    for (int i = 0; i < 4; i++) {
      byte b = (byte) (l & 0xFF);
      curNode = curNode.get(b);
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
