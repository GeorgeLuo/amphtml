/*
 *
 * ====================================================================
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
 *  ====================================================================
 */

/*
 * Changes to the original project are Copyright 2019, Verizon Media Inc..
 */

package dev.amp.validator.utils;

import amp.validator.Validator;
import org.testng.Assert;
import org.testng.annotations.Test;

import static amp.validator.Validator.AttrSpec.DispatchKeyType;

/**
 * Test for {@link DispatchKeyUtils}
 *
 * @author GeorgeLuo
 */

public class DispatchKeyUtilsTest {

  @Test
  public void testMakeDispatchKey() {
    Assert.assertEquals(DispatchKeyUtils.makeDispatchKey(DispatchKeyType.NAME_DISPATCH, "custom-element",
      "amp-image-lightbox", ""), "custom-element");
    Assert.assertEquals(DispatchKeyUtils.makeDispatchKey(DispatchKeyType.NAME_VALUE_DISPATCH, "custom-element",
      "amp-image-lightbox", ""), "custom-element\u0000amp-image-lightbox");
    Assert.assertEquals(DispatchKeyUtils.makeDispatchKey(DispatchKeyType.NAME_VALUE_PARENT_DISPATCH, "custom-element",
      "amp-image-lightbox", ""), "custom-element\u0000amp-image-lightbox\u0000true");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testMakeDispatchKeyNoneDispatch() {
    DispatchKeyUtils.makeDispatchKey(DispatchKeyType.NONE_DISPATCH, "custom-element",
      "amp-image-lightbox", "");
  }

  @Test
  public void testGetDispatchKeyForTagSpecOrNone() {
    Validator.AttrSpec.Builder attrSpecBuilder = Validator.AttrSpec.newBuilder();
    attrSpecBuilder.setDispatchKey(DispatchKeyType.NAME_DISPATCH);
    attrSpecBuilder.setName("someAttr");

    Validator.TagSpec.Builder tagSpecBuilder = Validator.TagSpec.newBuilder();
    tagSpecBuilder.addAttrs(attrSpecBuilder.build());

    Assert.assertEquals(DispatchKeyUtils.getDispatchKeyForTagSpecOrNone(tagSpecBuilder.build()), "someAttr");

    attrSpecBuilder = Validator.AttrSpec.newBuilder();
    attrSpecBuilder.setDispatchKey(DispatchKeyType.NAME_VALUE_DISPATCH);
    attrSpecBuilder.setName("someAttr");
    attrSpecBuilder.addValueCasei("someValue");

    tagSpecBuilder = Validator.TagSpec.newBuilder();
    tagSpecBuilder.addAttrs(attrSpecBuilder.build());

    Assert.assertEquals(DispatchKeyUtils.getDispatchKeyForTagSpecOrNone(tagSpecBuilder.build()),
      "someAttr\u0000someValue");

    attrSpecBuilder = Validator.AttrSpec.newBuilder();
    attrSpecBuilder.setDispatchKey(DispatchKeyType.NAME_VALUE_PARENT_DISPATCH);
    attrSpecBuilder.setName("someAttr");
    attrSpecBuilder.addValueCasei("someValue");

    tagSpecBuilder = Validator.TagSpec.newBuilder();
    tagSpecBuilder.addAttrs(attrSpecBuilder.build());
    tagSpecBuilder.setMandatoryParent("mandatoryParent");

    Assert.assertEquals(DispatchKeyUtils.getDispatchKeyForTagSpecOrNone(tagSpecBuilder.build()),
      "someAttr\u0000someValue\u0000true");

  }
}
