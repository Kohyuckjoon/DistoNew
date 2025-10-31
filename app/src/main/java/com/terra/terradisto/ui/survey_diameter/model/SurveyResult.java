package com.terra.terradisto.ui.survey_diameter.model;

import androidx.annotation.NonNull;

public class SurveyResult {
    public int id;
    public String mapNumber;        // 도엽 번호
    public String manholType;       // 맨홀 타입 (1개, 2개, 3개, 4개)

    // === 관경 측정치 (Scenery) 데이터 필드 (4개) ===
    private String tvSceneryFirst;  // 1번 관경
    private String tvScenerySecond; // 2번 관경
    private String tvSceneryThird;  // 3번 관경
    private String tvSceneryFourth; // 4번 관경
    private String tvSceneryFifth; // 5번 관경
    private String tvScenerySixth; // 6번 관경

    // === 재질 선택 (Pipe Material) 데이터 필드 (4개) ===
    private String etPipMaterialFirst; // 1번 재질
    private String etPipMaterialSecond; // 2번 재질
    private String etPipMaterialThird;  // 3번 재질
    private String etPipMaterialFourth; // 4번 재질
    private String etPipMaterialFifth; // 5번 재질
    private String etPipMaterialSixth; // 6번 재질

    // === 수기 입력치 (Input) 데이터 필드 (4개) ===
    private String etInputFirst; // 1번 수기 입력
    private String etInputSecond; // 2번 수기 입력
    private String etInputThird;  // 3번 수기 입력
    private String etInputFourth; // 4번 수기 입력
    private String etInputFifth; // 5번 수기 입력
    private String etInputSixth; // 6번 수기 입력

    // === 비고 입력치 (Note) 데이터 필드 (4개) ===
    private String etNoteFirst; // 1번 비고 입력
    private String etNoteSecond; // 2번 비고 입력
    private String etNoteThird; // 3번 비고 입력
    private String etNoteFourth; // 4번 비고 입력
    private String etNoteFifth; // 5번 비고 입력
    private String etNoteSixth; // 6번 비고 입력

//    public SurveyResult(String mapNumber, String manholType, String tvSceneryFirst, String tvScenerySecond, String tvSceneryThird, String tvSceneryFourth, String etPipMaterialFirst, String etPipMaterialSecond, String etPipMaterialThird, String etPipMaterialFourth, String etInputFirst, String etInputSecond, String etInputThird, String etInputFourth) {
//        this.mapNumber = mapNumber;
//        this.manholType = manholType;
//        this.tvSceneryFirst = tvSceneryFirst;
//        this.tvScenerySecond = tvScenerySecond;
//        this.tvSceneryThird = tvSceneryThird;
//        this.tvSceneryFourth = tvSceneryFourth;
//        this.etPipMaterialFirst = etPipMaterialFirst;
//        this.etPipMaterialSecond = etPipMaterialSecond;
//        this.etPipMaterialThird = etPipMaterialThird;
//        this.etPipMaterialFourth = etPipMaterialFourth;
//        this.etInputFirst = etInputFirst;
//        this.etInputSecond = etInputSecond;
//        this.etInputThird = etInputThird;
//        this.etInputFourth = etInputFourth;
//    }

    public SurveyResult(String mapNumber, String manholType, String tvSceneryFirst, String tvScenerySecond, String tvSceneryThird, String tvSceneryFourth, String tvSceneryFifth, String tvScenerySixth, String etPipMaterialFirst, String etPipMaterialSecond, String etPipMaterialThird, String etPipMaterialFourth, String etPipMaterialFifth, String etPipMaterialSixth, String etInputFirst, String etInputSecond, String etInputThird, String etInputFourth, String etInputFifth, String etInputSixth, String etNoteFirst, String etNoteSecond, String etNoteThird, String etNoteFourth, String etNoteFifth, String etNoteSixth) {
        this.mapNumber = mapNumber;
        this.manholType = manholType;
        this.tvSceneryFirst = tvSceneryFirst;
        this.tvScenerySecond = tvScenerySecond;
        this.tvSceneryThird = tvSceneryThird;
        this.tvSceneryFourth = tvSceneryFourth;
        this.tvSceneryFifth = tvSceneryFifth;
        this.tvScenerySixth = tvScenerySixth;
        this.etPipMaterialFirst = etPipMaterialFirst;
        this.etPipMaterialSecond = etPipMaterialSecond;
        this.etPipMaterialThird = etPipMaterialThird;
        this.etPipMaterialFourth = etPipMaterialFourth;
        this.etPipMaterialFifth = etPipMaterialFifth;
        this.etPipMaterialSixth = etPipMaterialSixth;
        this.etInputFirst = etInputFirst;
        this.etInputSecond = etInputSecond;
        this.etInputThird = etInputThird;
        this.etInputFourth = etInputFourth;
        this.etInputFifth = etInputFifth;
        this.etInputSixth = etInputSixth;
        this.etNoteFirst = etNoteFirst;
        this.etNoteSecond = etNoteSecond;
        this.etNoteThird = etNoteThird;
        this.etNoteFourth = etNoteFourth;
        this.etNoteFifth = etNoteFifth;
        this.etNoteSixth = etNoteSixth;
    }

    // Room 또는 Deserialization을 위해 기본 생성자 추가 권장
    public SurveyResult() {
    }

    // -----------------------------------------------------------------
    // Getter/Setter (Null-Safe 로직 적용)
    // -----------------------------------------------------------------

    @NonNull
    public String getMapNumber() {
        return mapNumber != null ? mapNumber : "";
    }

    public void setMapNumber(String mapNumber) {
        this.mapNumber = mapNumber;
    }

    @NonNull
    public String getManholType() {
        return manholType != null ? manholType : "";
    }

    public void setManholType(String manholType) {
        this.manholType = manholType;
    }

    @NonNull
    public String getTvSceneryFirst() {
        return tvSceneryFirst != null ? tvSceneryFirst : "";
    }

    public void setTvSceneryFirst(String tvSceneryFirst) {
        this.tvSceneryFirst = tvSceneryFirst;
    }

    @NonNull
    public String getTvScenerySecond() {
        return tvScenerySecond != null ? tvScenerySecond : "";
    }

    public void setTvScenerySecond(String tvScenerySecond) {
        this.tvScenerySecond = tvScenerySecond;
    }

    @NonNull
    public String getTvSceneryThird() {
        return tvSceneryThird != null ? tvSceneryThird : "";
    }

    public void setTvSceneryThird(String tvSceneryThird) {
        this.tvSceneryThird = tvSceneryThird;
    }

    @NonNull
    public String getTvSceneryFourth() {
        return tvSceneryFourth != null ? tvSceneryFourth : "";
    }

    public void setTvSceneryFourth(String tvSceneryFourth) {
        this.tvSceneryFourth = tvSceneryFourth;
    }

    @NonNull
    public String getEtPipMaterialFirst() {
        return etPipMaterialFirst != null ? etPipMaterialFirst : "";
    }

    public void setEtPipMaterialFirst(String etPipMaterialFirst) {
        this.etPipMaterialFirst = etPipMaterialFirst;
    }

    @NonNull
    public String getEtPipMaterialSecond() {
        return etPipMaterialSecond != null ? etPipMaterialSecond : "";
    }

    public void setEtPipMaterialSecond(String etPipMaterialSecond) {
        this.etPipMaterialSecond = etPipMaterialSecond;
    }

    @NonNull
    public String getEtPipMaterialThird() {
        return etPipMaterialThird != null ? etPipMaterialThird : "";
    }

    public void setEtPipMaterialThird(String etPipMaterialThird) {
        this.etPipMaterialThird = etPipMaterialThird;
    }

    @NonNull
    public String getEtPipMaterialFourth() {
        return etPipMaterialFourth != null ? etPipMaterialFourth : "";
    }

    public void setEtPipMaterialFourth(String etPipMaterialFourth) {
        this.etPipMaterialFourth = etPipMaterialFourth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtInputFirst() {
        return etInputFirst;
    }

    public void setEtInputFirst(String etInputFirst) {
        this.etInputFirst = etInputFirst;
    }

    public String getEtInputSecond() {
        return etInputSecond;
    }

    public void setEtInputSecond(String etInputSecond) {
        this.etInputSecond = etInputSecond;
    }

    public String getEtInputThird() {
        return etInputThird;
    }

    public void setEtInputThird(String etInputThird) {
        this.etInputThird = etInputThird;
    }

    public String getEtInputFourth() {
        return etInputFourth;
    }

    public void setEtInputFourth(String etInputFourth) {
        this.etInputFourth = etInputFourth;
    }

    public String getTvSceneryFifth() {
        return tvSceneryFifth;
    }

    public void setTvSceneryFifth(String tvSceneryFifth) {
        this.tvSceneryFifth = tvSceneryFifth;
    }

    public String getTvScenerySixth() {
        return tvScenerySixth;
    }

    public void setTvScenerySixth(String tvScenerySixth) {
        this.tvScenerySixth = tvScenerySixth;
    }

    public String getEtPipMaterialFifth() {
        return etPipMaterialFifth;
    }

    public void setEtPipMaterialFifth(String etPipMaterialFifth) {
        this.etPipMaterialFifth = etPipMaterialFifth;
    }

    public String getEtPipMaterialSixth() {
        return etPipMaterialSixth;
    }

    public void setEtPipMaterialSixth(String etPipMaterialSixth) {
        this.etPipMaterialSixth = etPipMaterialSixth;
    }

    public String getEtInputFifth() {
        return etInputFifth;
    }

    public void setEtInputFifth(String etInputFifth) {
        this.etInputFifth = etInputFifth;
    }

    public String getEtInputSixth() {
        return etInputSixth;
    }

    public void setEtInputSixth(String etInputSixth) {
        this.etInputSixth = etInputSixth;
    }

    public String getEtNoteFirst() {
        return etNoteFirst;
    }

    public void setEtNoteFirst(String etNoteFirst) {
        this.etNoteFirst = etNoteFirst;
    }

    public String getEtNoteSecond() {
        return etNoteSecond;
    }

    public void setEtNoteSecond(String etNoteSecond) {
        this.etNoteSecond = etNoteSecond;
    }

    public String getEtNoteThird() {
        return etNoteThird;
    }

    public void setEtNoteThird(String etNoteThird) {
        this.etNoteThird = etNoteThird;
    }

    public String getEtNoteFourth() {
        return etNoteFourth;
    }

    public void setEtNoteFourth(String etNoteFourth) {
        this.etNoteFourth = etNoteFourth;
    }

    public String getEtNoteFifth() {
        return etNoteFifth;
    }

    public void setEtNoteFifth(String etNoteFifth) {
        this.etNoteFifth = etNoteFifth;
    }

    public String getEtNoteSixth() {
        return etNoteSixth;
    }

    public void setEtNoteSixth(String etNoteSixth) {
        this.etNoteSixth = etNoteSixth;
    }
}