/*
 * Copyright 2021 Airsaid. https://github.com/airsaid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.airsaid.localization.translate.impl.yandex;

import com.airsaid.localization.translate.AbstractTranslator;
import com.airsaid.localization.translate.TranslationException;
import com.airsaid.localization.translate.lang.Lang;
import com.airsaid.localization.translate.lang.Languages;
import com.airsaid.localization.translate.util.AgentUtil;
import com.airsaid.localization.translate.util.GsonUtil;
import com.airsaid.localization.translate.util.UrlBuilder;
import com.airsaid.localization.utils.NotificationUtil;
import com.android.tools.idea.io.netty.handler.codec.http.HttpRequest;
import com.esotericsoftware.minlog.Log;
import com.intellij.openapi.util.Pair;
import com.intellij.util.io.HttpRequests;
import com.intellij.util.io.RequestBuilder;
import com.thoughtworks.selenium.Selenium;
import icons.PluginIcons;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.xml.ws.WebServiceClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author airsaid
 */
public class YandexTranslator extends AbstractTranslator {
  private static final String KEY = "Yandex";
  private static final String HOST_URL = "https://translate.yandex.ru/";
  public WebDriver driverToYt = null;

  private List<Lang> supportedLanguages;


  @Override
  public String doTranslate(@NotNull Lang fromLang, @NotNull Lang toLang, @NotNull String text) throws TranslationException {
    List<Lang> supportedLanguages = getSupportedLanguages();
    if (!supportedLanguages.contains(toLang)) {
      throw new TranslationException(fromLang, toLang, text, toLang.getEnglishName() + " is not supported.");
    }
    System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
    if (driverToYt == null){
      driverToYt = new ChromeDriver();
    }
    driverToYt.get("http://someUrl");
//    driver.quit();
    try {
      return "-----" + driverToYt.getTitle();
    } catch (Exception e) { return "-\\"; }

  }









  @NotNull
  @Override
  public String parsingResult(@NotNull Lang fromLang, @NotNull Lang toLang, @NotNull String text, @NotNull String resultText) {
    return null;
  }


  @Override
  @NotNull
  public List<Lang> getSupportedLanguages() {
    if (supportedLanguages == null) {
      List<Lang> languages = Languages.getLanguages();
      supportedLanguages = new ArrayList<>(104);
      for (int i = 1; i <= 104; i++) {
        supportedLanguages.add(languages.get(i));
      }
    }
    return supportedLanguages;
  }

  @Override
  public @NotNull String getRequestUrl(@NotNull Lang fromLang, @NotNull Lang toLang, @NotNull String text) {
    return new UrlBuilder(HOST_URL)
//        .addQueryParameter("sl", fromLang.getCode()) // source language code (auto for auto detection)
//        .addQueryParameter("tl", toLang.getCode()) // translation language
//        .addQueryParameter("client", "gtx") // client of request (guess)
//        .addQueryParameters("dt", "t") // specify what to return
//        .addQueryParameter("dj", "1") // json response with names
//        .addQueryParameter("ie", "UTF-8") // input encoding
//        .addQueryParameter("oe", "UTF-8") // output encoding
//        .addQueryParameter("tk", YandexToken.getToken(text)) // translate token
        .build();
  }

  @Override
  public @NotNull List<Pair<String, String>> getRequestParams(@NotNull Lang fromLang, @NotNull Lang toLang, @NotNull String text) {
    List<Pair<String, String>> params = new ArrayList<>();
    params.add(Pair.create("q", text));
    return params;
  }

  @Override
  public void configureRequestBuilder(@NotNull RequestBuilder requestBuilder) {
    requestBuilder.userAgent(AgentUtil.getUserAgent())
        .tuner(connection -> connection.setRequestProperty("Referer", YandexTranslator.HOST_URL));
  }

  @Override
  public @NotNull String getName() {return "Yandex";}
  @Override
  public @NotNull Icon getIcon() {return PluginIcons.YANDEX_ICON;}
  @Override
  public boolean isNeedAppId() {return false;}
  @Override
  public boolean isNeedAppKey() {return false;}
  @Override
  public @NotNull String getKey() {return KEY;}
}
