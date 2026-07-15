package org.apache.hop.rest.v1.resources.docs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DocsResourceTest {

  @Test
  public void testRewriteImageUrls() {
    String markdown = "![alt text](../../assets/images/logo.png)";
    String expected = "![alt text](/api/v1/docs/assets/images/logo.png)";
    String result = DocsResource.rewriteImageUrls(markdown);
    assertEquals(expected, result);
  }

  @Test
  public void testRewriteImageUrlsSvg() {
    String markdown = "![icon](assets/icon.svg)";
    String expected = "![icon](/api/v1/docs/assets/icon.svg)";
    String result = DocsResource.rewriteImageUrls(markdown);
    assertEquals(expected, result);
  }

  @Test
  public void testRewriteImageUrlsJpg() {
    String markdown = "![photo](images/photo.jpg)";
    String expected = "![photo](/api/v1/docs/assets/images/photo.jpg)";
    String result = DocsResource.rewriteImageUrls(markdown);
    assertEquals(expected, result);
  }

  @Test
  public void testRewriteImageUrlsMultipleImages() {
    String markdown = "![a](img1.png) and ![b](img2.jpg)";
    String expected = "![a](/api/v1/docs/assets/img1.png) and ![b](/api/v1/docs/assets/img2.jpg)";
    String result = DocsResource.rewriteImageUrls(markdown);
    assertEquals(expected, result);
  }

  @Test
  public void testRewriteImageUrlsNoMatch() {
    String markdown = "![alt](http://example.com/image.png)";
    String result = DocsResource.rewriteImageUrls(markdown);
    assertEquals(markdown, result);
  }

  @Test
  public void testRewriteImageUrlsNoAssetsPrefix() {
    String markdown = "![photo](images/photo.jpg)";
    String expected = "![photo](/api/v1/docs/assets/images/photo.jpg)";
    String result = DocsResource.rewriteImageUrls(markdown);
    assertEquals(expected, result);
  }

  @Test
  public void testRewriteImageUrlsMixedPrefixes() {
    String markdown = "![a](../../assets/img1.png) and ![b](icons/img2.png)";
    String expected =
        "![a](/api/v1/docs/assets/img1.png) and ![b](/api/v1/docs/assets/icons/img2.png)";
    String result = DocsResource.rewriteImageUrls(markdown);
    assertEquals(expected, result);
  }
}
