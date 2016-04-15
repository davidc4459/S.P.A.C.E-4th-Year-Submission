/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-02-18 22:11:37 UTC)
 * on 2016-03-11 at 13:06:20 UTC 
 * Modify at your own risk.
 */

package com.example.david.myapplication.backend.competitionPostApi.model;

/**
 * Model definition for CompetitionPost.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the competitionPostApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class CompetitionPost extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String ageGroup;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String competitionType;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String date;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String information;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String organisers;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String post;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String time;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String youthClub;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAgeGroup() {
    return ageGroup;
  }

  /**
   * @param ageGroup ageGroup or {@code null} for none
   */
  public CompetitionPost setAgeGroup(java.lang.String ageGroup) {
    this.ageGroup = ageGroup;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCompetitionType() {
    return competitionType;
  }

  /**
   * @param competitionType competitionType or {@code null} for none
   */
  public CompetitionPost setCompetitionType(java.lang.String competitionType) {
    this.competitionType = competitionType;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDate() {
    return date;
  }

  /**
   * @param date date or {@code null} for none
   */
  public CompetitionPost setDate(java.lang.String date) {
    this.date = date;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getInformation() {
    return information;
  }

  /**
   * @param information information or {@code null} for none
   */
  public CompetitionPost setInformation(java.lang.String information) {
    this.information = information;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOrganisers() {
    return organisers;
  }

  /**
   * @param organisers organisers or {@code null} for none
   */
  public CompetitionPost setOrganisers(java.lang.String organisers) {
    this.organisers = organisers;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPost() {
    return post;
  }

  /**
   * @param post post or {@code null} for none
   */
  public CompetitionPost setPost(java.lang.String post) {
    this.post = post;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTime() {
    return time;
  }

  /**
   * @param time time or {@code null} for none
   */
  public CompetitionPost setTime(java.lang.String time) {
    this.time = time;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getYouthClub() {
    return youthClub;
  }

  /**
   * @param youthClub youthClub or {@code null} for none
   */
  public CompetitionPost setYouthClub(java.lang.String youthClub) {
    this.youthClub = youthClub;
    return this;
  }

  @Override
  public CompetitionPost set(String fieldName, Object value) {
    return (CompetitionPost) super.set(fieldName, value);
  }

  @Override
  public CompetitionPost clone() {
    return (CompetitionPost) super.clone();
  }

}
