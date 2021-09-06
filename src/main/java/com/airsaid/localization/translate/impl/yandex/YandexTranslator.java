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
import com.airsaid.localization.translate.lang.Lang;
import com.airsaid.localization.translate.lang.Languages;
import com.airsaid.localization.translate.util.AgentUtil;
import com.airsaid.localization.translate.util.GsonUtil;
import com.airsaid.localization.translate.util.UrlBuilder;
import com.intellij.openapi.util.Pair;
import com.intellij.util.io.RequestBuilder;
import icons.PluginIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author airsaid
 */
public class YandexTranslator extends AbstractTranslator {
  private static final String KEY = "Yandex";
  private static final String HOST_URL_COM = "https://translate.yandex.com";//????
  private static final String HOST_URL = "https://translate.yandex.com";//????
  private static String BASE_URL = HOST_URL.concat("/translate_a/single");//????

  private List<Lang> supportedLanguages;

  @Override
  public @NotNull String getKey() {
    return KEY;
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
    return new UrlBuilder(BASE_URL)
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
}
