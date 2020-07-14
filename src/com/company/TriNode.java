package com.company;

import java.util.concurrent.ConcurrentHashMap;


class TriNode {
  ConcurrentHashMap<Byte, TriNode> _map = new ConcurrentHashMap<>(256);
  byte _bitMap[] = new byte[32];
  TriNode(boolean isLastNode) {
    if (!isLastNode) {
      _map = new ConcurrentHashMap<>();
      _map.clear();
    } else {
      _map = null;
    }
    for (int i=0; i< 32; i++) {
      _bitMap[i] = 0;
    }
  }

  public TriNode putIfAbsent(byte b, boolean isLastNode) {
    if (isLastNode) {
      int unsignedbyte = b &0xFF;
     _bitMap[unsignedbyte/8] |= 1<<(unsignedbyte%8);
     return  new DummyTriNode(true);
    } else {
      TriNode t = new TriNode(isLastNode);
      _map.putIfAbsent(b, t);
      return _map.get(b);
    }
  }

  public TriNode get(Byte b, boolean isLastNode) {
    if (isLastNode) {
      int unsignedbyte = b &0xFF;
      if ((_bitMap[unsignedbyte/8] & (1<<(unsignedbyte%8))) != 0) {
        return new DummyTriNode(isLastNode);
      } else {
        return null;
      }
    }
    return _map.get(b);
  }
}
