/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.voiceclone.practical;

import static cn.zhuatech.voiceclone.practical.PracticalInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class PracticalService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(r, "text", "maxCodePoints");
    String text = text(r, "text", 8000);
    int max = integer(r, "maxCodePoints", 2, 1000);
    int[] points = text.codePoints().toArray();
    List<Map<String, Object>> chunks = new ArrayList<>();
    int index = 0;
    while (index < points.length) {
      int end = Math.min(points.length, index + max), preferred = -1;
      for (int i = index; i < end; i++) if ("。！？!?；;\n".indexOf(points[i]) >= 0) preferred = i + 1;
      if (preferred > index) end = preferred;
      String part = new String(points, index, end - index);
      chunks.add(
          Map.of(
              "index",
              chunks.size() + 1,
              "fromCodePoint",
              index,
              "toCodePointExclusive",
              end,
              "codePointCount",
              end - index,
              "text",
              part));
      index = end;
    }
    return Map.of(
        "chunks",
        chunks,
        "totalCodePoints",
        points.length,
        "textPreserved",
        true,
        "audioGenerated",
        false);
  }
}
