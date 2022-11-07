package digda.lab.lab.poi;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static <T> List<T> convertToDAO(File file, Class<T> daoClass) {
        Workbook workbook = getWorkbook(file);
        List<T> daoList = new ArrayList<>();
        workbook.sheetIterator().forEachRemaining(sheet -> {
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            Map<String, Integer> finder = createFinder(firstRow, daoClass);

            sheet.rowIterator().forEachRemaining(row -> {
                if(row.getRowNum() != firstRowNum) {
                    T t = rowToDAO(finder, row, daoClass);
                    if(t != null) {
                        daoList.add(t);
                    }
                }
            });
        });
        return daoList;
    }

    private static <T> Map<String, Integer> createFinder(Row row, Class<T> daoClass){
        Map<String, Integer> finder = new HashMap<>();
        Field[] fields = daoClass.getDeclaredFields();
        for(Field field : fields){
            String name;
            try {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                name = annotation.name();
            } catch (Exception e) {
                name = field.getName();
            }
            finder.put(name, -1);
        }

        row.cellIterator().forEachRemaining(cell -> {
            if(cell.getCellType() != CellType.STRING)
                throw new RuntimeException("구분 타입은 String 이여야 합니다.");
            finder.put(cell.getStringCellValue(), cell.getColumnIndex());
        });

        return finder;
    }

    private static <T> T rowToDAO(Map<String, Integer> finder, Row row, Class<T> daoClass) {
        T t;
        try {
            Constructor<T> noArgsConstructor = daoClass.getConstructor(null);
            t = noArgsConstructor.newInstance();
            Field[] fields = daoClass.getDeclaredFields();
            for(Field field: fields) {
                String name;
                try {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    name = annotation.name();
                } catch (Exception e) {
                    name = field.getName();
                }
                Integer index = finder.get(name);
                if(index == -1)
                    continue;
                Cell cell = row.getCell(index);
                field.setAccessible(true);
                field.set(t, matchFields(field, cell));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return t;
    }

    private static Object matchFields(Field field, Cell cell) {
        Object result = null;
        switch (cell.getCellType()){
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            case STRING:
            case FORMULA:
                result = cell.getStringCellValue();
                break;
            case BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if(field.getType().getSimpleName().equals("Long")){
                    result = Double.valueOf(cell.getNumericCellValue()).longValue();
                } else {
                    result = cell.getNumericCellValue();
                }
                break;
        }
        return result;
    }

    private static Workbook getWorkbook(File file) {
        String extension = FilenameUtils.getExtension(file.getName());
        // 예제에서는 xlsx 파일만 치환 예정
        try {
            if (extension.equals("xlsx"))
                return new XSSFWorkbook(file);
            else if (extension.equals("xls")) {
                return new HSSFWorkbook(new FileInputStream(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("파일이 엑셀파일이 아닙니다.");
    }
}
