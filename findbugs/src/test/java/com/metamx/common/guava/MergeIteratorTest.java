/*
 * Copyright 2011,2012 Metamarkets Group Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.metamx.common.guava;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 */
public class MergeIteratorTest
{
  @Test
  public void testSanity() throws Exception
  {
    MergeIterator<Integer> iter = new MergeIterator<>(
        Ordering.<Integer>natural(),
        Lists.newArrayList(
            Arrays.asList(1, 3, 5, 7, 9).iterator(),
            Arrays.asList(2, 8).iterator(),
            Arrays.asList(4, 6, 8).iterator()
        )
    );

    Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 8, 9), Lists.newArrayList(iter));
  }

  @Test
  public void testScrewsUpOnOutOfOrder() throws Exception
  {
    MergeIterator<Integer> iter = new MergeIterator<>(
        Ordering.<Integer>natural(),
        Lists.newArrayList(
            Arrays.asList(1, 3, 5, 4, 7, 9).iterator(),
            Arrays.asList(2, 8).iterator(),
            Arrays.asList(4, 6).iterator()
        )
    );

    Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5, 4, 6, 7, 8, 9), Lists.newArrayList(iter));
  }
}
