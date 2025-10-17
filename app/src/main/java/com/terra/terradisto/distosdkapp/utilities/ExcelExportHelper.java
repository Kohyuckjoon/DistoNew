package com.terra.terradisto.distosdkapp.utilities;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.terra.terradisto.ui.survey_diameter.model.SurveyResult;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExportHelper {
    private static final String TAG = "ExcelExportHelper";

    /**
     * Room DB에서 조회한 측정 데이터를 엑셀 파일로 생성하고 저장
     * @param context 파일 저장을 위한 Context (Application Context 권장)
     * @param surveyDataList SurveyDiameterDao.getAllResults()로 조회한 데이터 목록
     * @return 성공 시 저장된 파일의 절대 경로, 실패 시 null
     * #전체 데이터 내보내기
     */
    public static String makeSurveyExcel(Context context, List<SurveyResult> surveyDataList) {

        final String TEMPLATE_FILE_NAME = "report_template.xls";
//        final String TEMPLATE_FILE_NAME = "report_template.xlsx";
        Workbook workbook = null;
        POIFSFileSystem fs = null;

        if (surveyDataList == null || surveyDataList.isEmpty()) {
            Log.e(TAG, "데이터 목록이 비어있습니다. 엑셀 생성 중지.");
            return null;
        }

        try {
            // 1. 템플릿 파일 읽어 Workbook 객체 생성
            try (java.io.InputStream is = context.getAssets().open(TEMPLATE_FILE_NAME)) {
                Log.d(TAG, "템플릿 파일 InputStream 열기 성공: " + TEMPLATE_FILE_NAME);
                fs = new POIFSFileSystem(is);
                workbook = new HSSFWorkbook(fs);
                Log.d(TAG, "HSSFWorkbook 객체 생성 성공 (템플릿 로드 성공)");
            } catch (Exception e) {
                Log.e(TAG, ".xls 템플릿 파일 로드 중 오류 발생 (POIFSFileSystem 또는 HSSFWorkbook 생성 실패)", e);
                return null;
            }

            final int START_ROW_INDEX = 6;      // 0-based index for Excel Row 7
            final int COL_MATERIAL = 5;         // Column F (재질)
            final int COL_DIAMETER = 6;         // Column G (심도)
            final int COL_FLAT = 7;             // Column H (평면)
            final int COL_DEPTH = 8;            // Column I (심도)

            // 2. 각 SurveyResult에 대해 시트를 생성, data mapping
            for (int i = 0; i < surveyDataList.size(); i++) {
                SurveyResult data = surveyDataList.get(i);
                Sheet sheet;

                // index 0
                if (i == 0) {
                    sheet = workbook.getSheetAt(0);
                } else {
                    sheet = workbook.cloneSheet(0); // sheet copy
                }

                // Sheet Name : mapNumber
                String sheetName = data.mapNumber + (i + 1);
                workbook.setSheetName(i, sheetName);

                Log.e(TAG, sheetName + " 시트에 데이터 맵핑 시작. (ID : " + data.getId() + ")");

                String[] materials = {
                        data.getEtPipMaterialFirst(),
                        data.getEtPipMaterialSecond(),
                        data.getEtPipMaterialThird(),
                        data.getEtPipMaterialFourth()
                };

                String[] diameters = {
                        data.getTvSceneryFirst(),
                        data.getTvScenerySecond(),
                        data.getTvSceneryThird(),
                        data.getTvSceneryFourth()
                };

                // 3. 4개의 파이프 데이터 셋을 4개 행에 Mapping (Excel Row 7, 8, 9, 10)
                for (int rowOffset = 0; rowOffset < 4; rowOffset++) {
                    int currentRowIndex = START_ROW_INDEX + rowOffset;
                    Row row = sheet.getRow(currentRowIndex);

                    if (row == null) {
                        row = sheet.createRow(currentRowIndex);
                    }

                    Cell cellMaterial = row.getCell(COL_MATERIAL);
                    if (cellMaterial == null) {
                        cellMaterial = row.createCell(COL_MATERIAL);
                    }
                    cellMaterial.setCellValue(materials[rowOffset]);

                    Cell cellDiameter = row.getCell(COL_DIAMETER);
                    if (cellDiameter == null) {
                        cellDiameter = row.createCell(COL_DIAMETER);
                    }
                    cellDiameter.setCellValue(diameters[rowOffset]);

                    // [주황색 셀] 검측결과(B) - 평면(H), 심도(I)는 공백("") 처리
                    Cell cellFlat = row.getCell(COL_FLAT);
                    if (cellFlat == null) {
                        cellFlat = row.createCell(COL_FLAT);
                    }
                    cellFlat.setCellValue("");

                    Cell cellDepth = row.getCell(COL_DEPTH);
                    if (cellDepth == null) {
                        cellDepth = row.createCell(COL_DEPTH);
                    }
                    cellDepth.setCellValue("");
                }
            }

            String sFileName = "Disto_Survey_Report_" + System.currentTimeMillis() + ".xls"; // 출력 파일 .xls로 변경

            // 4.파일 저장 로직
            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            if (directory == null) {
                directory = context.getFilesDir();
            }

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File finalFile = new File(directory, sFileName);

            //  3-3. finalFile 객체를 사용하여 파일 이름 쓰기
            try (FileOutputStream fo = new FileOutputStream(finalFile);
                 Workbook w = workbook) {

                w.write(fo);
                Log.e(TAG, "Report file download successfull : " + finalFile.getAbsolutePath());

                // 파일 생성 후 MediaStore 업데이트 (파일 탐색기에 표시되도록)
                android.media.MediaScannerConnection.scanFile(
                        context,
                        new String[] { finalFile.getAbsolutePath() },
                        null,
                        null
                );

                return finalFile.getAbsolutePath();
            } catch (IOException e) {
                Log.e(TAG, "파일 쓰기 오류", e);
                return null;
            }
        } catch (Exception exception) {
            Log.e(TAG, "엑셀 생성 중 치명적인 오류 발생 (템플릿 기반): " + exception.getMessage(), exception);
            return null;
        } finally {
            // Workbook과 POIFSFileSystem 모두 정리
            try {
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                Log.e(TAG, "워크북 닫기 오류", e);
            }
            try {
                if (fs != null) fs.close();
            } catch (IOException e) {
                Log.e(TAG, "POIFSFileSystem 닫기 오류", e);
            }
        }
    }

    public static String makeSurveyExcelBackUp(Context context, List<SurveyResult> surveyDataList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("전체 측정 데이터");
        String sFileName = "Disto_Survey_" + System.currentTimeMillis() + ".xlsx";

        try {
            // 1. Header Create
            // 제공된 필드에 목록 순서대로 헤더를 작성
            String[] headers = {
                    "ID", "도엽 번호", "맨홀 타입",
                    "1번 관경", "2번 관경", "3번 관경", "4번 관경",
                    "1번 재질", "2번 재질", "3번 재질", "4번 재질",
                    "1번 수기 입력", "2번 수기 입력", "3번 수기 입력", "4번 수기 입력"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // 2. Data Row
            int rowNum = 1;
            for (SurveyResult data : surveyDataList) {
                Row row = sheet.createRow(rowNum++);

                // data mapping
                int col = 0;
                row.createCell(col++).setCellValue(data.getId());
                row.createCell(col++).setCellValue(data.getMapNumber());
                row.createCell(col++).setCellValue(data.getManholType());

                // 관경 측정치 (String 또는 double 변환 필요 시 로직 추가)
                row.createCell(col++).setCellValue(data.getTvSceneryFirst());
                row.createCell(col++).setCellValue(data.getTvScenerySecond());
                row.createCell(col++).setCellValue(data.getTvSceneryThird());
                row.createCell(col++).setCellValue(data.getTvSceneryFourth());

                // 재질 선택
                row.createCell(col++).setCellValue(data.getEtPipMaterialFirst());
                row.createCell(col++).setCellValue(data.getEtPipMaterialSecond());
                row.createCell(col++).setCellValue(data.getEtPipMaterialThird());
                row.createCell(col++).setCellValue(data.getEtPipMaterialFourth());

                // 수기 입력치
                row.createCell(col++).setCellValue(data.getEtInputFirst());
                row.createCell(col++).setCellValue(data.getEtInputSecond());
                row.createCell(col++).setCellValue(data.getEtInputThird());
                row.createCell(col++).setCellValue(data.getEtInputFourth());
            }

            // 3. 파일 저장 경로 설정 및 쓰기
            File directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            if (directory == null) {
                // 대체 경로 (files 폴더)는 유지
                directory = context.getFilesDir();
            }

            if (!directory.exists()) {
                // Documents 폴더가 없으면 생성
                directory.mkdirs();
            }
            File file = new File(directory, sFileName);

            // 💡 try-with-resources로 FileOutputStream과 Workbook을 함께 처리
            try (FileOutputStream fo = new FileOutputStream(file);
                 Workbook w = workbook) { // workbook을 여기서 resource로 선언

                w.write(fo);

                Log.e(TAG, "Disto_file_download successfull : " + file.getAbsolutePath());

                // 🚨🚨🚨 추가: PC 접근이 용이한 Download 폴더로 파일 복사 🚨🚨🚨
                copyFileToPublicDownload(context, file);

                Log.e(TAG, "Disto_file_download successfull : " + file.getAbsolutePath());
                return file.getAbsolutePath(); // 성공 경로 반환
            } catch (IOException e) {
                Log.e(TAG, "파일 쓰기 오류", e);
                return null;
            }
        } catch (Exception exception) {
            Log.e(TAG, "엑셀 생성 중 오류 발생", exception);
            return null;
        } finally {
            try {
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                Log.e(TAG, "워크북 닫기 오류", e);
            }
        }
    }

//    public static String makeSurveyExcel(Context context, List<SurveyResult> surveyDataList) {
//        // 템플릿 파일 이름 (프로젝트 내 assets 폴더에 위치)
//        final String TEMPLATE_FILE_NAME = "report_template_test.xlsx";
//        Workbook workbook = null;
//
//        try {
//            // 1. 템플릿 파일 읽어 Workbook 객체 생성
//            try (java.io.InputStream is = context.getAssets().open(TEMPLATE_FILE_NAME)) {
//                workbook = new XSSFWorkbook(is);
//                Log.e(TAG, "tabplate Open : " + TEMPLATE_FILE_NAME);
//            } catch (IOException e) {
//                Log.e(TAG, "템플릿 파일 오류 : " + TEMPLATE_FILE_NAME, e);
//                return null; // 템플릿 로드 실패시 종료
//            }
//
//            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트 사용
//            String sFileName = "Disto_Survey_Report_" + System.currentTimeMillis() + ".xlsx";
//
//            // 2. 데이터 맵핑
//            if (surveyDataList == null || surveyDataList.isEmpty()) {
//                Log.e(TAG, "데이터 목록이 비어있습니다.");
//                return null;
//            }
//
//            SurveyResult data = surveyDataList.get(0); // 첫번째 데이터만 사용 (보고서당 데이터 1개)
//
//            // 3. 고정된 셀 주소에 대한 데이터 입력
//
//            // A. 헤더 /개요
//            // 도엽 번호
//            sheet.getRow(13).getCell(2).setCellValue("test");
//            sheet.getRow(6).getCell(6).setCellValue(data.getTvSceneryFirst());
//
//            // 4. 파일 저장 로직
//            //  4-1. 디렉토리 경로 가져옴
//            File directory = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
//
//            if (directory == null) {
//                directory = context.getFilesDir();
//            }
//
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            //  4-2. 디렉터리와 파일 이름을 합친 최종 File 객체를 생성
//            File finalFile = new File(directory, sFileName);
//
//            //  4-3. finalFile 객체를 사용하여 파일 이름 쓰기
//            try (FileOutputStream fo = new FileOutputStream(finalFile);
//                Workbook w = workbook) {
//
//                w.write(fo);
//                Log.e(TAG, "Report file download successfull : " + finalFile.getAbsolutePath());
//                copyFileToPublicDownload(context, finalFile);
//                return finalFile.getAbsolutePath();
//            } catch (IOException e) {
//                Log.e(TAG, "파일 쓰기 오류", e);
//                return null;
//            }
//        } catch (Exception exception) {
//            Log.e(TAG, "엑셀 생성 중 오류 발생", exception);
//            return null;
//        } finally {
//            try {
//                if (workbook != null) workbook.close();
//            } catch (IOException e) {
//                Log.e(TAG, "워크북 닫기 오류", e);
//            }
//        }
//    }

    /**
     * 내부 저장소 파일을 Download 폴더로 복사하는 헬퍼 메서드 (API 29 이상 호환성 확보 필요)
     * 간단한 테스트를 위해 임시로 File 복사를 사용합니다.
     */
    private static void copyFileToPublicDownload(Context context, File sourceFile) {
        // 공용 다운로드 경로: /storage/emulated/0/Download
        File publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (publicDir == null) {
            Log.e(TAG, "공용 Download 디렉토리를 찾을 수 없음");
            return;
        }

        if (!publicDir.exists()) {
            publicDir.mkdirs();
        }

        File destFile = new File(publicDir, sourceFile.getName());

        try (java.io.InputStream in = new java.io.FileInputStream(sourceFile);
             java.io.OutputStream out = new java.io.FileOutputStream(destFile)) {

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            Log.e(TAG, "File copied to Download: " + destFile.getAbsolutePath());

            // 💡 복사 후, Android 갤러리/파일 탐색기에 업데이트를 알림 (매우 중요)
            android.media.MediaScannerConnection.scanFile(
                    context,
                    new String[] { destFile.getAbsolutePath() },
                    null,
                    null
            );

        } catch (IOException e) {
            Log.e(TAG, "Download 폴더로 파일 복사 실패", e);
        }
    }
}
