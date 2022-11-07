package digda.lab.lab.poi;

public class ExcelDAO {
    private Long id;
    private String kind;
    private String person;
    @ExcelColumn(name = "inOut")
    private String type;
    private String categoryName;
    private String superCategoryName;
    private String etc1;
    private String etc2;
}
