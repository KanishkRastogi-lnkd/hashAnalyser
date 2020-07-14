package com.company;

import com.google.common.hash.HashFunction;
import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.CRC32;
import java.util.Random;
import com.google.common.hash.Hashing;



public class FindHashCollision implements Callable<Result> {
  private static final int SKIP_MAX = 17;
  int id;
  ConcurrentTrie map;
  int idx1;
  char [] set;
  String hashName;
  Random r;
  public FindHashCollision(char[] set, int id, ConcurrentTrie map, int idx1, String hashName) {
    this.id = id;
    this.idx1 = idx1;
    this.set =set;
    this.map = map;
    this.r = new Random();
    this.hashName = hashName;
  }
  public Result call () throws NoSuchAlgorithmException {
    int idx2,idx3;
    long collision=0;
    int skip = 0;
    int total = 0;
    HashFunction hashFunction = null;
    switch (hashName) {
      case "murmur32":
        hashFunction = Hashing.murmur3_32();
        break;
      case "crc32":
        hashFunction = Hashing.crc32();
        break;
      default:
        throw new NoSuchAlgorithmException( hashName);
    }
    for (idx2=0;idx2<set.length;idx2++) {
      for (idx3=0;idx3<set.length;idx3++) {
        if (skip != 0) {
          skip--;
          continue;
        }
        skip =  (r.nextInt()%SKIP_MAX);
        if (skip < 0) {
          skip = skip *-1;
        }
        total++;
        StringBuilder s = new StringBuilder();
        s.append(set[idx1]).append(set[idx2]).append(set[idx3]);

        byte[] hash ;
        long h=0;
        try {
          h = hashFunction.hashBytes(s.toString().getBytes("UTF-8")).asInt();
 //         System.out.println(" String " + hex(s.toString().getBytes("UTF-8")) + " " + Long.toHexString(h) + " " + idx1 + " " + idx2 + "  "+ idx3);
        } catch (Exception e) {
          System.out.println(e);
          e.printStackTrace();
        }


        if (!map.isPresent(h)) {
          map.insert(h);
        } else {
          collision++;
        }
      }
    }
    Result res = new Result(total, collision);
    return res;

  }

  private String hex(byte[] bytes) {
    StringBuilder s = new StringBuilder();
    for (int i=0; i<bytes.length; i++) {
      s.append(Integer.toHexString(Byte.toUnsignedInt(bytes[i])));
    }
    return s.toString();
  }
}
