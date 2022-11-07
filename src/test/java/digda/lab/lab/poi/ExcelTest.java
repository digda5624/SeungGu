package digda.lab.lab.poi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

public class ExcelTest {

    @Test
    @DisplayName("[success] 엑셀 테스트")
    public void excelTest(){
        // given
        List<ExcelDAO> excelDAOS = ExcelUtils.convertToDAO(new File("/Users/hyeonseung-gu/projects/lab/src/test/java/digda/lab/lab/poi/categoryData.xlsx"), ExcelDAO.class);
        System.out.println("excelDAOS = " + excelDAOS);

        // when

        // then
    }

    @Test
    @DisplayName("[success] reflectionTest")
    public void reflectionTest() throws NoSuchFieldException {
        // given
        Field id = ExcelDAO.class.getDeclaredField("id");
        Class<?> type = id.getType();
        String name = type.getSimpleName();
        System.out.println("name = " + name);
        // when

        // then
    }
}
