/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Driss Amri
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package be.drissamri.service.impl;

import be.drissamri.service.HashService;
import be.drissamri.service.SupportedProtocol;
import be.drissamri.service.exception.InvalidURLException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class Base36HashService implements HashService {
  private static final int RADIX = 36;
  private static final String PIPE = "-";

  @Override
  public String shorten(String url) {
    return encode(url);
  }

  private String encode(String url) {
    if (StringUtils.isEmpty(url)) {
      throw new InvalidURLException("Supplied invalid url: empty");
    }

    boolean isSupportedProtocol = SupportedProtocol.contains(url);
    if (!isSupportedProtocol) {
      throw new InvalidURLException("URL protocol not supported");
    }

    String hexValue = Integer.toString(url.hashCode(), RADIX);
    if (hexValue.startsWith(PIPE)) {
      hexValue = hexValue.substring(1);
    }

    // TODO: Implement database check to prevent collisions
    return hexValue;
  }

}
